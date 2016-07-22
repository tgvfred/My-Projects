package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAllergies extends ScheduledEventsServicePort{
	public RetrieveAllergies(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAllergies")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public int getNumberOfAllergies(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllergiesResponse/allergies").getLength();		
	}
	
	public List<String> getAllAllergies(){
		List<String>  allergies = new ArrayList<String>();
		for(int x = 1 ; x < getNumberOfAllergies() ; x++){
			allergies.add(getResponseNodeValueByXPath("/Envelope/Body/retrieveAllergiesResponse/allergies[" + x +"]"));
		}
		return allergies;
	}
}