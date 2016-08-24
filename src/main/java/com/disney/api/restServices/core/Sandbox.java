package com.disney.api.restServices.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.disney.api.restServices.Rest;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class Sandbox {
	
	//@Test
	public void test() {
		TestReporter.setDebugLevel(2);
		String json = "{\"chargeAccountIdentifiers\": [{\"chargeAccountId\": \"2364\"},"+
					   " {\"chargeAccountId\": \"2365\"}]}";
		
		/**RestResponse response= Rest.folio("Development").chargeAccountServiceV2().chargeAccount().retrieve().sendPutRequest(json);
		TestReporter.assertTrue(response.getStatusCode() == 200, "Validate status code returned ["+response.getStatusCode()+"] was [200]");
	**/}
	
	@Test
	public void test3() {

		TestReporter.setDebugLevel(2);
		Reservation res = new GenerateReservation().bookResortReservation().CONTEMPORARY("Sleepy");
		res.setGuestInfo(new Guest());
		res.quickBook();
		
		res.addRoundTripDME();
		System.out.println(res.getTravelPlanSegmentId());
		
	}
	
	//@Test
	public void test2(){
		TestReporter.setDebugLevel(2);
		TestReporter.logDebug("Is executed from Jenkins, updating build name");
		//String buildId = System.getenv("BUILD_ID");
		String buildUrl = "http://jenkins-pc.wdw-ilab.wdw.disney.com:9090/job/CoMo_API_ShowDiningSerivce/74/" + "configSubmit";
		String buildEnv = "Latest_CM";
		String buildId = "74";
		
		URI url = null;
		try {
			url = new URI(buildUrl);
		} catch ( URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		RestService rest = new RestService();
		String json = "{\"displayName\": \"#" + buildId + " - " + buildEnv + "\", \"description\": \"\", \"core:apply\": \"\"}";
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Submit", "Save"));
		params.add(new BasicNameValuePair("displayName", "#" + buildId + " - " + buildEnv));
		params.add(new BasicNameValuePair("json", json));
		params.add(new BasicNameValuePair("description", ""));
		params.add(new BasicNameValuePair("core:apply", ""));
		TestReporter.logInfo(rest.sendPostRequest(url, HeaderType.JENKINS, params).getResponse());
	}
}
