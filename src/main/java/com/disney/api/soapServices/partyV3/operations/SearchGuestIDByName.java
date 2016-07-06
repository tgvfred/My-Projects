package com.disney.api.soapServices.partyV3.operations;

import com.disney.api.soapServices.partyV3.PartyV3;
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
}
