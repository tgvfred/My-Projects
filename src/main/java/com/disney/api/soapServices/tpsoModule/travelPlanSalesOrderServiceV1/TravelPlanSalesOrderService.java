package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1;

import com.disney.api.soapServices.core.BaseSoapService;

public class TravelPlanSalesOrderService extends BaseSoapService {
	public TravelPlanSalesOrderService(String environment) {		
		setEnvironmentServiceURL("TravelPlanSalesOrderServiceV1", environment);
	}
}
