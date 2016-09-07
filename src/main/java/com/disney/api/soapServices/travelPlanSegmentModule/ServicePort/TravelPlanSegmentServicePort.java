package com.disney.api.soapServices.travelPlanSegmentModule.ServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class TravelPlanSegmentServicePort extends BaseSoapService {
	public TravelPlanSegmentServicePort(String environment) {		
		setEnvironmentServiceURL("TravelPlanSegmentServicePort", environment);
	}
}
