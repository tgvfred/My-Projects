package com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.response;

public class UpdatePreArrivalInformationResponse {
	private Boolean success;

	/**
	* No args constructor for use in serialization
	* 
	*/
	public UpdatePreArrivalInformationResponse() {
	 }

	/**
	* 
	* @param success
	*/
	public UpdatePreArrivalInformationResponse(Boolean success) {
	this.success = success;
	 }

	/**
	* 
	* @return
	* The success
	*/
	public Boolean getSuccess() {
	return success;
	 }

	/**
	* 
	* @param success
	* The success
	*/
	public void setSuccess(Boolean success) {
	this.success = success;
	 }
}
