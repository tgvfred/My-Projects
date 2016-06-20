package com.disney.api.soapServices.cardAuthorizationServiceV2;

import com.disney.api.soapServices.core.BaseSoapService;

public class CardAuthorizationServiceV2 extends BaseSoapService {
	public CardAuthorizationServiceV2(String environment) {
		setEnvironmentServiceURL("CardAuthorizationServiceV2", environment);
	}
}