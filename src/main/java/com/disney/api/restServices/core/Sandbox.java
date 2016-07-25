package com.disney.api.restServices.core;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class Sandbox {

	@Test
	public void test() throws ClientProtocolException, IOException{
		RestService rest = new RestService();
		System.out.println(rest.sendGetRequest("http://jenkins-pc.wdw-ilab.wdw.disney.com:9090/view/CompositeModernization/api/json?pretty=true"));
	}
}
