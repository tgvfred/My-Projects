package com.disney.api.soapServices.diningModule.showDiningService;

import com.disney.api.soapServices.core.BaseSoapService;

public class ShowDiningService extends BaseSoapService {
	public ShowDiningService(String environment) {			
		setEnvironmentServiceURL("ShowDiningServicePort", environment);	
	}

}
