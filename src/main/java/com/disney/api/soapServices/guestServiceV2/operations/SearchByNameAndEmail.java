package com.disney.api.soapServices.guestServiceV2.operations;

import com.disney.api.soapServices.guestServiceV2.GuestServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class SearchByNameAndEmail extends GuestServiceV2{
	public SearchByNameAndEmail(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchByNameAndEmail")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public String getOdsGuestId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/@id");
	}

	public String getPrefix(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@salutationType");
	}
	
	public String getFirstName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@firstName");
	}

	public String getMiddleName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@middleName");
	}

	public String getLastName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestName/@lastName");
	}

	public String getNotPhone(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotPhone");
	}

	public String getDoNotEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotEmail");
	}

	public String getDoNotMail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotMail");
	}
	
	public String getDoNotContact(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/guestPrivacyPolicy/@doNotContact");
	}
	
	public String getAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@addressLine1");
	}
	
	public String getCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@city");
	}
	
	public String getState(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@state");
	}
	
	public String getCountry(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/@country");
	}
	
	public String getPostalCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/postalCode/@code");
	}
	
	public String getAddressLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/postalAddress/postalAddressLocator/@id");
	}

	public String getPhoneNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/telecomAddress/@number");
	}
	public String getPhoneLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/telecomAddress/telecomAddressLocator/@id");
	}
	
	public String getEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/emailAddress/@email");
	}
	
	public String getEmailLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchByNameAndEmailResponse/searchGuestResponse/searchGuestResponseDetails/guest/emailAddress/emailAddressLocator/@id");
	}

	public void setFirstName(String firstName){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndEmail/searchGuestRequest/@firstName", firstName);
	}

	public void setLastName(String lastName){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndEmail/searchGuestRequest/@lastName", lastName);
	}
	
	public void setEmail(String email){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndEmail/searchGuestRequest/@email", email);
	}


	public void setPostalCode(String postalCode){
		setRequestNodeValueByXPath("/Envelope/Body/searchByNameAndEmail/searchGuestRequest/@postalCode", postalCode);
	}

	
}
