package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.objects;

public class ExternalReferenceTO {
	private String referenceName;
	private String referenceValue;

	/**
	* 
	* @return
	* The referenceName
	*/
	public String getReferenceName() {
	return referenceName;
	 }

	/**
	* 
	* @param referenceName
	* The referenceName
	*/
	public void setReferenceName(String referenceName) {
	this.referenceName = referenceName;
	 }

	/**
	* 
	* @return
	* The referenceValue
	*/
	public String getReferenceValue() {
	return referenceValue;
	 }

	/**
	* 
	* @param referenceValue
	* The referenceValue
	*/
	public void setReferenceValue(String referenceValue) {
	this.referenceValue = referenceValue;
	 }
}
