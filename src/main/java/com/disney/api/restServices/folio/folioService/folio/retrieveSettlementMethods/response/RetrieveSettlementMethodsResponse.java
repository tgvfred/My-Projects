package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response;

import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response.objects.Card;

public class RetrieveSettlementMethodsResponse {
	private Object endDate;
	private Object deviceTerminalID;
	private Long folioID;
	private String mop;
	private String mopType;
	private Long partyID;
	private Object paymentID;
	private Long settlementMethodID;
	private Card card;
	private Object kttwSettlement;
	private Object directBillInformation;
	private Object rsrInformation;
	private Object folioSettlement;
	private Boolean active;
	private Boolean incidentalUse;
	private Object expressCheckOut;

	/**
	* 
	* @return
	* The endDate
	*/
	public Object getEndDate() {
	return endDate;
	 }

	/**
	* 
	* @param endDate
	* The endDate
	*/
	public void setEndDate(Object endDate) {
	this.endDate = endDate;
	 }

	/**
	* 
	* @return
	* The deviceTerminalID
	*/
	public Object getDeviceTerminalID() {
	return deviceTerminalID;
	 }

	/**
	* 
	* @param deviceTerminalID
	* The deviceTerminalID
	*/
	public void setDeviceTerminalID(Object deviceTerminalID) {
	this.deviceTerminalID = deviceTerminalID;
	 }

	/**
	* 
	* @return
	* The folioID
	*/
	public Long getFolioID() {
	return folioID;
	 }

	/**
	* 
	* @param folioID
	* The folioID
	*/
	public void setFolioID(Long folioID) {
	this.folioID = folioID;
	 }

	/**
	* 
	* @return
	* The mop
	*/
	public String getMop() {
	return mop;
	 }

	/**
	* 
	* @param mop
	* The mop
	*/
	public void setMop(String mop) {
	this.mop = mop;
	 }

	/**
	* 
	* @return
	* The mopType
	*/
	public String getMopType() {
	return mopType;
	 }

	/**
	* 
	* @param mopType
	* The mopType
	*/
	public void setMopType(String mopType) {
	this.mopType = mopType;
	 }

	/**
	* 
	* @return
	* The partyID
	*/
	public Long getPartyID() {
	return partyID;
	 }

	/**
	* 
	* @param partyID
	* The partyID
	*/
	public void setPartyID(Long partyID) {
	this.partyID = partyID;
	 }

	/**
	* 
	* @return
	* The paymentID
	*/
	public Object getPaymentID() {
	return paymentID;
	 }

	/**
	* 
	* @param paymentID
	* The paymentID
	*/
	public void setPaymentID(Object paymentID) {
	this.paymentID = paymentID;
	 }

	/**
	* 
	* @return
	* The settlementMethodID
	*/
	public Long getSettlementMethodID() {
	return settlementMethodID;
	 }

	/**
	* 
	* @param settlementMethodID
	* The settlementMethodID
	*/
	public void setSettlementMethodID(Long settlementMethodID) {
	this.settlementMethodID = settlementMethodID;
	 }

	/**
	* 
	* @return
	* The card
	*/
	public Card getCard() {
	return card;
	 }

	/**
	* 
	* @param card
	* The card
	*/
	public void setCard(Card card) {
	this.card = card;
	 }

	/**
	* 
	* @return
	* The kttwSettlement
	*/
	public Object getKttwSettlement() {
	return kttwSettlement;
	 }

	/**
	* 
	* @param kttwSettlement
	* The kttwSettlement
	*/
	public void setKttwSettlement(Object kttwSettlement) {
	this.kttwSettlement = kttwSettlement;
	 }

	/**
	* 
	* @return
	* The directBillInformation
	*/
	public Object getDirectBillInformation() {
	return directBillInformation;
	 }

	/**
	* 
	* @param directBillInformation
	* The directBillInformation
	*/
	public void setDirectBillInformation(Object directBillInformation) {
	this.directBillInformation = directBillInformation;
	 }

	/**
	* 
	* @return
	* The rsrInformation
	*/
	public Object getRsrInformation() {
	return rsrInformation;
	 }

	/**
	* 
	* @param rsrInformation
	* The rsrInformation
	*/
	public void setRsrInformation(Object rsrInformation) {
	this.rsrInformation = rsrInformation;
	 }

	/**
	* 
	* @return
	* The folioSettlement
	*/
	public Object getFolioSettlement() {
	return folioSettlement;
	 }

	/**
	* 
	* @param folioSettlement
	* The folioSettlement
	*/
	public void setFolioSettlement(Object folioSettlement) {
	this.folioSettlement = folioSettlement;
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
	* The incidentalUse
	*/
	public Boolean getIncidentalUse() {
	return incidentalUse;
	 }

	/**
	* 
	* @param incidentalUse
	* The incidentalUse
	*/
	public void setIncidentalUse(Boolean incidentalUse) {
	this.incidentalUse = incidentalUse;
	 }

	/**
	* 
	* @return
	* The expressCheckOut
	*/
	public Object getExpressCheckOut() {
	return expressCheckOut;
	 }

	/**
	* 
	* @param expressCheckOut
	* The expressCheckOut
	*/
	public void setExpressCheckOut(Object expressCheckOut) {
	this.expressCheckOut = expressCheckOut;
	 }
}
