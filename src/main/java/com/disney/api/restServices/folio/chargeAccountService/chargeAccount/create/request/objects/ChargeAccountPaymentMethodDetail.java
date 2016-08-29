package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class ChargeAccountPaymentMethodDetail {
	private String active = "true";
	private String paymentMethodName = "Visa";
	private String paymentMethodTypeName = "CreditCard";
	private String chargingPrivilegesIndicator = "false";
	private String isSubAccountPaymentMethod = "false";
	private CardDetailTO cardDetailTO;

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
