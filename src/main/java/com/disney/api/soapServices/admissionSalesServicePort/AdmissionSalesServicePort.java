package com.disney.api.soapServices.admissionSalesServicePort;

import com.disney.api.soapServices.core.BaseSoapService;

public class AdmissionSalesServicePort extends BaseSoapService {
	public AdmissionSalesServicePort(String environment) {
		setEnvironmentServiceURL("AdmissionSalesServicePort", environment);
	}
}
