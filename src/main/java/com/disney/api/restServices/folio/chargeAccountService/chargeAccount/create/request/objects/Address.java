package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class Address {
	private String addressLineOne;
	private String city;
	private String country;
	private String postalCode;
	private String state;

	/**
	* 
	* @return
	* The addressLineOne
	*/
	public String getAddressLineOne() {
	return addressLineOne;
	 }

	/**
	* 
	* @param addressLineOne
	* The addressLineOne
	*/
	public void setAddressLineOne(String addressLineOne) {
		addressLineOne = "3395 NE 9th Dr";
	this.addressLineOne = addressLineOne;
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
		city = "Homestead";
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
		country = "US";
	this.country = country;
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
		postalCode = "33033";
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
		state = "FL";
	this.state = state;
	 }

}
