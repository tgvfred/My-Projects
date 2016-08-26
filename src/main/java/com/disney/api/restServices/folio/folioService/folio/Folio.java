package com.disney.api.restServices.folio.folioService.folio;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.folioService.folio.retrieveGuests.retrieveGuests;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.retrieveSettlementMethods;

public class Folio {
	private RestService restService;
	private String resource = "/Folio";
	public Folio(RestService restService){
		this.restService = restService;
	}
	
	public retrieveGuests retrieveGuests() {
		return new retrieveGuests(restService, resource);
	}
	
	public retrieveSettlementMethods retrieveSettlementMethods(){
		return new retrieveSettlementMethods(restService, resource);
	}
}
