package com.disney.api.soapServices.profileServicePort.operations;

import com.disney.api.soapServices.profileServicePort.ProfileServicePort;
import com.disney.utils.XMLTools;

public class RetrieveProfilesByCode extends ProfileServicePort{
	public RetrieveProfilesByCode(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveProfilesByCode")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setProfileCode(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCode/retrieveProfilesByCodeRequest/profileCodes", value);}
	public void setIncludeInactiveProfiles(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCode/retrieveProfilesByCodeRequest/includeInactiveProfiles", value);}
	public void getCode(){getRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/code");}
	public void getDescription(){getRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/description");}
	public void getProfileId(){getRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/profileId");}
	public void getProfileType(){getRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/profileType");}
	public void getActive(){getRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/active");}
	public void getPriority(){getRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/priority");}
}