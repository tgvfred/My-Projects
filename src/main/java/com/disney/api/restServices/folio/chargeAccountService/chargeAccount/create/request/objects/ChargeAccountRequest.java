package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class ChargeAccountRequest {
	private String chargeAccountType = "GUEST_ACCOUNT";
	private RootChargeAccountRequest rootChargeAccountRequest;

	public ChargeAccountRequest(){
		rootChargeAccountRequest = new RootChargeAccountRequest();
	}
	/**
	* 
	* @return
	* The chargeAccountType
	*/
	public String getChargeAccountType() {
	return chargeAccountType;
	 }

	/**
	* 
	* @param chargeAccountType
	* The chargeAccountType
	*/
	public void setChargeAccountType(String chargeAccountType) {
	this.chargeAccountType = chargeAccountType;
	 }

	/**
	* 
	* @return
	* The rootChargeAccountRequest
	*/
	public RootChargeAccountRequest getRootChargeAccountRequest() {
	return rootChargeAccountRequest;
	 }

	/**
	* 
	* @param rootChargeAccountRequest
	* The rootChargeAccountRequest
	*/
	public void setRootChargeAccountRequest(RootChargeAccountRequest rootChargeAccountRequest) {
	this.rootChargeAccountRequest = rootChargeAccountRequest;
	 }
}
