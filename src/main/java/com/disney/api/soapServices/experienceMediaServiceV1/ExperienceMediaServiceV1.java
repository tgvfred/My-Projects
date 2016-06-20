package com.disney.api.soapServices.experienceMediaServiceV1;

import com.disney.api.soapServices.core.BaseSoapService;

public class ExperienceMediaServiceV1 extends BaseSoapService{
	public ExperienceMediaServiceV1() {
		// TODO Auto-generated constructor stub
	}
	
	public ExperienceMediaServiceV1(String environment) {	
		setEnvironmentServiceURL("ExperienceMediaServiceV1", environment);	
	}
}
