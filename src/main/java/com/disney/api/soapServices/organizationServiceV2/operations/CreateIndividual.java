package com.disney.api.soapServices.organizationServiceV2.operations;

import com.disney.api.soapServices.organizationServiceV2.OrganizationServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class CreateIndividual extends OrganizationServiceV2{
	public CreateIndividual(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createIndividual")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario("OrganizationServiceV2", getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public String getOdsIndividualId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/@id");
	}
	
	public String getFirstName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/individualName/@firstName");
	}
	
	public String getMiddleName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/individualName/@middleName");
	}
	
	public String getLastName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/individualName/@lastName");
	}

	public String getPhoneNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/telecomAddresses/@number");
	}
	public String getPhoneLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/telecomAddresses/telecomAddressLocator/@id");
	}
	
	public String getEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/emailAddresses/@email");
	}
	
	public String getEmailLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createIndividualResponse/createIndividualResponse/createIndividualResponseDetails/individual/emailAddresses/emailAddressLocator/@id");
	}

	public void setFirstName(String name){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/individualName/@firstName", name);
	}

	public void setMiddleName(String name){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/individualName/@middleName", name);
	}

	public void setLastName(String name){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/individualName/@lastName", name);
	}

	public void setTravelAgencyId(String agencyId){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/organizationIndividual/@organizationID", agencyId);
	}

	public void setTravelAgentRole(String role){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/organizationIndividual/@individualRole", role);
	}
	
	public void setPhoneNumber(String number){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/telecomAddresses/@number", number);
	}

	public void setEmail(String email){
		setRequestNodeValueByXPath("/Envelope/Body/createIndividual/createIndividualRequest/individual/emailAddresses/@email", email);
	}
}
