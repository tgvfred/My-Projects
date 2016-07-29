package com.disney.api.restServices.core;

/**
 * Just playing around with some different ways of using rest services with Jackson 
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.lang.WordUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import com.disney.AutomationException;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class RestService {
    private String environment = "";
    private String mainResource = "";
	int statusCode = 0;
	String responseFormat;
	protected String responseAsString = null;
	String userAgent; 
	protected HttpContext httpContext = new BasicHttpContext();
	
	protected HttpClient httpClient = null;
	private ObjectMapper mapper = new ObjectMapper().
		      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
	
	//constructor
	public RestService() {
	    httpClient = HttpClientBuilder.create().build();
	}//constructor
	public RestService(String environment) {
	    httpClient = HttpClientBuilder.create().build();
	    setEnvironment(environment);
	}
	
	/*
	 * Encapsulation area 
	 */
	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = Environment.getEnvironmentName(environment);
	}
	
	public String getMainResource() {
		return mainResource;
	}

	public void setMainResource(String mainResource) {
		this.mainResource = mainResource;
	}
	
	public String getUserAgent(){ return this.userAgent;}
	
	public void setUserAgent(String userAgent){ this.userAgent = userAgent;	}	
	
	public int getStatusCode(){ return statusCode; }
	
	private void setStatusCode(HttpResponse httpResponse){ 	statusCode = httpResponse.getStatusLine().getStatusCode(); }
	
	public String getResponseFormat(){ return responseFormat; }
	
	private void setResponseFormat(HttpResponse httpResponse){ responseFormat = ContentType.getOrDefault(httpResponse.getEntity()).getMimeType().replace("application/", "");	}
	
	public String getTdmURL(String resource){
		String tdmURL = "http://fldcvpswa6204.wdw.disney.com/EnvSrvcEndPntRepository/rest/retrieveServiceEndpoint/{environment}/{resource}";
		String responseXML = "";
		Document responseDoc = null;
		String  tdmResource = getMainResource().contains("REST") ? getMainResource() : "REST_" + getMainResource();
		tdmURL = tdmURL.replace("{environment}", WordUtils.capitalize(getEnvironment()));
		tdmURL = tdmURL.replace("{resource}", tdmResource);

		try { 
			responseXML = sendGetRequest(tdmURL);
			responseXML = responseXML.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>","").trim();
			responseDoc = XMLTools.makeXMLDocument(responseXML);
		} catch (Exception e) {
			throw new AutomationException("Error getting TDM url");
		}

		return XMLTools.getFirstNodeValueByTagName(responseDoc, "endPoint") + resource;
	}
	/**
	 * Sends a GET request
	 * 
	 * @param 	URL for the service you are testing
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendGetRequest(String URL) throws ClientProtocolException, IOException{
	    	
		HttpUriRequest request = new HttpGet(URL);
		
		HttpResponse httpResponse = httpClient.execute( request, httpContext);
		
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	/**
	 * Sends a GET request
	 * 
	 * @param 	URL for the service you are testing
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendGetRequest(String URL, Header[] headers) throws ClientProtocolException, IOException{
	    	HttpUriRequest request = new HttpGet(URL);
		request.setHeaders(headers);
		//System.out.println(URL);
		HttpResponse httpResponse = httpClient.execute( request, httpContext);
		
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		//setResponseCookie(httpResponse);
		
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	/**
	 * Sends a post (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPostRequest(String URL, List<NameValuePair> params) throws ClientProtocolException, IOException{
		
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
		httppost.setEntity(new UrlEncodedFormEntity(params));
		
		HttpResponse httpResponse = httpClient.execute(httppost);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);

		return responseAsString;
	}
	
	/**
	 * Sends a post (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPostRequest(String URL,Header[] headers, List<NameValuePair> params) throws ClientProtocolException, IOException{
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
		httppost.setHeaders(headers);
		httppost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		
		HttpResponse httpResponse = httpClient.execute( httppost);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);
		
		
		
		return responseAsString;
	}
	
	public String sendPostRequest(String URL,Header[] headers, String body) throws ClientProtocolException, IOException{
		
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);
		httppost.setHeaders(headers);
		httppost.setEntity( new ByteArrayEntity(body.getBytes("UTF-8")));
		
		HttpResponse httpResponse = httpClient.execute(httppost, httpContext);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		
		return responseAsString;
	}
	/**
	 * Sends a put (create) request, pass in the parameters for the json arguments to create
	 * 
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
/*	public String sendPutRequest() throws ClientProtocolException, IOException{
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(url);
		putRequest.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
		
		HttpResponse httpResponse = httpClient.execute(putRequest);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}*/
	/**
	 * Sends a put (create) request, pass in the parameters for the json arguments to create
	 * 
	 * @param 	URL		for the service
	 * @param 	body	json to send
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 *//*
	public String sendPutRequest(String body) {
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(url);
		try {
			putRequest.setEntity( new ByteArrayEntity(body.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(putRequest);
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		return responseAsString;
	}*/
	public HttpResponse sendPutRequest(String url, String body) {
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(url);
		//checkP12();
		try {
			putRequest.setEntity( new ByteArrayEntity(body.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(putRequest);
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		return httpResponse;
	}
	/**
	 * Sends a put (create) request, pass in the parameters for the json arguments to create
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPutRequest(String URL, List<NameValuePair> params) throws ClientProtocolException, IOException{
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(URL);
		putRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		
		HttpResponse httpResponse = httpClient.execute(putRequest, httpContext);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	public String sendPutRequest(String URL,  Header[] headers ,List<NameValuePair> params) throws ClientProtocolException, IOException{
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(URL);
		putRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		if (headers != null) putRequest.setHeaders(headers);
		HttpResponse httpResponse = httpClient.execute(putRequest, httpContext);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		responseAsString = EntityUtils.toString(httpResponse.getEntity());
		//System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	/**
	 * Sends a patch (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPatchRequest(String URL, List<NameValuePair> params) throws ClientProtocolException, IOException{
	    return sendPatchRequest(URL, null, params);
	}
	
	/**
	 * Sends a patch (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendPatchRequest(String URL, Header[] headers, List<NameValuePair> params) throws ClientProtocolException, IOException{
		//HttpClient httpclient = HttpClients.createDefault();
		HttpPatch patchRequest = new HttpPatch(URL);
		patchRequest.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
		if (headers != null) patchRequest.setHeaders(headers);
		
		HttpResponse httpResponse = httpClient.execute(patchRequest, httpContext);
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		if(httpResponse.getEntity() != null){
		    responseAsString = EntityUtils.toString(httpResponse.getEntity());
		}else{
		    responseAsString = "";
		}
		//System.out.println("String response: " + responseAsString);
		
		return responseAsString;
	}
	
	
	/**
	 * Sends a delete request.  Depends on the service if a response is returned.
	 * If no response is returned, will return null	 * 
	 * 
	 * @param 	URL		for the service
	 * @return 	response in string format or null
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public String sendDeleteRequest(String URL) throws ClientProtocolException, IOException{

		HttpUriRequest deleteRequest = new HttpDelete(URL);
		
		HttpResponse httpResponse = httpClient.execute( deleteRequest, httpContext );

		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		if (httpResponse.getEntity()!=null){
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("String response: " + responseAsString);
		}		
		
		return responseAsString;
	}
	
	public String sendDeleteRequest(String URL,Header[] headers, List<NameValuePair> params) throws ClientProtocolException, IOException{
	        URI uri = null;
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("bluesourcestaging.herokuapp.com").setPath("/admin/titles/5884").addParameters(params);
		try {
		   uri =builder.build();
		} catch (URISyntaxException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		HttpDelete deleteRequest = new HttpDelete(uri);
		
		if (headers != null) deleteRequest.setHeaders(headers);
		HttpResponse httpResponse = httpClient.execute( deleteRequest, httpContext );

		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		if (httpResponse.getEntity()!=null){
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
			System.out.println("String response: " + responseAsString);
		}		
		
		return responseAsString;
	}
	
	public String sendDeleteRequest(URI uri,Header[] headers) throws ClientProtocolException, IOException{

		HttpDelete deleteRequest = new HttpDelete(uri);
		
		if (headers != null) deleteRequest.setHeaders(headers);
		HttpResponse httpResponse = httpClient.execute( deleteRequest, httpContext );

		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		if (httpResponse.getEntity()!=null){
			responseAsString = EntityUtils.toString(httpResponse.getEntity());
			//System.out.println("String response: " + responseAsString);
		}		
		
		return responseAsString;
	}
	
	/**
	 * Sends an options request.  Options should give what the acceptable methods are for
	 * the service (GET, HEAD, PUT, POST, etc).  There should be some sort of an ALLOW 
	 * header that will give you the allowed methods.  May or may not be a body to the response, 
	 * depending on the service.  
	 * 
	 * This method will return all the headers and the test should parse through and find the header 
	 * it needs, that will give the allowed methods, as the naming convention will be different for each service.  
	 * 
	 * @param 	URL		for the service
	 * @return 	returns an array of headers
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public Header[] sendOptionsRequest(String URL ) throws ClientProtocolException, IOException{
		HttpClient httpclient = HttpClients.createDefault();
		HttpOptions httpOptions=new HttpOptions(URL);
		
		HttpResponse httpResponse=httpclient.execute(httpOptions);
		System.out.println("Response Headers: ");
		Header[] headers = httpResponse.getAllHeaders();
		for (Header header: headers ){	
			System.out.println(header.getName() + " : " + header.getValue());
		}
		
		setStatusCode(httpResponse);		
		setResponseFormat(httpResponse);
		
		return headers;
	}
	

	
	/**
	 * Uses the class instance of the responeAsString to map to object
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public <T> T mapJSONToObject(Class<T> clazz) throws IOException {
		return mapJSONToObject(responseAsString, clazz);
		
	}
	
	/**
	 * Can pass in any json as a string and map to object
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public <T> T mapJSONToObject(String stringResponse, Class<T> clazz) throws IOException {
		
		return mapper.readValue(stringResponse, clazz);
	}
	
	/**
	 * Can pass in any json as a string and maps to tree
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public JsonNode mapJSONToTree(String stringResponse) throws IOException {
				
		return mapper.readTree(stringResponse);
	}
	
	/**
	 * Uses the class instance of the responeAsString to map to tree
	 * @param clazz
	 * @return
	 * @throws IOException
	 */
	public JsonNode mapJSONToTree() throws IOException {
		return mapJSONToTree(responseAsString);
	}
	
	private void checkP12(){
		KeyStore clientStore;
		TestReporter.logDebug("Entering BaseSoapRequest#checkP12");
		try {
	
			TestReporter.logDebug("Creating Keystore Instance");
			clientStore = KeyStore.getInstance("PKCS12");
	
			//clientStore.load(new FileInputStream(new File(getClass().getResource("/com/disney/certificates/webvan/TWDC.WDPR.Passport.QA.p12").getPath())), "Disney123".toCharArray());
	
			TestReporter.logDebug("Retrieving WebVan Certificate");
			InputStream is = new URL("https://github.disney.com/WDPRO-QA/lilo/raw/master/end_to_end/CommerceFlow/src/main/resources/com/disney/certificates/webvan/TWDC.WDPR.Passport.QA.p12").openStream();
	
			TestReporter.logDebug("Loading WebVan Certifcate into Keystore");
			clientStore.load(is, "Disney123".toCharArray());
	
	
			TestReporter.logDebug("Unlocking WebVan cert with key");
	        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	        kmf.init(clientStore, "Disney123".toCharArray());
	        KeyManager[] kms = kmf.getKeyManagers();
	       
	       // String path = getClass().getResource("/com/disney/certificates/webvan/cacerts").getPath();
	        TestReporter.logDebug("Retrieving CA Cert Store");
	        InputStream isCA = new URL("https://github.disney.com/WDPRO-QA/lilo/raw/master/end_to_end/CommerceFlow/src/main/resources/com/disney/certificates/webvan/cacerts").openStream();
			
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
}
