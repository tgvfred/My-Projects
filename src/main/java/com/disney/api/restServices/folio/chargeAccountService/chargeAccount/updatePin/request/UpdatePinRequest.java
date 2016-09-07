package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.request;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.request.objects.ChargeAccountIdentifier;
public class UpdatePinRequest {
	private List<ChargeAccountIdentifier> chargeAccountIdentifiers = new ArrayList<ChargeAccountIdentifier>();
	private String pinNumber;

	/**
	* 
	* @return
	* The chargeAccountIdentifiers
	*/
	public List<ChargeAccountIdentifier> getChargeAccountIdentifiers() {
	return chargeAccountIdentifiers;
	 }

	/**
	* 
	* @param chargeAccountIdentifiers
	* The chargeAccountIdentifiers
	*/
	public void setChargeAccountIdentifiers(List<ChargeAccountIdentifier> chargeAccountIdentifiers) {
	this.chargeAccountIdentifiers = chargeAccountIdentifiers;
	 }
	
	public void addChargeAccountId(String id){
		ChargeAccountIdentifier ca = new ChargeAccountIdentifier();
		ca.setChargeAccountId(id);
		this.chargeAccountIdentifiers.add(ca);
	 }

	/**
	* 
	* @return
	* The pinNumber
	*/
	public String getPinNumber() {
	return pinNumber;
	 }

	/**
	* 
	* @param pinNumber
	* The pinNumber
	*/
	public void setPinNumber(String pinNumber) {
	this.pinNumber = pinNumber;
	 }

}
