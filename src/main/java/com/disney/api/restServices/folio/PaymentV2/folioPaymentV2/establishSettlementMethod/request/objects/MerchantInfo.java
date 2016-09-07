package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects;

public class MerchantInfo {
	private String city;
	private String name;
	private String state;
	private String zipCode;

	/**
	* 
	* @return
	* The city
	*/
	public String getCity() {
	return city;
	 }

	/**
	* 
	* @param city
	* The city
	*/
	public void setCity(String city) {
	this.city = city;
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
	* The state
	*/
	public String getState() {
	return state;
	 }

	/**
	* 
	* @param state
	* The state
	*/
	public void setState(String state) {
	this.state = state;
	 }

	/**
	* 
	* @return
	* The zipCode
	*/
	public String getZipCode() {
	return zipCode;
	 }

	/**
	* 
	* @param zipCode
	* The zipCode
	*/
	public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
	 }
}
