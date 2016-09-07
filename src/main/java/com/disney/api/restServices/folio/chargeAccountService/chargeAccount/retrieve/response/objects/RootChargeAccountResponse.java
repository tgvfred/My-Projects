package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.objects;

import java.util.ArrayList;
import java.util.List;

public class RootChargeAccountResponse {
	private List<Object> nodeChargeAccountResponse = new ArrayList<Object>();
	private CommonChargeAccountResponse commonChargeAccountResponse;
	private List<Object> ownedNodeChargeAccountResponse = new ArrayList<Object>();

	/**
	* 
	* @return
	* The nodeChargeAccountResponse
	*/
	public List<Object> getNodeChargeAccountResponse() {
	return nodeChargeAccountResponse;
	 }

	/**
	* 
	* @param nodeChargeAccountResponse
	* The nodeChargeAccountResponse
	*/
	public void setNodeChargeAccountResponse(List<Object> nodeChargeAccountResponse) {
	this.nodeChargeAccountResponse = nodeChargeAccountResponse;
	 }

	/**
	* 
	* @return
	* The commonChargeAccountResponse
	*/
	public CommonChargeAccountResponse getCommonChargeAccountResponse() {
	return commonChargeAccountResponse;
	 }

	/**
	* 
	* @param commonChargeAccountResponse
	* The commonChargeAccountResponse
	*/
	public void setCommonChargeAccountResponse(CommonChargeAccountResponse commonChargeAccountResponse) {
	this.commonChargeAccountResponse = commonChargeAccountResponse;
	 }

	/**
	* 
	* @return
	* The ownedNodeChargeAccountResponse
	*/
	public List<Object> getOwnedNodeChargeAccountResponse() {
	return ownedNodeChargeAccountResponse;
	 }

	/**
	* 
	* @param ownedNodeChargeAccountResponse
	* The ownedNodeChargeAccountResponse
	*/
	public void setOwnedNodeChargeAccountResponse(List<Object> ownedNodeChargeAccountResponse) {
	this.ownedNodeChargeAccountResponse = ownedNodeChargeAccountResponse;
	 }
}
