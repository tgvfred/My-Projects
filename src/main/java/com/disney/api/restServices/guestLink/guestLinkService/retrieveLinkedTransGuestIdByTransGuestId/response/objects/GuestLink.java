package com.disney.api.restServices.guestLink.guestLinkService.retrieveLinkedTransGuestIdByTransGuestId.response.objects;

import java.util.ArrayList;
import java.util.List;

public class GuestLink {
	private List<Object> xbands = new ArrayList<Object>();
	private List<LinkedTransactionalGuest> linkedTransactionalGuests = new ArrayList<LinkedTransactionalGuest>();
	private List<Object> linkedEntitlementDetails = new ArrayList<Object>();

	/**
	* 
	* @return
	* The xbands
	*/
	public List<Object> getXbands() {
	return xbands;
	 }

	/**
	* 
	* @param xbands
	* The xbands
	*/
	public void setXbands(List<Object> xbands) {
	this.xbands = xbands;
	 }

	/**
	* 
	* @return
	* The linkedTransactionalGuests
	*/
	public List<LinkedTransactionalGuest> getLinkedTransactionalGuests() {
	return linkedTransactionalGuests;
	 }

	/**
	* 
	* @param linkedTransactionalGuests
	* The linkedTransactionalGuests
	*/
	public void setLinkedTransactionalGuests(List<LinkedTransactionalGuest> linkedTransactionalGuests) {
	this.linkedTransactionalGuests = linkedTransactionalGuests;
	 }

	/**
	* 
	* @return
	* The linkedEntitlementDetails
	*/
	public List<Object> getLinkedEntitlementDetails() {
	return linkedEntitlementDetails;
	 }

	/**
	* 
	* @param linkedEntitlementDetails
	* The linkedEntitlementDetails
	*/
	public void setLinkedEntitlementDetails(List<Object> linkedEntitlementDetails) {
	this.linkedEntitlementDetails = linkedEntitlementDetails;
	 }
}
