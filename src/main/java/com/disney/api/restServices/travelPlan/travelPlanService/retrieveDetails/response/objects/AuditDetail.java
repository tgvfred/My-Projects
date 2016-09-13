package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects;

public class AuditDetail {
	private String createdBy;
	private Long createdDate;
	private String updatedBy;
	private Long updatedDate;
	private String status;

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
	* The createdDate
	*/
	public Long getCreatedDate() {
	return createdDate;
	 }

	/**
	* 
	* @param createdDate
	* The createdDate
	*/
	public void setCreatedDate(Long createdDate) {
	this.createdDate = createdDate;
	 }

	/**
	* 
	* @return
	* The updatedBy
	*/
	public String getUpdatedBy() {
	return updatedBy;
	 }

	/**
	* 
	* @param updatedBy
	* The updatedBy
	*/
	public void setUpdatedBy(String updatedBy) {
	this.updatedBy = updatedBy;
	 }

	/**
	* 
	* @return
	* The updatedDate
	*/
	public Long getUpdatedDate() {
	return updatedDate;
	 }

	/**
	* 
	* @param updatedDate
	* The updatedDate
	*/
	public void setUpdatedDate(Long updatedDate) {
	this.updatedDate = updatedDate;
	 }

	/**
	* 
	* @return
	* The status
	*/
	public String getStatus() {
	return status;
	 }

	/**
	* 
	* @param status
	* The status
	*/
	public void setStatus(String status) {
	this.status = status;
	 }

}
