package com.disney.api.soapServices.diningModule.scheduledEventsBatchService;

import com.disney.api.soapServices.core.BaseSoapService;

public class ScheduledEventsBatchService extends BaseSoapService {

	public ScheduledEventsBatchService(String environment) {		
		setEnvironmentServiceURL("ScheduledEventsBatchService", environment);
	}
}
