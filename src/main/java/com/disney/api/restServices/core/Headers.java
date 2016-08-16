package com.disney.api.restServices.core;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

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
	 *
	 */
	public static enum HeaderType {
	  	JENKINS,
	    BASIC_CONVO;
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
