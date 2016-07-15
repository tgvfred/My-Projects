package com.disney.api.soapServices.chargeGroup;

import com.disney.api.soapServices.core.BaseSoapService;

public class ChargeGroup extends BaseSoapService {
	public ChargeGroup(String environment) {
		setEnvironmentServiceURL("ChargeGroup", environment);
	}

}
