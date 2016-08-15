package com.disney.api.soapServices.accommodationModule.experienceMediaServiceV1.operations;

import java.util.Arrays;

import com.disney.api.soapServices.accommodationModule.experienceMediaServiceV1.ExperienceMediaServiceV1;
import com.disney.utils.XMLTools;

public class RetrieveDetails extends ExperienceMediaServiceV1{
	public RetrieveDetails(String environment, String scenario){
		super(environment);
		//UI Booking
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveDetails")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public String getKttwId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/kttwDetails/@kttwId");
	}

	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/guestReferenceDetail/guest/partyId");
	}

	public String getTravelplanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/kttwDetails/@travelPlanId");
	}

	public String[] getKttwIds(int numberOfGuests){
		String[] kttwIds = new String[numberOfGuests];
		
		for(int loopCounter = 1; loopCounter <= numberOfGuests; loopCounter++){
			String xpath = "/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails["+String.valueOf(loopCounter)+"]/kttwDetails/@kttwId";
			kttwIds[loopCounter-1] = getResponseNodeValueByXPath(xpath);
		}
		
		Arrays.sort(kttwIds);
		
		return kttwIds;
	}
	
	public String getRfid(){
		try{
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/kttwDetails/rfIdDetail/@id");
		}catch(RuntimeException e){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/xBandDetails/@id");
		}
	}
	
	public String getManufacturerUid(){
		try{				
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/kttwDetails/rfIdDetail/@manufacturerUID");
		}catch(RuntimeException e){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/xBandDetails/@manufacturerUID");
		}
	}
	
	public void setTPID(String TP_ID){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveDetails/travelPlanId", TP_ID);
	}
	
	public String getEncodingStatus(){
		try{
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/kttwDetails/rfIdDetail/encodingDetails/@encodingStatus");
		}catch(RuntimeException e){
			return getResponseNodeValueByXPath("/Envelope/Body/retrieveDetailsResponse/response/guestExperienceMediaDetails/xBandDetails/encodingDetails/@encodingStatus");
		}
	}
}
