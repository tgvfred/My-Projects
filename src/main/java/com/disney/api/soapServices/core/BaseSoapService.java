package com.disney.api.soapServices.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.XMLConstants;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import jxl.read.biff.BiffException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.WordUtils;
import org.apache.http.client.methods.HttpGet;
import org.jaxen.SimpleNamespaceContext;
import org.testng.Reporter;
//import org.testng.Reporter;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.disney.AutomationException;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.github.content.Content;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.test.utils.Randomness;
import com.disney.test.utils.Regex;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.test.utils.Base64Coder;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.VirtualTable;
/*import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlImporter;
import com.eviware.soapui.support.SoapUIException;*/


@SuppressWarnings({ "unused", "deprecation" })
public abstract class BaseSoapService{

	private String strEnvironment = null;
	private String strService = null;
	private String strOperationName = null;
	private String strServiceURL = null;
	private String strResponseURI = null;
	private String intResponseStatusCode = null;
	private String responseTemplate = null;
	private  Document requestDocument = null;
	private  Document responseDocument = null;
	protected   StringBuffer buffer = new StringBuffer();
	private String conversationID = "";
	private String faultString;
	private static String tdmURL = "QAAUTO_";
	//private static String virtualServiceEndPoint;	//DJS - added for grant new access service

	/*****************************
	 **** Start Gets and Sets ****
	 *****************************/

	/**
	 * @summary Takes the current Request XML Document stored in memory and
	 *          return it as a string for simple output
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setRequestDocument}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Will return the current Request XML as a string
	 */
	public String getRequest() {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException("Failed to create XML Transformer");
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		try {
			transformer.transform(new DOMSource(getRequestDocument()), new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(
					"Failed to transform Request XML Document. Ensure XML Document has been successfully loaded.");
		}
		return sw.toString();
	}

	/**
	 * @summary Takes the current Response XML Document stored in memory and
	 *          return it as a string for simple output
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setResponseDocument}
	 * @author Justin Phlegar
	 * @version Created 08/28/2014
	 * @return Will return the current Response XML as a string
	 */
	public String getResponse() {
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException("Failed to create XML Transformer");
		}
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		try {
			transformer.transform(new DOMSource(getResponseDocument()),
					new StreamResult(sw));
		} catch (TransformerException e) {
			throw new RuntimeException(
					"Failed to transform Response XML Document. Ensure XML Document has been successfully loaded.");
		}
		return sw.toString();
	}

	/**
	 * @summary Returns the environment under test. Current accepted
	 *          environments are: <br>
	 *          Dev - Developer server or environment <br>
	 *          Bashful - Integrated Testing Environment <br>
	 *          Sleepy - Functional Environment 1 <br>
	 *          Snow White - Functional Environment 2 <br>
	 *          Doc - Performance Environment 1 <br>
	 *          Evil Queen - Performance Environment 2 <br>
	 *          Grumpy - Pre-Production Staging Environment
	 * @precondition The environment under test needs to be set by
	 *               {@link #setEnvironment(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the environment under test as a String
	 */
	public String getEnvironment() {
		return strEnvironment;
	}

	/**
	 * @summary After a service request has been sent, if the Status code of the
	 *          response has been stored, then this function can be used to
	 *          retrieve that status code
	 * @precondition The Response Status Code needs to be set by
	 *               {@link #setRepsonseStatusCode(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the Status Code of a response as a String
	 */
	public String getResponseStatusCode() {
		return intResponseStatusCode;
	}

	/**
	 * @summary Return the URL of the service under test
	 * @precondition The Service URL needs to be set by
	 *               {@link #setServiceURL(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the Service URL as a String
	 */
	public String getServiceURL() {
		return strServiceURL;
	}
	public String getService() {
		return strService;
	}
	
	public String getOperation() {
		return strOperationName;
	}

	/**
	 * @summary This is used to retrieve the current XML Document as it is in
	 *          memory.
	 * @precondition The XML Document needs to be set by
	 *               {@link #setRequestDocument(Document)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the stored Request XML as a Document
	 */
	protected Document getRequestDocument() {
		return requestDocument;
	}

	/**
	 * @summary This is used to retrieve the current Response Document as it is
	 *          in memory
	 * @precondition The Response Document needs to be set by
	 *               {@link #setResponseDocument(Document)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the stored Response XML as a Document object
	 */
	protected  Document getResponseDocument() {
		return responseDocument;
	}

	/**
	 * @summary This is used to retrieve the current Response URI string as it
	 *          is in memory
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setResponseBaseURI(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Returns the stored Response URI as a String
	 */
	protected String getResponseBaseURI() {
		return strResponseURI;
	}

	/**
	 * @summary This is used to retrieve the current Response XML Template as it
	 *          is in memory
	 * @precondition Requires XML Document to be loaded by using
	 *               {@link #setResponseTemplate(String)}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @return Will return the Response XML Template as a String
	 */
	protected String getResponseTemplate() {
		return responseTemplate;
	}

	/**
	 * @summary Sets the environment under test. Current accepted environments
	 *          are: <br>
	 *          Dev - Developer server or environment <br>
	 *          Bashful - Integrated Testing Environment <br>
	 *          Sleepy - Functional Environment 1 <br>
	 *          Snow White - Functional Environment 2 <br>
	 *          Doc - Performance Environment 1 <br>
	 *          Evil Queen - Performance Environment 2 <br>
	 *          Grumpy - Pre-Production Staging Environment <br>
	 * <br>
	 *          Can be retrieved by {@link #getEnvironment()}
	 * @precondition The environment under test must be one of the environments
	 *               listed above.
	 * @author Justin Phlegar
	 * @version Created 08/28/2014
	 * @param environment
	 *            String: Environment under test
	 */
	protected void setEnvironment(String environment) {
		strEnvironment = Environment.getEnvironmentName(environment);
	}

	/**
	 * @summary Used to store the XML file as a Document object in memory. Can
	 *          be retrieved using {@link #getRequestDocument()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc
	 *            Document XML file of the Request to be stored in memory
	 */
	protected  void setRequestDocument(Document doc) {
		requestDocument = doc;
	}

	/**
	 * @summary Used to store the XML file as a Document object in memory. Can
	 *          be retrieved using {@link #getRequestDocument()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param code
	 *            String: Response error code
	 */
	protected void setRepsonseStatusCode(String code) {
		intResponseStatusCode = code;
	}

