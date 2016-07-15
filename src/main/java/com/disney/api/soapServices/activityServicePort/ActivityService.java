package com.disney.api.soapServices.activityServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class ActivityService extends BaseSoapService {
	public ActivityService(String environment) {	
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Bashful")){			
			setEnvironmentServiceURL("ActivityServicePort", environment);
		}else{
			setEnvironmentServiceURL("http://10.51.158.199:8080/Activity/ActivityServicePort");	
		}    
	}
}
