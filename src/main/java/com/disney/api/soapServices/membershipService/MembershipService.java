package com.disney.api.soapServices.membershipService;
import com.disney.api.soapServices.core.BaseSoapService;

public class MembershipService extends BaseSoapService{
	
	public MembershipService() {
		// TODO Auto-generated constructor stub
	}
	
	public MembershipService(String environment) {	
		setEnvironmentServiceURL("MembershipService", environment);	
	}

}
