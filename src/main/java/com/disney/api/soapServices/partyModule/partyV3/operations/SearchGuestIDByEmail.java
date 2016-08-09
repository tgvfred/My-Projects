package com.disney.api.soapServices.partyModule.partyV3.operations;

import com.disney.api.soapServices.partyModule.partyV3.PartyV3;
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
	/**
	 * Retrieves all party IDs in the SOAP response
	 * @return - all party IDs in the SOAP response
	 */
	public int getNumberOfResponsePartyIds(){
		int number = 0;
		try{number = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchGuestIDByEmailResponse/return/partyIds").getLength();}catch(Exception e){}
		return number;
	}
}
