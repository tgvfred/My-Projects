package com.disney.api.soapServices.roomInventoryModule.accommodationStatusComponentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationStatusComponentService extends BaseSoapService {
	public AccommodationStatusComponentService(String environment) {	
		setEnvironmentServiceURL("AccommodationStatusComponentService", environment);	
	}
}