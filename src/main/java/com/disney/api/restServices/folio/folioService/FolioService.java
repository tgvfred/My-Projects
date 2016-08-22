package com.disney.api.restServices.folio.folioService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.folioService.folio.folioService;

public class FolioService {
	private RestService restService;
	public FolioService(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("folio");
	}
	
	public FolioService folioService(){
		return new FolioService(restService);
	}
}
