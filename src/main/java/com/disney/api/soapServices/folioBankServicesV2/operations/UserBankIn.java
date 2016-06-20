package com.disney.api.soapServices.folioBankServicesV2.operations;

import com.disney.api.soapServices.folioBankServicesV2.FolioBankServicesV2;
import com.disney.utils.XMLTools;


public class UserBankIn extends FolioBankServicesV2{
	
	public UserBankIn(String environment){
		super(environment);
	
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bankIn")));
	
	    generateServiceContext();			
//	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), "BankIn"));
	    setRequestNodeValueByXPath(getTestScenario("FolioService", getOperation(), "BankIn"));
	    removeComments() ;
	    removeWhiteSpace();
	}
	//Set request parameter 
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/bankIn/bankInRequest/locationID", locationId);
	}
	
	public void setUser(String userName){
		setRequestNodeValueByXPath("/Envelope/Body/bankIn/context/userName", userName);
	}
  
	
}