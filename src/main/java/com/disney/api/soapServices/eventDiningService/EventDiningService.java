package com.disney.api.soapServices.eventDiningService;

import com.disney.api.soapServices.core.BaseSoapService;

public class EventDiningService extends BaseSoapService {
	public EventDiningService(){
		
	}
	
	public EventDiningService(String environment) {	
		setEnvironmentServiceURL("EventDiningServicePort", environment);	
	}

}