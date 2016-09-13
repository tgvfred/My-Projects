package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects;

public class EmailDetail {
	private String address;
	private Long locatorId;
	private Long guestLocatorId;
	private String locatorUseType;
	private Boolean primary;

	/**
	* 
	* @return
	* The address
	*/
	public String getAddress() {
	return address;
	 }

	/**
	* 
	* @param address
	* The address
	*/
	public void setAddress(String address) {
	this.address = address;
	 }

	/**
	* 
	* @return
	* The locatorId
	*/
	public Long getLocatorId() {
	return locatorId;
	 }

	/**
	* 
	* @param locatorId
	* The locatorId
	*/
	public void setLocatorId(Long locatorId) {
	this.locatorId = locatorId;
	 }

	/**
	* 
	* @return
	* The guestLocatorId
	*/
	public Long getGuestLocatorId() {
	return guestLocatorId;
	 }

	/**
	* 
	* @param guestLocatorId
	* The guestLocatorId
	*/
	public void setGuestLocatorId(Long guestLocatorId) {
	this.guestLocatorId = guestLocatorId;
	 }

	/**
	* 
	* @return
	* The locatorUseType
	*/
	public String getLocatorUseType() {
	return locatorUseType;
	 }

	/**
	* 
	* @param locatorUseType
	* The locatorUseType
	*/
	public void setLocatorUseType(String locatorUseType) {
	this.locatorUseType = locatorUseType;
	 }

	/**
	* 
	* @return
	* The primary
	*/
	public Boolean getPrimary() {
	return primary;
	 }

	/**
	* 
	* @param primary
	* The primary
	*/
	public void setPrimary(Boolean primary) {
	this.primary = primary;
	 }

}
