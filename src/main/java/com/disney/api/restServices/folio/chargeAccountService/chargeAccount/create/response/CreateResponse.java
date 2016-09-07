package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response;

import java.util.ArrayList;
import java.util.List;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.objects.RootChargeAccountCreateResponse;

public class CreateResponse {
	private RootChargeAccountCreateResponse rootChargeAccountCreateResponse;
	private List<Object> nodeChargeAccountCreateResponses = new ArrayList<Object>();

	/**
	* 
	* @return
	* The rootChargeAccountCreateResponse
	*/
	public RootChargeAccountCreateResponse getRootChargeAccountCreateResponse() {
	return rootChargeAccountCreateResponse;
	 }

	/**
	* 
	* @param rootChargeAccountCreateResponse
	* The rootChargeAccountCreateResponse
	*/
	public void setRootChargeAccountCreateResponse(RootChargeAccountCreateResponse rootChargeAccountCreateResponse) {
	this.rootChargeAccountCreateResponse = rootChargeAccountCreateResponse;
	 }

	/**
	* 
	* @return
	* The nodeChargeAccountCreateResponses
	*/
	public List<Object> getNodeChargeAccountCreateResponses() {
	return nodeChargeAccountCreateResponses;
	 }

	/**
	* 
	* @param nodeChargeAccountCreateResponses
	* The nodeChargeAccountCreateResponses
	*/
	public void setNodeChargeAccountCreateResponses(List<Object> nodeChargeAccountCreateResponses) {
	this.nodeChargeAccountCreateResponses = nodeChargeAccountCreateResponses;
	 }
}
