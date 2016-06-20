package com.disney.api.soapServices.guestAccessControlService;

import com.disney.api.soapServices.core.BaseSoapService;

public class GuestAccessControlServiceV1 extends BaseSoapService {

	public GuestAccessControlServiceV1(String environment) {		
		setEnvironmentServiceURL("GuestAccessControlServiceV1Port", environment);
	}
}