	/**
	 * @summary Set a Response XML Document to be stored in memory to be
	 *          retrieved and edited easily. Retrieve XML Document using
	 *          {@link #getResponseDocument()} or as a String using
	 *          {@link #getResponse()}
	 * @precondition Requires valid XML Document to be sent
	 * @author Justin Phlegar
	 * @version Created 08/28/2014
	 * @param doc
	 *            Document: XML file of the Response to be stored in memory
	 */
	protected void setResponseDocument(Document doc) {
		responseDocument = doc;
	}

	/**
	 * @summary Used to store the XML file of the base Soap Response file as a
	 *          Document object in memory. Can be retrieved using
	 *          {@link #getResponseTemplate()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param response
	 *            String: Store the base XML response as a String
	 */
	protected void setResponseTemplate(String response) {
		responseTemplate = response;
	}

	/**
	 * @summary Used to store the main Namespace URI for a Response document.
	 *          Can be retrieved using {@link #getRequestDocument()}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param uri
	 *            String: Main Namespace URI of a response.
	 */
	protected void setResponseBaseURI(String uri) {
		strResponseURI = uri;
	}

	/**
	 * @summary Used to store URL of the Service Under Test in memory. Can be
	 *          retrieved using {@link #getServiceURL())}
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param url
	 *            String: URL Endpoint of the Service Under Test
	 */
	protected void setServiceURL(String url) {
		strServiceURL = url;
	}
	
	protected void setService(String service) {
		strService = service;
	}

	/***************************
	 **** End Gets and Sets ****
	 ***************************/

	/*************************************
	 ********* Public Methods ************
	 *************************************/

	/**
	 * @summary Lazily check the response and return the value of the first
	 *          matching tag
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param tag
	 *            String: Finds and returns the value of the first tag it finds
	 *            with this tag name
	 */
	protected String getFirstNodeValueByTagName(String tag) {
		// Get the response document from memory
		NodeList nList = getResponseDocument().getElementsByTagName(tag);
		return nList.item(0).getTextContent();
	}

	/**
	 * @summary Takes an xpath and return the value if found in the request
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 */
	public String getRequestNodeValueByXPath(String xpath) {
		return XMLTools.getValueByXpath(getRequestDocument(), xpath);
	}

	/**
	 * @summary Takes an xpath and return the value if found in the response
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 */
	public  String getResponseNodeValueByXPath(String xpath) {
		return XMLTools.getValueByXpath(getResponseDocument(), xpath);
	}


	protected String getResponseNodeValueByXPath(String xpath, String[][] namespaces) {
		SimpleNamespaceContext nsContext = new SimpleNamespaceContext();
		for(int nsCounter = 0; nsCounter < namespaces.length; nsCounter++){
			nsContext.addNamespace(namespaces[nsCounter][0], namespaces[nsCounter][1]);
		}
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression expr;
		NodeList nList = null;
		Document doc = getResponseDocument();

		try {
			expr = xPath.compile(xpath);
			nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return nList.item(0).getTextContent();
	}
	
	protected Object[][] getTestScenario(String service, String operation, String scenario){
		String[][] tabArray = null;
		if (service.isEmpty()) throw new RuntimeException("The service is blank");
		if (operation.isEmpty()) throw new RuntimeException("The operation is blank");
		if (scenario.isEmpty()) throw new RuntimeException("The scenario is blank");
		String url = tdmURL + "API_TEST_SCENARIO_DATA_"+service.toUpperCase()+"_" + operation.toUpperCase()+"_" + (scenario.toUpperCase().replace(" ", "_"));
		//System.out.println(url);
		Recordset rs = VirtualTable.compileXML(url, new VirtualTable().getAllTestRows(url));
		//rs.print();
		return removeRowsWithRowNumber(rs.getArray(), 0);
	}
	
	/**
	 * @summary Find and open the excel file sent. If successful, look and find
	 *          the matching scenario name then return its xpath and value data.
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param file
	 *            String: File location either in the project or the hard disk
	 *            path
	 * @param scenario
	 *            String: Name of the scenario to poll data for
	 * @throws FileNotFoundException
	 *             Could not find the file in the path given
	 * @throws BiffException
	 * @throws IOException
	 *             Failed to open the file


	/**
	 * @summary Takes the pre-built Request XML in memory and sends to the
	 *          service
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @throws Exception 
	 * @throws UnsupportedOperationException
	 *             Operation given did not match any of the existing operations
	 * @throws SOAPException
	 * @throws IOException
	 *             Failed to read the Request properly
	 */
	public void sendRequest() {
		TestReporter.logDebug("Entering BaseSoapService#sendRequest()");
		SOAPMessage request = null;
		SOAPMessage response = null;
		SOAPConnectionFactory connectionFactory = null;
		SOAPConnection connection = null;
		SOAPBody responseBody = null;
		MessageFactory messageFactory = null;

		String url;
		
		url = getServiceURL();
		TestReporter.logInfo("Service URL to send request: [" + url + "]");
		TestReporter.logInfo("Set WebVan Cert");
		checkP12();
		
		try {
			TestReporter.logDebug("Initializing Soap Message Factory");
			messageFactory = MessageFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL);
			
			String header = System.getenv("header");
			if(header != null) {				
				header = header.equalsIgnoreCase("Dark") ? "SHADOW" : "";
				header = header.equalsIgnoreCase("Shadow") ? "SHADOW" : "";
				
				if(header.equals("SHADOW")) TestReporter.logInfo("Sending request to Dark Side");
				else TestReporter.logInfo("Sending request to Lite Side");
			}
			else {
				TestReporter.logInfo("Sending request to Lite Side");
				header = "";
			}
			
			// Convert XML Request to SoapMessage
			TestReporter.logInfo("Request to send: \n" + getRequest());
			TestReporter.logDebug("Convertting request to a Soap Message");
			
			request = messageFactory.createMessage(new MimeHeaders(), new StringBufferInputStream(getRequest()));		
			MimeHeaders soapHeader = request.getMimeHeaders();
			soapHeader.addHeader("X-Disney-Internal-PoolOverride", header);		
			
			// Send out Soap Request to the endpoint
			TestReporter.logDebug("Initializing Soap Connection Factory");
			connectionFactory = SOAPConnectionFactory.newInstance();
			
			TestReporter.logDebug("Establishing connection to Service");
			connection = connectionFactory.createConnection();
			
			TestReporter.logDebug("Sending Request to Service");
			response = connection.call(request, url);
			TestReporter.logDebug("Received Response from Service");
			
			// Normalize Response and get the soap body
			response.getSOAPBody().normalize();
			responseBody = response.getSOAPBody();
		} catch (UnsupportedOperationException uoe) {
			throw new RuntimeException(
					"Operation given did not match any operations in the service"
							+ uoe.getCause());
		} catch (SOAPException soape) {
			TestReporter.logAPI(true, soape.getMessage(), this);
			throw new RuntimeException(soape.getMessage(), soape.getCause());
		} catch (IOException ioe) {
			throw new RuntimeException("Failed to read the request properly"
					+ ioe.getCause());
		}

		TestReporter.logInfo("Checking Response for faults");
		// Check for faults and report
		if (responseBody.hasFault()) {
			TestReporter.logInfo("Fault Found");
			SOAPFault newFault = responseBody.getFault();
			faultString = newFault.getFaultString();
			setRepsonseStatusCode(newFault.getFaultCode());
			//TestReporter.logAPI(false, "Soap Response FAULT:  " + newFault.getFaultCode(), this);
		} else {
			TestReporter.logInfo("No fault found");
			setRepsonseStatusCode("200");
		}

		try {
			TestReporter.logDebug("Closing Soap Connection");
			connection.close();

		} catch (SOAPException soape) {
			throw new RuntimeException(soape.getCause());
		}

		TestReporter.logDebug("Convertting Response to XML Document");
		// Covert Soap Response to XML and set it as Response in memory
		Document doc = XMLTools.makeXMLDocument(response);
		doc.normalize();
		setResponseDocument(doc);
		setResponseBaseURI(responseBody.getNamespaceURI());
		TestReporter.logInfo("Response returned: \n" + getResponse());
	//	System.out.println();
	//	System.out.println();
	//	System.out.println("Response");
	//	System.out.println(getResponse());
	//	return response;
	}

