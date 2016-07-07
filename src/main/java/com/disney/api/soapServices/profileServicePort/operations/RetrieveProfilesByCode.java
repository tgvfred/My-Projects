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
	/**
	 * Sets the profile code for which to search, in the SOAP request
	 * @param value - profile code for which to search
	 */
	public void setProfileCode(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCode/retrieveProfilesByCodeRequest/profileCodes", value);}
	/**
	 * Sets the flag to include inactive profiles in the SOAP request
	 * @param value - flag to include inactive profiles 
	 */
	public void setIncludeInactiveProfiles(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByCode/retrieveProfilesByCodeRequest/includeInactiveProfiles", value);}
	/**
	 * Gets the profile code from the SOAP response
	 * @return - profile code
	 */
	public String getCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/code");}
	/**
	 * Gets the profile description from the SOAP response
	 * @return - profile description
	 */
	public String getDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/description");}
	/**
	 * Gets the profile ID from the SOAP response
	 * @return - profile ID
	 */
	public String getProfileId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/profileId");}
	/**
	 * Gets the profile type from the SOAP response
	 * @return - profile type
	 */
	public String getProfileType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/profileType");}
	/**
	 * Gets the boolean string indicating if a profile is active, from the SOAP response
	 * @return - boolean string indicating if a profile is active
	 */
	public String getActive(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/active");}
	/**
	 * Gets the boolean string indicating if a profile is a priority, from the SOAP response
	 * @return - boolean string indicating if a profile is a priority
	 */
	public String getPriority(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByCodeResponse/return/profileDetails/priority");}
}