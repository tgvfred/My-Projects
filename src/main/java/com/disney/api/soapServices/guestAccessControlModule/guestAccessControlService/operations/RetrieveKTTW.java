package com.disney.api.soapServices.guestAccessControlModule.guestAccessControlService.operations;

import com.disney.api.soapServices.guestAccessControlModule.guestAccessControlService.GuestAccessControlServiceV1;
import com.disney.utils.XMLTools;


public class RetrieveKTTW extends GuestAccessControlServiceV1{
				
	public RetrieveKTTW(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveKTTW")));

		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
			
	public void setPartyId(String partyId){
		setRequestNodeValueByXPath("//retrieveKTTWRequest/partyId", partyId);
	}
	
	public void setKttwId(String kttwId){
		setRequestNodeValueByXPath("//retrieveKTTWRequest/kttwId", kttwId);
	}
	
	public void setTravelPlanId(String travelPlanId){
		setRequestNodeValueByXPath("//retrieveKTTWRequest/tpId", travelPlanId);
	}
	
}
