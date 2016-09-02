package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

import java.util.ArrayList;
import java.util.List;

public class GuestInfoTO {
	private String firstName = "Marisol";
	private String lastName = "Centeno";
	private String txnGuestId = "0";
	private List<ExternalReference> externalReference = new ArrayList<ExternalReference>();

	public GuestInfoTO(){
		externalReference.add(new ExternalReference() );
	}
	/**
	* 
	* @return
	* The firstName
	*/
	public String getFirstName() {
	return firstName;
	 }

	/**
	* 
	* @param firstName
	* The firstName
	*/
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	 }

	/**
	* 
	* @return
	* The lastName
	*/
	public String getLastName() {
	return lastName;
	 }

	/**
	* 
	* @param lastName
	* The lastName
	*/
	public void setLastName(String lastName) {
	this.lastName = lastName;
	 }

	/**
	* 
	* @return
	* The txnGuestId
	*/
	public String getTxnGuestId() {
	return txnGuestId;
	 }

	/**
	* 
	* @param txnGuestId
	* The txnGuestId
	*/
	public void setTxnGuestId(String txnGuestId) {
	this.txnGuestId = txnGuestId;
	 }

	/**
	* 
	* @return
	* The externalReference
	*/
	public List<ExternalReference> getExternalReference() {
	return externalReference;
	 }

	/**
	* 
	* @param externalReference
	* The externalReference
	*/
	public void setExternalReference(List<ExternalReference> externalReference) {
	this.externalReference = externalReference;
	 }
}
