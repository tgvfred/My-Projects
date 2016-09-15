package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class RootChargeAccountRequest {
	private ChargeAccountCommonRequest chargeAccountCommonRequest;

	public RootChargeAccountRequest(){
		chargeAccountCommonRequest = new ChargeAccountCommonRequest();
	}
	/**
	* 
	* @return
	* The chargeAccountCommonRequest
	*/
	public ChargeAccountCommonRequest getChargeAccountCommonRequest() {
	return chargeAccountCommonRequest;
	 }

	/**
	* 
	* @param chargeAccountCommonRequest
	* The chargeAccountCommonRequest
	*/
	public void setChargeAccountCommonRequest(ChargeAccountCommonRequest chargeAccountCommonRequest) {
	this.chargeAccountCommonRequest = chargeAccountCommonRequest;
	 }
}