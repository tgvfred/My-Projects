package com.disney.api.soapServices.eventDiningService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class EventDiningService extends BaseSoapService {
	public EventDiningService(){
		
	}
	
	public EventDiningService(String environment) {		
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Bashful")){
			setEnvironmentServiceURL("EventDiningServicePort", environment);
		}else{
			setEnvironmentServiceURL("EventDiningServicePort", environment, "http://10.51.158.200:8080/Dining/EventDiningServicePort");
		}
	}

}