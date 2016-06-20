package com.disney.api.soapServices.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

public class CompleteCheckIn extends AccommodationFulfillmentServicePort{

	public CompleteCheckIn(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("completeCheckIn")));
		System.out.println(getRequest());
		
		generateServiceContext();		
		//All fields are dynamically required, sheet not necessary
		//setRequestNodeValueByXPath(getTestScenario("/services/accommodationFulfillmentServicePort/completeCheckIn/completeCheckInInput.xls", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setRoomNumber(String roomNumber){
		setRequestNodeValueByXPath("/Envelope/Body/completeCheckIn/completeCheckInRequest/roomNumber",roomNumber);
	}
	
	public void setTravelComponentGroupingId(String tcgId){
		setRequestNodeValueByXPath("/Envelope/Body/completeCheckIn/completeCheckInRequest/travelComponentGroupingId",tcgId);
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/completeCheckIn/completeCheckInRequest/locationId", locationId);
		}
		
	public void setTravelComponentId(String travelComponentId){
		setRequestNodeValueByXPath("/Envelope/Body/completeCheckIn/completeCheckInRequest/travelComponentId", travelComponentId);
	}


}
