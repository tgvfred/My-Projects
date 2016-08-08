package com.disney.api.soapServices.diningModule.tableServiceDiningServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class TableServiceDiningServicePort  extends BaseSoapService {	
	public TableServiceDiningServicePort(String environment) {	
		setEnvironmentServiceURL("TableServiceDiningServicePort", environment);
	}

}