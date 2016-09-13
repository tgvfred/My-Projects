package com.disney.api.restServices.core;

import org.junit.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.request.RetrieveRequest;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.RetreiveResponse;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.objects.GuestInfoTO;
import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieve.response.objects.RootChargeAccountResponse;
import com.disney.utils.TestReporter;

public class Sandbox {

	@Test
	public void test() {
		TestReporter.setDebugLevel(1);
		
		Rest.folio("Bashful").folioService().chargeAccount().retrieveGuests().sendGetRequest("ODS", "2504588196", "1");
	}
}
