package com.disney.api.restServices.folio.PaymentV2;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.establishSettlementMethod;;


public class PaymentV2 {
	private RestService restService;
	public PaymentV2(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("FolioPaymentV2");
	}
	
	public PaymentV2 folio(){
		return new PaymentV2(restService);
	}
}
