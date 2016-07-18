package com.disney.api.soapServices.seatedEventsComponentService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class SeatedEventsComponentService extends BaseSoapService{
	public SeatedEventsComponentService(String environment) {	
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Bashful")){	
			setEnvironmentServiceURL("SeatedEventsComponentService", environment);
		}else{
			setEnvironmentServiceURL("SeatedEventsComponentService", environment, "http://10.51.158.200:8080/Dining/SeatedEventsComponentServicePort");
		}
	}
}
