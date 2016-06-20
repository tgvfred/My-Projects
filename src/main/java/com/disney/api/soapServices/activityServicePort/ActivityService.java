package com.disney.api.soapServices.activityServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class ActivityService extends BaseSoapService {
	public ActivityService(String environment) {	
		setEnvironmentServiceURL("ActivityServicePort", environment);	
	}
}
