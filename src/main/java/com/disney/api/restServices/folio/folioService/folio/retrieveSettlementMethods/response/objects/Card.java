package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response.objects;

public class Card {
	private Object creditCardExpirationDate;
	private Object cardStatus;
	private String creditCardNumber;
	private String creditCardType;
	private Object cvvNumber;
	private String name;
	private Address address;
	private Object authorizationNumber;
	private String brandName;
	private Object deviceTerminalId;
	private CardAuthorizationInfo cardAuthorizationInfo;

	/**
	* 
	* @return
	* The creditCardExpirationDate
	*/
	public Object getCreditCardExpirationDate() {
	return creditCardExpirationDate;
	 }

	/**
	* 
	* @param creditCardExpirationDate
	* The creditCardExpirationDate
	*/
	public void setCreditCardExpirationDate(Object creditCardExpirationDate) {
	this.creditCardExpirationDate = creditCardExpirationDate;
	 }

	/**
	* 
	* @return
	* The cardStatus
	*/
	public Object getCardStatus() {
	return cardStatus;
	 }

	/**
	* 
	* @param cardStatus
	* The cardStatus
	*/
	public void setCardStatus(Object cardStatus) {
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
	this.creditCardNumber = creditCardNumber;
	 }

	/**
	* 
	* @return
	* The creditCardType
	*/
	public String getCreditCardType() {
	return creditCardType;
	 }

	/**
	* 
	* @param creditCardType
	* The creditCardType
	*/
	public void setCreditCardType(String creditCardType) {
	this.creditCardType = creditCardType;
	 }

	/**
	* 
	* @return
	* The cvvNumber
	*/
	public Object getCvvNumber() {
	return cvvNumber;
	 }

	/**
	* 
	* @param cvvNumber
	* The cvvNumber
	*/
	public void setCvvNumber(Object cvvNumber) {
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
	* The authorizationNumber
	*/
	public Object getAuthorizationNumber() {
	return authorizationNumber;
	 }

	/**
	* 
	* @param authorizationNumber
	* The authorizationNumber
	*/
	public void setAuthorizationNumber(Object authorizationNumber) {
	this.authorizationNumber = authorizationNumber;
	 }

	/**
	* 
	* @return
	* The brandName
	*/
	public String getBrandName() {
	return brandName;
	 }

	/**
	* 
	* @param brandName
	* The brandName
	*/
	public void setBrandName(String brandName) {
	this.brandName = brandName;
	 }

	/**
	* 
	* @return
	* The deviceTerminalId
	*/
	public Object getDeviceTerminalId() {
	return deviceTerminalId;
	 }

	/**
	* 
	* @param deviceTerminalId
	* The deviceTerminalId
	*/
	public void setDeviceTerminalId(Object deviceTerminalId) {
	this.deviceTerminalId = deviceTerminalId;
	 }

	/**
	* 
	* @return
	* The cardAuthorizationInfo
	*/
	public CardAuthorizationInfo getCardAuthorizationInfo() {
	return cardAuthorizationInfo;
	 }

	/**
	* 
	* @param cardAuthorizationInfo
	* The cardAuthorizationInfo
	*/
	public void setCardAuthorizationInfo(CardAuthorizationInfo cardAuthorizationInfo) {
	this.cardAuthorizationInfo = cardAuthorizationInfo;
	 }
}
