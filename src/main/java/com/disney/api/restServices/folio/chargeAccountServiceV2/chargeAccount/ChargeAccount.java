package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.Retrieve;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieveGuests.retrieveGuests;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.updatePin.updatePin;

public class ChargeAccount{
	private RestService restService;
	private String resource = "/chargeaccount";
	public ChargeAccount(RestService restService){
		this.restService = restService;
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
