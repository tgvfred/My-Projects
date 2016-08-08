package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class ValidateBooking extends ShowDiningService {
	public ValidateBooking(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("validateBooking")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/facilityId", value);}
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/productId", value);}
	public void setEnterpriseProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/enterpriseProductId", value);}
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/serviceStartDate", value);}
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/partyRoles/guest/partyId", value);}
	public void setDclGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/partyRoles/guest/dclGuestId", value);}
	public void setGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/partyRoles/guest/guestId", value);}
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/dinnerShowPackage/servicePeriodId", value);}
	public String getStopReservation(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReservation");}
	public String getStopReason(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReason");}
	public String getRulesFired(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/rulesFired");}
}
