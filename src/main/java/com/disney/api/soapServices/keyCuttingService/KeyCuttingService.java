package com.disney.api.soapServices.keyCuttingService;

import com.disney.api.soapServices.core.BaseSoapService;

public class KeyCuttingService extends BaseSoapService {

	public KeyCuttingService(String environment) {		
		setEnvironmentServiceURL("KeyCuttingServiceIF", environment);
	}
}
