package com.disney.api.soapServices.accommodationSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;


public class AccommodationSalesServicePort extends BaseSoapService {
	public AccommodationSalesServicePort(String environment) {	
		setEnvironmentServiceURL("AccommodationSalesServicePort", environment);	
	}

}