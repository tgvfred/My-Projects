package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class ExternalReference {
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
		referenceName = "SWID";
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
		referenceValue = "{9AA2F8BE-530B-4BAB-93ED-7758B06AC4A3}";
	this.referenceValue = referenceValue;
	 }
}
