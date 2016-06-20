package com.disney.api.soapServices.keyCuttingService.operations;

import com.disney.api.soapServices.keyCuttingService.KeyCuttingService;
import com.disney.utils.XMLTools;

public class IsNewKeyRequired extends KeyCuttingService{
	public IsNewKeyRequired(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("isNewKeyRequired")));

		generateServiceContext();	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setResourceNumber(String resourceNumber){
		setRequestNodeValueByXPath("/Envelope/Body/isNewKeyRequired/newRoomAccessList/@resourceNumber", resourceNumber);
	}
	
	public void setFacilityId(String facilityId){
		setRequestNodeValueByXPath("/Envelope/Body/isNewKeyRequired/newRoomAccessList/@facilityId", facilityId);
	}	
	
	public void setStartAndEndDaysOut(String startDaysOut, String endDaysOut){
		setRequestNodeValueByXPath("/Envelope/Body/isNewKeyRequired/newRoomAccessList/@startDate","fx:GetDate; Daysout:" + startDaysOut );
		setRequestNodeValueByXPath("/Envelope/Body/isNewKeyRequired/newRoomAccessList/@endDate","fx:GetDate; Daysout:" +  endDaysOut);
	}
	
	public String getIsKeyRequiredResult(){
		return getResponseNodeValueByXPath("/Envelope/Body/isNewKeyRequiredResponse/returnParameter");
	}
}
