package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request;

import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects.SettlementMethodRequest;

public class EstablishSettlementMethodRequest {
	private SettlementMethodRequest settlementMethodRequest;

	public EstablishSettlementMethodRequest(){
		settlementMethodRequest = new SettlementMethodRequest();
	}
	
	/**
	* 
	* @return
	* The settlementMethodRequest
	*/
	public SettlementMethodRequest getSettlementMethodRequest() {
	return settlementMethodRequest;
	 }

	/**
	* 
	* @param settlementMethodRequest
	* The settlementMethodRequest
	*/
	public void setSettlementMethodRequest(SettlementMethodRequest settlementMethodRequest) {
	this.settlementMethodRequest = settlementMethodRequest;
	 }
}
