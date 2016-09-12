package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.response.objects;

import java.util.ArrayList;
import java.util.List;

public class ComponentGrouping {
	private Accommodation accommodation;
	private Object upgradedAccommodation;
	private List<Object> tickets = new ArrayList<Object>();
	private Object dme;
	private Long travelComponentGroupingId;
	private Object bundleDetail;

	/**
	* 
	* @return
	* The accommodation
	*/
	public Accommodation getAccommodation() {
	return accommodation;
	 }

	/**
	* 
	* @param accommodation
	* The accommodation
	*/
	public void setAccommodation(Accommodation accommodation) {
	this.accommodation = accommodation;
	 }

	/**
	* 
	* @return
	* The upgradedAccommodation
	*/
	public Object getUpgradedAccommodation() {
	return upgradedAccommodation;
	 }

	/**
	* 
	* @param upgradedAccommodation
	* The upgradedAccommodation
	*/
	public void setUpgradedAccommodation(Object upgradedAccommodation) {
	this.upgradedAccommodation = upgradedAccommodation;
	 }

	/**
	* 
	* @return
	* The tickets
	*/
	public List<Object> getTickets() {
	return tickets;
	 }

	/**
	* 
	* @param tickets
	* The tickets
	*/
	public void setTickets(List<Object> tickets) {
	this.tickets = tickets;
	 }

	/**
	* 
	* @return
	* The dme
	*/
	public Object getDme() {
	return dme;
	 }

	/**
	* 
	* @param dme
	* The dme
	*/
	public void setDme(Object dme) {
	this.dme = dme;
	 }

	/**
	* 
	* @return
	* The travelComponentGroupingId
	*/
	public Long getTravelComponentGroupingId() {
	return travelComponentGroupingId;
	 }

	/**
	* 
	* @param travelComponentGroupingId
	* The travelComponentGroupingId
	*/
	public void setTravelComponentGroupingId(Long travelComponentGroupingId) {
	this.travelComponentGroupingId = travelComponentGroupingId;
	 }

	/**
	* 
	* @return
	* The bundleDetail
	*/
	public Object getBundleDetail() {
	return bundleDetail;
	 }

	/**
	* 
	* @param bundleDetail
	* The bundleDetail
	*/
	public void setBundleDetail(Object bundleDetail) {
	this.bundleDetail = bundleDetail;
	 }

}
