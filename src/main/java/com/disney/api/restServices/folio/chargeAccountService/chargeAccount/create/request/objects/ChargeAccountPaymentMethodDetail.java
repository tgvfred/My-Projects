package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

import com.disney.test.utils.Randomness;

public class ChargeAccountPaymentMethodDetail {
	private String active = "true";
	private String paymentMethodName= "Visa";
	private String paymentMethodTypeName = "CreditCard";
	private String paymentMethodStartDate = Randomness.generateCurrentXMLDatetime(10)+"-04:00";
	private String paymentMethodEndDate = Randomness.generateCurrentXMLDatetime(15)+"-04:00";
	private String chargingPrivilegesIndicator = "false";
	private String isSubAccountPaymentMethod = "false";
	private KttwPaymentDetail kttwPaymentDetail;
	private CardDetailTO cardDetailTO;

	public ChargeAccountPaymentMethodDetail(){
		cardDetailTO = new CardDetailTO();
		kttwPaymentDetail = new KttwPaymentDetail();
	}
	/**
	* 
	* @param kttwPaymentDetail
	* @param isSubAccountPaymentMethod
	* @param chargingPrivilegesIndicator
	* @param active
	* @param paymentMethodStartDate
	* @param paymentMethodName
	* @param paymentMethodEndDate
	* @param paymentMethodTypeName
	*/
	public ChargeAccountPaymentMethodDetail(String active, String paymentMethodName, String paymentMethodTypeName, String paymentMethodStartDate, String paymentMethodEndDate, String chargingPrivilegesIndicator, String isSubAccountPaymentMethod, KttwPaymentDetail kttwPaymentDetail) {
	this.active = active;
	this.paymentMethodName = paymentMethodName;
	this.paymentMethodTypeName = paymentMethodTypeName;
	this.paymentMethodStartDate = paymentMethodStartDate;
	this.paymentMethodEndDate = paymentMethodEndDate;
	this.chargingPrivilegesIndicator = chargingPrivilegesIndicator;
	this.isSubAccountPaymentMethod = isSubAccountPaymentMethod;
	this.kttwPaymentDetail = kttwPaymentDetail;
	 }
	/**
	* 
	* @return
	* The active
	*/
	public String getActive() {
	return active;
	 }
	
	/**
	* 
	* @param active
	* The active
	*/
	public void setActive(String active) {
	this.active = active;
	 }

	/**
	* 
	* @return
	* The paymentMethodName
	*/
	public String getPaymentMethodName() {
	return paymentMethodName;
	 }

	/**
	* 
	* @param paymentMethodName
	* The paymentMethodName
	*/
	public void setPaymentMethodName(String paymentMethodName) {
	this.paymentMethodName = paymentMethodName;
	 }

	/**
	* 
	* @return
	* The paymentMethodTypeName
	*/
	public String getPaymentMethodTypeName() {
	return paymentMethodTypeName;
	 }

	/**
	* 
	* @param paymentMethodTypeName
	* The paymentMethodTypeName
	*/
	public void setPaymentMethodTypeName(String paymentMethodTypeName) {
	this.paymentMethodTypeName = paymentMethodTypeName;
	 }
	/**
	* 
	* @return
	* The paymentMethodStartDate
	*/
	public String getPaymentMethodStartDate() {
	return paymentMethodStartDate;
	 }

	/**
	* 
	* @param paymentMethodStartDate
	* The paymentMethodStartDate
	*/
	public void setPaymentMethodStartDate(String paymentMethodStartDate) {
	this.paymentMethodStartDate = paymentMethodStartDate;
	 }

	/**
	* 
	* @return
	* The paymentMethodEndDate
	*/
	public String getPaymentMethodEndDate() {
	return paymentMethodEndDate;
	 }

	/**
	* 
	* @param paymentMethodEndDate
	* The paymentMethodEndDate
	*/
	public void setPaymentMethodEndDate(String paymentMethodEndDate) {
	this.paymentMethodEndDate = paymentMethodEndDate;
	 }
	/**
	* 
	* @return
	* The chargingPrivilegesIndicator
	*/
	public String getChargingPrivilegesIndicator() {
	return chargingPrivilegesIndicator;
	 }

	/**
	* 
	* @param chargingPrivilegesIndicator
	* The chargingPrivilegesIndicator
	*/
	public void setChargingPrivilegesIndicator(String chargingPrivilegesIndicator) {
	this.chargingPrivilegesIndicator = chargingPrivilegesIndicator;
	 }

	/**
	* 
	* @return
	* The isSubAccountPaymentMethod
	*/
	public String getIsSubAccountPaymentMethod() {
	return isSubAccountPaymentMethod;
	 }

	/**
	* 
	* @param isSubAccountPaymentMethod
	* The isSubAccountPaymentMethod
	*/
	public void setIsSubAccountPaymentMethod(String isSubAccountPaymentMethod) {
	this.isSubAccountPaymentMethod = isSubAccountPaymentMethod;
	 }
	/**
	* 
	* @return
	* The kttwPaymentDetail
	*/
	public KttwPaymentDetail getKttwPaymentDetail() {
	return kttwPaymentDetail;
	 }

	/**
	* 
	* @param kttwPaymentDetail
	* The kttwPaymentDetail
	*/
	public void setKttwPaymentDetail(KttwPaymentDetail kttwPaymentDetail) {
	this.kttwPaymentDetail = kttwPaymentDetail;
	 }
	/**
	* 
	* @return
	* The cardDetailTO
	*/
	public CardDetailTO getCardDetailTO() {
	return cardDetailTO;
	 }

	/**
	* 
	* @param cardDetailTO
	* The cardDetailTO
	*/
	public void setCardDetailTO(CardDetailTO cardDetailTO) {
	this.cardDetailTO = cardDetailTO;
	 }
}
