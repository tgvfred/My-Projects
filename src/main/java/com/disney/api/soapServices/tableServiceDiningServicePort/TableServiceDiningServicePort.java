package com.disney.api.soapServices.tableServiceDiningServicePort;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;

public class TableServiceDiningServicePort  extends BaseSoapService {	
	public TableServiceDiningServicePort(String environment) {	
		setEnvironmentServiceURL("TableServiceDiningServicePort", environment);
	}

}