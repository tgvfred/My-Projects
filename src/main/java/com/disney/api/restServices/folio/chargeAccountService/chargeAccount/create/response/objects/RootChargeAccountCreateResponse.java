package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.response.objects;

import java.util.ArrayList;
import java.util.List;

public class RootChargeAccountCreateResponse {
	private Long chargeAccountId;
	private Object externalReferenceTO;
	private List<Object> nodeChargeAccountCreateResponses = new ArrayList<Object>();

	/**
	* 
	* @return
	* The chargeAccountId
	*/
	public Long getChargeAccountId() {
	return chargeAccountId;
	 }

	/**
	* 
	* @param chargeAccountId
	* The chargeAccountId
	*/
	public void setChargeAccountId(Long chargeAccountId) {
	this.chargeAccountId = chargeAccountId;
	 }

	/**
	* 
	* @return
	* The externalReferenceTO
	*/
	public Object getExternalReferenceTO() {
	return externalReferenceTO;
	 }

	/**
	* 
	* @param externalReferenceTO
	* The externalReferenceTO
	*/
	public void setExternalReferenceTO(Object externalReferenceTO) {
	this.externalReferenceTO = externalReferenceTO;
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
