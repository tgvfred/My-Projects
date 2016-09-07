package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request;

import java.util.ArrayList;
import java.util.List;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.request.objects.*;


public class CreateRequest {
	private List<ChargeAccountRequest> chargeAccountRequests = new ArrayList<ChargeAccountRequest>();

	public void addChargeAccountRequest(){

        chargeAccountRequests.add(new ChargeAccountRequest());
    }
	
	public CreateRequest(){
		chargeAccountRequests.add(new ChargeAccountRequest());
	}
	/**
	* 
	* @return
	* The chargeAccountRequests
	*/
	public List<ChargeAccountRequest> getChargeAccountRequests() {
	return chargeAccountRequests;
	 }

	/**
	* 
	* @param chargeAccountRequests
	* The chargeAccountRequests
	*/
	public void setChargeAccountRequests(List<ChargeAccountRequest> chargeAccountRequests) {
		
	this.chargeAccountRequests = chargeAccountRequests;
	 }
}
