package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

public class CheckingIn extends AccommodationFulfillmentServicePort{

	public CheckingIn(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("checkingIn")));
		System.out.println(getRequest());
		
		generateServiceContext();			
	//	setRequestNodeValueByXPath(getTestScenario("/services/accommodationFulfillmentServicePort/checkingIn/checkingInInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTravelComponentGroupingId(String tcgId){
		setRequestNodeValueByXPath("/Envelope/Body/checkingIn/request/travelComponentGroupingId",tcgId);
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/checkingIn/request/locationId", locationId);
		}
		
	/*public void setGuestId(String guestId){
		setRequestNodeValueByXPath("/Envelope/Body/checkingIn/request/checkInGuestDetails/guestId", guestId);
	}*/


}
