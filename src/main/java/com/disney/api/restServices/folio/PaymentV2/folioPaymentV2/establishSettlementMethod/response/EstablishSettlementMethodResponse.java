package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.response;

import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects.CardHolderInfo;

public class EstablishSettlementMethodResponse {
	private CardHolderInfo cardHolderInfo;
	private Object settlementMethodId;
	private Long folioId;
	private String retrievalReferenceNumber;
	private String retrievalReferenceNumberKey;
	private String truncatedCardNumber;
	private Object authorizationCode;
	private String paymentMethod;
	private String paymentMethodType;
	private Object expressCheckout;

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

	/**
	* 
	* @return
	* The settlementMethodId
	*/
	public Object getSettlementMethodId() {
	return settlementMethodId;
	 }

	/**
	* 
	* @param settlementMethodId
	* The settlementMethodId
	*/
	public void setSettlementMethodId(Object settlementMethodId) {
	this.settlementMethodId = settlementMethodId;
	 }

	/**
	* 
	* @return
	* The folioId
	*/
	public Long getFolioId() {
	return folioId;
	 }

	/**
	* 
	* @param folioId
	* The folioId
	*/
	public void setFolioId(Long folioId) {
	this.folioId = folioId;
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

	/**
	* 
	* @return
	* The truncatedCardNumber
	*/
	public String getTruncatedCardNumber() {
	return truncatedCardNumber;
	 }

	/**
	* 
	* @param truncatedCardNumber
	* The truncatedCardNumber
	*/
	public void setTruncatedCardNumber(String truncatedCardNumber) {
	this.truncatedCardNumber = truncatedCardNumber;
	 }

	/**
	* 
	* @return
	* The authorizationCode
	*/
	public Object getAuthorizationCode() {
	return authorizationCode;
	 }

	/**
	* 
	* @param authorizationCode
	* The authorizationCode
	*/
	public void setAuthorizationCode(Object authorizationCode) {
	this.authorizationCode = authorizationCode;
	 }

	/**
	* 
	* @return
	* The paymentMethod
	*/
	public String getPaymentMethod() {
	return paymentMethod;
	 }

	/**
	* 
	* @param paymentMethod
	* The paymentMethod
	*/
	public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
	 }

	/**
	* 
	* @return
	* The paymentMethodType
	*/
	public String getPaymentMethodType() {
	return paymentMethodType;
	 }

	/**
	* 
	* @param paymentMethodType
	* The paymentMethodType
	*/
	public void setPaymentMethodType(String paymentMethodType) {
	this.paymentMethodType = paymentMethodType;
	 }

	/**
	* 
	* @return
	* The expressCheckout
	*/
	public Object getExpressCheckout() {
	return expressCheckout;
	 }

	/**
	* 
	* @param expressCheckout
	* The expressCheckout
	*/
	public void setExpressCheckout(Object expressCheckout) {
	this.expressCheckout = expressCheckout;
	 }
}
