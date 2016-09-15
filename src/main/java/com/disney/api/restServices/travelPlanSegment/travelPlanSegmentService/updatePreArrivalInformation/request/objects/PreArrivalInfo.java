package com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.request.objects;

import java.util.ArrayList;
import java.util.List;

import com.disney.test.utils.Randomness;

public class PreArrivalInfo {
	private String termsAndConditionsVersionNumber = "11.27.2012_4_1.0";
	private String arrivalTime = Randomness.generateCurrentXMLDatetime(10)+"-04:00";
	private String departureTime = Randomness.generateCurrentXMLDatetime(15)+"-04:00";
	private List<OnsiteContactDetail> onsiteContactDetails = new ArrayList<OnsiteContactDetail>();

	
	public PreArrivalInfo(){
		onsiteContactDetails.add(new OnsiteContactDetail());
	}
	
	public void addOnsiteContactDetail(){
		onsiteContactDetails.add(new OnsiteContactDetail());
	}
	/**
	* 
	* @return
	* The termsAndConditionsVersionNumber
	*/
	public String getTermsAndConditionsVersionNumber() {
	return termsAndConditionsVersionNumber;
	 }

	/**
	* 
	* @param termsAndConditionsVersionNumber
	* The termsAndConditionsVersionNumber
	*/
	public void setTermsAndConditionsVersionNumber(String termsAndConditionsVersionNumber) {
	this.termsAndConditionsVersionNumber = termsAndConditionsVersionNumber;
	 }

	/**
	* 
	* @return
	* The arrivalTime
	*/
	public String getArrivalTime() {
	return arrivalTime;
	 }

	/**
	* 
	* @param arrivalTime
	* The arrivalTime
	*/
	public void setArrivalTime(String arrivalTime) {
	this.arrivalTime = arrivalTime;
	 }

	/**
	* 
	* @return
	* The departureTime
	*/
	public String getDepartureTime() {
	return departureTime;
	 }

	/**
	* 
	* @param departureTime
	* The departureTime
	*/
	public void setDepartureTime(String departureTime) {
	this.departureTime = departureTime;
	 }

	/**
	* 
	* @return
	* The onsiteContactDetails
	*/
	public List<OnsiteContactDetail> getOnsiteContactDetails() {
	return onsiteContactDetails;
	 }

	/**
	* 
	* @param onsiteContactDetails
	* The onsiteContactDetails
	*/
	public void setOnsiteContactDetails(List<OnsiteContactDetail> onsiteContactDetails) {
	this.onsiteContactDetails = onsiteContactDetails;
	 }
}
