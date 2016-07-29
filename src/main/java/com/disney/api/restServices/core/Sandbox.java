package com.disney.api.restServices.core;

import org.apache.http.HttpResponse;
import org.junit.Test;

import com.disney.api.restServices.Rest;

public class Sandbox {

	@Test
	public void test() {

		String json = "{"+
					    "\"chargeAccountIdentifiers\": [{"+
					        "\"chargeAccountId\": \"2364\""+
					   " },"+
					   " {"+
					        "\"chargeAccountId\": \"2365\""+
					   " }]"+
					"}";
		
		HttpResponse httpResponse = Rest.folio("Development").chargeAccountServiceV2().chargeAccount().retrieve().sendPutRequest(json);
		System.out.println( httpResponse.getStatusLine());
	}
}
