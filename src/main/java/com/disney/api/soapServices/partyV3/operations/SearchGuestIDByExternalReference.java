package com.disney.api.soapServices.partyV3.operations;

import com.disney.api.soapServices.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class SearchGuestIDByExternalReference extends PartyV3{
	public SearchGuestIDByExternalReference(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchGuestIDByExternalReference")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setGuestExternalReference(String type, String value){
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByExternalReference/searchGuestIDByExternalReferenceRequest/externalRefSourceType", type);
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByExternalReference/searchGuestIDByExternalReferenceRequest/externalRefValues", value);
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchGuestIDByExternalReferenceResponse/return/partyIds");
	}
}
