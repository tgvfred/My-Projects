package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

import java.util.ArrayList;
import java.util.List;

public class ChargeAccountCommonRequest {
	private String description= "From Booking";
	private Period period;
	private String active = "true";
	private String txnFacilityId = "80010401";
	private String srcAcctCenterId = "2";
	private String defaultFolioRequired = "false";
	private List<ChargeAccountPaymentMethodDetail> chargeAccountPaymentMethodDetail = new ArrayList<ChargeAccountPaymentMethodDetail>();
	private List<GuestInfoTO> guestInfoTO = new ArrayList<GuestInfoTO>();

	public ChargeAccountCommonRequest(){
		chargeAccountPaymentMethodDetail.add(new ChargeAccountPaymentMethodDetail());
		guestInfoTO.add(new GuestInfoTO());
		period = new Period();
	}
	
	/**
	* 
	* @param srcAcctCenterId
	* @param chargeAccountPaymentMethodDetail
	* @param guestInfoTO
	* @param description
	* @param active
	* @param txnFacilityId
	* @param period
	* @param defaultFolioRequired
	*/
	public ChargeAccountCommonRequest(String description, Period period, String active, String txnFacilityId, String srcAcctCenterId, String defaultFolioRequired, List<ChargeAccountPaymentMethodDetail> chargeAccountPaymentMethodDetail, List<GuestInfoTO> guestInfoTO) {
	this.description = description;
	this.period = period;
	this.active = active;
	this.txnFacilityId = txnFacilityId;
	this.srcAcctCenterId = srcAcctCenterId;
	this.defaultFolioRequired = defaultFolioRequired;
	this.chargeAccountPaymentMethodDetail = chargeAccountPaymentMethodDetail;
	this.guestInfoTO = guestInfoTO;
	 }
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
	* The period
	*/
	public Period getPeriod() {
		
	return period;
	 }

	/**
	* 
	* @param period
	* The period
	*/
	public void setPeriod(Period period) {
		period.setStartDate("2016-08-29T14:34:07-04:00");
	this.period = period;
	 }

	/**
	* 
	* @return
	* The active
	*/
	public String getActive() {
	return active;
	 }

	/**
	* 
	* @param active
	* The active
	*/
	public void setActive(String active) {
	this.active = active;
	 }
	/**
	* 
	* @return
	* The txnFacilityId
	*/
	public String getTxnFacilityId() {
	return txnFacilityId;
	 }

	/**
	* 
	* @param txnFacilityId
	* The txnFacilityId
	*/
	public void setTxnFacilityId(String txnFacilityId) {
	this.txnFacilityId = txnFacilityId;
	 }

	/**
	* 
	* @return
	* The srcAcctCenterId
	*/
	public String getSrcAcctCenterId() {
	return srcAcctCenterId;
	 }

	/**
	* 
	* @param srcAcctCenterId
	* The srcAcctCenterId
	*/
	public void setSrcAcctCenterId(String srcAcctCenterId) {
	this.srcAcctCenterId = srcAcctCenterId;
	 }

	/**
	* 
	* @return
	* The defaultFolioRequired
	*/
	public String getDefaultFolioRequired() {
	return defaultFolioRequired;
	 }

	/**
	* 
	* @param defaultFolioRequired
	* The defaultFolioRequired
	*/
	public void setDefaultFolioRequired(String defaultFolioRequired) {
	this.defaultFolioRequired = defaultFolioRequired;
	 }
	/**
	* 
	* @return
	* The chargeAccountPaymentMethodDetail
	*/
	public List<ChargeAccountPaymentMethodDetail> getChargeAccountPaymentMethodDetail() {
	return chargeAccountPaymentMethodDetail;
	 }

	/**
	* 
	* @param chargeAccountPaymentMethodDetail
	* The chargeAccountPaymentMethodDetail
	*/
	public void setChargeAccountPaymentMethodDetail(List<ChargeAccountPaymentMethodDetail> chargeAccountPaymentMethodDetail) {
		this.chargeAccountPaymentMethodDetail = chargeAccountPaymentMethodDetail;
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
}
