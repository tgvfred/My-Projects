package com.disney.api.soapServices.eventDiningService.operations;

import com.disney.api.soapServices.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;

public class ValidateBooking extends EventDiningService {
	public ValidateBooking(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("validateBooking")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/facilityId", value);}
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/productId", value);}
	public void setEnterpriseProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/enterpriseProductId", value);}
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/serviceStartDate", value);}
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/partyRoles/guest/partyId", value);}
	public void setDclGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/partyRoles/guest/dclGuestId", value);}
	public void setGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/partyRoles/guest/guestId", value);}
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/servicePeriodId", value);}
	public String getStopReservation(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReservation");}
}
