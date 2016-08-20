package com.disney.utils.dataFactory;


import org.testng.annotations.Test;

import com.disney.api.soapServices.dvcModule.membershipService.operations.SearchMemberships;

public class sandbox {
	
	@Test
	public void test(){
		SearchMemberships search = new SearchMemberships("Sleepy", "Main");
		search.sendRequest();
		System.out.println(search.getRequest());
		System.out.println(search.getResponse());
	}
}
