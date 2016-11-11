package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class BookWithPay extends AccommodationSalesServicePort{
	public BookWithPay(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("bookWithPay")));
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setInventoryTrackingId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/inventoryTrackingId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/InventoryTrackingId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/basicOrderDetails/reservationReferenceNo", value);		
	}
	

	public void setMemberRefNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/externalReference/externalReferenceNumber", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/travelPlanGuest/membershipDetail/membershipId", value);			
	}

	public void setMemberNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/membershipId", value);
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/roomReservationDetail/guestReferenceDetails/guest/membershipDetail/membershipId", value);	
		
	}

	public void setMemberMembershipID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/reservationRequest/roomDetail/externalReferences/externalReferenceNumber", value);	
		
	}

	public void setContractID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/bookWithPay/paymentRequest/pointsPayments/payingContractSummary/payingContracts/contractId", value);	
		
	}
}
