package com.disney.api.soapServices.partyModule.partyV3.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.partyModule.partyV3.PartyV3;
import com.disney.utils.XMLTools;

public class RetrievePartyByExternalGuestId extends PartyV3{
	public RetrievePartyByExternalGuestId(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrievePartyByExternalGuestId")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setExternalGuestIDAndSource(String id, String source){
		setRequestNodeValueByXPath("/Envelope/Body/retrievePartyByExternalGuestId/retrievePartyByExternalGuestIdRequest/externalGuestId", id);
		setRequestNodeValueByXPath("/Envelope/Body/retrievePartyByExternalGuestId/retrievePartyByExternalGuestIdRequest/externalReferenceSource", source);
	}
	
	public boolean isPartyFound(){
		try{
			if(getResponseNodeValueByXPath("/Envelope/Body/retrievePartyByExternalGuestIdResponse/return/message").equals("Party Information Found")) return true;
			else return false;
		}catch(XPathNotFoundException e){
			return false;
		}
	}
}
