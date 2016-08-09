package com.disney.api.soapServices.keycuttingModule.keyCuttingService;

import com.disney.api.soapServices.core.BaseSoapService;

public class KeyCuttingService extends BaseSoapService {

	public KeyCuttingService(String environment) {		
		setEnvironmentServiceURL("KeyCuttingServiceIF", environment);
	}
}
