package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
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

	/**
	 * Sets the facility ID in the SOAP Request
	 * @param value - facility ID
	 */
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/facilityId", value);}
	/**
	 * Sets the product ID in the SOAP request
	 * @param value - product ID
	 */
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/productId", value);}
	/**
	 * Sets the enterprise product ID in the SOAP request
	 * @param value - enterprise product ID
	 */
	public void setEnterpriseProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/enterpriseProductId", value);}
	/**
	 * Sets the service start dateTime in the SOAP request
	 * @param value service start dateTime
	 */
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/serviceStartDate", value);}
	/**
	 * Sets party ID for the first party role node in the SOAP request
	 * @param value - party ID for the first party role node 
	 */
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/partyRoles/guest/partyId", value);}
	/**
	 * Sets DCL guest ID for the first party role node in the SOAP request
	 * @param value - DCL guest ID for the first party role node 
	 */
	public void setDclGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/partyRoles/guest/dclGuestId", value);}
	/**
	 * Sets guest ID for the first party role node in the SOAP request
	 * @param value - guest ID for the first party role node
	 */
	public void setGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/partyRoles/guest/guestId", value);}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/validateBooking/eventDiningPackage/servicePeriodId", value);}
	/**
	 * Gets the "stop reservation" value from the SOAP response
	 * @return
	 */
	public String getStopReservation(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReservation");}
	/**
	 * Gets the stop reason from the SOAP response
	 * @return - stop reason
	 */
	public String getStopReason(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/stopReason");}
	/**
	 * Gets the rules fired from the SOAP response
	 * @return - rules fired
	 */
	public String getRulesFired(){return getResponseNodeValueByXPath("/Envelope/Body/validateBookingResponse/bookingRulesValidationResponse/rulesFired");}
}
