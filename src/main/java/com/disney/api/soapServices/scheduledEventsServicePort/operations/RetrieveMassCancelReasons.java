package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveMassCancelReasons extends ScheduledEventsServicePort{
	Map<String, String> massCancelReasonsCode = new HashMap<String, String>();
	Map<String, String> massCancelReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> massCancelReasonsIds = new HashMap<String, String>();
	int numMassCancelsReasons = -1;
	
	public RetrieveMassCancelReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveMassCancelReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all mass cancel codes
	 * @return - all mass cancel codes
	 */
	public Map<String, String> getMassCancelCodes(){
		if(numMassCancelsReasons == -1) getNumberOfMassCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveMassCancelReasonsResponse/massCancelReasons/optionCode");
		for(int i = 0; i < numMassCancelsReasons; i++){
			massCancelReasonsCode.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return massCancelReasonsCode;
	}
	/**
	 * Retrieves all mass cancel descriptions
	 * @return - all mass cancel descriptions
	 */
	public Map<String, String> getMassCancelDescriptions(){
		if(numMassCancelsReasons == -1) getNumberOfMassCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveMassCancelReasonsResponse/massCancelReasons/optionDescription");
		for(int i = 0; i < numMassCancelsReasons; i++){
			massCancelReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return massCancelReasonsDescriptions;
	}
	/**
	 * Retrieves all mass cancel ids
	 * @return - all mass cancel ids
	 */
	public Map<String, String> getMassCancelIds(){
		if(numMassCancelsReasons == -1) getNumberOfMassCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveMassCancelReasonsResponse/massCancelReasons/optionId");
		for(int i = 0; i < numMassCancelsReasons; i++){
			massCancelReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return massCancelReasonsIds;
	}
	/**
	 * Retrieves the number of mass cancel reasons
	 * @return - number of mass cancel reasons
	 */
	public int getNumberOfMassCancelReasons(){
		numMassCancelsReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveMassCancelReasonsResponse/massCancelReasons").getLength();
		return numMassCancelsReasons;
	}
}