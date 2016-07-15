package com.disney.api.soapServices.scheduledEventsServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class ScheduledEventsServicePort extends BaseSoapService{
	public ScheduledEventsServicePort(String environment) {	
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Bashful")){	
			setEnvironmentServiceURL("ScheduledEventsServicePort", environment);
		}else{
			setEnvironmentServiceURL("http://10.51.158.200:8080/Dining/ScheduledEventsServicePort");
		}
	}
}
