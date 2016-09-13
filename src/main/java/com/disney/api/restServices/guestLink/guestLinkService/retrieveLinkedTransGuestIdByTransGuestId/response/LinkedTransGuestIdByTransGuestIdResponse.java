package com.disney.api.restServices.guestLink.guestLinkService.retrieveLinkedTransGuestIdByTransGuestId.response;

import com.disney.api.restServices.guestLink.GuestLink;

public class LinkedTransGuestIdByTransGuestIdResponse {
	private String type;
	private String value;
	private GuestLink guestLink;

	/**
	* 
	* @return
	* The type
	*/
	public String getType() {
	return type;
	 }

	/**
	* 
	* @param type
	* The type
	*/
	public void setType(String type) {
	this.type = type;
	 }

	/**
	* 
	* @return
	* The value
	*/
	public String getValue() {
	return value;
	 }

	/**
	* 
	* @param value
	* The value
	*/
	public void setValue(String value) {
	this.value = value;
	 }

	/**
	* 
	* @return
	* The guestLink
	*/
	public GuestLink getGuestLink() {
	return guestLink;
	 }

	/**
	* 
	* @param guestLink
	* The guestLink
	*/
	public void setGuestLink(GuestLink guestLink) {
	this.guestLink = guestLink;
	 }
}
