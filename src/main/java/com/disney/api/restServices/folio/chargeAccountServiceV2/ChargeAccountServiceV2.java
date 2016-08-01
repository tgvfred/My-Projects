package com.disney.api.restServices.folio.chargeAccountServiceV2;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountServiceV2.chargeAccount.ChargeAccount;

public class ChargeAccountServiceV2 {
	private RestService restService;
	public ChargeAccountServiceV2(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("ChargeAccountServiceV2");
	}
	
	public ChargeAccount chargeAccount(){
		return new ChargeAccount(restService);
	}
	
}
