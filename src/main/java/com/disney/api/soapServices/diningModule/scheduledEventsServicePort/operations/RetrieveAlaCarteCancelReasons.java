package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAlaCarteCancelReasons extends ScheduledEventsServicePort{
	int numberOfReasons = -1;
	
	public RetrieveAlaCarteCancelReasons(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAlaCarteCancelReasons")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Returns the number of ALC cancellation reasons
	 * @return - number of ALC cancellation reasons
	 */
	public int getNumberOfAlcCancelReasons(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAlaCarteCancelReasonsResponse/alaCarteCancelReasons").getLength();}	
	/**
	 * Retrieves all cancellation reason option codes
	 * @return - all cancellation reason option codes
	 */
	public Map<String, String> getAllOptionCodes(){
		if(numberOfReasons == -1) numberOfReasons = getNumberOfAlcCancelReasons();
		Map<String, String> optionsCodes = new HashMap<String, String>();
		for(int code = 1; code <= numberOfReasons; code++){
			optionsCodes.put(String.valueOf(code), getResponseNodeValueByXPath("/Envelope/Body/retrieveAlaCarteCancelReasonsResponse/alaCarteCancelReasons["+String.valueOf(code)+"]/optionCode"));
		}
		return optionsCodes;
	}	
	/**
	 * Retrieves all cancellation reason option descriptions
	 * @return - all cancellation reason option descriptions
	 */
	public Map<String, String> getAllOptionDescriptions(){
		if(numberOfReasons == -1) numberOfReasons = getNumberOfAlcCancelReasons();
		Map<String, String> optionsDescriptions = new HashMap<String, String>();
		for(int code = 1; code <= numberOfReasons; code++){
			optionsDescriptions.put(String.valueOf(code), getResponseNodeValueByXPath("/Envelope/Body/retrieveAlaCarteCancelReasonsResponse/alaCarteCancelReasons["+String.valueOf(code)+"]/optionDescription"));
		}
		return optionsDescriptions;
	}	
	/**
	 * Retrieves all cancellation reason option ids
	 * @return - all cancellation reason option ids
	 */
	public Map<String, String> getAllOptionIds(){
		if(numberOfReasons == -1) numberOfReasons = getNumberOfAlcCancelReasons();
		Map<String, String> optionIds = new HashMap<String, String>();
		for(int code = 1; code <= numberOfReasons; code++){
			optionIds.put(String.valueOf(code), getResponseNodeValueByXPath("/Envelope/Body/retrieveAlaCarteCancelReasonsResponse/alaCarteCancelReasons["+String.valueOf(code)+"]/optionId"));
		}
		return optionIds;
	}
}