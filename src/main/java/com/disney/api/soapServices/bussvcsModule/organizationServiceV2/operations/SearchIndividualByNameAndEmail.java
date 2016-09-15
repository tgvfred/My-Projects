package com.disney.api.soapServices.bussvcsModule.organizationServiceV2.operations;

import com.disney.api.soapServices.bussvcsModule.organizationServiceV2.OrganizationServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class SearchIndividualByNameAndEmail  extends OrganizationServiceV2{
	public SearchIndividualByNameAndEmail(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchIndividualByNameAndEmail")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario("OrganizationServiceV2", getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public String getOdsIndividualId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/@id");
	}

	public String getPrefix(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/individualName/@salutationType");
	}
	
	public String getFirstName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/individualName/@firstName");
	}

	public String getLastName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/individualName/@lastName");
	}
	public String getAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/@addressLine1");
	}
	
	public String getCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchIndividualResponseDetails/individuals/organizations/postalAddresses/@city");
	}
	
	public String getState(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/state/@code");
	}
	
	public String getCountry(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/country/@code");
	}
	
	public String getPostalCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/postalCode/@code");
	}
	
	public String getAddressLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchIndividualResponseDetails/individuals/postalAddress/postalAddressLocator/@id");
	}

	public String getPhoneNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/telecomAddresses/@number");
	}
	public String getPhoneLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/telecomAddresses/telecomAddressLocator/@id");
	}
	
	public String getEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchIndividualResponseDetails/individuals/emailAddresses/@email");
	}
	
	public String getEmailLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchIndividualResponseDetails/individuals/emailAddresses/emailAddressLocator/@id");
	}
	
	public String getAgencyOdsID(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/@id");
	}

	public String getAgencyName(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/@name");
	}

	public String getAgencyAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/@addressLine1");
	}

	public String getAgencyCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/@city");
	}

	public String getAgencyState(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/state/@code");
	}

	public String getAgencyPostalCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmailResponse/searchIndividualResponse/searchindividualResponseDetails/individuals/organizations/postalAddresses/postalCode/@code");
	}	
	
	public void setFirstName(String firstName){
		setRequestNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmail/searchIndividualRequest/@firstName", firstName);
	}

	public void setLastName(String lastName){
		setRequestNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmail/searchIndividualRequest/@lastName", lastName);
	}
	
	public void setEmail(String email){
		setRequestNodeValueByXPath("/Envelope/Body/searchIndividualByNameAndEmail/searchIndividualRequest/@email", email);
	}

}