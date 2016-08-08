package com.disney.api.soapServices.profileModule.profileServicePort.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.profileModule.profileServicePort.ProfileServicePort;
import com.disney.utils.XMLTools;

public class RetrieveProfilesByRoutingType extends ProfileServicePort{
	public RetrieveProfilesByRoutingType(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveProfilesByRoutingType")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets profile type in the SOAP request
	 * @param value - profile type 
	 */
	public void setProfileType(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingType/retrieveProfilesByRoutingTypeRequest/profileType", value);}
	/**
	 * Sets routing type in the SOAP request
	 * @param value - routing type 
	 */
	public void setRoutingType(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingType/retrieveProfilesByRoutingTypeRequest/routingType", value);}
	/**
	 * Sets profile code in the SOAP request
	 * @param value - profile code 
	 */
	public void setProfileCode(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingType/retrieveProfilesByRoutingTypeRequest/profileCode", value);}
	/**
	 * Sets the flag that determines if inactive profiles are to be included, in the SOAP request
	 * @param value - flag that determines if inactive profiles are to be included
	 */
	public void setIncludeInactiveProfiles(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingType/retrieveProfilesByRoutingTypeRequest/includeInactiveProfiles", value);}
	/**
	 * Gets the profile code from the SOAP response
	 * @return - profile code
	 */
	public String getCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/code");}
	/**
	 * Gets the profile description from the SOAP response
	 * @return - profile description
	 */
	public String getDescription(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/description");}
	/**
	 * Gets the profile ID from the SOAP response
	 * @return - profile ID
	 */
	public String getProfileId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/profileId");}
	/**
	 * Gets the profile type from the SOAP response
	 * @return - profile type
	 */
	public String getProfileType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/profileType");}
	/**
	 * Gets the profile routing type from the SOAP response
	 * @return - profile routing type 
	 */
	public String getRoutingType(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/routing/routingType");}
	/**
	 * Gets the boolean string indicating if a profile is active, from the SOAP response
	 * @return - boolean string indicating if a profile is active
	 */
	public String getActive(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/active");}
	/**
	 * Gets the boolean string indicating if a profile is a priority, from the SOAP response
	 * @return - boolean string indicating if a profile is a priority
	 */
	public String getPriority(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails/priority");}
	/**
	 * Retrieves all profileDetails nodes in the SOAP response
	 * @return - all profileDetails nodes in the SOAP response
	 */
	public NodeList getProfileDetailsNode(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveProfilesByRoutingTypeResponse/return/profileDetails");}
	/**
	 * Gets the number of profileDetails nodes in the SOAP response
	 * @return - number of profileDetails nodes in the SOAP response
	 */
	public int getNumberofProfileDetailsNodes(){
		try{return getProfileDetailsNode().getLength();}
		catch(Exception e){return 0;}
	}
}
