package com.disney.api.soapServices.profileModule.profileServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class ProfileServicePort extends BaseSoapService {

	public ProfileServicePort(String environment) {		
		setEnvironmentServiceURL("ProfileServicePort", environment);
	}
}
