package com.disney.api.soapServices.pricingModule.pricingWebService;

import com.disney.api.soapServices.core.BaseSoapService;

public class PricingWebService extends BaseSoapService {	
	public PricingWebService(String environment) {		
		setEnvironmentServiceURL("PricingWebService", environment);
	}

}