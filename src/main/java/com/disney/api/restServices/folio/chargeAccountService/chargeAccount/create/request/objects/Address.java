package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class Address {
	private String addressLineOne ="3395 NE 9th Dr";
	private String city = "Homestead";
	private String country =  "US";
	private String postalCode = "33033";
	private String state = "FL";

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
