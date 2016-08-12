package com.disney.api.soapServices.dvcModule.DVCShadowReservationService;
import com.disney.api.soapServices.core.BaseSoapService;

public class DVCShadowReservationService extends BaseSoapService{
	public DVCShadowReservationService() {
		// TODO Auto-generated constructor stub
	}
	
	public DVCShadowReservationService(String environment) {	
		setEnvironmentServiceURL("DVCShadowReservationService", environment);	
	}
}
