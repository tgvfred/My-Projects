package com.disney.api.soapServices.activityServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class ActivityService extends BaseSoapService {
	public ActivityService(String environment) {	
		if(!environment.equalsIgnoreCase("Bashful")){			
			setEnvironmentServiceURL("ActivityServicePort", environment);
		}else{
			setEnvironmentServiceURL("http://10.51.158.199:8080/Activity/ActivityServicePort");	
		}    
	}
}