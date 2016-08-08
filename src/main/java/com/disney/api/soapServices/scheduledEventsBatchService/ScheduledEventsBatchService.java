package com.disney.api.soapServices.scheduledEventsBatchService;

import com.disney.api.soapServices.core.BaseSoapService;

public class ScheduledEventsBatchService extends BaseSoapService {

	public ScheduledEventsBatchService(String environment) {		
		setEnvironmentServiceURL("ScheduledEventsBatchService", environment);
	}
}
