package com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.objects;

import java.util.ArrayList;
import java.util.List;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.objects.AddressDetail;

public class Guest {
	private String firstName = "Quick22";
	private String lastName = "Book22";
	private String partyId = "0";
	private List<AddressDetail> addressDetails = new ArrayList<AddressDetail>();
	private String doNotMailIndicator = "false";
	private String doNotPhoneIndicator ="false";
	private String preferredLanguage = "eng";
	private String dclGuestId = "0";
	private String guestId ="238497533";
	private String active = "true";

	public Guest(){
		addressDetails.add(new AddressDetail());
	}
	

	public void addAddressDetails(){
       addressDetails.add(new AddressDetail());
	}

	/**
	* 
	* @return
	* The firstName
	*/
	public String getFirstName() {
	return firstName;
	 }

	/**
	* 
	* @param firstName
	* The firstName
	*/
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	 }

	/**
	* 
	* @return
	* The lastName
	*/
	public String getLastName() {
	return lastName;
	 }

	/**
	* 
	* @param lastName
	* The lastName
	*/
	public void setLastName(String lastName) {
	this.lastName = lastName;
	 }

	/**
	* 
	* @return
	* The partyId
	*/
	public String getPartyId() {
	return partyId;
	 }

	/**
	* 
	* @param partyId
	* The partyId
	*/
	public void setPartyId(String partyId) {
	this.partyId = partyId;
	 }

	/**
	* 
	* @return
	* The addressDetails
	*/
	public List<AddressDetail> getAddressDetails() {
	return addressDetails;
	 }

	/**
	* 
	* @param addressDetails
	* The addressDetails
	*/
	public void setAddressDetails(List<AddressDetail> addressDetails) {
	this.addressDetails = addressDetails;
	 }

	/**
	* 
	* @return
	* The doNotMailIndicator
	*/
	public String getDoNotMailIndicator() {
	return doNotMailIndicator;
	 }

	/**
	* 
	* @param doNotMailIndicator
	* The doNotMailIndicator
	*/
	public void setDoNotMailIndicator(String doNotMailIndicator) {
	this.doNotMailIndicator = doNotMailIndicator;
	 }

	/**
	* 
	* @return
	* The doNotPhoneIndicator
	*/
	public String getDoNotPhoneIndicator() {
	return doNotPhoneIndicator;
	 }

	/**
	* 
	* @param doNotPhoneIndicator
	* The doNotPhoneIndicator
	*/
	public void setDoNotPhoneIndicator(String doNotPhoneIndicator) {
	this.doNotPhoneIndicator = doNotPhoneIndicator;
	 }

	/**
	* 
	* @return
	* The preferredLanguage
	*/
	public String getPreferredLanguage() {
	return preferredLanguage;
	 }

	/**
	* 
	* @param preferredLanguage
	* The preferredLanguage
	*/
	public void setPreferredLanguage(String preferredLanguage) {
	this.preferredLanguage = preferredLanguage;
	 }

	/**
	* 
	* @return
	* The dclGuestId
	*/
	public String getDclGuestId() {
	return dclGuestId;
	 }

	/**
	* 
	* @param dclGuestId
	* The dclGuestId
	*/
	public void setDclGuestId(String dclGuestId) {
	this.dclGuestId = dclGuestId;
	 }

	/**
	* 
	* @return
	* The guestId
	*/
	public String getGuestId() {
	return guestId;
	 }

	/**
	* 
	* @param guestId
	* The guestId
	*/
	public void setGuestId(String guestId) {
	this.guestId = guestId;
	 }

	/**
	* 
	* @return
	* The active
	*/
	public String getActive() {
	return active;
	 }

	/**
	* 
	* @param active
	* The active
	*/
	public void setActive(String active) {
	this.active = active;
	 }
}
