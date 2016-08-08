package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCommentTypes extends ScheduledEventsServicePort{
	Map<String, String> commentTypes = new HashMap<String, String>();
	int numCommentTypes = -1;
	
	public RetrieveCommentTypes(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCommentTypes")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all comment types
	 * @return - all comment types
	 */
	public Map<String, String> getCommentTypes(){
		if(numCommentTypes == -1) getNumberOfCommentTypes();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCommentTypesResponse/commentTypes");
		for(int i = 0; i < numCommentTypes; i++){
			commentTypes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return commentTypes;
	}
	/**
	 * Retrieves the number of comment types
	 * @return - number of comment types
	 */
	public int getNumberOfCommentTypes(){
		numCommentTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCommentTypesResponse/commentTypes").getLength();
		return numCommentTypes;
	}
}