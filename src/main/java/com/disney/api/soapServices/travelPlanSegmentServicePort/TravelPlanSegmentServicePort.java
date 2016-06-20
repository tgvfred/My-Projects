package com.disney.api.soapServices.travelPlanSegmentServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class TravelPlanSegmentServicePort extends BaseSoapService {
	public TravelPlanSegmentServicePort(String environment) {		
		setEnvironmentServiceURL("TravelPlanSegmentServicePort", environment);
	}
}
