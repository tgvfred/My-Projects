package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveGuestRequests extends ScheduledEventsServicePort{
	Map<String, String> guestRequestsCodes = new HashMap<String, String>();
	Map<String, String> guestRequestsDescriptions = new HashMap<String, String>();
	Map<String, String> guestRequestsIds = new HashMap<String, String>();
	int numGuestRequests = -1;
	
	public RetrieveGuestRequests(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveGuestRequests")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all guest request codes
	 * @return - all guest request codes
	 */
	public Map<String, String> getGuestRequestCodes(){
		if(numGuestRequests == -1) getNumberOfGuestRequests();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestRequestsResponse/guestRequests/optionCode");
		for(int i = 0; i < numGuestRequests; i++){
			guestRequestsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return guestRequestsCodes;
	}
	/**
	 * Retrieves all guest request descriptions
	 * @return - all guest request descriptions
	 */
	public Map<String, String> getGuestRequestDescriptions(){
		if(numGuestRequests == -1) getNumberOfGuestRequests();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestRequestsResponse/guestRequests/optionDescription");
		for(int i = 0; i < numGuestRequests; i++){
			guestRequestsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return guestRequestsDescriptions;
	}
	/**
	 * Retrieves all guest request ids
	 * @return - all guest request ids
	 */
	public Map<String, String> getGuestRequestIds(){
		if(numGuestRequests == -1) getNumberOfGuestRequests();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestRequestsResponse/guestRequests/optionId");
		for(int i = 0; i < numGuestRequests; i++){
			guestRequestsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return guestRequestsIds;
	}
	/**
	 * Retrieves the number of guest requests
	 * @return - number of guest requests
	 */
	public int getNumberOfGuestRequests(){
		numGuestRequests = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveGuestRequestsResponse/guestRequests").getLength();
		return numGuestRequests;
	}
}