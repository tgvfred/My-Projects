package com.disney.api.restServices.folio.PaymentV2;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.FolioPaymentV2;

public class PaymentV2 {
	private RestService restService;
	
	public PaymentV2(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("FolioPaymentV2");
	}
	
	public FolioPaymentV2 folioPaymentV2(){
		return new FolioPaymentV2(restService);
	}
}
