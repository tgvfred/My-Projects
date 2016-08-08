package com.disney.api.soapServices.admissionModule.admissionSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AdmissionSalesServicePort extends BaseSoapService {
	public AdmissionSalesServicePort(String environment) {
		setEnvironmentServiceURL("AdmissionSalesServicePort", environment);
	}
}
