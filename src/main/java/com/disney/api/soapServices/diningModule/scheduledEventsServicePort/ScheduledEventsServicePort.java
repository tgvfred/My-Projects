package com.disney.api.soapServices.diningModule.scheduledEventsServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class ScheduledEventsServicePort extends BaseSoapService{
	public ScheduledEventsServicePort(String environment) {	
		setEnvironmentServiceURL("ScheduledEventsServicePort", environment);
	}
}
