package com.disney.api.soapServices.tableServiceDiningServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class TableServiceDiningServicePort  extends BaseSoapService {	
	public TableServiceDiningServicePort(String environment) {	
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Bashful")){
			setEnvironmentServiceURL("TableServiceDiningServicePort", environment);	
		}else{
			setEnvironmentServiceURL("TableServiceDiningServicePort", environment, "http://10.51.158.200:8080/Dining/TableServiceDiningServicePort");
		}
	}

}