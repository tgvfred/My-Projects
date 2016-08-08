package com.disney.api.soapServices.folioModule.chargeAccountServiceV2.operations;

import com.disney.api.soapServices.folioModule.chargeAccountServiceV2.ChargeAccountServiceV2;
import com.disney.utils.XMLTools;

public class Create extends ChargeAccountServiceV2{
	public Create(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("create")));
	
		generateServiceContext();
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setStartDate(String startDate){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='period'][1]/*[local-name(.)='startDate'][1]", startDate);
	}
	
	public void setFacilityId(String facilityId){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='txnFacilityId'][1]", facilityId);
	}
	
	public void setPaymentMethodStartDate(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='chargeAccountPaymentMethodDetail'][1]/*[local-name(.)='paymentMethodStartDate'][1]", value);
	}
	
	public void setPaymentMethodEndDate(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='chargeAccountPaymentMethodDetail'][1]/*[local-name(.)='paymentMethodEndDate'][1]", value);
	}
	
	public void setKttwNumber(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='chargeAccountPaymentMethodDetail'][1]/*[local-name(.)='kttwPaymentDetail'][1]/*[local-name(.)='kttwNumber'][1]", value);
	}
	
	public void setReservationTransactionGuestId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='chargeAccountPaymentMethodDetail'][1]/*[local-name(.)='kttwPaymentDetail'][1]/*[local-name(.)='reservationTxnGuestId'][1]", value);
	}
	
	public void setReferenceNumber(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='create'][1]/*[local-name(.)='chargeAccountRequests'][1]/*[local-name(.)='rootChargeAccountRequest'][1]/*[local-name(.)='chargeAccountCommonRequest'][1]/*[local-name(.)='guestInfoTO'][1]/*[local-name(.)='externalReference'][1]/*[local-name(.)='referenceValue'][1]", value);
	}
	
	public String getChargeAccountid(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createResponse'][1]/*[local-name(.)='chargeAccountCreateResponses'][1]/*[local-name(.)='rootChargeAccountCreateResponse'][1]/*[local-name(.)='chargeAccountId'][1]");
	}
}
