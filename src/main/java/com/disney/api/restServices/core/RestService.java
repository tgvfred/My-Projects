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
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

import com.disney.AutomationException;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.test.utils.Randomness;
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
	
	//constructor
	public RestService() {
		TestReporter.logDebug("Initializing RestService");
		TestReporter.logDebug("Creating Http Client instance");
	    httpClient = HttpClientBuilder.create().build();
	}
	//constructor
	public RestService(String environment) {
		TestReporter.logDebug("Initializing RestService");
		TestReporter.logDebug("Creating Http Client instance");
	    httpClient = HttpClientBuilder.create().build();

	    TestReporter.logDebug("Set ENVIRONMENT to [" + environment + "]");
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
	    TestReporter.logDebug("Translating ["+environment+"] to legacy environment name if required");
	}
	
	public String getMainResource() {
		return mainResource;
	}

	public void setMainResource(String mainResource) {
		this.mainResource = mainResource;
	}
	
	public String getUserAgent(){ return this.userAgent;}
	
	public void setUserAgent(String userAgent){ this.userAgent = userAgent;	}	
	
	private String getTdmURL(String resource){
		TestReporter.logDebug("Entering RestService.getTdmUrl");
		String tdmURL = "http://fldcvpswa6204.wdw.disney.com/EnvSrvcEndPntRepository/rest/retrieveServiceEndpoint/{environment}/{resource}";
		String responseXML = "";
		Document responseDoc = null;

	    TestReporter.logDebug("Validing Main Resource is set");
	    if(getMainResource() == null || getMainResource().isEmpty()) throw new AutomationException("Main resource was not set. Set with restService.setMainResource(string)");
	    
		String  tdmResource = getMainResource().contains("REST") ? getMainResource() : "REST_" + getMainResource();
		
		TestReporter.logDebug("Main Resource is [" + tdmResource +"]");
		tdmURL = tdmURL.replace("{environment}", WordUtils.capitalize(getEnvironment()));
		tdmURL = tdmURL.replace("{resource}", tdmResource);
		TestReporter.logInfo("TDM URL [" + tdmURL + "]");
		try { 
			TestReporter.logDebug("Sending request to TDM Endpoint Repo");
			responseXML = sendRequest(new HttpGet(tdmURL)).getResponse();
			TestReporter.logDebug("Recieved Response from TDM Endpoint Repo");
			responseXML = responseXML.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>","").trim();
			TestReporter.logDebug("Creating XML Document from TDM response");
			responseDoc = XMLTools.makeXMLDocument(responseXML);
		} catch (Exception e) {
			TestReporter.logDebug("Failed to retrieve TDM URL");
			throw new AutomationException("Error getting TDM url");
		}
		TestReporter.logDebug("Retrieving Endpoint from TDM response");
		String url =XMLTools.getFirstNodeValueByTagName(responseDoc, "endPoint") + resource;
		TestReporter.logInfo("TDM Endpoint retrieved [" + url +"]");
		TestReporter.logDebug("Exiting RestService.getTdmUrl");
		return url;
	}
	/**
	 * Sends a GET request
	 * 
	 * @param 	URL for the service you are testing
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendGetRequest(String resource) {
	   	return sendGetRequest(resource, null);
	}
	
	/**
	 * Sends a GET request
	 * 
	 * @param 	resource for the service you are testing
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendGetRequest(String resource, HeaderType type) {
		TestReporter.logDebug("Preparing to send GET request");
		TestReporter.logDebug("Getting Rest endpoint from TDM");
		String url = getTdmURL(resource);
		TestReporter.logDebug("Creating Http GET instance with URL of ["+url+"]");
		HttpGet request = new HttpGet(url);
	    if(type != null) {
	    	request.setHeaders(Headers.createHeader(type));
	    }
		return sendRequest(request);
	}
	
	public RestResponse sendPostRequest(String resource, HeaderType type, List<NameValuePair> params, String json){
		TestReporter.logDebug("Preparing to send POST request");
		TestReporter.logDebug("Getting Rest endpoint from TDM");
		String url = getTdmURL(resource);
		TestReporter.logDebug("Creating Http POST instance with URL of ["+url+"]");
		HttpPost httppost = new HttpPost(url);
		
		if(type != null){
	    	httppost.setHeaders(Headers.createHeader(type));
	    }
		
		try {
			if(params !=  null){
		    	String allParams= "";
		    	for (NameValuePair param : params){
		    		allParams += "[" +param.getName() + ": " + param.getValue()+"] ";
		    	}
				TestReporter.logInfo("Adding Parameters " + allParams);
				httppost.setEntity(new UrlEncodedFormEntity(params));

		    }
			
			if(json !=  null){
				TestReporter.logInfo("Adding json [" + json + "]");
				httppost.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendRequest(httppost);
	}
	
	/**
	 * Sends a post (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	resource		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPostRequest(String resource, List<NameValuePair> params){
		return sendPostRequest(resource, null, params, null);
	}
	
	/**
	 * Sends a post (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	resource		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 *//*
	public RestResponse sendPostRequest(String resource, Header[] headers, List<NameValuePair> params) {
		return sendPostRequest(resource, headers, params, null);
	}*/
	
	public RestResponse sendPostRequest(URI url, HeaderType type, List<NameValuePair> params) {
		HttpPost httppost = new HttpPost(url.toString());
		if(type !=  null) {
			httppost.setHeaders(Headers.createHeader(type));
		}

		try {
			if(params !=  null) httppost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendRequest(httppost);
	}
	
	public RestResponse sendPostRequest(String resource, HeaderType type, String body){
		return sendPostRequest(resource, type, null, body);
	}
	
	public RestResponse sendPutRequest(String resource, HeaderType type, List<NameValuePair> params, String json){
		TestReporter.logDebug("Preparing to send PUT request");
		TestReporter.logDebug("Getting Rest endpoint from TDM");
		String url = getTdmURL(resource);
		TestReporter.logDebug("Creating Http PUT instance with URL of ["+url+"]");
		HttpPut httpPut = new HttpPut(url);
		if(type != null){
			httpPut.setHeaders(Headers.createHeader(type));
	    }
		
		try {
			if(params !=  null){
		    	String allParams= "";
		    	for (NameValuePair param : params){
		    		allParams += "[" +param.getName() + ": " + param.getValue()+"] ";
		    	}
				TestReporter.logInfo("Adding Parameters " + allParams);
				httpPut.setEntity(new UrlEncodedFormEntity(params));

		    }
			
			if(json !=  null){
				TestReporter.logInfo("Adding json [" + json + "]");
				httpPut.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendRequest(httpPut);
	}
	
	public RestResponse sendPutRequest(String resource, HeaderType type, String json) {
		return sendPutRequest(resource, type, null, json);
	}
	/**
	 * Sends a put (create) request, pass in the parameters for the json arguments to create
	 * 
	 * @param 	resource		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPutRequest(String resource,HeaderType type, List<NameValuePair> params) {
		return sendPutRequest(resource, type , params, null);
	}
	
	public RestResponse sendPutRequest(String resource, HeaderType type) {
		return sendPutRequest(resource,type, null, null);
	}
	
	public RestResponse sendPutRequest(String resource,  List<NameValuePair> params){
		return sendPutRequest(resource, null, params, null);
	}
	
	public RestResponse sendPutRequest(String resource,  String json) {
		return sendPutRequest(resource, null, json);
	}

	public RestResponse sendPatchRequest(String resource,HeaderType type,  List<NameValuePair> params, String json){
		TestReporter.logDebug("Preparing to send PATCH request");
		TestReporter.logDebug("Getting Rest endpoint from TDM");
		String url = getTdmURL(resource);
		TestReporter.logDebug("Creating Http PATCH instance with URL of ["+url+"]");
		HttpPatch httpPatch = new HttpPatch(url);
		if(type != null){
			httpPatch.setHeaders(Headers.createHeader(type));
	    }
		

		try {
			if(params !=  null){
		    	String allParams= "";
		    	for (NameValuePair param : params){
		    		allParams += "[" +param.getName() + ": " + param.getValue()+"] ";
		    	}
				TestReporter.logInfo("Adding Parameters " + allParams);
				httpPatch.setEntity(new UrlEncodedFormEntity(params));

		    }
			
			if(json !=  null){
				TestReporter.logInfo("Adding json [" + json + "]");
				httpPatch.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sendRequest(httpPatch);
	}
	/**
	 * Sends a patch (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	resource		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPatchRequest(String resource, HeaderType type, List<NameValuePair> params){
	    return sendPatchRequest(resource, type, params, null);
	}
	
	public RestResponse sendPatchRequest(String resource, List<NameValuePair> params, String json){
	    return sendPatchRequest(resource, null, params,json);
	}
	
	/**
	 * Sends a patch (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	resource		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPatchRequest(String resource,  List<NameValuePair> params) {
		 return sendPatchRequest(resource, null, params,null);
	}
	
	public RestResponse sendPatchRequest(String resource, HeaderType type, String json) {
		 return sendPatchRequest(resource, type, null, json);
	}
	
	
	/**
	 * Sends a delete request.  Depends on the service if a response is returned.
	 * If no response is returned, will return null	 * 
	 * 
	 * @param 	resource		for the service
	 * @return 	response in string format or null
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	
	public RestResponse sendDeleteRequest(String resource,HeaderType type){
		TestReporter.logDebug("Preparing to send PATCH request");
		TestReporter.logDebug("Getting Rest endpoint from TDM");
		String url = getTdmURL(resource);
		TestReporter.logDebug("Creating Http PATCH instance with URL of ["+url+"]");
		HttpDelete httpDelete = new HttpDelete(url);
		
		if(type != null){
	    	httpDelete.setHeaders(Headers.createHeader(type));
	    }
		
		return sendRequest(httpDelete);
	}
	
	public RestResponse sendDeleteRequest(String resource ){
		return sendDeleteRequest(resource, null); 
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
	public Header[] sendOptionsRequest(String resource ) {
		HttpOptions httpOptions=new HttpOptions(getTdmURL(resource));
		return sendRequest(httpOptions).getHeaders();
	}

	private RestResponse sendRequest(HttpUriRequest request){
		RestResponse response = null;
		try {
			TestReporter.logDebug("Sending request");
			response = new RestResponse(httpClient.execute(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
		TestReporter.logDebug("Setting URI used on RestResponse");		
		response.setServiceURL(request.getURI().toString());
		TestReporter.logDebug("Returning RestResponse to calling method");
		return response;
	}
}
