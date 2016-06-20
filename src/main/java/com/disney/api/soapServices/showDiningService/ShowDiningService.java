package com.disney.api.soapServices.showDiningService;

import com.disney.api.soapServices.core.BaseSoapService;

public class ShowDiningService extends BaseSoapService {
	public ShowDiningService(){
		
	}
	
	public ShowDiningService(String environment) {	
		setEnvironmentServiceURL("ShowDiningServicePort", environment);	
	}

}
