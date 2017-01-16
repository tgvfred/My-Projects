package com.disney.api.restServices.accommodation.accommodationSales.updateComments.response.objects;

public class AuditDetail {
	private String createdBy;
	private Long createdDate;
	private Object updatedBy;
	private Object updatedDate;
	private Object status;

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
	public Object getUpdatedBy() {
	return updatedBy;
	 }

	/**
	* 
	* @param updatedBy
	* The updatedBy
	*/
	public void setUpdatedBy(Object updatedBy) {
	this.updatedBy = updatedBy;
	 }

	/**
	* 
	* @return
	* The updatedDate
	*/
	public Object getUpdatedDate() {
	return updatedDate;
	 }

	/**
	* 
	* @param updatedDate
	* The updatedDate
	*/
	public void setUpdatedDate(Object updatedDate) {
	this.updatedDate = updatedDate;
	 }

	/**
	* 
	* @return
	* The status
	*/
	public Object getStatus() {
	return status;
	 }

	/**
	* 
	* @param status
	* The status
	*/
	public void setStatus(Object status) {
	this.status = status;
	 }
}
