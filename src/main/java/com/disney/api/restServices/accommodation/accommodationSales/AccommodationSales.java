package com.disney.api.restServices.accommodation.accommodationSales;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.accommodation.accommodationSales.updateComments.updateComments;

public class AccommodationSales {
	private RestService restService;
	private String resource = "/accommodationsales";
	

	public updateComments updateComments(){
		return new updateComments(restService, resource);
	}
}
