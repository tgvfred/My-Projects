package com.disney.api.soapServices.folioBankServicesV2.operations;

import com.disney.api.soapServices.folioBankServicesV2.FolioBankServicesV2;
import com.disney.utils.XMLTools;


public class IsUserBankedIn extends FolioBankServicesV2{
	
	public IsUserBankedIn(String environment){
		super(environment);
	
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("isUserBankedIn")));
		
	    generateServiceContext();			
	    //setRequestNodeValueByXPath(getTestScenario("/services/folioServicePort/bankIn/bankIn.xls", "BankIn"));
	    removeComments() ;
	    removeWhiteSpace();
	}

	
	public void setUser(String userName){
		setRequestNodeValueByXPath("/Envelope/Body/isUserBankedIn/context/userName", userName);
	}
  
	public String getLocationId(){
		return getResponseNodeValueByXPath("/Envelope/Body/isUserBankedInResponse/returnParameter");
	}
	
}