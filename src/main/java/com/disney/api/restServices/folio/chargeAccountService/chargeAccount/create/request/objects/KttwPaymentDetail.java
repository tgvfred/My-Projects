package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects;

public class KttwPaymentDetail {
	private String campusId = "0";
	private String kttwNumber = "991946168311680202" ;
	private String reservationTxnGuestId = "238431649";

	/**
	* No args constructor for use in serialization
	* 
	*/
	public KttwPaymentDetail() {
	 }

	/**
	* 
	* @param kttwNumber
	* @param reservationTxnGuestId
	* @param campusId
	*/
	public KttwPaymentDetail(String campusId, String kttwNumber, String reservationTxnGuestId) {
	this.campusId = campusId;
	this.kttwNumber = kttwNumber;
	this.reservationTxnGuestId = reservationTxnGuestId;
	 }

	/**
	* 
	* @return
	* The campusId
	*/
	public String getCampusId() {
	return campusId;
	 }

	/**
	* 
	* @param campusId
	* The campusId
	*/
	public void setCampusId(String campusId) {
	this.campusId = campusId;
	 }

	/**
	* 
	* @return
	* The kttwNumber
	*/
	public String getKttwNumber() {
	return kttwNumber;
	 }

	/**
	* 
	* @param kttwNumber
	* The kttwNumber
	*/
	public void setKttwNumber(String kttwNumber) {
	this.kttwNumber = kttwNumber;
	 }

	/**
	* 
	* @return
	* The reservationTxnGuestId
	*/
	public String getReservationTxnGuestId() {
	return reservationTxnGuestId;
	 }

	/**
	* 
	* @param reservationTxnGuestId
	* The reservationTxnGuestId
	*/
	public void setReservationTxnGuestId(String reservationTxnGuestId) {
	this.reservationTxnGuestId = reservationTxnGuestId;
	 }
}
