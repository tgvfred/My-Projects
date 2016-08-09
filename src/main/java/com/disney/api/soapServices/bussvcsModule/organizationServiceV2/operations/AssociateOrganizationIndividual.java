package com.disney.api.soapServices.bussvcsModule.organizationServiceV2.operations;

import com.disney.api.soapServices.bussvcsModule.organizationServiceV2.OrganizationServiceV2;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class AssociateOrganizationIndividual extends OrganizationServiceV2{
	public AssociateOrganizationIndividual(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("associateOrganizationIndividual")));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		setRequestNodeValueByXPath(getTestScenario("OrganizationServiceV2", getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	
	public String getIsSuccess(){
		return getResponseNodeValueByXPath("/Envelope/Body/associateOrganizationIndividualResponse/result");
	}
	
	public void setOrganizationId(String id){
		setRequestNodeValueByXPath("/Envelope/Body/associateOrganizationIndividual/organizationIndividualRequest/organizationIndividualLink/@organizationID", id);
	}
	

	public void setIndividualId(String id){
		setRequestNodeValueByXPath("/Envelope/Body/associateOrganizationIndividual/organizationIndividualRequest/organizationIndividualLink/@individualID", id);
	}

}
