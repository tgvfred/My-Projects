package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveChangeReasons extends ScheduledEventsServicePort{
	Map<String, String> changeReasonDescriptions = new HashMap<String, String>();
	Map<String, String> changeReasonIds = new HashMap<String, String>();
	int numChangeReasons = -1;
	public RetrieveChangeReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveChangeReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all change reason descriptions
	 * @return - all change reason descriptions
	 */
	public Map<String, String> getChangeReasonCodes(){
		if(numChangeReasons == -1) getNumberOfChangeReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveChangeReasonsResponse/changeReasons/optionCode");
		for(int i = 0; i < numChangeReasons; i++){
			changeReasonDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return changeReasonDescriptions;
	}
	/**
	 * Retrieves all change reason descriptions
	 * @return - all change reason descriptions
	 */
	public Map<String, String> getChangeReasonDescriptions(){
		if(numChangeReasons == -1) getNumberOfChangeReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveChangeReasonsResponse/changeReasons/optionDescription");
		for(int i = 0; i < numChangeReasons; i++){
			changeReasonIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return changeReasonIds;
	}
	/**
	 * Retrieves the number of change reasons
	 * @return - number of change reasons
	 */
	public int getNumberOfChangeReasons(){
		numChangeReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveChangeReasonsResponse/changeReasons").getLength();
		return numChangeReasons;
	}
}