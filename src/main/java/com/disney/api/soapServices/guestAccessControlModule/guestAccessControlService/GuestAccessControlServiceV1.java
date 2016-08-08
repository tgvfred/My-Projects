package com.disney.api.soapServices.guestAccessControlModule.guestAccessControlService;

import com.disney.api.soapServices.core.BaseSoapService;

public class GuestAccessControlServiceV1 extends BaseSoapService {

	public GuestAccessControlServiceV1(String environment) {		
		setEnvironmentServiceURL("GuestAccessControlServiceV1Port", environment);
	}
}
