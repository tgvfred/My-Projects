package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

public class CheckIn extends AccommodationFulfillmentServicePort{

	public CheckIn(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("checkIn")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTravelComponentGroupingId(String tcgId){
		setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/travelComponentGroupingId",tcgId);
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/locationId", locationId);
		}
		
	public void setGuestId(String guestId){
		setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/checkInGuestDetails/guestId", guestId);
	}


}
