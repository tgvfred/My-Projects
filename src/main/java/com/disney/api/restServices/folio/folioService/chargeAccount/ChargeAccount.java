package com.disney.api.restServices.folio.folioService.chargeAccount;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.folioService.chargeAccount.retrieveGuests.retrieveGuests;

public class ChargeAccount {
	private RestService restService;
	private String resource = "/chargeaccount";
	public ChargeAccount(RestService restService){
		this.restService = restService;
	}
	public retrieveGuests retrieveGuests(){
		return new retrieveGuests(restService,resource);
	}
}
