package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCancelReasons extends ScheduledEventsServicePort{
	Map<String, String> cancelReasonCodes = new HashMap<String, String>();
	Map<String, String> cancelReasonDescriptions = new HashMap<String, String>();
	Map<String, String> cancelReasonIds= new HashMap<String, String>();
	int numCancelReasons = -1;
	
	public RetrieveCancelReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCancelReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	
	
	

	/**
	 * Retrieves all cancel reason codes
	 * @return - all cancel reason codes
	 */
	public Map<String, String> getCancelReasonCodes(){
		if(numCancelReasons == -1) getNumberOfCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCancelReasonsResponse/cancelReasons/optionCode");
		for(int i = 0; i < numCancelReasons; i++){
			cancelReasonCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonCodes;
	}	
	/**
	 * Retrieves all cancel reason descriptions
	 * @return - all cancel reason descriptions
	 */
	public Map<String, String> getCancelReasonDescriptions(){
		if(numCancelReasons == -1) getNumberOfCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCancelReasonsResponse/cancelReasons/optionDescription");
		for(int i = 0; i < numCancelReasons; i++){
			cancelReasonDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonDescriptions;
	}
	/**
	 * Retrieves all cancel reason ids
	 * @return - all cancel reason ids
	 */
	public Map<String, String> getCancelReasonIds(){
		if(numCancelReasons == -1) getNumberOfCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCancelReasonsResponse/cancelReasons/optionId");
		for(int i = 0; i < numCancelReasons; i++){
			cancelReasonIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonIds;
	}
	/**
	 * Retrieves the number of cancel reasons
	 * @return - number of cancel reasons
	 */
	public int getNumberOfCancelReasons(){
		numCancelReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCancelReasonsResponse/cancelReasons").getLength();
		return numCancelReasons;
	}
}