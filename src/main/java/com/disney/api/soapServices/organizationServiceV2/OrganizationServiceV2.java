package com.disney.api.soapServices.organizationServiceV2;

import com.disney.api.soapServices.core.BaseSoapService;

public class OrganizationServiceV2 extends BaseSoapService {

	public OrganizationServiceV2(String environment) {		
		setEnvironmentServiceURL("Organizationv2", environment);
	}
}
