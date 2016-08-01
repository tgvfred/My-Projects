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
	
	public String getTdmURL(String resource){
		String tdmURL = "http://fldcvpswa6204.wdw.disney.com/EnvSrvcEndPntRepository/rest/retrieveServiceEndpoint/{environment}/{resource}";
		String responseXML = "";
		Document responseDoc = null;
		String  tdmResource = getMainResource().contains("REST") ? getMainResource() : "REST_" + getMainResource();
		tdmURL = tdmURL.replace("{environment}", WordUtils.capitalize(getEnvironment()));
		tdmURL = tdmURL.replace("{resource}", tdmResource);

		try { 
			responseXML = sendGetRequest(tdmURL).getResponse();
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
	public RestResponse sendGetRequest(String url) {
	   	return sendGetRequest(url, null);
	}
	
	/**
	 * Sends a GET request
	 * 
	 * @param 	URL for the service you are testing
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendGetRequest(String URL, Header[] headers) {
	    HttpUriRequest request = new HttpGet(URL);
	    if(headers !=  null) request.setHeaders(headers);
		return sendRequest(request);
	}
	
	public RestResponse sendPostRequest(String URL, Header[] headers, List<NameValuePair> params, String json){
		HttpPost httppost = new HttpPost(URL);
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
	 * @param 	url		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPostRequest(String url, List<NameValuePair> params){
		return sendPostRequest(url, null, params, null);
	}
	
	/**
	 * Sends a post (update) request, pass in the parameters for the json arguments to update
	 * 
	 * @param 	url		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPostRequest(String url, Header[] headers, List<NameValuePair> params) {
		return sendPostRequest(url, headers, params, null);
	}
	
	public RestResponse sendPostRequest(String url,Header[] headers, String body){
		return sendPostRequest(url, headers, null, body);
	}
	
	public RestResponse sendPutRequest(String URL, Header[] headers, List<NameValuePair> params, String json){
		HttpPut httpPut = new HttpPut(URL);
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
	
	public RestResponse sendPutRequest(String url, String json) {
		return sendPutRequest(url, null, null, json);
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
	public RestResponse sendPutRequest(String url, List<NameValuePair> params) {
		return sendPutRequest(url, null, params, null);
	}
	
	public RestResponse sendPutRequest(String url, Header[] headers) {
		return sendPutRequest(url, headers, null, null);
	}
	
	public RestResponse sendPutRequest(String url,  Header[] headers ,List<NameValuePair> params){
		return sendPutRequest(url, headers, params, null);
	}
	
	public RestResponse sendPatchRequest(String URL, Header[] headers, List<NameValuePair> params, String json){
		HttpPatch httpPatch = new HttpPatch(URL);
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
	 * @param 	URL		for the service
	 * @param 	params	arguments to update
	 * @return 	response in string format
	 * @throws 	ClientProtocolException
	 * @throws 	IOException
	 */
	public RestResponse sendPatchRequest(String URL, List<NameValuePair> params){
	    return sendPatchRequest(URL, null, params,null);
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
	public RestResponse sendPatchRequest(String URL, Header[] headers, List<NameValuePair> params) {
		 return sendPatchRequest(URL, headers, params,null);
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
	
	public RestResponse sendDeleteRequest(String URL,Header[] headers){
		HttpDelete httpDelete = new HttpDelete(URL);
		if(headers !=  null) httpDelete.setHeaders(headers);
		return sendRequest(httpDelete);
	}
	
	public RestResponse sendDeleteRequest(String url ){
		return sendDeleteRequest(url, null); 
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
	public Header[] sendOptionsRequest(String URL ) {
		HttpOptions httpOptions=new HttpOptions(URL);
		return sendRequest(httpOptions).getHeaders();
	}

	private RestResponse sendRequest(HttpUriRequest request){
		RestResponse response = null;
		try {
			response = new RestResponse(httpClient.execute(request));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
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
