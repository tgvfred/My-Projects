package com.disney.api.soapServices.partyV3.operations;

import com.disney.api.soapServices.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class CreateAndRetrieveParty extends PartyV3{
	public CreateAndRetrieveParty(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createAndRetrieveParty")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setExternalReferenceValue(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/externalreference/externalReferenceValue", value);
	}
	
	public void setPrimaryGuestAge(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/individual/age", value);
	}
	
	public void setPrimaryGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/individual/firstName", value);
	}
	
	public void setPrimaryGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/individual/lastName", value);
	}
	
	public void setLocatorAddressLine1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/locator/addressInfo/addressLine1", value);
	}
	
	public void setLocatorCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/locator/addressInfo/city", value);
	}
	
	public void setLocatorState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/locator/addressInfo/state", value);
	}
	
	public void setLocatorZipCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/locator/addressInfo/zipCode", value);
	}
	
	public void setLocatorCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/locator/country", value);
	}
	
	public void setLocatorPhoneNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createAndRetrieveParty/createPartyRequest/locator/phoneInfo/number", value);
	}
	
	public String getPartyid(){
		return getResponseNodeValueByXPath("/Envelope/Body/createAndRetrievePartyResponse/return/party/partyId");
	}
}
