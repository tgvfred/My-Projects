package com.disney.api.soapServices.showDiningService;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class ShowDiningService extends BaseSoapService {
	public ShowDiningService(){
		
	}
	
	public ShowDiningService(String environment) {			
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Bashful")){
			setEnvironmentServiceURL("ShowDiningServicePort", environment);	
		}else{
			setEnvironmentServiceURL("http://10.51.158.200:8080/Dining/ShowDiningServicePort");
		}
	}

}