	/**
	 * @summary Update multiple XPath nodes or attributes based on the value. The value
	 *          is not limited to simple values, but may also call various
	 *          functions by adding "fx:" as a prefix. Please see
	 *          {@link #handleValueFunction} for more information
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 * @param value
	 *            String: Depending on value given, will update the xpath value,
	 *            attribute, or call a separate function. 
	 *            <br><br><b>Value syntax expressions:</b>
	 *            <br><b>value="abc"</b>  -- Indirectly states that the node value will be set as "abc"
	 *            <br><b>value="value:abc"</b>  -- Directly states that the node value will be set as "abc"
	 *            <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be set as "abc"
	 *            <br><b>value="fx:funcName"</b> -- Will call the function "funcName" to be handled in {@link #handleValueFunction}
	 */
	public void setRequestNodeValueByXPath(String xpath, String value) {
			setRequestNodeValueByXPath(getRequestDocument(),xpath,value);
	}
	
	public void setRequestNodeValueByXPathAndAddNode(String xpath, String value){
		try{
			setRequestNodeValueByXPath(xpath, value);
		}catch(Exception e){
			String node = xpath.substring(xpath.lastIndexOf("/") + 1,  xpath.length());
			xpath = xpath.substring(0, xpath.lastIndexOf("/"));
			if(node.contains("@"))	node = node.substring(0,node.indexOf("@"));
			setRequestNodeValueByXPath(xpath, "fx:AddNode;Node:" + node);
			setRequestNodeValueByXPath(xpath, value);
		}
	}
	/**
	 * @summary Update multiple XPath nodes or attributes based on the value. The value
	 *          is not limited to simple values, but may also call various
	 *          functions by adding "fx:" as a prefix. Please see
	 *          {@link #handleValueFunction} for more information
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 * @param value
	 *            String: Depending on value given, will update the xpath value,
	 *            attribute, or call a separate function. 
	 *            <br><br><b>Value syntax expressions:</b>
	 *            <br><b>value="abc"</b>  -- Indirectly states that the node value will be set as "abc"
	 *            <br><b>value="value:abc"</b>  -- Directly states that the node value will be set as "abc"
	 *            <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be set as "abc"
	 *            <br><b>value="fx:funcName"</b> -- Will call the function "funcName" to be handled in {@link #handleValueFunction}
	 */
	public void setRequestNodeValueByXPath(Object[][] scenarios) {
		for (int x = 0; x < scenarios.length; x++) {
			try{
			setRequestNodeValueByXPath(getRequestDocument(),scenarios[x][0].toString(),
					scenarios[x][1].toString());
			}catch(XPathNotFoundException | XPathNullNodeValueException e){}
		}
	}

	/*

	/**
	 * @summary Main validation function that validates and reports findings
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc Document: XML Document to evalute
	 * @param xpath String: xpath to evaluate
	 * @param value String: Depending on value given, will validate the xpath node or attribute value,
	 *  		  	<br><br><b>Value syntax expressions:</b>
	 *	            <br><b>value="abc"</b>  -- Indirectly states that the node value will be validated and expected to be "abc"
	 *  	        <br><b>value="value:abc"</b>  -- Directly states that the node value will be validated and expected to be "abc"
	 *      	    <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be validated and expected to be "abc"
	 *            
	 *//*
	private boolean validateNodeValueByXPath(Document doc, String xpath, String regexValue) {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression expr;
		NodeList nList = null;
		String xPathValue = "";
		String errorMessage = "";
		
		//Find the node based on xpath expression
		try {
			expr = xPath.compile(xpath);
			nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		}catch (XPathExpressionException xpe) {
			errorMessage = "Failed to build xpath. Please check format.";
			//throw new RuntimeException("Xpath evaluation failed with xpath [ " + xpath + " ] ", xpe.getCause());	
		}
		
		//Ensure an element was found, if not then throw error and fail
		if (nList.item(0) == null && errorMessage.isEmpty()) {
			errorMessage = "No xpath was found with the given path ";
			//throw new RuntimeException("No xpath was found with the given path ");
		}
		
		if (errorMessage.isEmpty()){
			//Handle prefix types
			if (regexValue.trim().toLowerCase().contains("value:")) {
				
				//Node value was specifically stated. Find value of node to validate based on xpath
				regexValue = regexValue.substring(regexValue.indexOf(":") + 1,
						regexValue.length()).trim();
				xPathValue = nList.item(0).getTextContent();
			} else if (regexValue.trim().toLowerCase().contains("attribute")) {
				//Node attribute was specifically stated. Find attribute of node to validate based on xpath and attribute name
				regexValue = regexValue.substring(0,
						regexValue.indexOf(":") + 1).trim();
				String[] attributeParams = regexValue.split(",");
				NamedNodeMap attr = nList.item(0).getAttributes();
				Node nodeAttr = attr.getNamedItem(attributeParams[0]);
				xPathValue = nodeAttr.getTextContent();
			} else {
				//Default path. Get node value based on xpath
				xPathValue = nList.item(0).getTextContent();
			}
		}

		Regex regex = new Regex();

		//Validate expected value with actual value and report in html table 
		if(!errorMessage.isEmpty()){
			buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
					+ xpath + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ regexValue + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ errorMessage + "</td>");
			buffer.append("<td style='width: 100px; color: red; text-align: center;'><b>Fail</b></td></tr>");
		}else if (regex.match(regexValue, xPathValue)) {		
			buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
					+ xpath + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ regexValue + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ xPathValue + "</td>");
			buffer.append("<td style='width: 100px; color: green; text-align: center;'><b>Pass</b></td></tr>");
		} else {
			buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
					+ xpath + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ regexValue + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ xPathValue + "</td>");
			buffer.append("<td style='width: 100px; color: red; text-align: center;'><b>Fail</b></td></tr>");
		}
		//return boolean
		return regex.match(regexValue, xPathValue);
	}*/

