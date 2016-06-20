package com.disney.api.soapServices.folioBankServicesV2;

import com.disney.api.soapServices.core.BaseSoapService;

public class FolioBankServicesV2 extends BaseSoapService {

	public FolioBankServicesV2(String environment) {		
		setEnvironmentServiceURL("FolioBankServiceV2", environment);
	}
}
