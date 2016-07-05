package com.disney.api.soapServices.partyV3.operations;


import com.disney.api.soapServices.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class CreateParty extends PartyV3{
	public CreateParty(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createParty")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setExternalReferenceValue(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/externalreference/externalReferenceValue", value);
	}
	
	public void setPrimaryGuestAge(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/individual/age", value);
	}
	
	public void setPrimaryGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/individual/firstName", value);
	}
	
	public void setPrimaryGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/individual/lastName", value);
	}
	
	public void setPrimaryGuestPrefix(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/individual/salutation", value);
	}
	
	public void setAddressLine1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[1]/addressInfo/addressLine1", value);
	}
	
	public void setAddressCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[1]/addressInfo/city", value);
	}
	
	public void setAddressState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[1]/addressInfo/state", value);
	}
	
	public void setAddressZipCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[1]/addressInfo/zipCode", value);
	}
	
	public void setAddressLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[1]/locatorId", value);
	}
	
	public void setAddressCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[1]/country", value);
	}
	
	public void setPhoneNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[2]/phoneInfo/number", value);
	}
	
	public void setPhoneNumberLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[2]/locatorId", value);
	}
	public void setEmailAddress(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[3]/emailInfo/emailAddress", value);
	}
	
	public void setEmailAddressLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/createParty/partyRequest/locator[3]/locatorId", value);
	}
	
	public String getPartyid(){
		return getResponseNodeValueByXPath("/Envelope/Body/createPartyResponse/return/partyId");
	}
}
