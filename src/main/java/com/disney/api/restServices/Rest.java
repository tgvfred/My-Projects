package com.disney.api.restServices;

import com.disney.api.restServices.accommodation.Accommodation;
import com.disney.api.restServices.core.RestService;

public class Rest{
	
	
	public static Accommodation accommodation(String environment){
		RestService restService = new RestService(environment);
		return new Accommodation(restService);
	}
	
}
