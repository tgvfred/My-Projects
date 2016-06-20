package com.disney.api.soapServices.scheduledEventsComponentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class ScheduledEventsComponentService extends BaseSoapService{
	public ScheduledEventsComponentService(String environment) {		
		setEnvironmentServiceURL("ScheduledEventsComponentsService", environment);
	}
}
