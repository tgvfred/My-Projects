package com.disney.api.restServices.folio.chargeAccountService.chargeAccount;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.create.create;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.Retrieve;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.retrieveGuests;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.updatePin.updatePin;

public class ChargeAccount{
	private RestService restService;
	private String resource = "/chargeaccountV2";
	public ChargeAccount(RestService restService){
		this.restService = restService;
	}
	
	public create create() {
		return new create(restService,resource);
	}
	
	public Retrieve retrieve(){
		return new Retrieve(restService,resource);
	}
	
	public retrieveGuests retrieveGuests(){
		return new retrieveGuests(restService,resource);
	}
	
	public updatePin updatePin(){
		return new updatePin(restService,resource);
	}
}
