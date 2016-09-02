package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects;

public class CardHolderInfo {
	private String addressLine1;
	private String city;
	private String country;
	private String name;
	private String postalCode;
	private String state;

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
}
