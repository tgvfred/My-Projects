package com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountServiceV2.ChargeAccountServiceV2;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.retrieve.Retrieve;

public class ChargeAccount{
	private RestService restService;
	private String resource = "/chargeaccount";
	public ChargeAccount(RestService restService){
		this.restService = restService;
	}
	
	public Retrieve retrieve(){
		return new Retrieve(restService,resource);
	}
}
