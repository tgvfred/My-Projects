package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

public class CheckOut extends AccommodationFulfillmentServicePort{

	public CheckOut(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("checkOut")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTravelComponentGroupingId(String tcgId){
		setRequestNodeValueByXPath("/Envelope/Body/checkOut/request/travelComponentGroupingId",tcgId);
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/checkOut/request/locationId", locationId);
		}
		
	public void setGuestId(String guestId){
		setRequestNodeValueByXPath("/Envelope/Body/checkIn/request/checkInGuestDetails/guestId", guestId);
	}


}
