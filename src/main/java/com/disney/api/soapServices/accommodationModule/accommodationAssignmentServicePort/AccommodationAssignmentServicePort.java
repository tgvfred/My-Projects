package com.disney.api.soapServices.accommodationModule.accommodationAssignmentServicePort;

import com.disney.api.soapServices.core.BaseSoapService;


public class AccommodationAssignmentServicePort extends BaseSoapService {	
	public AccommodationAssignmentServicePort(String environment) {	
		setEnvironmentServiceURL("AccommodationAssignmentServicePort", environment);	
	}

}