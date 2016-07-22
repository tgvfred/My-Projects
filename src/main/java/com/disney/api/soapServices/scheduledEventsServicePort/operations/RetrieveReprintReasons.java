package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveReprintReasons extends ScheduledEventsServicePort{
	Map<String, String> reprintReasonsCodes = new HashMap<String, String>();
	Map<String, String> reprintReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> reprintReasonsIds = new HashMap<String, String>();
	int numReprintReasons = -1;
	
	public RetrieveReprintReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveReprintReasons")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all reprint reasons codes
	 * @return - all reprint reasons codes
	 */
	public Map<String, String> getReprintReasonCodes(){
		if(numReprintReasons == -1) getNumberOfReprintReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReprintReasonsResponse/reprintReasons/optionCode");
		for(int i = 0; i < numReprintReasons; i++){
			reprintReasonsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return reprintReasonsCodes;
	}
	/**
	 * Retrieves all reprint reasons descriptions
	 * @return - all reprint reasons descriptions
	 */
	public Map<String, String> getReprintReasonDescriptions(){
		if(numReprintReasons == -1) getNumberOfReprintReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReprintReasonsResponse/reprintReasons/optionDescription");
		for(int i = 0; i < numReprintReasons; i++){
			reprintReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return reprintReasonsDescriptions;
	}
	/**
	 * Retrieves all reprint reasons ids
	 * @return - all reprint reasons ids
	 */
	public Map<String, String> getReprintReasonIds(){
		if(numReprintReasons == -1) getNumberOfReprintReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReprintReasonsResponse/reprintReasons/optionId");
		for(int i = 0; i < numReprintReasons; i++){
			reprintReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return reprintReasonsIds;
	}
	/**
	 * Retrieves the number of reprint reasons reasons
	 * @return - number of reprint reasons reasons
	 */
	public int getNumberOfReprintReasons(){
		numReprintReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReprintReasonsResponse/reprintReasons").getLength();
		return numReprintReasons;
	}
}