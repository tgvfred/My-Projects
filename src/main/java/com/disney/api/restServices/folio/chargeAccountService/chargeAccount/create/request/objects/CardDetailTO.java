package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class CardDetailTO {
	private String cardStatus;
	private String creditCardNumber;
	private String cvvNumber;
	private String name;
	private Address address;
	private CardAuthorizationDetailTO cardAuthorizationDetailTO;

	/**
	* 
	* @return
	* The cardStatus
	*/
	public String getCardStatus() {
	return cardStatus;
	 }

	/**
	* 
	* @param cardStatus
	* The cardStatus
	*/
	public void setCardStatus(String cardStatus) {
		cardStatus = "Valid";
	this.cardStatus = cardStatus;
	 }

	/**
	* 
	* @return
	* The creditCardNumber
	*/
	public String getCreditCardNumber() {
	return creditCardNumber;
	 }

	/**
	* 
	* @param creditCardNumber
	* The creditCardNumber
	*/
	public void setCreditCardNumber(String creditCardNumber) {
		creditCardNumber = "xxxxxxxxxxxx7840";
	this.creditCardNumber = creditCardNumber;
	 }

	/**
	* 
	* @return
	* The cvvNumber
	*/
	public String getCvvNumber() {
	return cvvNumber;
	 }

	/**
	* 
	* @param cvvNumber
	* The cvvNumber
	*/
	public void setCvvNumber(String cvvNumber) {
		cvvNumber = "423";
	this.cvvNumber = cvvNumber;
	 }

	/**
	* 
	* @return
	* The name
	*/
	public String getName() {
	return name;
	 }

	/**
	* 
	* @param name
	* The name
	*/
	public void setName(String name) {
		name = "Marisol Centeno";
	this.name = name;
	 }

	/**
	* 
	* @return
	* The address
	*/
	public Address getAddress() {
	return address;
	 }

	/**
	* 
	* @param address
	* The address
	*/
	public void setAddress(Address address) {
	this.address = address;
	 }

	/**
	* 
	* @return
	* The cardAuthorizationDetailTO
	*/
	public CardAuthorizationDetailTO getCardAuthorizationDetailTO() {
	return cardAuthorizationDetailTO;
	 }

	/**
	* 
	* @param cardAuthorizationDetailTO
	* The cardAuthorizationDetailTO
	*/
	public void setCardAuthorizationDetailTO(CardAuthorizationDetailTO cardAuthorizationDetailTO) {
	this.cardAuthorizationDetailTO = cardAuthorizationDetailTO;
	 }
}
