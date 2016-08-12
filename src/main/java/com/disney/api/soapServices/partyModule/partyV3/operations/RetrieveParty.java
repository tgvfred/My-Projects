package com.disney.api.soapServices.partyModule.partyV3.operations;

import com.disney.api.soapServices.partyModule.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class RetrieveParty extends PartyV3{
	public RetrieveParty(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveParty")));
	//	setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setPartyId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveParty/partyId", value);
	}
	
	public String getMessage(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/message");
	}

	public String getExternalReferenceValue(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/externalreference/externalReferenceValue");
	}

	public String getGuestCountry(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/locatorInfo/addressInfo/../country");
	}
	
	public String getGuestAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/locatorInfo/addressInfo/addressLine1");
	}

	public String getGuestCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/locatorInfo/addressInfo/city");
	}

	public String getGuestRegion(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/locatorInfo/addressInfo/regionName");
	}

	public String getGuestState(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/locatorInfo/addressInfo/state");
	}

	public String getGuestZipCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrievePartyResponse/return/party/locatorInfo/addressInfo/zipCode");
	}
}
