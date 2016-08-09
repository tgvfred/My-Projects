package com.disney.api.soapServices.partyModule.partyService;

import com.disney.api.soapServices.core.BaseSoapService;

public class PartyServiceIF  extends BaseSoapService{

	public PartyServiceIF(String environment) {		
		setEnvironmentServiceURL("PartyServicePort", environment);
	}
}