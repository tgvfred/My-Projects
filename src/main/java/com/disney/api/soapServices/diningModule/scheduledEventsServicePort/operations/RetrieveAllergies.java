package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAllergies extends ScheduledEventsServicePort{
	Map<String, String> allergies = new HashMap<String, String>();
	int numAllergies = -1;
	
	public RetrieveAllergies(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAllergies")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Retrieves all allergies
	 * @return - all allergies
	 */
	public Map<String, String> getAllergies(){
		if(numAllergies == -1) getNumberOfAllergies();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllergiesResponse/allergies");
		for(int i = 0; i < numAllergies; i++){
			allergies.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return allergies;
	}
	/**
	 * Retrieves the number of allergies
	 * @return - number of allergies
	 */
	public int getNumberOfAllergies(){
		numAllergies = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllergiesResponse/allergies").getLength();
		return numAllergies;
	}
}
