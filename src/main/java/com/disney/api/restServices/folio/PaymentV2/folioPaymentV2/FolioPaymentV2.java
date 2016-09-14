package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.establishSettlementMethod;

public class FolioPaymentV2 {
	private RestService restService;
	private String resource = "/foliopayment";
	
	public FolioPaymentV2 (RestService restService){
		this.restService =restService;
	}
	
	public establishSettlementMethod establishSettlementMethod(){
		return new establishSettlementMethod(restService, resource);
	}
}
