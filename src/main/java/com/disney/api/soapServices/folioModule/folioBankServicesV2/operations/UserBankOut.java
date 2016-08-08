package com.disney.api.soapServices.folioModule.folioBankServicesV2.operations;

import com.disney.api.soapServices.folioModule.folioBankServicesV2.FolioBankServicesV2;
import com.disney.utils.XMLTools;


public class UserBankOut extends FolioBankServicesV2{
	String encodingStatus = "";
	String encodingError = "";
	public UserBankOut(String environment){
		super(environment);
		//UI Booking
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bankOut")));
	
		
		generateServiceContext();			
//		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), "Bank Out"));
		setRequestNodeValueByXPath(getTestScenario("FolioService", getOperation(), "Bank Out"));
		removeComments() ;
		removeWhiteSpace();
	}
	//Set request parameter 
	public void setBagnumber(String banNumber){
		setRequestNodeValueByXPath("/Envelope/Body/bankOut/bankOutRequest/bagNumber", banNumber);
	}
	
	public void setUser(String userName){
		setRequestNodeValueByXPath("/Envelope/Body/bankOut/context/userName", userName);
	}
   // Get Response 
	public String getStatus(){
		return getResponseNodeValueByXPath("/Envelope/Body/bankOutResponse/returnParameter/currentBankOut");
    }
	
	public String getMessage(){
		return getResponseNodeValueByXPath("/Envelope/Body/Fault/faultstring");
    }
	
}