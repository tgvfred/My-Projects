package com.disney.api.restServices;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.Folio;
import com.disney.api.restServices.folio.chargeAccountServiceV2.ChargeAccountServiceV2;

public class Rest{
	
	public static Folio folio(String environment){
		RestService restService = new RestService(environment);
		return new Folio(restService);
	}
	
}
