package com.disney.api.soapServices.seatedEventsComponentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class SeatedEventsComponentService extends BaseSoapService{
	public SeatedEventsComponentService(String environment) {	
		setEnvironmentServiceURL("SeatedEventsComponentService", environment);
	}
}
