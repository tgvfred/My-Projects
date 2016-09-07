package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveInventoryOverideReasons extends ScheduledEventsServicePort{
	Map<String, String> inventoryOverideReasonsCodes = new HashMap<String, String>();
	Map<String, String> inventoryOverideReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> inventoryOverideReasonsIds = new HashMap<String, String>();
	int numInventoryOverideReasons = -1;
	
	public RetrieveInventoryOverideReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveInventoryOverideReasons")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all inventory overide reason codes
	 * @return - all inventory overide reason codes
	 */
	public Map<String, String> getInventoryOverideReasonsCodes(){
		if(numInventoryOverideReasons == -1) getNumberOfInventoryOverideReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveInventoryOverideReasonsResponse/inventoryOverideReasons/optionCode");
		for(int i = 0; i < numInventoryOverideReasons; i++){
			inventoryOverideReasonsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return inventoryOverideReasonsCodes;
	}
	/**
	 * Retrieves all inventory overide reason descriptions
	 * @return - all inventory overide reason descriptions
	 */
	public Map<String, String> getInventoryOverideReasonsDescriptions(){
		if(numInventoryOverideReasons == -1) getNumberOfInventoryOverideReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveInventoryOverideReasonsResponse/inventoryOverideReasons/optionDescription");
		for(int i = 0; i < numInventoryOverideReasons; i++){
			inventoryOverideReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return inventoryOverideReasonsDescriptions;
	}
	/**
	 * Retrieves all inventory overide reason ids
	 * @return - all inventory overide reason ids
	 */
	public Map<String, String> getInventoryOverideReasonsIds(){
		if(numInventoryOverideReasons == -1) getNumberOfInventoryOverideReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveInventoryOverideReasonsResponse/inventoryOverideReasons/optionId");
		for(int i = 0; i < numInventoryOverideReasons; i++){
			inventoryOverideReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return inventoryOverideReasonsIds;
	}
	/**
	 * Retrieves the number of inventory overide reason codes
	 * @return - number of inventory overide reason codes
	 */
	public int getNumberOfInventoryOverideReasons(){
		numInventoryOverideReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveInventoryOverideReasonsResponse/inventoryOverideReasons").getLength();
		return numInventoryOverideReasons;
	}
}