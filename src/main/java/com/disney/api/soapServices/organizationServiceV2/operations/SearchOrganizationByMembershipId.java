package com.disney.api.soapServices.organizationServiceV2.operations;

import com.disney.api.soapServices.organizationServiceV2.OrganizationServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class SearchOrganizationByMembershipId  extends OrganizationServiceV2{
	public SearchOrganizationByMembershipId(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("searchOrganizationByMembershipId")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario("OrganizationServiceV2", getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public String getOdsOrganizationId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/@id");
	}

	public String getId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/memberships/@programNumber");
	}

	public String getName(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/@name");
	}
	
	public String getAddress1(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/postalAddresses/@addressLine1");
	}
	
	public String getCity(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/postalAddresses/@city");
	}
	
	public String getState(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/postalAddresses/state/@code");
	}
	
	public String getCountry(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/postalAddresses/country/@code");
	}
	
	public String getPostalCode(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/postalAddresses/postalCode/@code");
	}
	
	public String getAddressLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/postalAddresses/postalAddressLocator/@id");
	}

	public String getPhoneNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/telecomAddresses/@number");
	}
	public String getPhoneLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/telecomAddresses/telecomAddressLocator/@id");
	}
	
	public String getEmail(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/emailAddresses/@email");
	}
	
	public String getEmailLocatorId(){
		return getResponseNodeValueByXPath("/Envelope/Body/createOrganizationResponse/createOrganizationResponse/organization/emailAddresses/emailAddressLocator/@id");
	}

	public void setProgramId(String id){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/memberships/@programNumber", id);
	}
	
	public void setProgramType(String type){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/memberships/@programName", type);
	}
	
	public void setName(String name){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/@name", name);
	}

	public void setAddress1(String address1){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/postalAddresses/@addressLine1", address1);
	}

	public void setCity(String city){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/postalAddresses/@city", city);
	}

	public void setState(String state){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/postalAddresses/@state", state);
	}

	public void setCountry(String country){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/postalAddresses/@country", country);
	}

	public void setPostalCode(String postalCode){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/postalAddresses/postalCode/@code", postalCode);
	}

	public void setPhoneNumber(String number){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/telecomAddresses/@number", number);
	}

	public void setEmail(String email){
		setRequestNodeValueByXPath("/Envelope/Body/createOrganization/createOrganizationRequest/organization/emailAddresses/@email", email);
	}
}