	/**
	 * @summary Validate XML Repsonse and reports findings
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc Document: XML Document to evalute
	 * @param xpath String: xpath to evaluate
	 * @param value String: Depending on value given, will validate the xpath node or attribute value,
	 *  		  	<br><br><b>Value syntax expressions:</b>
	 *	            <br><b>value="abc"</b>  -- Indirectly states that the node value will be validated and expected to be "abc"
	 *  	        <br><b>value="value:abc"</b>  -- Directly states that the node value will be validated and expected to be "abc"
	 *      	    <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be validated and expected to be "abc"
	 *            
	 */
	public boolean validateNodeValueByXPath(Document doc, Object[][] scenarios) {
		boolean status = true;
		buffer.setLength(0);
		buffer.append("<table border='1' width='100%'>");
		buffer.append("<tr><td style='width: 100px; color: black; text-align: center;'><b>XPath</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Regex</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Value</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Status</b></td></tr>");
		for (int x = 0; x < scenarios.length; x++) {
			if (!validateNodeValueByXPath(doc, scenarios[x][0].toString(),
					scenarios[x][1].toString())) {
				status = false;
			}
		}
		buffer.append("</table>");
		Reporter.log(buffer.toString()+ "<br/>");
		return status;
	}

	/*public boolean validateResponse(String resourcePath, String scenario) {
		return validateNodeValueByXPath(getResponseDocument(),
				getTestScenario(resourcePath, scenario));
	}*/

	public boolean validateResponse(String service, String operation, String scenario) {
		return validateNodeValueByXPath(getResponseDocument(),
				getTestScenario(service,operation, scenario));
	}
	
	protected void generateServiceContext() {

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression expr;
		NodeList nList = null;
		String xpath = "//serviceContext";
		Document doc = getRequestDocument();
		try {
			expr = xPath.compile(xpath);
			nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (nList.item(0) == null) {
			xpath = "//context";
			try {
				expr = xPath.compile(xpath);
				nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Set Context Address Role
		setRequestNodeValueByXPath(getRequestDocument(), xpath + "/addressRole", "UNKNOWN");

		conversationID = Randomness.generateConversationId();
//		System.out.println(conversationID);
		// Set Context Conversation Id
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/conversationId",
				conversationID);

		// Set Context Message Id
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/messageId",
				Randomness.generateMessageId());

		// Set Context Party 1 Id
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/party1Id", "UNKNOWN");

		// Set Context Party 1 Type
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/party1Type", "UNKNOWN");

		// Set Context Party 2 Id
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/party2Id", "UNKNOWN");

		// Set Context Party 2 Type
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/party2Type", "UNKNOWN");

		// Set Context Path Host Name
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/pathHostName", "UNKNOWN");

		// Set Context Path Id
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/pathId", "UNKNOWN");

		// Set Context Principle Id
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/principalId", "UNKNOWN");

		// Set Context Principal Method
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/principalMethod", "UNKNOWN");

		// Set Context Principal Role
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/principalRole", "UNKNOWN");

		// Set Context Principal URI
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/principalUri", "UNKNOWN");

		// Set Context Principal Value
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/principalValue", "UNKNOWN");

		// Set Context Request Timestamp
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/requestTimeStamp",
				Randomness.generateCurrentXMLDatetime());

		// Set Context Address Role
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/toAddressRole", "UNKNOWN");

		// Set Context User Name
		setRequestNodeValueByXPath(getRequestDocument(),xpath + "/userName", "AutoJUnit.user");
	}

