package com.disney.api.soapServices.roomInventoryModule.accommodationAssignmentServicePort;

import com.disney.api.soapServices.core.BaseSoapService;


public class AccommodationAssignmentServicePort extends BaseSoapService {
	public AccommodationAssignmentServicePort() {
		// TODO Auto-generated constructor stub
	}
	
	public AccommodationAssignmentServicePort(String environment) {	
		setEnvironmentServiceURL("AccommodationAssignmentServicePort", environment);	
	}

}
