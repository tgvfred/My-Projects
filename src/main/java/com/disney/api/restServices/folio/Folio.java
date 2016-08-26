package com.disney.api.restServices.folio;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.chargeAccountService.ChargeAccountService;
import com.disney.api.restServices.folio.folioService.FolioService;

public class Folio {
	private RestService restService;
	
	public Folio(RestService restService){
		this.restService = restService;
	}
	
	public ChargeAccountService chargeAccountService(){
		return new ChargeAccountService(restService);
	}
	
	public FolioService folioService(){
		return new FolioService(restService);
	}
}
