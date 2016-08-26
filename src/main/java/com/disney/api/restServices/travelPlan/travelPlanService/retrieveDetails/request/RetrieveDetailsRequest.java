package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request;

public class RetrieveDetailsRequest {
	private String travelPlanSegmentId;
	private String retrieveComments;
	private String retrieveRoom;

	/**
	* 
	* @return
	* The travelPlanSegmentId
	*/
	public String getTravelPlanSegmentId() {
	return travelPlanSegmentId;
	 }

	/**
	* 
	* @param travelPlanSegmentId
	* The travelPlanSegmentId
	*/
	public void setTravelPlanSegmentId(String travelPlanSegmentId) {
	this.travelPlanSegmentId = travelPlanSegmentId;
	 }

	/**
	* 
	* @return
	* The retrieveComments
	*/
	public String getRetrieveComments() {
	return retrieveComments;
	 }

	/**
	* 
	* @param retrieveComments
	* The retrieveComments
	*/
	public void setRetrieveComments(String retrieveComments) {
	this.retrieveComments = retrieveComments;
	 }

	/**
	* 
	* @return
	* The retrieveRoom
	*/
	public String getRetrieveRoom() {
	return retrieveRoom;
	 }

	/**
	* 
	* @param retrieveRoom
	* The retrieveRoom
	*/
	public void setRetrieveRoom(String retrieveRoom) {
	this.retrieveRoom = retrieveRoom;
	 }
}
