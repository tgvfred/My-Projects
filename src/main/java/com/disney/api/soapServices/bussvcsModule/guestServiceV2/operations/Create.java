package com.disney.api.soapServices.bussvcsModule.guestServiceV2.operations;

import com.disney.api.soapServices.bussvcsModule.guestServiceV2.GuestServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class Create extends GuestServiceV2{
	public Create(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("create")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public String getOdsGuestId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/@id");
	}

	public String getPrefix(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestName/@salutationType");
	}
	
	public String getFirstName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestName/@firstName");
	}

	public String getMiddleName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestName/@middleName");
	}

	public String getLastName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestName/@lastName");
	}

	public String getNotPhone(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestPrivacyPolicy/@doNotPhone");
	}

	public String getDoNotEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestPrivacyPolicy/@doNotEmail");
	}

	public String getDoNotMail(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestPrivacyPolicy/@doNotMail");
	}
	
	public String getDoNotContact(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/guestPrivacyPolicy/@doNotContact");
	}
	
	public String getAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/postalAddress/@addressLine1");
	}
	
	public String getCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/postalAddress/@city");
	}
	
	public String getState(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/postalAddress/@state");
	}
	
	public String getCountry(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/postalAddress/@country");
	}
	
	public String getPostalCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/postalAddress/postalCode/@code");
	}
	
	public String getAddressLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/postalAddress/postalAddressLocator/@id");
	}

	public String getPhoneNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/telecomAddress/@number");
	}
	public String getPhoneLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/telecomAddress/telecomAddressLocator/@id");
	}
	
	public String getEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/emailAddress/@email");
	}
	
	public String getEmailLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createResponse/createGuestResponse/createGuestResponseDetails/guest/emailAddress/emailAddressLocator/@id");
	}

	public void setPrefix(String prefix){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/guestName/@salutationType", prefix); 
	}
	public void setFirstName(String firstName){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/guestName/@firstName", firstName);
	}

	public void setMiddleName(String middleName){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/guestName/@middleName", middleName);
	}

	public void setLastName(String lastName){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/guestName/@lastName", lastName);
	}

	public void setAge(String age){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/@statedAge", age);
	}

	public void setAddress1(String address1){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/postalAddress/@addressLine1", address1);
	}

	public void setCity(String city){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/postalAddress/@city", city);
	}

	public void setState(String state){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/postalAddress/@state", state);
	}

	public void setCountry(String country){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/postalAddress/@country", country);
	}

	public void setPostalCode(String postalCode){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/postalAddress/postalCode/@code", postalCode);
	}

	public void setPhoneNumber(String number){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/telecomAddress/@number", number);
	}

	public void setEmail(String email){
		setRequestNodeValueByXPath("/Envelope/Body/create/createGuestRequest/guest/emailAddress/@email", email);
	}
}
