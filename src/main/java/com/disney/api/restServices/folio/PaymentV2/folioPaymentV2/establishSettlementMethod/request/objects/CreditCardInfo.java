package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects;

public class CreditCardInfo {
	private String cardNumber;
	private String cardSecurityNumber;
	private String creditCardType = "Visa";
	private String expirationDate;
	private CardHolderInfo cardHolderInfo;

	public CreditCardInfo(){
		cardHolderInfo = new CardHolderInfo();
	}
	/**
	* 
	* @return
	* The cardNumber
	*/
	public String getCardNumber() {
	return cardNumber;
	 }

	/**
	* 
	* @param cardNumber
	* The cardNumber
	*/
	public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
	 }

	/**
	* 
	* @return
	* The cardSecurityNumber
	*/
	public String getCardSecurityNumber() {
	return cardSecurityNumber;
	 }

	/**
	* 
	* @param cardSecurityNumber
	* The cardSecurityNumber
	*/
	public void setCardSecurityNumber(String cardSecurityNumber) {
	this.cardSecurityNumber = cardSecurityNumber;
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
	* The expirationDate
	*/
	public String getExpirationDate() {
	return expirationDate;
	 }

	/**
	* 
	* @param expirationDate
	* The expirationDate
	*/
	public void setExpirationDate(String expirationDate) {
	this.expirationDate = expirationDate;
	 }

	/**
	* 
	* @return
	* The cardHolderInfo
	*/
	public CardHolderInfo getCardHolderInfo() {
	return cardHolderInfo;
	 }

	/**
	* 
	* @param cardHolderInfo
	* The cardHolderInfo
	*/
	public void setCardHolderInfo(CardHolderInfo cardHolderInfo) {
	this.cardHolderInfo = cardHolderInfo;
	 }
}
