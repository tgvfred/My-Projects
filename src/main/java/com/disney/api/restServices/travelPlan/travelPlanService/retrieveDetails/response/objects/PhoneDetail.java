package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects;

public class PhoneDetail {
	private String deviceType;
	private Long extension;
	private String number;
	private Long locatorId;
	private Long guestLocatorId;
	private String locatorUseType;
	private Boolean primary;

	/**
	* 
	* @return
	* The deviceType
	*/
	public String getDeviceType() {
	return deviceType;
	 }

	/**
	* 
	* @param deviceType
	* The deviceType
	*/
	public void setDeviceType(String deviceType) {
	this.deviceType = deviceType;
	 }

	/**
	* 
	* @return
	* The extension
	*/
	public Long getExtension() {
	return extension;
	 }

	/**
	* 
	* @param extension
	* The extension
	*/
	public void setExtension(Long extension) {
	this.extension = extension;
	 }

	/**
	* 
	* @return
	* The number
	*/
	public String getNumber() {
	return number;
	 }

	/**
	* 
	* @param number
	* The number
	*/
	public void setNumber(String number) {
	this.number = number;
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
