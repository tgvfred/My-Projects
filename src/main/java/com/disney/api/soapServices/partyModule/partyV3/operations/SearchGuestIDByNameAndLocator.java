package com.disney.api.soapServices.partyModule.partyV3.operations;

import com.disney.api.soapServices.partyModule.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class SearchGuestIDByNameAndLocator extends PartyV3{
	public SearchGuestIDByNameAndLocator(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchGuestIDByNameAndLocator")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByNameAndLocator/searchGuestIDByNameAndLocatorRequest/firstName", value);;
	}

	public void setGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByNameAndLocator/searchGuestIDByNameAndLocatorRequest/lastName", value);;
	}

	public void setLocatorType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByNameAndLocator/searchGuestIDByNameAndLocatorRequest/locatorType", value);;
	}

	public void setLocatorValue(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByNameAndLocator/searchGuestIDByNameAndLocatorRequest/locatorValue", value);;
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchGuestIDByNameAndLocatorResponse/return/partyIds");
	}
	/**
	 * Retrieves all party IDs in the SOAP response
	 * @return - all party IDs in the SOAP response
	 */
	public int getNumberOfResponsePartyIds(){
		int number = 0;
		try{number = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchGuestIDByNameAndLocatorResponse/return/partyIds").getLength();}catch(Exception e){}
		return number;
	}
}
