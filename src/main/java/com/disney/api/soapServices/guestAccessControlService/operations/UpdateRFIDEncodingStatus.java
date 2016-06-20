package com.disney.api.soapServices.guestAccessControlService.operations;

import com.disney.api.soapServices.guestAccessControlService.GuestAccessControlServiceV1;
import com.disney.utils.XMLTools;


public class UpdateRFIDEncodingStatus extends GuestAccessControlServiceV1{
	public UpdateRFIDEncodingStatus(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateRFIDEncodingStatus")));

		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		}
	
		public void setRfidMediaId(String partyId){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/rfidMediaId", partyId);
		}
		
		public void setManufacturerId(String manufacturerId){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/manufacturerUID", manufacturerId);
		}
		
		public void setGuestId(String guestId){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/guestId", guestId);
		}
		
		public void setAccessEncodingStatus(String accessEncodingStatus){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessEncodingStatus", accessEncodingStatus);
		}
		
		public void setAcessUsageStartDaysOut(String accessUsageStartDaysOut){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessUsageDate", "fx:GetDate; Daysout:" + accessUsageStartDaysOut);
		}
		
		public void setFacilityId(String facilityId){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/facilityId", facilityId);
		}
		
		public void setKttwId(String kttwId){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/kttwId", kttwId);
		}
		
		public void setMediaAccessId(String mediaAccessId){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/mediaAccessId", mediaAccessId);
		}	
	
		public void setGuestRoomIndicator(String guestRoomIndicator){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/guestRoomIndicator", guestRoomIndicator);
		}	
		
		public void setEndDaysOut(String endDaysOut){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/accessPeriod/endDate", endDaysOut);
		}	
		
		public void setStartDaysOut(String startDaysOut){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/accessPeriod/startDate", startDaysOut);
		}	
		
		public void setResourceNumber(String resourceNumber){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/resourceNumber", resourceNumber);
		}	
		
		public void setResourceName(String resourceName){
			setRequestNodeValueByXPath("/Envelope/Body/updateRFIDEncodingStatus/request/mediaAccessEncodingDetails/accessDetail/resourceName", resourceName);
		}	
		
		public String getRfidMediaId(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/return/guestMediaDetails/rfidDetails/rfidMediaId");
		}
		
		public String getAccessEncodingStatus(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/return/guestMediaDetails/rfidDetails/mediaAccessEncodingDetails/accessEncodingStatus");
		}

		public String getAccessMediaId(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/return/guestMediaDetails/rfidDetails/mediaAccessEncodingDetails/accessDetail/mediaAccessId");
		}
}
