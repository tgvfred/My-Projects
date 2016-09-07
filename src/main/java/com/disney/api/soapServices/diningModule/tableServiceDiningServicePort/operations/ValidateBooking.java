package com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.TableServiceDiningServicePort;
import com.disney.utils.XMLTools;

public class ValidateBooking extends TableServiceDiningServicePort{
	public ValidateBooking(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("validateBooking")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/facilityId", value);}
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/productId", value);}
	public void setEnterpriseProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/enterpriseProductId", value);}
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/serviceStartDate", value);}
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/partyRoles/guest/partyId", value);}
	public void setDclGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/partyRoles/guest/dclGuestId", value);}
	public void setGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/partyRoles/guest/guestId", value);}
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/servicePeriodId", value);}
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/tableService/inventoryDetails/reservableResourceId", value);}
	public String getStopReservation(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReservation");}
	public String getStopReason(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReason");}
	public String getRulesFired(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/rulesFired");}
}
