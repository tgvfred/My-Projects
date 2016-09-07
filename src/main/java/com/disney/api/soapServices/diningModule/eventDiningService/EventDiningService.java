package com.disney.api.soapServices.diningModule.eventDiningService;

import com.disney.api.soapServices.core.BaseSoapService;

public class EventDiningService extends BaseSoapService {	
	public EventDiningService(String environment) {		
		setEnvironmentServiceURL("EventDiningServicePort", environment);
	}

}