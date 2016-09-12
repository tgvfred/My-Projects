package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects;

public class Period {
	private Long endDate;
	private Long startDate;

	/**
	* 
	* @return
	* The endDate
	*/
	public Long getEndDate() {
	return endDate;
	 }

	/**
	* 
	* @param endDate
	* The endDate
	*/
	public void setEndDate(Long endDate) {
	this.endDate = endDate;
	 }

	/**
	* 
	* @return
	* The startDate
	*/
	public Long getStartDate() {
	return startDate;
	 }

	/**
	* 
	* @param startDate
	* The startDate
	*/
	public void setStartDate(Long startDate) {
	this.startDate = startDate;
	 }

}
