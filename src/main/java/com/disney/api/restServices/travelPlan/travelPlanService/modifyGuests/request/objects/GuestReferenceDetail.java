package com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.objects;

import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.objects.Guest;

public class GuestReferenceDetail {
	private String age = "0";
	private String ageType = "ADULT";
	private Guest guest;
	private String purposeOfVisit = "Leisure";
	private String role = "Guest";
	private String correlationID = "0";

	public GuestReferenceDetail(){
		guest = new Guest();
	}
	/**
	* 
	* @return
	* The age
	*/
	public String getAge() {
	return age;
	 }

	/**
	* 
	* @param age
	* The age
	*/
	public void setAge(String age) {
	this.age = age;
	 }

	/**
	* 
	* @return
	* The ageType
	*/
	public String getAgeType() {
	return ageType;
	 }

	/**
	* 
	* @param ageType
	* The ageType
	*/
	public void setAgeType(String ageType) {
	this.ageType = ageType;
	 }

	/**
	* 
	* @return
	* The guest
	*/
	public Guest getGuest() {
	return guest;
	 }

	/**
	* 
	* @param guest
	* The guest
	*/
	public void setGuest(Guest guest) {
	this.guest = guest;
	 }

	/**
	* 
	* @return
	* The purposeOfVisit
	*/
	public String getPurposeOfVisit() {
	return purposeOfVisit;
	 }

	/**
	* 
	* @param purposeOfVisit
	* The purposeOfVisit
	*/
	public void setPurposeOfVisit(String purposeOfVisit) {
	this.purposeOfVisit = purposeOfVisit;
	 }

	/**
	* 
	* @return
	* The role
	*/
	public String getRole() {
	return role;
	 }

	/**
	* 
	* @param role
	* The role
	*/
	public void setRole(String role) {
	this.role = role;
	 }

	/**
	* 
	* @return
	* The correlationID
	*/
	public String getCorrelationID() {
	return correlationID;
	 }

	/**
	* 
	* @param correlationID
	* The correlationID
	*/
	public void setCorrelationID(String correlationID) {
	this.correlationID = correlationID;
	 }

	
}
