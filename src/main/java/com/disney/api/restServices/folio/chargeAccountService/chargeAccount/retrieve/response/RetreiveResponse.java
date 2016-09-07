package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response;

import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.objects.RootChargeAccountResponse;

public class RetreiveResponse {
	private RootChargeAccountResponse rootChargeAccountResponse;
	private Object nodeChargeAccountResponse;

	/**
	* 
	* @return
	* The rootChargeAccountResponse
	*/
	public RootChargeAccountResponse getRootChargeAccountResponse() {
	return rootChargeAccountResponse;
	 }

	/**
	* 
	* @param rootChargeAccountResponse
	* The rootChargeAccountResponse
	*/
	public void setRootChargeAccountResponse(RootChargeAccountResponse rootChargeAccountResponse) {
	this.rootChargeAccountResponse = rootChargeAccountResponse;
	 }

	/**
	* 
	* @return
	* The nodeChargeAccountResponse
	*/
	public Object getNodeChargeAccountResponse() {
	return nodeChargeAccountResponse;
	 }

	/**
	* 
	* @param nodeChargeAccountResponse
	* The nodeChargeAccountResponse
	*/
	public void setNodeChargeAccountResponse(Object nodeChargeAccountResponse) {
	this.nodeChargeAccountResponse = nodeChargeAccountResponse;
	 }	

}
