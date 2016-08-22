package com.disney.api.restServices.folio.folioService.folio;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.folioService.folio.retrieveGuests.retrieveGuests;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.retrieveSettlementMethod;

public class folioService {
	private RestService restService;
	private String resource = "/folio";
	public void folio(RestService restService){
		this.restService = restService;
	}
	
	public retrieveGuests retrieveGuests() {
		return new retrieveGuests(restService,resource);
	}
	
	public retrieveSettlementMethod retrieveSettlementMethods(){
		return new retrieveSettlementMethod(restService,resource);
	}
}
