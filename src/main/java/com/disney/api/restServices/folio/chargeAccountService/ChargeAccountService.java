package com.disney.api.restServices.folio.chargeAccountService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.ChargeAccount;

public class ChargeAccountService {
	private RestService restService;
	public ChargeAccountService(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("ChargeAccountService");
	}
	
	public ChargeAccount chargeAccount(){
		return new ChargeAccount(restService);
	}
	
}