	protected String sendGetRequest(String strUrl) throws Exception {

		StringBuilder rawResponse = new StringBuilder();

		URL urlRequest = null;

		try {
			urlRequest = new URL(strUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		HttpURLConnection conn = (HttpURLConnection) urlRequest
				.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");

		InputStream stream = conn.getInputStream();
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(
				stream));

		String buffer = "";
		while ((buffer = bufferReader.readLine()) != null) {
			rawResponse.append(buffer);
		}

		return rawResponse.toString();
	}

	protected void setEnvironmentServiceURL(String service, String environment) {
		setEnvironment(environment);
		setService(service);
		// include the %20 as whitespace for URL format
		if (getEnvironment().toLowerCase().contentEquals("snow white") || getEnvironment().toLowerCase().contentEquals("snow_white")) {
			setEnvironment("Snow%20White");
		} else if (getEnvironment().toLowerCase().contentEquals("evil queen") || getEnvironment().toLowerCase().contentEquals("evil_queen")) {
			setEnvironment("Evil%20Queen");
		}
		String url = "http://fldcvpswa6204.wdw.disney.com/EnvSrvcEndPntRepository/rest/retrieveServiceEndpoint/{environment}/{service}";
		String responseXML = "";
		Document responseDoc = null;
		url = url.replace("{environment}", WordUtils.capitalize(getEnvironment()));
		url = url.replace("{service}", service);

		try { 
			responseXML = sendGetRequest(url);
			responseXML = removeTDMXMLAttributes(responseXML);
			responseDoc = XMLTools.makeXMLDocument(responseXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setServiceURL(getFirstNodeValueByTagName(responseDoc, "endPoint"));

	}
	


	protected void setEnvironmentServiceURL(String service, String environment, String url) {
		setEnvironment(environment);
		setService(service);
		setServiceURL(url);
	}
	
	//DJS - This was added as proof of concept for endpoints utilizing Service Virtualization
	
	public void setEnvironmentServiceURLSV(String service, String environment) {
		setEnvironment(environment);

		// include the %20 as whitespace for URL format
		if (getEnvironment().toLowerCase().contentEquals("snow white") || getEnvironment().toLowerCase().contentEquals("snow_white")) {
			setEnvironment("Snow%20White");
		} else if (getEnvironment().toLowerCase().contentEquals("evil queen") || getEnvironment().toLowerCase().contentEquals("evil_queen")) {
			setEnvironment("Evil%20Queen");
		}
		String url = "http://fldcvpswa6204.wdw.disney.com/EnvSrvcEndPntRepository/rest/retrieveServiceEndpoint/{environment}/{service}";
		String responseXML = "";
		Document responseDoc = null;
		url = url.replace("{environment}", WordUtils.capitalize(getEnvironment()));
		url = url.replace("{service}", service);

		try { 
			responseXML = sendGetRequest(url);
			responseXML = removeTDMXMLAttributes(responseXML);
			responseDoc = XMLTools.makeXMLDocument(responseXML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setServiceURL(getFirstNodeValueByTagName(responseDoc, "endPoint") + "?wsdl");

	}
	
	protected String buildRequestFromWSDL(String operationName){
		String token = "P2FjY2Vzc190b2tlbj00ZmExNzBlZjE3NTA2MTM1ZGJkZTFiMzdjYjlhZDRlNDQ1MjVjN2Vm";
		strOperationName = operationName;
		String url = "https://github.disney.com/api/v3/repos/phlej001/TestDataOnDemand/contents/TestDataOnDemand/soap-xml-storage/{environment}/{service}/{operation}.xml" +Base64Coder.decodeString(token);
		url = url.replace("{environment}", WordUtils.capitalize(getEnvironment().replace("_CM",""))); 
		url = url.replace("{service}", getService());
		url = url.replace("{operation}", getOperation());
		HttpGet request = new HttpGet(url);
		Content content =  new RestService().sendRequest(request).mapJSONToObject(Content.class);
		String rawRequest= new String(Base64.decodeBase64(content.getContent().getBytes() ));
	
		return rawRequest;
	}
	
	/*protected String buildRequestFromWSDL(String service, boolean oldSchool) {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setUseParentHandlers(false);
		logger.setLevel(Level.OFF);
		
		
		checkP12();

		WsdlProject project = null;
		try {
			project = new WsdlProject();
		} catch (XmlException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SoapUIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		WsdlInterface[] wsdls = null;
		try {
			nwsdl = WsdlInterfaceFactory.importWsdl(project, getServiceURL(), true )[0];
		} catch (SoapUIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			
			wsdls = WsdlImporter.importWsdl(project, getServiceURL().replace("https", "http"));
		//	wsdls = WsdlImporter.importWsdl(project, "http://10.8.164.34/wdpr/pmsr/sleepy/ChargeAccountServiceV2?wsdl");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WsdlInterface wsdl = wsdls[0];
		WsdlOperation op = wsdl.getOperationByName(service);
		setResponseTemplate(op.createResponse(true));
		return op.createRequest(true);
	}*/

	protected void removeComments() {
		setRequestDocument((Document) XMLTools
				.removeComments(getRequestDocument()));
	}

	protected void removeWhiteSpace() {
		setRequestDocument(XMLTools.removeWhiteSpace(getRequestDocument()));
	}

	public static String getFirstNodeValueByTagName(Document doc, String tag) {
		NodeList nList = doc.getElementsByTagName(tag);
		return nList.item(0).getTextContent();
	}


	protected String removeTDMXMLAttributes(String xml) {
		xml = xml
				.replace(
						"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>",
						"");
		xml = xml.trim();
		return xml;
	}

	/*private boolean validateRepsonseXML() {
		Document doc = getResponseDocument();
		String uri = getResponseBaseURI();

		if (doc == null) {
			throw new RuntimeException("Reponse document was null.");
		} else if (uri.isEmpty()) {
			throw new RuntimeException("URI was null.");
		}

		return XMLTools.validateXMLSchema(uri, doc);
	}*/
	
	public String getConversationID(){
		return conversationID;
	}
	
	
	private void checkP12(){
		KeyStore clientStore;
		TestReporter.logDebug("Entering BaseSoapRequest#checkP12");
		try {

			TestReporter.logDebug("Creating Keystore Instance");
			clientStore = KeyStore.getInstance("PKCS12");

			String token = "P2FjY2Vzc190b2tlbj00ZmExNzBlZjE3NTA2MTM1ZGJkZTFiMzdjYjlhZDRlNDQ1MjVjN2Vm";
			String url = "https://github.disney.com/api/v3/repos/WDPRO-QA/lilo/contents/end_to_end/CommerceFlow/src/main/resources/com/disney/certificates/webvan/TWDC.WDPR.Passport.QA.p12" +Base64Coder.decodeString(token);
			HttpGet request = new HttpGet(url);
			Content content =  new RestService().sendRequest(request).mapJSONToObject(Content.class);
			String downloadURL = content.getDownloadUrl();
			
			TestReporter.logDebug("Retrieving WebVan Certificate");
			InputStream is = new URL(downloadURL).openStream();
			
		
			TestReporter.logDebug("Loading WebVan Certifcate into Keystore");
			clientStore.load(is, "Disney123".toCharArray());

			TestReporter.logDebug("Unlocking WebVan cert with key");
	        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	        kmf.init(clientStore, "Disney123".toCharArray());
	        KeyManager[] kms = kmf.getKeyManagers();
	       
	       // String path = getClass().getResource("/com/disney/certificates/webvan/cacerts").getPath();
	        TestReporter.logDebug("Retrieving CA Cert Store");
	        url = "https://github.disney.com/api/v3/repos/WDPRO-QA/lilo/contents/end_to_end/CommerceFlow/src/main/resources/com/disney/certificates/webvan/cacerts" +Base64Coder.decodeString(token);
			request = new HttpGet(url);
			content =  new RestService().sendRequest(request).mapJSONToObject(Content.class);
			downloadURL = content.getDownloadUrl();
	        InputStream isCA = new URL(downloadURL).openStream();
	        
	        TestReporter.logDebug("Unlocking CA Cert Store with key");
	        KeyStore trustStore = KeyStore.getInstance("JKS");
	        trustStore.load(isCA, "changeit".toCharArray());
	
	        TestReporter.logDebug("Generating Trust Manager");
	        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	        
	        TestReporter.logDebug("Loading CA Cert Store into Trust Manager");
	        tmf.init(trustStore);
	        TrustManager[] tms = tmf.getTrustManagers();
	
	        TestReporter.logDebug("Generating SSL Context");
	        SSLContext sslContext = null;
	        sslContext = SSLContext.getInstance("TLS");
	        
	        TestReporter.logDebug("Loading WebVan Cert into Trust Manager with SSL Context");
	        sslContext.init(kms, tms, new SecureRandom());
	
	        TestReporter.logDebug("Establishing initial SSL Socket");
	        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException | KeyManagementException e) {
			e.printStackTrace();
		}
		TestReporter.logDebug("Exitting BaseSoapRequest#checkP12");
    }
	
	public String getFaultString(){
		if(faultString == null || faultString.isEmpty()) {
			faultString = "No Fault Found in Response";
		}
		return faultString;
	}
	

	public String getServiceExceptionErrorMessage(){
		String error= "";
		try{
			error = getResponseNodeValueByXPath("//ServiceException/applicationErrors/dynamicErrorMessage");
		}catch(XPathNotFoundException xpe){}
		
		return error;
	}
	
	public String getServiceExceptionApplicationFaultCode(){
		String error= "";
		try{
			error = getResponseNodeValueByXPath("//ServiceException/applicationErrors/applicationFaultCode/code");
		}catch(XPathNotFoundException xpe){}
		
		return error;
	}

	public String getServiceExceptionApplicationFaultMessage(){
		String error= "";
		try{
			error = getResponseNodeValueByXPath("//ServiceException/applicationErrors/applicationFaultCode/message");
		}catch(XPathNotFoundException xpe){}
		
		return error;
	}
	public String getServiceExceptionApplicationFaultModule(){
		String error= "";
		try{
			error = getResponseNodeValueByXPath("//ServiceException/applicationErrors/applicationFaultCode/moduleName");
		}catch(XPathNotFoundException xpe){}
		
		return error;
	}
	
	/*public static boolean validateXMLSchema(String uri, Document doc){
        
	       try {
	           SchemaFactory factory = 
	        		   SchemaFactory.newInstance(XMLConstants.DEFAULT_NS_PREFIX);	           
	           Schema schema = factory.newSchema(new URL(uri));
	           Validator validator = schema.newValidator();
	           validator.validate(new DOMSource(doc));
	       } catch (IOException | SAXException e) {
	           System.out.println("Exception: "+e.getMessage());
	           return false;
	       }
	       return true;
	   }*/

	/**
	 * @summary Main validation function that validates and reports findings
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc Document: XML Document to evalute
	 * @param xpath String: xpath to evaluate
	 * @param value String: Depending on value given, will validate the xpath node or attribute value,
	 *  		  	<br><br><b>Value syntax expressions:</b>
	 *	            <br><b>value="abc"</b>  -- Indirectly states that the node value will be validated and expected to be "abc"
	 *  	        <br><b>value="value:abc"</b>  -- Directly states that the node value will be validated and expected to be "abc"
	 *      	    <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be validated and expected to be "abc"
	 *            
	 */
	protected boolean validateNodeValueByXPath(Document doc, String xpath, String regexValue) {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression expr;
		NodeList nList = null;
		String xPathValue = "";
		String errorMessage = "";
		
		//Find the node based on xpath expression
		try {
			expr = xPath.compile(xpath);
			nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		}catch (XPathExpressionException xpe) {
			errorMessage = "Failed to build xpath [ " + xpath + " ]. Please check format.";
			//throw new RuntimeException("Xpath evaluation failed with xpath [ " + xpath + " ] ", xpe.getCause());	
		}
		
		//Ensure an element was found, if not then throw error and fail
		if (nList.item(0) == null && errorMessage.isEmpty()) {
			errorMessage = "No xpath was found with the path [ " + xpath + " ] ";
			//throw new RuntimeException("No xpath was found with the path [ " + xpath + " ] ");
		}
		
		if (errorMessage.isEmpty()){
			//Handle prefix types
			if (regexValue.trim().toLowerCase().contains("value:")) {
				
				//Node value was specifically stated. Find value of node to validate based on xpath
				regexValue = regexValue.substring(regexValue.indexOf(":") + 1,
						regexValue.length()).trim();
				xPathValue = nList.item(0).getTextContent();
			} else if (regexValue.trim().toLowerCase().contains("attribute")) {
				//Node attribute was specifically stated. Find attribute of node to validate based on xpath and attribute name
				regexValue = regexValue.substring(0,
						regexValue.indexOf(":") + 1).trim();
				String[] attributeParams = regexValue.split(",");
				NamedNodeMap attr = nList.item(0).getAttributes();
				Node nodeAttr = attr.getNamedItem(attributeParams[0]);
				xPathValue = nodeAttr.getTextContent();
			} else {
				//Default path. Get node value based on xpath
				xPathValue = nList.item(0).getTextContent();
			}
		}

		Regex regex = new Regex();

		//Validate expected value with actual value and report in html table 
		if(!errorMessage.isEmpty()){
			buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
					+ xpath + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ regexValue + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ errorMessage + "</td>");
			buffer.append("<td style='width: 100px; color: red; text-align: center;'><b>Fail</b></td></tr>");
		}else if (regex.match(regexValue, xPathValue)) {		
			buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
					+ xpath + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ regexValue + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ xPathValue + "</td>");
			buffer.append("<td style='width: 100px; color: green; text-align: center;'><b>Pass</b></td></tr>");
		} else {
			buffer.append("<tr><td style='width: 100px; color: black; text-align: left;'>"
					+ xpath + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ regexValue + "</td>");
			buffer.append("<td style='width: 100px; color: black; text-align: center;'>"
					+ xPathValue + "</td>");
			buffer.append("<td style='width: 100px; color: red; text-align: center;'><b>Fail</b></td></tr>");
		}
		//return boolean
		return regex.match(regexValue, xPathValue);
	}

	/**
	 * @summary Validate XML Repsonse and reports findings
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param doc Document: XML Document to evalute
	 * @param xpath String: xpath to evaluate
	 * @param value String: Depending on value given, will validate the xpath node or attribute value,
	 *  		  	<br><br><b>Value syntax expressions:</b>
	 *	            <br><b>value="abc"</b>  -- Indirectly states that the node value will be validated and expected to be "abc"
	 *  	        <br><b>value="value:abc"</b>  -- Directly states that the node value will be validated and expected to be "abc"
	 *      	    <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be validated and expected to be "abc"
	 *            
	 */
/*	public boolean validateNodeValueByXPath(Document doc, Object[][] scenarios) {
		boolean status = true;
		buffer.append("<table border='1' width='100%'>");
		buffer.append("<tr><td style='width: 100px; color: black; text-align: center;'><b>XPath</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Regex</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Value</b></td>");
		buffer.append("<td style='width: 100px; color: black; text-align: center;'><b>Status</b></td></tr>");
		for (int x = 0; x < scenarios.length; x++) {
			if (!validateNodeValueByXPath(doc, scenarios[x][0].toString(),
					scenarios[x][1].toString())) {
				status = false;
			}
		}
		buffer.append("</table>");
		Reporter.log(buffer.toString());
		return status;
	}
	*/

	/**
	 * @summary Update an XPath node or attribute based on the value. The value
	 *          is not limited to simple values, but may also call various
	 *          functions by adding "fx:" as a prefix. Please see
	 *          {@link #handleValueFunction} for more information
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath
	 *            String: xpath to evaluate
	 * @param value
	 *            String: Depending on value given, will update the xpath value,
	 *            attribute, or call a separate function. 
	 *            <br><br><b>Value syntax expressions:</b>
	 *            <br><b>value="abc"</b>  -- Indirectly states that the node value will be set as "abc"
	 *            <br><b>value="value:abc"</b>  -- Directly states that the node value will be set as "abc"
	 *            <br><b>value="attribute:attrName,abc"</b>  -- Directly states that the node attribute "attrName" will be set as "abc"
	 *            <br><b>value="fx:funcName"</b> -- Will call the function "funcName" to be handled in {@link #handleValueFunction}  
	 * @throws XPathExpressionException
	 *             Could not match evaluate xPath
	 * @throws RuntimeException
	 *             Could not match xPath to a node, element or attribute
	 */
	protected  void setRequestNodeValueByXPath(Document doc, String xpath, String value) {
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression expr;
		NodeList nList = null;
		//Document doc = getRequestDocument();
	//	Element element = (Element) doc.getElementsByTagName("pmtInfo");
		//Find the node based on xpath expression
		try {
			expr = xPath.compile(xpath);
			nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		}catch (XPathExpressionException xpe) {		
			throw new RuntimeException("Xpath evaluation failed with xpath [ " + xpath + " ] ", xpe.getCause());	
		}
		
		//Ensure an element was found, if not then throw error and fail
		if (nList.item(0) == null){	
			throw new XPathNotFoundException(xpath);
		}
		
		//System.out.println("XPATH: " + xpath + " || VALUE: " +value);
		
		if( value == null || value.isEmpty()){
			throw new XPathNullNodeValueException(xpath);
		}
		
		//Handle prefix types
		if (value.trim().toLowerCase().contains("value:")) {
			
			//Node value was specifically stated. Update node value
			value = value.substring(value.indexOf(":") + 1, value.length())
					.trim();
			
			//Handle function if necessary
			if (value.contains("fx:")) {
				value = handleValueFunction(doc, value, xpath);
			}
			
			//If a prior function call previous updated the XML, nothing more is needed.
			if (!value.equalsIgnoreCase("XMLUpdated")) {
				nList.item(0).setTextContent(value);
			}

		} else if (value.trim().toLowerCase().contains("addattribute")) {

			//Node attribute was specifically stated. Determine the attribute to find, then update the attribute 
			//value = value.substring(value.indexOf(":") + 1,value.length()).trim();
			//String[] attributeParams = value.split(";");

			//Handle function if necessary
			if (value.contains("fx:")) {
				value = handleValueFunction(doc, value, xpath);
			}
			
			//If a prior function call previous updated the XML, nothing more is needed.			
			if (value.equalsIgnoreCase("XMLUpdated")) {
				//Find the attribute and set for editting
				NamedNodeMap attr = nList.item(0).getAttributes();
				Node nodeAttr = attr.getNamedItem(value);
				
				if (!value.equalsIgnoreCase("XMLUpdated")) {
				//Update attribute
					nodeAttr.setTextContent(value);
				}
			}
		} else {
			//Default path. Update node value based on xpath
			//Handle function if necessary
			if (value.contains("fx:")) {
				value = handleValueFunction(doc, value, xpath);
			}
			
			//If a prior function call previous updated the XML, nothing more is needed.
			if (!value.equalsIgnoreCase("XMLUpdated")) {
				if(value.equalsIgnoreCase("true")) value = "true";
				else if(value.equalsIgnoreCase("false")) value = "false";
				else if(xpath.replace(" ", "").equalsIgnoreCase("groupcode")){
					if (value.equals("1825") || value.equals("1905") || value.equals("4250") || value.equals("4268") ) value = "0" + value;
				}
				nList.item(0).setTextContent(value);
			}
		}
		
		//Store changes
		setRequestDocument(doc);		
	}

	

	/**
	 * @summary Call functions during setting of the xpath
	 * @author Justin Phlegar
	 * @version Created: 08/28/2014
	 * @param xpath String: Xpath to run the function on 
	 * @param function String: function to call
	 * 					<br><br><b>Supported Functions:</b>
	 * 					<br><b>value="fx:addnode"</b>  -- Add a new node at Xpath position. Expects "Node:nodeName" where nodeName will be the name given to the node
	 * 		            <br><b>value="fx:getdatetime"</b>  -- Set date and time in a format accepted by XML. Expects "DaysOut:x" where x is the number of days out
	 * 					<br><b>value="fx:getdate"</b>  -- Set date in a format accepted by XML. Expects "DaysOut:x" where x is the number of days out 					
	 *  				<br><b>value="fx:removenode"</b>  -- Remove a node at Xpath position.
	 *   				<br><b>value="fx:randomnumber"</b>  -- Set a string of random numbers. Expects "Node:x" where x is the length of the string to output
	 *    				<br><b>value="fx:randomstring"</b>  -- Set a string of random characters. Expects "Node:x" where x is the length of the string to output
	 *        			<br><b>value="fx:randomalphanumeric"</b>  -- Set a string of random numbers and characters. Expects "Node:x" where x is the length of the string to output 		  	
	 */
	private String handleValueFunction(Document doc, String function, String xpath) {
		String[] params = function.split(";");
		String command = params[0];
		String[] length = new String[2];
		String[] tagName = new String[2];
		String[] daysOut = new String[2];

		switch (command.toLowerCase()) {
		case "fx:getdatetime":
			daysOut = params[1].split(":");
			if (daysOut[0].trim().equalsIgnoreCase("DaysOut")) {
				return Randomness.generateCurrentXMLDatetime(Integer.parseInt(daysOut[1]));
			} else {
				// report error
			}
			
		case "fx:getdate":
			daysOut = params[1].split(":");
			if (daysOut[0].trim().equalsIgnoreCase("DaysOut")) {
				return Randomness.generateCurrentXMLDate(Integer.parseInt(daysOut[1]));
			} else{
				// report error 
			}
		case "fx:addnode":
			String tag = params[1].replace("node:", "").replace("Node:", "");
//			tagName = params[1].split(":");
//			if (tagName[0].trim().equalsIgnoreCase("Node")) {
//				if(tagName.length > 2){
//					for(int i = 2; i < tagName.length; i++){
//						tagName[1] = tagName[1] + ":" + tagName[i];
//					}
//				}
//				setRequestDocument(XMLTools.addNode(doc,tagName[1].trim(), xpath));
//			} else {
//				// report error
//			}
			setRequestDocument(XMLTools.addNode(doc,tag.trim(), xpath));
			return "XMLUpdated";
			
		case "fx:addnodes":
			tagName = params[1].split(":");
			if (tagName[0].toLowerCase().trim().contains("node")) {
				String[] nodes = tagName[1].split("/");
				String appendedXpath = xpath;
				for(String node : nodes){
					setRequestDocument(XMLTools.addNode(doc,node.replaceAll("\\[(.*?)\\]",""), appendedXpath));
					appendedXpath += "/"+node;
				}
			} else {
				// report error
			}
			return "XMLUpdated";

		case "fx:addattribute":
			tagName = params[1].split(":",2);
			if (tagName[0].trim().equalsIgnoreCase("attribute")) {
				setRequestDocument(XMLTools.addAttribute(doc,tagName[1].trim(), xpath));
			} else {
				// report error
			}
			return "XMLUpdated";
			
		case "fx:addnamespace":
			tagName = params[1].split(":",2);
			if (tagName[0].trim().equalsIgnoreCase("namespace")) {
				setRequestDocument(XMLTools.addNamespace(doc,tagName[1].trim(), xpath));
			} else {
				// report error
			}
			return "XMLUpdated";


		case "fx:removenode":
			setRequestDocument(XMLTools.removeNode(doc, xpath));
			return "XMLUpdated";
			
		case "fx:removeattribute":
			String attribute = xpath.substring(xpath.lastIndexOf("@") + 1, xpath.length());
			xpath = xpath.substring(0,xpath.lastIndexOf("@") -1);
			setRequestDocument(XMLTools.removeAttribute(doc,attribute, xpath));
			return "XMLUpdated";
/*
		case "fx:dbquery":
			break;

		case "fx:dbresult":
			break;
*/

		case "fx:randomnumber":
			length = params[1].split(":");
			if (length[0].trim().equalsIgnoreCase("Node")) {
				return Randomness.randomNumber(Integer.parseInt(length[1]));
			} else {
				// report error
			}

		case "fx:randomstring":
			length = params[1].split(":");
			if (length[0].trim().equalsIgnoreCase("Node")) {
				return Randomness.randomString(Integer.parseInt(length[1]));
			} else {
				return Randomness.randomString(Integer.parseInt(length[0]));
		}

		case "fx:randomalphanumeric":
			length = params[1].split(":");
			if (length[0].trim().equalsIgnoreCase("Node")) {
				return Randomness.randomAlphaNumeric(Integer.parseInt(length[1]));
			} else {
				// report error
			}
		case "fx:conversationid":
			return Randomness.generateConversationId();
				
		case "fx:messageid":
			return Randomness.generateMessageId();
					
		default:
			throw new RuntimeException("The command [" + command + " ] is not a valid command");
		}
	}


protected static boolean validateNodeContainsValueByXPath(Document doc, String xpath, String testValue) {
     XPathFactory xPathFactory = XPathFactory.newInstance();
     XPath xPath = xPathFactory.newXPath();
     XPathExpression expr;
     NodeList nList = null;
     int element = 0;
     boolean isContained = false;

     try{
         expr = xPath.compile(xpath);
         nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
     }catch(XPathExpressionException e1){
     	throw new RuntimeException("Xpath expression '" + xpath + "' did not exist." );
     }
     
     //Iterate through all nodes in the list
     do{
     		//Test to see if the test value is found in the same node structure as the locatro value
     		if(nList.item(element).getTextContent().toLowerCase().contains(testValue.toLowerCase())){
     			isContained = true;      			
     		}
     	element++;
 		if(element == nList.getLength() - 1 && !isContained){
 			Reporter.log("The test value [" +testValue+ "] was not contained in any nodes", true);
 			throw new RuntimeException("The test value [" +testValue+ "] was not contained in any nodes");
 		}
     }while(element < nList.getLength() && !isContained);
         
     return isContained;
}

	public boolean validateNodeContainsValueByXPathAndLocatorValue(Document doc, String xpath, String locatorValue, String testValue) {
     XPathFactory xPathFactory = XPathFactory.newInstance();
     XPath xPath = xPathFactory.newXPath();
     XPathExpression expr;
     NodeList nList = null;
     int element = 0;
     boolean isContained = false;             
     
     try{
         expr = xPath.compile(xpath);
         nList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
     }catch(XPathExpressionException e1){
     	throw new RuntimeException("Xpath expression '" + xpath + "' did not exist." );
     }
     
     //Iterate through all nodes in the list
     do{
     	//Test to see if the locator value is found
     	if(nList.item(element).getTextContent().toLowerCase().contains(locatorValue.toLowerCase())){
     		//Test to see if the test value is found in the same node structure as the locatro value
     		if(nList.item(element).getTextContent().toLowerCase().contains(testValue.toLowerCase())){
     			isContained = true;      			
     		}else{
     			Reporter.log("The locator value [" +locatorValue+ "] was found, but the test value [" +testValue+ "] was not contained in the child nodes", true);
     			throw new RuntimeException("The locator value [" +locatorValue+ "] was found, but the test value [" +testValue+ "] was node contained in the child nodes");
     		}
     	}
     	element++;
     }while(element < nList.getLength() && !isContained);
         
     return isContained;
     
}
	protected   Object[][] removeRowsWithRowNumber(Object[][] array, double rowNotToBeAdd)
    {
        List<Object[]> rowsToKeep = new ArrayList<Object[]>(array.length);
        for( int i =0; i<array.length; i++){
            if(i!=rowNotToBeAdd){
            	Object[] row = array[i];
            rowsToKeep.add(row);
            }
        }

        array= new Object[rowsToKeep.size()][];
        for(int i=0; i < rowsToKeep.size(); i++)
        {
        	array[i] = rowsToKeep.get(i);
        }
        return array;
    }

}
