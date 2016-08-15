package com.disney.api.soapServices.partyModule.partyV3.operations;

import com.disney.api.soapServices.partyModule.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class SearchGuestIDByName extends PartyV3{
	public SearchGuestIDByName(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchGuestIDByName")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByName/searchGuestIDByNameRequest/firstName", value);;
	}

	public void setGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByName/searchGuestIDByNameRequest/lastName", value);;
	}
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchGuestIDByNameResponse/return/partyIds");
	}
	/**
	 * Retrieves all party IDs in the SOAP response
	 * @return - all party IDs in the SOAP response
	 */
	public int getNumberOfResponsePartyIds(){
		int number = 0;
		try{number = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchGuestIDByNameResponse/return/partyIds").getLength();}catch(Exception e){}
		return number;
	}
}
