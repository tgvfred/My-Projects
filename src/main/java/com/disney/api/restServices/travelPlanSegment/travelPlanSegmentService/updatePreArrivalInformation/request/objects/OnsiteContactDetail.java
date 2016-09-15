package com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.request.objects;

public class OnsiteContactDetail {
	private String contactType = "Phone";
	private String contactValue = "9854579854";

	/**
	* No args constructor for use in serialization
	* 
	*/
	public OnsiteContactDetail(){	
	}
	
	/**
	* 
	* @return
	* The contactType
	*/
	public String getContactType() {
	return contactType;
	 }

	/**
	* 
	* @param contactType
	* The contactType
	*/
	public void setContactType(String contactType) {
	this.contactType = contactType;
	 }

	/**
	* 
	* @return
	* The contactValue
	*/
	public String getContactValue() {
	return contactValue;
	 }

	/**
	* 
	* @param contactValue
	* The contactValue
	*/
	public void setContactValue(String contactValue) {
	this.contactValue = contactValue;
	 }
}
