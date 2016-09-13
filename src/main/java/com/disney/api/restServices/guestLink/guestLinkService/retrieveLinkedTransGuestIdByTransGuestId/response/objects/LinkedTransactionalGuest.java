package com.disney.api.restServices.guestLink.guestLinkService.retrieveLinkedTransGuestIdByTransGuestId.response.objects;

import java.util.ArrayList;
import java.util.List;

public class LinkedTransactionalGuest {
	private String type;
	private String value;
	private Object inactiveDateTime;
	private List<TransactionalGuestExternalReference> transactionalGuestExternalReferences = new ArrayList<TransactionalGuestExternalReference>();

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
	* The inactiveDateTime
	*/
	public Object getInactiveDateTime() {
	return inactiveDateTime;
	 }

	/**
	* 
	* @param inactiveDateTime
	* The inactiveDateTime
	*/
	public void setInactiveDateTime(Object inactiveDateTime) {
	this.inactiveDateTime = inactiveDateTime;
	 }

	/**
	* 
	* @return
	* The transactionalGuestExternalReferences
	*/
	public List<TransactionalGuestExternalReference> getTransactionalGuestExternalReferences() {
	return transactionalGuestExternalReferences;
	 }

	/**
	* 
	* @param transactionalGuestExternalReferences
	* The transactionalGuestExternalReferences
	*/
	public void setTransactionalGuestExternalReferences(List<TransactionalGuestExternalReference> transactionalGuestExternalReferences) {
	this.transactionalGuestExternalReferences = transactionalGuestExternalReferences;
	 }
}
