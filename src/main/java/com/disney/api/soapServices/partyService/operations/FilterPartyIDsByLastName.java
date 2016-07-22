package com.disney.api.soapServices.partyService.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class FilterPartyIDsByLastName extends PartyV3{
	public FilterPartyIDsByLastName(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("filterPartyIDsByLastName")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets the guest last name in the SOAP request
	 * @param value - guest last name
	 */
	public void setLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/filterPartyIDsByLastName/filterPartyIDsByLastNameRequest/lastName", value);}
	/**
	 * Sets the guest party ID in the SOAP request
	 * @param value - guest party ID
	 */
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/filterPartyIDsByLastName/filterPartyIDsByLastNameRequest/partyIds", value);}
	/**
	 * Adds a guest party ID node and sets the value in the SOAP REQuest
	 * @param value - guest party ID
	 */
	public void addPartyIdNodeAndSetValue(String value){
		int ids = XMLTools.getNodeList(getRequestDocument(), "/Envelope/Body/filterPartyIDsByLastName/filterPartyIDsByLastNameRequest/partyIds").getLength();
		setRequestNodeValueByXPath("/Envelope/Body/filterPartyIDsByLastName/filterPartyIDsByLastNameRequest", "fx:addnode;node:partyIds");
		setRequestNodeValueByXPath("/Envelope/Body/filterPartyIDsByLastName/filterPartyIDsByLastNameRequest/partyIds["+String.valueOf(ids+1)+"]", value);
	}
	/**
	 * Retrieves the guest party ID from the SOAP response
	 * @return - guest party id
	 */
	public String getResponsePartyId(){return getResponseNodeValueByXPath("/Envelope/Body/filterPartyIDsByLastNameResponse/return/partyIds");}
	/**
	 * Retrieves a specific guest party ID node value from the SOAP response
	 * @return - guest party id
	 */
	public String getResponsePartyId(String index){return getResponseNodeValueByXPath("/Envelope/Body/filterPartyIDsByLastNameResponse/return/partyIds["+index+"]");}
	/**
	 * Retrieves all party IDs in the SOAP response
	 * @return - all party IDs in the SOAP response
	 */
	public NodeList getResponsePartyIds(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/filterPartyIDsByLastNameResponse/return/partyIds");}
	/**
	 * Retrieves all party IDs in the SOAP response
	 * @return - all party IDs in the SOAP response
	 */
	public int getNumberOfResponsePartyIds(){
		int number = 0;
		try{number = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/filterPartyIDsByLastNameResponse/return/partyIds").getLength();}catch(Exception e){}
		return number;
	}
}
