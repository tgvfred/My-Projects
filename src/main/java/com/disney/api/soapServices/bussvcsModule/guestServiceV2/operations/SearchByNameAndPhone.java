package com.disney.api.soapServices.bussvcsModule.guestServiceV2.operations;

import com.disney.api.soapServices.bussvcsModule.guestServiceV2.GuestServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class SearchByNameAndPhone extends GuestServiceV2{
	public SearchByNameAndPhone(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByNameAndPhone")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public String getOdsGuestId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/@id");
	}

	public String getPrefix(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@salutationType");
	}
	
	public String getFirstName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@firstName");
	}

	public String getMiddleName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@middleName");
	}

	public String getLastName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@lastName");
	}

	public String getNotPhone(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotPhone");
	}

	public String getDoNotEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotEmail");
	}

	public String getDoNotMail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotMail");
	}
	
	public String getDoNotContact(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotContact");
	}
	
	public String getAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@addressLine1");
	}
	
	public String getCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@city");
	}
	
	public String getState(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@state");
	}
	
	public String getCountry(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@country");
	}
	
	public String getPostalCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/postalCode/@code");
	}
	
	public String getAddressLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/postalAddressLocator/@id");
	}

	public String getPhoneNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/telecomAddress/@number");
	}
	public String getPhoneLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/telecomAddress/telecomAddressLocator/@id");
	}
	
	public String getEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/emailAddress/@email");
	}
	
	public String getEmailLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndPhoneResponse/searchGuestResponse/searchGuestResponseDetails/guest/emailAddress/emailAddressLocator/@id");
	}

	public void setFirstName(String firstName){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndPhone/searchGuestRequest/@firstName", firstName);
	}

	public void setLastName(String lastName){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndPhone/searchGuestRequest/@lastName", lastName);
	}
	
	public void setPhoneNumber(String phone){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndPhone/searchGuestRequest/@phoneNumber", phone);
	}


	public void setPostalCode(String postalCode){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndPhone/searchGuestRequest/@postalCode", postalCode);
	}

	
}
