package com.disney.api.soapServices.accommodationServiceV1;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationServiceV1 extends BaseSoapService {
		
	public AccommodationServiceV1(String environment) {	
		setEnvironmentServiceURL("AccommodationServiceV1", environment);	
	}

}
