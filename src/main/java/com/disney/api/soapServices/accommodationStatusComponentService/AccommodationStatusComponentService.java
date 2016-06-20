package com.disney.api.soapServices.accommodationStatusComponentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationStatusComponentService extends BaseSoapService {
	public AccommodationStatusComponentService(String environment) {	
		setEnvironmentServiceURL("AccommodationStatusComponentService", environment);	
	}
}
