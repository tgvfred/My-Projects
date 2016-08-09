package com.disney.api.soapServices.bussvcsModule.guestServiceV2;

import com.disney.api.soapServices.core.BaseSoapService;

public class GuestServiceV2 extends BaseSoapService {

	public GuestServiceV2(String environment) {		
		setEnvironmentServiceURL("GuestServiceV2", environment);
	}
}
