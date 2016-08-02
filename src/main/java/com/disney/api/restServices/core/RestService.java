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
	
	
	//constructor
	public RestService() {
	    httpClient = HttpClientBuilder.create().build();
	}
	//constructor
	public RestService(String environment) {
	    httpClient = HttpClientBuilder.create().build();
	    this.environment= environment;
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
	
	private String getTdmURL(String resource){
		String tdmURL = "http://fldcvpswa6204.wdw.disney.com/EnvSrvcEndPntRepository/rest/retrieveServiceEndpoint/{environment}/{resource}";
		String responseXML = "";
		Document responseDoc = null;
		String  tdmResource = getMainResource().contains("REST") ? getMainResource() : "REST_" + getMainResource();
		tdmURL = tdmURL.replace("{environment}", WordUtils.capitalize(getEnvironment()));
		tdmURL = tdmURL.replace("{resource}", tdmResource);
		try { 
			responseXML = sendRequest(new HttpGet(tdmURL)).getResponse();
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
	public RestResponse sendGetRequest(String resource, Header[] headers) {
	    HttpUriRequest request = new HttpGet(getTdmURL(resource));
	    if(headers !=  null) request.setHeaders(headers);
		return sendRequest(request);
	}
	
	public RestResponse sendPostRequest(String resource, Header[] headers, List<NameValuePair> params, String json){
		HttpPost httppost = new HttpPost(getTdmURL(resource));
		if(headers !=  null) httppost.setHeaders(headers);

		try {
			if(params !=  null) httppost.setEntity(new UrlEncodedFormEntity(params));
			if(json !=  null)httppost.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
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
	 */
	public RestResponse sendPostRequest(String resource, Header[] headers, List<NameValuePair> params) {
		return sendPostRequest(resource, headers, params, null);
	}
	
	public RestResponse sendPostRequest(URI url, Header[] headers, List<NameValuePair> params) {
		HttpPost httppost = new HttpPost(url.toString());
		if(headers !=  null) httppost.setHeaders(headers);

		try {
			if(params !=  null) httppost.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendRequest(httppost);
	}
	
	public RestResponse sendPostRequest(String resource,Header[] headers, String body){
		return sendPostRequest(resource, headers, null, body);
	}
	
	public RestResponse sendPutRequest(String resource, Header[] headers, List<NameValuePair> params, String json){
		HttpPut httpPut = new HttpPut(getTdmURL(resource));
		if(headers !=  null) httpPut.setHeaders(headers);

		try {
			if(params !=  null) httpPut.setEntity(new UrlEncodedFormEntity(params));
			if(json !=  null)httpPut.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sendRequest(httpPut);
	}
	
	public RestResponse sendPutRequest(String resource, String json) {
		return sendPutRequest(resource, null, null, json);
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
	public RestResponse sendPutRequest(String resource, List<NameValuePair> params) {
		return sendPutRequest(resource, null, params, null);
	}
	
	public RestResponse sendPutRequest(String resource, Header[] headers) {
		return sendPutRequest(resource, headers, null, null);
	}
	
	public RestResponse sendPutRequest(String resource,  Header[] headers ,List<NameValuePair> params){
		return sendPutRequest(resource, headers, params, null);
	}
	
	public RestResponse sendPatchRequest(String resource, Header[] headers, List<NameValuePair> params, String json){
		HttpPatch httpPatch = new HttpPatch(getTdmURL(resource));
		if(headers !=  null) httpPatch.setHeaders(headers);

		try {
			if(params !=  null) httpPatch.setEntity(new UrlEncodedFormEntity(params));
			if(json !=  null)httpPatch.setEntity( new ByteArrayEntity(json.getBytes("UTF-8")));
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
	public RestResponse sendPatchRequest(String resource, List<NameValuePair> params){
	    return sendPatchRequest(resource, null, params,null);
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
	public RestResponse sendPatchRequest(String resource, Header[] headers, List<NameValuePair> params) {
		 return sendPatchRequest(resource, headers, params,null);
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
	
	public RestResponse sendDeleteRequest(String resource,Header[] headers){
		HttpDelete httpDelete = new HttpDelete(getTdmURL(resource));
		if(headers !=  null) httpDelete.setHeaders(headers);
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
			response = new RestResponse(httpClient.execute(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setServiceURL(request.getURI().toString());
		return response;
	}
}
