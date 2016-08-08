package com.disney.api.soapServices.availSEModule.builtInventoryService;

import com.disney.api.soapServices.core.BaseSoapService;

public class BuiltInventoryService extends BaseSoapService{
	public BuiltInventoryService(String environment) {
		setEnvironmentServiceURL("BuiltInventoryService", environment);
	}
}