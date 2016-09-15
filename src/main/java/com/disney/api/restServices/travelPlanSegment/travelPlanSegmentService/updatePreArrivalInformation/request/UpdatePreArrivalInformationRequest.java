package com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.request;

import com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.request.objects.PreArrivalInfo;

public class UpdatePreArrivalInformationRequest {
	private String travelPlanSegmentId;
	private PreArrivalInfo preArrivalInfo;

	public UpdatePreArrivalInformationRequest(){
		preArrivalInfo = new PreArrivalInfo();
	}
	
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
	* The preArrivalInfo
	*/
	public PreArrivalInfo getPreArrivalInfo() {
	return preArrivalInfo;
	 }

	/**
	* 
	* @param preArrivalInfo
	* The preArrivalInfo
	*/
	public void setPreArrivalInfo(PreArrivalInfo preArrivalInfo) {
	this.preArrivalInfo = preArrivalInfo;
	 }
}
