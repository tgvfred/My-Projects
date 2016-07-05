package com.disney.api.soapServices.partyV3.operations;

import com.disney.api.soapServices.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class SearchGuestIDByEmail extends PartyV3{
	public SearchGuestIDByEmail(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchGuestIDByEmail")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setGuestEmail(String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByEmail/searchGuestIDByEmailRequest/email", value);
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchGuestIDByEmailResponse/return/partyIds");
	}
}
