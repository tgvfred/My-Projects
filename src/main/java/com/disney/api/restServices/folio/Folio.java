package com.disney.api.restServices.folio;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountServiceV2.ChargeAccountServiceV2;

public class Folio {
	private RestService restService;
	
	public Folio(RestService restService){
		this.restService = restService;
	}
	
	public ChargeAccountServiceV2 chargeAccountServiceV2(){
		return new ChargeAccountServiceV2(restService);
	}
}
