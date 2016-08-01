package com.disney.api.restServices.core;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import com.disney.AutomationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestResponse {
	private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	HttpResponse response = null;
	int statusCode = 0;
	String responseFormat = "";
	String responseAsString = "";
	
	public RestResponse(HttpResponse httpResponse){
		response  = httpResponse;
		statusCode = response.getStatusLine().getStatusCode();
		responseFormat = ContentType.getOrDefault(response.getEntity()).getMimeType().replace("application/", "");
		try {
			responseAsString = EntityUtils.toString(response.getEntity());
		} catch (ParseException | IOException e) {
			throw new AutomationException(e.getMessage(), e);
		}
	}
	
	public int getStatusCode(){ return statusCode; }	
	public String getResponseFormat(){ return responseFormat; }
	public String getResponse(){ return responseAsString; }
	public Header[] getHeaders(){ return response.getAllHeaders();}

	
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
}
