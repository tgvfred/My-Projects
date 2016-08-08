package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveSpecialNeeds extends ScheduledEventsServicePort{
	Map<String, String> specialNeedsCodes = new HashMap<String, String>();
	Map<String, String> specialNeedsDescriptions = new HashMap<String, String>();
	Map<String, String> specialNeedsIds = new HashMap<String, String>();
	int numSpecialNeeds = -1;
	
	public RetrieveSpecialNeeds(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSpecialNeeds")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}

	/**
	 * Retrieves all special needs codes
	 * @return - all special needs codes
	 */
	public Map<String, String> getSpecialNeedsCodes(){
		if(numSpecialNeeds == -1) getNumberOfSpecialNeeds();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialNeedsResponse/specialNeeds/optionCode");
		for(int i = 0; i < numSpecialNeeds; i++){
			specialNeedsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialNeedsCodes;
	}
	/**
	 * Retrieves all special needs descriptions
	 * @return - all special needs descriptions
	 */
	public Map<String, String> getSpecialNeedsDescriptions(){
		if(numSpecialNeeds == -1) getNumberOfSpecialNeeds();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialNeedsResponse/specialNeeds/optionDescription");
		for(int i = 0; i < numSpecialNeeds; i++){
			specialNeedsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialNeedsDescriptions;
	}
	/**
	 * Retrieves all special needs ids
	 * @return - all special needs ids
	 */
	public Map<String, String> getSpecialNeedsIds(){
		if(numSpecialNeeds == -1) getNumberOfSpecialNeeds();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialNeedsResponse/specialNeeds/optionId");
		for(int i = 0; i < numSpecialNeeds; i++){
			specialNeedsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialNeedsIds;
	}
	/**
	 * Retrieves the number of special needs
	 * @return - number of special needs
	 */
	public int getNumberOfSpecialNeeds(){
		numSpecialNeeds = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSpecialNeedsResponse/specialNeeds").getLength();
		return numSpecialNeeds;
	}
}