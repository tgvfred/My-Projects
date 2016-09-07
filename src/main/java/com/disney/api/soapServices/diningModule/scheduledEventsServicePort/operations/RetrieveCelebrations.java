package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCelebrations extends ScheduledEventsServicePort{
	Map<String, String> celebrationsCodes = new HashMap<String, String>();
	Map<String, String> celebrationsDescriptions = new HashMap<String, String>();
	Map<String, String> celebrationsIds = new HashMap<String, String>();
	int numCelebrations = -1;
	
	public RetrieveCelebrations(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCelebrations")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all Celebrations codes
	 * @return - all Celebrations codes
	 */
	public Map<String, String> getCelebrationsCodes(){
		if(numCelebrations == -1) getNumberOfCelebrations();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCelebrationsResponse/celebrations/optionCode");
		for(int i = 0; i < numCelebrations; i++){
			celebrationsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return celebrationsCodes;
	}
	/**
	 * Retrieves all Celebrations descriptions
	 * @return - all Celebrations descriptions
	 */
	public Map<String, String> getCelebrationsDescriptions(){
		if(numCelebrations == -1) getNumberOfCelebrations();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCelebrationsResponse/celebrations/optionDescription");
		for(int i = 0; i < numCelebrations; i++){
			celebrationsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return celebrationsDescriptions;
	}
	/**
	 * Retrieves all Celebrations ids
	 * @return - all Celebrations ids
	 */
	public Map<String, String> getCelebrationsIds(){
		if(numCelebrations == -1) getNumberOfCelebrations();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCelebrationsResponse/celebrations/optionId");
		for(int i = 0; i < numCelebrations; i++){
			celebrationsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return celebrationsIds;
	}
	/**
	 * Retrieves the number of Celebrations
	 * @return - number of Celebrations
	 */
	public int getNumberOfCelebrations(){
		numCelebrations = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCelebrationsResponse/celebrations").getLength();
		return numCelebrations;
	}
}