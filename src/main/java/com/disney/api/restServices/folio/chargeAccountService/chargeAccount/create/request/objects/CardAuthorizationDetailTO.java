package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class CardAuthorizationDetailTO {
	private String expirationMonth = "6";
	private String expirationYear = "20";
	private String retrievalReferenceNumber = "423730003693";
	private String retrievalReferenceNumberKey = "HGBkD8";

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
		
	this.retrievalReferenceNumberKey = retrievalReferenceNumberKey;
	 }
}
