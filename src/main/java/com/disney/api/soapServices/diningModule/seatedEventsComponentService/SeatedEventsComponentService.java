package com.disney.api.soapServices.diningModule.seatedEventsComponentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class SeatedEventsComponentService extends BaseSoapService{
	public SeatedEventsComponentService(String environment) {	
		setEnvironmentServiceURL("SeatedEventsComponentService", environment);
	}
}
