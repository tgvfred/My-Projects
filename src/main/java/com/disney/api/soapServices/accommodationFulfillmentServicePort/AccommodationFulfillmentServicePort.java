package com.disney.api.soapServices.accommodationFulfillmentServicePort;

import com.disney.api.soapServices.core.BaseSoapService;


public class AccommodationFulfillmentServicePort extends BaseSoapService {
	public AccommodationFulfillmentServicePort() {
		// TODO Auto-generated constructor stub
	}
	
	public AccommodationFulfillmentServicePort(String environment) {	
		setEnvironmentServiceURL("AccommodationFulfillmentServicePort", environment);	
	}

}
