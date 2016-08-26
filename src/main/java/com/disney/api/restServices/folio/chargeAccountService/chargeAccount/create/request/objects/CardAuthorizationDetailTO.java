package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class CardAuthorizationDetailTO {
	private String expirationMonth;
	private String expirationYear;
	private String retrievalReferenceNumber;
	private String retrievalReferenceNumberKey;

	/**
	* 
	* @return
	* The expirationMonth
	*/
	public String getExpirationMonth() {
	return expirationMonth;
	 }

	/**
	* 
	* @param expirationMonth
	* The expirationMonth
	*/
	public void setExpirationMonth(String expirationMonth) {
		expirationMonth = "6";
	this.expirationMonth = expirationMonth;
	 }

	/**
	* 
	* @return
	* The expirationYear
	*/
	public String getExpirationYear() {
	return expirationYear;
	 }

	/**
	* 
	* @param expirationYear
	* The expirationYear
	*/
	public void setExpirationYear(String expirationYear) {
		expirationYear = "20";
	this.expirationYear = expirationYear;
	 }

	/**
	* 
	* @return
	* The retrievalReferenceNumber
	*/
	public String getRetrievalReferenceNumber() {
	return retrievalReferenceNumber;
	 }

	/**
	* 
	* @param retrievalReferenceNumber
	* The retrievalReferenceNumber
	*/
	public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
		retrievalReferenceNumber = "423730003693";
	this.retrievalReferenceNumber = retrievalReferenceNumber;
	 }

	/**
	* 
	* @return
	* The retrievalReferenceNumberKey
	*/
	public String getRetrievalReferenceNumberKey() {
	return retrievalReferenceNumberKey;
	 }

	/**
	* 
	* @param retrievalReferenceNumberKey
	* The retrievalReferenceNumberKey
	*/
	public void setRetrievalReferenceNumberKey(String retrievalReferenceNumberKey) {
		retrievalReferenceNumberKey = "HGBkD8";
	this.retrievalReferenceNumberKey = retrievalReferenceNumberKey;
	 }
}
