package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveSpecialEvents extends ScheduledEventsServicePort{
	Map<String, String> specialEventsCodes = new HashMap<String, String>();
	Map<String, String> specialEventsDescriptions = new HashMap<String, String>();
	Map<String, String> specialEventsIds = new HashMap<String, String>();
	int numSpecialEvents = -1;
	
	public RetrieveSpecialEvents(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSpecialEvents")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all special events codes
	 * @return - all special events codes
	 */
	public Map<String, String> getSpecialEventsCodes(){
		if(numSpecialEvents == -1) getNumberOfSpecialEvents();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialEventsResponse/specialEvents/optionCode");
		for(int i = 0; i < numSpecialEvents; i++){
			specialEventsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialEventsDescriptions;
	}
	/**
	 * Retrieves all special events descriptions
	 * @return - all special events descriptions
	 */
	public Map<String, String> getSpecialEventsDescriptions(){
		if(numSpecialEvents == -1) getNumberOfSpecialEvents();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialEventsResponse/specialEvents/optionDescription");
		for(int i = 0; i < numSpecialEvents; i++){
			specialEventsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialEventsDescriptions;
	}
	/**
	 * Retrieves all special events ids
	 * @return - all special events ids
	 */
	public Map<String, String> getSpecialEventsIds(){
		if(numSpecialEvents == -1) getNumberOfSpecialEvents();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialEventsResponse/specialEvents/optionId");
		for(int i = 0; i < numSpecialEvents; i++){
			specialEventsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialEventsIds;
	}
	/**
	 * Retrieves the number of special events
	 * @return - number of special events
	 */
	public int getNumberOfSpecialEvents(){
		numSpecialEvents = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialEventsResponse/specialEvents").getLength();
		return numSpecialEvents;
	}
}