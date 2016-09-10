package com.disney.api.restServices.core;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;

public class Headers {
	//private Header[] headers ;
	/**
	 * Used in coordination with RestService.createHeader() <br/>
	 * Enums: <br/>
	 * JENKINS:<br/> 
	 * 			Content-type: applications/x-www-form-urlencoded<br/><br/>
	 * BASIC_CONVO:<br/> 
	 * 			Content-type: application/json;charset=utf-8 <br/>
	 * 			Accept: application/json <br/>
	 * 			username: test116.user <br/>
	 *     		messageId: Random Alphanumic string <br/>
	 *     		Connection: keep-alive <br/>
	 *     		ConversationId:  Random Alphanumic string  <br/>
	 *     		requestedTimestamp: Current timestamp<br/><br/>
	 * REST :<br/>
	 * 			Authorization: BEARER and generated token<br/>    
	 *
	 */
	public static enum HeaderType {
	  	JENKINS,
	  	AUTH,
	    BASIC_CONVO,
	    REST,
		REST_NOAuth;
	}
	/*  private Header[] getHeaders(){
		  return headers;
	  }*/
	  
	  /**
	   * Automatically populates headers based on predefined options from RestService.HeaderType
	   * @param type Uses options from RestService.HeaderType enum
	   */
	  public static Header[]  createHeader(HeaderType type)  {
		  Header[] headers = null;
	        switch(type) {
		        case JENKINS:		        	
        			TestReporter.logInfo("Creating headers for [JENKINS]");
		        	headers = new Header[] {
		    	   		    new BasicHeader("Content-type", "application/x-www-form-urlencoded")};
		        	break;
		        case AUTH:		        	
        			TestReporter.logInfo("Creating headers for [AUTH]");

		        	headers= new Header[] {
		    	   		     new BasicHeader("Content-type", "application/x-www-form-urlencoded")};
		        	break;
		        case BASIC_CONVO:
        			TestReporter.logInfo("Creating headers for [BASIC_CONVO]");
		        	headers = new Header[] {
		    	   		     new BasicHeader("Content-type", "application/json;charset=utf-8")
		    	   		    ,new BasicHeader("Accept", "application/json")
		    	   		    ,new BasicHeader("username", "test116.user")
		    	   		    ,new BasicHeader("messageId", Randomness.generateMessageId())
		    	   		    ,new BasicHeader("Connection", "keep-alive")
		    	   		    ,new BasicHeader("conversationId",  Randomness.generateConversationId())
		    	   		    ,new BasicHeader("requestedTimestamp", Randomness.generateCurrentXMLDatetime() + ".000-04:00")
		    	   		};
		        	break;
		        case REST:
		        	TestReporter.logInfo("Creating headers for [REST]");
		        	List<NameValuePair> params = new ArrayList<NameValuePair>();
		        	params.add(new BasicNameValuePair("grant_type", "password"));
		        	params.add(new BasicNameValuePair("username", "mdxcontc@ngetestmail.com"));
		        	params.add(new BasicNameValuePair("password", "mickey1"));
		        	params.add(new BasicNameValuePair("client_id", "SE_TEST_EXTERNAL_PASSWORD"));
		        	params.add(new BasicNameValuePair("assertion_type", "disneyid"));
					AuthToken authToken = null;
					try {
						authToken = new RestService().sendPostRequest(new URI("https://stg.authorization.go.com/token"), HeaderType.AUTH, params).mapJSONToObject(AuthToken.class);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

		        	headers = new Header []{
		        			new BasicHeader("authorization", authToken.getTokenType() + " " + authToken.getAccessToken())
		        	};
		        	break;
		        case REST_NOAuth:
		        	headers = new Header []{
		        			//No Authorization Token will be sent
		        	};
		        break;
	            default:
	                break;
	        }
	        
	        //Logging headers
	        String allHeaders = "";
	    	for (Header header : headers){
	    		allHeaders += "[" +header.getName() + ": " + header.getValue()+"] ";
	    	}
			TestReporter.logInfo("Headers added " + allHeaders);
			
			return headers;
	    }
}
