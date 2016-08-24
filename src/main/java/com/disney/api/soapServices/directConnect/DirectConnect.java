package com.disney.api.soapServices.directConnect;

import com.disney.api.soapServices.core.BaseSoapService;

public class DirectConnect extends BaseSoapService{
	public DirectConnect(String environment){
		setEnvironmentServiceURL("DirectConnect", environment);
	}

}
