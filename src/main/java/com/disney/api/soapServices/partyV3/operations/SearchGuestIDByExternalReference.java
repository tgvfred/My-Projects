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
	/**
	 * Retrieves all party IDs in the SOAP response
	 * @return - all party IDs in the SOAP response
	 */
	public int getNumberOfResponsePartyIds(){
		int number = 0;
		try{number = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchGuestIDByExternalReferenceResponse/return/partyIds").getLength();}catch(Exception e){}
		return number;
	}
	
	public void addExternalReferenceValue(String value){
		int index = XMLTools.getNodeList(getRequestDocument(), "/Envelope/Body/searchGuestIDByExternalReference/searchGuestIDByExternalReferenceRequest/externalRefValues").getLength();
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByExternalReference/searchGuestIDByExternalReferenceRequest", "fx:addNode;node:externalRefValues");
		setRequestNodeValueByXPath("/Envelope/Body/searchGuestIDByExternalReference/searchGuestIDByExternalReferenceRequest/externalRefValues["+String.valueOf(index+1)+"]", value);
	}
}
