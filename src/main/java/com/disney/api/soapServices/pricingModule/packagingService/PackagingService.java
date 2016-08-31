package com.disney.api.soapServices.pricingModule.packagingService;

import com.disney.api.soapServices.core.BaseSoapService;

public class PackagingService  extends BaseSoapService {	
	public PackagingService(String environment) {		
		setEnvironmentServiceURL("PackagingService", environment);
	}
}
