package com.disney.api.soapServices.experienceMediaServiceV1.operations;

import com.disney.api.soapServices.experienceMediaServiceV1.ExperienceMediaServiceV1;
import com.disney.utils.XMLTools;

public class GrantNewAccess extends ExperienceMediaServiceV1{
	String encodingStatus = "";
	String encodingError = "";
	public GrantNewAccess(String environment, String scenario){
		super(environment);
		//UI Booking
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("grantNewAccess")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("//grantMediaAccessRequestDetails/@locationId", locationId);
	}
	
	public void setTcgId(String TCG_ID){
		setRequestNodeValueByXPath("//grantMediaAccessRequestDetails/@travelComponentGroupingId", TCG_ID);
	}
	
	public void setGuestId(String Guest_ID){
		setRequestNodeValueByXPath("//grantMediaAccessRequestDetails/@txnGuestId", Guest_ID);
	}
	
	public void setTpId(String TP_ID){
		setRequestNodeValueByXPath("//grantMediaAccessRequestDetails/@travelPlanId", TP_ID);
	}
	
	public void setEncoderId(String encoderId){
		setRequestNodeValueByXPath("//grantMediaAccessRequestDetails/@encoderId", encoderId);
	}
	
	public void setKttwId(String kttwId){
		setRequestNodeValueByXPath("//experienceMediaIdReference/@mediaId", kttwId);
	}
	
	public void setFacilityId(String facilityId){
		setRequestNodeValueByXPath("//resourceAccessDetails/@facilityId", facilityId);
	}
	
	public void setResourceId(String resourceId){
		setRequestNodeValueByXPath("//resourceAccessDetails/@resourceNumber", resourceId);
	}
	
	public void setRoomId(String roomId){
		setRequestNodeValueByXPath("//resourceAccessDetails/@id", roomId);
	}
	
	public void setStartDate(String startDate){
		setRequestNodeValueByXPath("//accessPeriod/@startDate", startDate);
	}
	
	public void setEndDate(String endDate){
		setRequestNodeValueByXPath("//accessPeriod/@endDate", endDate);
	}	

	public String getEncodingError(){
		return  getResponseNodeValueByXPath("//grantMediaAccessResponseDetails/@encodingStatus");
	}
	
	public String getEncodingStatus(){
		return getResponseNodeValueByXPath("//grantMediaAccessResponseDetails/@encodingStatus");
	}
	
//	public String get
	
}