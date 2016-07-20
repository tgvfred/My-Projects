package com.disney.api.soapServices.eventDiningService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class EventDiningService extends BaseSoapService {	
	public EventDiningService(String environment) {		
		setEnvironmentServiceURL("EventDiningServicePort", environment);
	}

}