package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response.objects;

public class Address {
	private String addressLineOne;
	private Object addressLineTwo;
	private String city;
	private Object country;
	private String postalCode;
	private String state;
	private Object regionName;

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
	* The addressLineTwo
	*/
	public Object getAddressLineTwo() {
	return addressLineTwo;
	 }

	/**
	* 
	* @param addressLineTwo
	* The addressLineTwo
	*/
	public void setAddressLineTwo(Object addressLineTwo) {
	this.addressLineTwo = addressLineTwo;
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
	public Object getCountry() {
	return country;
	 }

	/**
	* 
	* @param country
	* The country
	*/
	public void setCountry(Object country) {
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

	/**
	* 
	* @return
	* The regionName
	*/
	public Object getRegionName() {
	return regionName;
	 }

	/**
	* 
	* @param regionName
	* The regionName
	*/
	public void setRegionName(Object regionName) {
	this.regionName = regionName;
	 }
}
