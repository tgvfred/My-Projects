package com.disney.api.soapServices.guestAccessControlService.operations;

import com.disney.api.soapServices.guestAccessControlService.GuestAccessControlServiceV1;
import com.disney.utils.XMLTools;


public class RetrieveDetails extends GuestAccessControlServiceV1{
			
	public RetrieveDetails(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveDetails")));

		generateServiceContext();
		
		//DJS - code removed since it is not needed for setting up the actual request. We are setting the fields in the request as needed before it is sent.
		//setRequestNodeValueByXPath(getTestScenario("/services/guestAccessControlService/retrieveDetails/retrieveDetailsInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
		}
	
		public void setPartyId(String partyId){
			setRequestNodeValueByXPath("//request/mediaGuests/partyIds", partyId);
		}
		
		public void setKttwId(String kttwId){
			setRequestNodeValueByXPath("//request/mediaGuests/partyIds", kttwId);
		}
		
		public void setTravelPlanId(String travelPlanId){
			setRequestNodeValueByXPath("//request/mediaGuests/partyIds", travelPlanId);
		}
		
		public void setManufacturerId(String manufacturerId){
			setRequestNodeValueByXPath("//request/mediaGuests/manufacturerIds", manufacturerId);
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
		
		public String getKttwId(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/kttwDetails/@kttwId");
		}

}
