package com.disney.api.restServices;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.Folio;
import com.disney.api.restServices.folio.chargeAccountService.ChargeAccountService;
import com.disney.api.restServices.sales.Sales;
import com.disney.api.restServices.travelPlan.TravelPlan;

public class Rest{
	
	public static Folio folio(String environment){
		RestService restService = new RestService(environment);
		return new Folio(restService);
	}
	
	public static Sales sales(String environment){
		RestService restService = new RestService(environment);
		return new Sales(restService);
	}
	
	public static TravelPlan travelPlan(String environment){
		RestService restService = new RestService(environment);
		return new TravelPlan(restService);
	}
}
