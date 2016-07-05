package com.disney.api.soapServices.partyV3;

import com.disney.api.soapServices.core.BaseSoapService;

public class PartyV3  extends BaseSoapService{

	public PartyV3(String environment) {		
		setEnvironmentServiceURL("PartyV3", environment);
	}
}