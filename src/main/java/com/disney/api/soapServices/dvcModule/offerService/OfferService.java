package com.disney.api.soapServices.dvcModule.offerService;

import com.disney.api.soapServices.core.BaseSoapService;

public class OfferService extends BaseSoapService{

	public OfferService() {
		// TODO Auto-generated constructor stub
	}
	
	public OfferService(String environment) {	
		setEnvironmentServiceURL("OfferService", environment);	
	}
}
