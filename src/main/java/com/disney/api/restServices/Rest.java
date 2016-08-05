package com.disney.api.restServices;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.Folio;
import com.disney.api.restServices.folio.chargeAccountServiceV2.ChargeAccountServiceV2;
import com.disney.api.restServices.sales.Sales;

public class Rest{
	
	public static Folio folio(String environment){
		RestService restService = new RestService(environment);
		return new Folio(restService);
	}
	
	public static Sales sales(String environment){
		RestService restService = new RestService(environment);
		return new Sales(restService);
	}
}
