package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.response.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonChargeAccountResponse {

	private String description;
	private double endDate;
	private List<Object> externalReferences = new ArrayList<Object>();
	private String guaranteeType;
	private int id;
	private List<Object> mergedChargeAccountIds = new ArrayList<Object>();
	private double startDate;
	private String status;
	private List<Object> paymentMethodDetail = new ArrayList<Object>();
	private List<GuestInfoTO> guestInfoTO = new ArrayList<GuestInfoTO>();
	private Boolean active;
	private Boolean pinAvailable;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 * The description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 * The endDate
	 */
	public double getEndDate() {
		return endDate;
	}

	/**
	 * 
	 * @param endDate
	 * The endDate
	 */
	public void setEndDate(double endDate) {
		this.endDate = endDate;
	}

	/**
	 * 
	 * @return
	 * The externalReferences
	 */
	public List<Object> getExternalReferences() {
		return externalReferences;
	}

	/**
	 * 
	 * @param externalReferences
	 * The externalReferences
	 */
	public void setExternalReferences(List<Object> externalReferences) {
		this.externalReferences = externalReferences;
	}

	/**
	 * 
	 * @return
	 * The guaranteeType
	 */
	public String getGuaranteeType() {
		return guaranteeType;
	}

	/**
	 * 
	 * @param guaranteeType
	 * The guaranteeType
	 */
	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	/**
	 * 
	 * @return
	 * The id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 * The id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 * The mergedChargeAccountIds
	 */
	public List<Object> getMergedChargeAccountIds() {
		return mergedChargeAccountIds;
	}

	/**
	 * 
	 * @param mergedChargeAccountIds
	 * The mergedChargeAccountIds
	 */
	public void setMergedChargeAccountIds(List<Object> mergedChargeAccountIds) {
		this.mergedChargeAccountIds = mergedChargeAccountIds;
	}

	/**
	 * 
	 * @return
	 * The startDate
	 */
	public double getStartDate() {
		return startDate;
	}

	/**
	 * 
	 * @param startDate
	 * The startDate
	 */
	public void setStartDate(double startDate) {
		this.startDate = startDate;
	}

	/**
	 * 
	 * @return
	 * The status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 * The status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 * The paymentMethodDetail
	 */
	public List<Object> getPaymentMethodDetail() {
		return paymentMethodDetail;
	}

	/**
	 * 
	 * @param paymentMethodDetail
	 * The paymentMethodDetail
	 */
	public void setPaymentMethodDetail(List<Object> paymentMethodDetail) {
		this.paymentMethodDetail = paymentMethodDetail;
	}

	/**
	 * 
	 * @return
	 * The guestInfoTO
	 */
	public List<GuestInfoTO> getGuestInfoTO() {
		return guestInfoTO;
	}

	/**
	 * 
	 * @param guestInfoTO
	 * The guestInfoTO
	 */
	public void setGuestInfoTO(List<GuestInfoTO> guestInfoTO) {
		this.guestInfoTO = guestInfoTO;
	}

	/**
	 * 
	 * @return
	 * The active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * 
	 * @param active
	 * The active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * 
	 * @return
	 * The pinAvailable
	 */
	public Boolean getPinAvailable() {
		return pinAvailable;
	}

	/**
	 * 
	 * @param pinAvailable
	 * The pinAvailable
	 */
	public void setPinAvailable(Boolean pinAvailable) {
		this.pinAvailable = pinAvailable;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}