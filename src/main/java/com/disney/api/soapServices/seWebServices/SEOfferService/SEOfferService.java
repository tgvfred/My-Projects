package com.disney.api.soapServices.seWebServices.SEOfferService;

import com.disney.api.soapServices.core.BaseSoapService;

public class SEOfferService extends BaseSoapService{
	public SEOfferService(String environment) {
		setEnvironmentServiceURL("SEOfferService", environment);
	}
}