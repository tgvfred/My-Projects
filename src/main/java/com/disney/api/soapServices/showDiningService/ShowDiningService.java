package com.disney.api.soapServices.showDiningService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class ShowDiningService extends BaseSoapService {
	public ShowDiningService(String environment) {			
		setEnvironmentServiceURL("ShowDiningServicePort", environment);	
	}

}
