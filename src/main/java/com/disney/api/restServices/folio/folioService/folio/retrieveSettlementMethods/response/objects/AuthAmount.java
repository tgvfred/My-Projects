package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.response.objects;

public class AuthAmount {
	private Double amount;
	private String currency;

	/**
	* 
	* @return
	* The amount
	*/
	public Double getAmount() {
	return amount;
	 }

	/**
	* 
	* @param amount
	* The amount
	*/
	public void setAmount(Double amount) {
	this.amount = amount;
	 }

	/**
	* 
	* @return
	* The currency
	*/
	public String getCurrency() {
	return currency;
	 }

	/**
	* 
	* @param currency
	* The currency
	*/
	public void setCurrency(String currency) {
	this.currency = currency;
	 }
}
