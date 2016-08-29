package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

import java.util.ArrayList;
import java.util.List;

public class ChargeAccountCommonRequest {
	private String description = "From Booking";
	private Period period;
	private String active ="true";
	private List<ChargeAccountPaymentMethodDetail> chargeAccountPaymentMethodDetail = new ArrayList<ChargeAccountPaymentMethodDetail>();
	private List<GuestInfoTO> guestInfoTO = new ArrayList<GuestInfoTO>();

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
