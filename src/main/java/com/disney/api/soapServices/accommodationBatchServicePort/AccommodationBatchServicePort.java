package com.disney.api.soapServices.accommodationBatchServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AccommodationBatchServicePort extends BaseSoapService{
	public AccommodationBatchServicePort(String environment) {		
		setEnvironmentServiceURL("AccommodationBatchWSPort", environment);
	}
}
