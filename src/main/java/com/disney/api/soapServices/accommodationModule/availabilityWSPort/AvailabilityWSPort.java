package com.disney.api.soapServices.accommodationModule.availabilityWSPort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AvailabilityWSPort extends BaseSoapService{
	public AvailabilityWSPort(String environment) {		
		setEnvironmentServiceURL("AvailabilityWSPort", environment);
	}
}
