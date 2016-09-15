package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.response.objects;

public class CardHolderInfo {
	private String name;
	private String addressLine1;
	private Object addressLine2;
	private String city;
	private String state;
	private String postalCode;
	private String country;

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
	* The addressLine1
	*/
	public String getAddressLine1() {
	return addressLine1;
	 }

	/**
	* 
	* @param addressLine1
	* The addressLine1
	*/
	public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
	 }

	/**
	* 
	* @return
	* The addressLine2
	*/
	public Object getAddressLine2() {
	return addressLine2;
	 }

	/**
	* 
	* @param addressLine2
	* The addressLine2
	*/
	public void setAddressLine2(Object addressLine2) {
	this.addressLine2 = addressLine2;
	 }

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
	* The postalCode
	*/
	public String getPostalCode() {
	return postalCode;
	 }

	/**
	* 
	* @param postalCode
	* The postalCode
	*/
	public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
	 }

	/**
	* 
	* @return
	* The country
	*/
	public String getCountry() {
	return country;
	 }

	/**
	* 
	* @param country
	* The country
	*/
	public void setCountry(String country) {
	this.country = country;
	 }
}
