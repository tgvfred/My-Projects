package com.disney.api.soapServices.accommodationSalesComponentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationSalesComponentService extends BaseSoapService {
	public AccommodationSalesComponentService(String environment) {	
		setEnvironmentServiceURL("AccommodationSalesComponentServicePort", environment);	
	}
}
