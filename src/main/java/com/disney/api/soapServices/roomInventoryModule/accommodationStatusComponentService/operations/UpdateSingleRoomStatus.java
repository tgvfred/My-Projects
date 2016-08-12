package com.disney.api.soapServices.roomInventoryModule.accommodationStatusComponentService.operations;

import com.disney.api.soapServices.roomInventoryModule.accommodationStatusComponentService.AccommodationStatusComponentService;
import com.disney.utils.XMLTools;

public class UpdateSingleRoomStatus extends AccommodationStatusComponentService{
	public UpdateSingleRoomStatus(String environment, String scenario) {
		super(environment);
		// UI Booking
		// Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateSingleRoomStatus")));
		System.out.println(getRequest());

//		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

		removeComments();
		removeWhiteSpace();
	}

	public void setResourceId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updateSingleRoomStatus/arg0/updateStatusRequestDetails/resourceId", value);
	}

	public void setRoomNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/updateSingleRoomStatus/arg0/updateStatusRequestDetails/roomNumber", value);
	}
}
