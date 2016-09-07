package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.response;

public class RetrieveGuestsResponse {
	private String errorMessage;
	private Long applicationErrorCode;

	/**
	* 
	* @return
	* The errorMessage
	*/
	public String getErrorMessage() {
	return errorMessage;
	 }

	/**
	* 
	* @param errorMessage
	* The errorMessage
	*/
	public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
	 }

	/**
	* 
	* @return
	* The applicationErrorCode
	*/
	public Long getApplicationErrorCode() {
	return applicationErrorCode;
	 }

	/**
	* 
	* @param applicationErrorCode
	* The applicationErrorCode
	*/
	public void setApplicationErrorCode(Long applicationErrorCode) {
	this.applicationErrorCode = applicationErrorCode;
	 }

}
