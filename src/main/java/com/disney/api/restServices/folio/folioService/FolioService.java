package com.disney.api.restServices.folio.folioService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.folioService.chargeAccount.ChargeAccount;
import com.disney.api.restServices.folio.folioService.folio.Folio;

public class FolioService {
	private RestService restService;
	public FolioService(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("FolioService");
	}
	
	public Folio folio(){
		return new Folio(restService);
	}
	
	public ChargeAccount chargeAccount(){
		return new ChargeAccount(restService);
	}

}
