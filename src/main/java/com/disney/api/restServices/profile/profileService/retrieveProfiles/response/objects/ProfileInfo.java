package com.disney.api.restServices.profile.profileService.retrieveProfiles.response.objects;

import java.util.ArrayList;
import java.util.List;

public class ProfileInfo {
	private String code;
	private String description;
	private Object externalReferenceValue;
	private Long profileId;
	private String profileType;
	private String lastUpdateBy;
	private Long lastUpdateOn;
	private String createdBy;
	private Long createdOn;
	private Object profileServiceDetail;
	private List<Routing> routing = new ArrayList<Routing>();
	private Boolean active;
	private Boolean priority;

	/**
	* 
	* @return
	* The code
	*/
	public String getCode() {
	return code;
	 }

	/**
	* 
	* @param code
	* The code
	*/
	public void setCode(String code) {
	this.code = code;
	 }

	/**
	* 
	* @return
	* The description
	*/
	public String getDescription() {
	return description;
	 }

	/**
	* 
	* @param description
	* The description
	*/
	public void setDescription(String description) {
	this.description = description;
	 }

	/**
	* 
	* @return
	* The externalReferenceValue
	*/
	public Object getExternalReferenceValue() {
	return externalReferenceValue;
	 }

	/**
	* 
	* @param externalReferenceValue
	* The externalReferenceValue
	*/
	public void setExternalReferenceValue(Object externalReferenceValue) {
	this.externalReferenceValue = externalReferenceValue;
	 }

	/**
	* 
	* @return
	* The profileId
	*/
	public Long getProfileId() {
	return profileId;
	 }

	/**
	* 
	* @param profileId
	* The profileId
	*/
	public void setProfileId(Long profileId) {
	this.profileId = profileId;
	 }

	/**
	* 
	* @return
	* The profileType
	*/
	public String getProfileType() {
	return profileType;
	 }

	/**
	* 
	* @param profileType
	* The profileType
	*/
	public void setProfileType(String profileType) {
	this.profileType = profileType;
	 }

	/**
	* 
	* @return
	* The lastUpdateBy
	*/
	public String getLastUpdateBy() {
	return lastUpdateBy;
	 }

	/**
	* 
	* @param lastUpdateBy
	* The lastUpdateBy
	*/
	public void setLastUpdateBy(String lastUpdateBy) {
	this.lastUpdateBy = lastUpdateBy;
	 }

	/**
	* 
	* @return
	* The lastUpdateOn
	*/
	public Long getLastUpdateOn() {
	return lastUpdateOn;
	 }

	/**
	* 
	* @param lastUpdateOn
	* The lastUpdateOn
	*/
	public void setLastUpdateOn(Long lastUpdateOn) {
	this.lastUpdateOn = lastUpdateOn;
	 }

	/**
	* 
	* @return
	* The createdBy
	*/
	public String getCreatedBy() {
	return createdBy;
	 }

	/**
	* 
	* @param createdBy
	* The createdBy
	*/
	public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
	 }

	/**
	* 
	* @return
	* The createdOn
	*/
	public Long getCreatedOn() {
	return createdOn;
	 }

	/**
	* 
	* @param createdOn
	* The createdOn
	*/
	public void setCreatedOn(Long createdOn) {
	this.createdOn = createdOn;
	 }

	/**
	* 
	* @return
	* The profileServiceDetail
	*/
	public Object getProfileServiceDetail() {
	return profileServiceDetail;
	 }

	/**
	* 
	* @param profileServiceDetail
	* The profileServiceDetail
	*/
	public void setProfileServiceDetail(Object profileServiceDetail) {
	this.profileServiceDetail = profileServiceDetail;
	 }

	/**
	* 
	* @return
	* The routing
	*/
	public List<Routing> getRouting() {
	return routing;
	 }

	/**
	* 
	* @param routing
	* The routing
	*/
	public void setRouting(List<Routing> routing) {
	this.routing = routing;
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
	* The priority
	*/
	public Boolean getPriority() {
	return priority;
	 }

	/**
	* 
	* @param priority
	* The priority
	*/
	public void setPriority(Boolean priority) {
	this.priority = priority;
	 }
}
