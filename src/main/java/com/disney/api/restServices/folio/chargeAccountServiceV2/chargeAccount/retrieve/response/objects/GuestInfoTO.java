package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestInfoTO {

	private String firstName;
	private Object middleName;
	private Object suffix;
	private String title;
	private String lastName;
	private double txnGuestId;
	private List<ExternalReference> externalReference = new ArrayList<ExternalReference>();
	private Object address;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	 * The middleName
	 */
	public Object getMiddleName() {
		return middleName;
	}

	/**
	 * 
	 * @param middleName
	 * The middleName
	 */
	public void setMiddleName(Object middleName) {
		this.middleName = middleName;
	}

	/**
	 * 
	 * @return
	 * The suffix
	 */
	public Object getSuffix() {
		return suffix;
	}

	/**
	 * 
	 * @param suffix
	 * The suffix
	 */
	public void setSuffix(Object suffix) {
		this.suffix = suffix;
	}

	/**
	 * 
	 * @return
	 * The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 * The title
	 */
	public void setTitle(String title) {
		this.title = title;
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
	public double getTxnGuestId() {
		return txnGuestId;
	}

	/**
	 * 
	 * @param txnGuestId
	 * The txnGuestId
	 */
	public void setTxnGuestId(double txnGuestId) {
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

	/**
	 * 
	 * @return
	 * The address
	 */
	public Object getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 * The address
	 */
	public void setAddress(Object address) {
		this.address = address;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}