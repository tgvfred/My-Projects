package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects;

import java.util.ArrayList;
import java.util.List;

public class Guest {
	private Object suffix;
	private Object title;
	private String firstName;
	private String lastName;
	private Object middleName;
	private Long partyId;
	private List<Object> phoneDetails = new ArrayList<Object>();
	private List<Object> addressDetails = new ArrayList<Object>();
	private List<Object> emailDetails = new ArrayList<Object>();
	private Object membershipDetail;
	private Boolean doNotMailIndicator;
	private Boolean doNotPhoneIndicator;
	private String preferredLanguage;
	private Long dclGuestId;
	private Object dclTransferCode;
	private Long guestId;
	private Object guestTypeName;
	private Object favouriteCharacter;
	private Boolean active;
	private List<Object> guestIdReferences = new ArrayList<Object>();
	private Object gender;
	private Object dob;

	/**
	* 
	* @return
	* The suffix
	*/
	public Object getSuffix() {
	return suffix;
	 }

	/**
	* 
	* @param suffix
	* The suffix
	*/
	public void setSuffix(Object suffix) {
	this.suffix = suffix;
	 }

	/**
	* 
	* @return
	* The title
	*/
	public Object getTitle() {
	return title;
	 }

	/**
	* 
	* @param title
	* The title
	*/
	public void setTitle(Object title) {
	this.title = title;
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
	* The middleName
	*/
	public Object getMiddleName() {
	return middleName;
	 }

	/**
	* 
	* @param middleName
	* The middleName
	*/
	public void setMiddleName(Object middleName) {
	this.middleName = middleName;
	 }

	/**
	* 
	* @return
	* The partyId
	*/
	public Long getPartyId() {
	return partyId;
	 }

	/**
	* 
	* @param partyId
	* The partyId
	*/
	public void setPartyId(Long partyId) {
	this.partyId = partyId;
	 }

	/**
	* 
	* @return
	* The phoneDetails
	*/
	public List<Object> getPhoneDetails() {
	return phoneDetails;
	 }

	/**
	* 
	* @param phoneDetails
	* The phoneDetails
	*/
	public void setPhoneDetails(List<Object> phoneDetails) {
	this.phoneDetails = phoneDetails;
	 }

	/**
	* 
	* @return
	* The addressDetails
	*/
	public List<Object> getAddressDetails() {
	return addressDetails;
	 }

	/**
	* 
	* @param addressDetails
	* The addressDetails
	*/
	public void setAddressDetails(List<Object> addressDetails) {
	this.addressDetails = addressDetails;
	 }

	/**
	* 
	* @return
	* The emailDetails
	*/
	public List<Object> getEmailDetails() {
	return emailDetails;
	 }

	/**
	* 
	* @param emailDetails
	* The emailDetails
	*/
	public void setEmailDetails(List<Object> emailDetails) {
	this.emailDetails = emailDetails;
	 }

	/**
	* 
	* @return
	* The membershipDetail
	*/
	public Object getMembershipDetail() {
	return membershipDetail;
	 }

	/**
	* 
	* @param membershipDetail
	* The membershipDetail
	*/
	public void setMembershipDetail(Object membershipDetail) {
	this.membershipDetail = membershipDetail;
	 }

	/**
	* 
	* @return
	* The doNotMailIndicator
	*/
	public Boolean getDoNotMailIndicator() {
	return doNotMailIndicator;
	 }

	/**
	* 
	* @param doNotMailIndicator
	* The doNotMailIndicator
	*/
	public void setDoNotMailIndicator(Boolean doNotMailIndicator) {
	this.doNotMailIndicator = doNotMailIndicator;
	 }

	/**
	* 
	* @return
	* The doNotPhoneIndicator
	*/
	public Boolean getDoNotPhoneIndicator() {
	return doNotPhoneIndicator;
	 }

	/**
	* 
	* @param doNotPhoneIndicator
	* The doNotPhoneIndicator
	*/
	public void setDoNotPhoneIndicator(Boolean doNotPhoneIndicator) {
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
	public Long getDclGuestId() {
	return dclGuestId;
	 }

	/**
	* 
	* @param dclGuestId
	* The dclGuestId
	*/
	public void setDclGuestId(Long dclGuestId) {
	this.dclGuestId = dclGuestId;
	 }

	/**
	* 
	* @return
	* The dclTransferCode
	*/
	public Object getDclTransferCode() {
	return dclTransferCode;
	 }

	/**
	* 
	* @param dclTransferCode
	* The dclTransferCode
	*/
	public void setDclTransferCode(Object dclTransferCode) {
	this.dclTransferCode = dclTransferCode;
	 }

	/**
	* 
	* @return
	* The guestId
	*/
	public Long getGuestId() {
	return guestId;
	 }

	/**
	* 
	* @param guestId
	* The guestId
	*/
	public void setGuestId(Long guestId) {
	this.guestId = guestId;
	 }

	/**
	* 
	* @return
	* The guestTypeName
	*/
	public Object getGuestTypeName() {
	return guestTypeName;
	 }

	/**
	* 
	* @param guestTypeName
	* The guestTypeName
	*/
	public void setGuestTypeName(Object guestTypeName) {
	this.guestTypeName = guestTypeName;
	 }

	/**
	* 
	* @return
	* The favouriteCharacter
	*/
	public Object getFavouriteCharacter() {
	return favouriteCharacter;
	 }

	/**
	* 
	* @param favouriteCharacter
	* The favouriteCharacter
	*/
	public void setFavouriteCharacter(Object favouriteCharacter) {
	this.favouriteCharacter = favouriteCharacter;
	 }

	/**
	* 
	* @return
	* The active
	*/
	public Boolean getActive() {
	return active;
	 }

	/**
	* 
	* @param active
	* The active
	*/
	public void setActive(Boolean active) {
	this.active = active;
	 }

	/**
	* 
	* @return
	* The guestIdReferences
	*/
	public List<Object> getGuestIdReferences() {
	return guestIdReferences;
	 }

	/**
	* 
	* @param guestIdReferences
	* The guestIdReferences
	*/
	public void setGuestIdReferences(List<Object> guestIdReferences) {
	this.guestIdReferences = guestIdReferences;
	 }

	/**
	* 
	* @return
	* The gender
	*/
	public Object getGender() {
	return gender;
	 }

	/**
	* 
	* @param gender
	* The gender
	*/
	public void setGender(Object gender) {
	this.gender = gender;
	 }

	/**
	* 
	* @return
	* The dob
	*/
	public Object getDob() {
	return dob;
	 }

	/**
	* 
	* @param dob
	* The dob
	*/
	public void setDob(Object dob) {
	this.dob = dob;
	 }
}
