package com.disney.api.restServices.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.disney.api.restServices.Rest;
import com.disney.utils.TestReporter;

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
		
		RestResponse response= Rest.folio("Development").chargeAccountServiceV2().chargeAccount().retrieve().sendPutRequest(json);
		System.out.println( response.getStatusCode());
	}
	
	//@Test
	public void test2(){
		TestReporter.logDebug("Is executed from Jenkins, updating build name");
		//String buildId = System.getenv("BUILD_ID");
		String buildUrl = "http://jenkins-pc.wdw-ilab.wdw.disney.com:9090/job/CoMo_API_ShowDiningSerivce/74/" + "configSubmit";
		String buildEnv = "Latest_CM";
		String buildId = "74";
		
		RestService rest = new RestService();
		String json = "{\"displayName\": \"#" + buildId + " - " + buildEnv + "\", \"description\": \"\", \"core:apply\": \"\"}";
		Header[] headers =  {new BasicHeader("Content-type", "application/x-www-form-urlencoded")};
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Submit", "Save"));
		params.add(new BasicNameValuePair("displayName", "#" + buildId + " - " + buildEnv));
		params.add(new BasicNameValuePair("json", json));
		params.add(new BasicNameValuePair("description", ""));
		params.add(new BasicNameValuePair("core:apply", ""));
		TestReporter.log(rest.sendPostRequest(buildUrl, headers, params).getResponse());
	}
}
