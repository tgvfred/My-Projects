package com.disney.api.soapServices.folioModule.paymentService;

import com.disney.api.soapServices.core.BaseSoapService;

public class PaymentService extends BaseSoapService {

	public PaymentService(String environment) {		
		setEnvironmentServiceURL("Payment", environment);
	}
}