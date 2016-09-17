package com.disney.api.restServices.accommodation;

import com.disney.api.restServices.accommodation.accommodationSales.AccommodationSales;
import com.disney.api.restServices.core.RestService;

public class Accommodation {
	private RestService restService;
		
	public Accommodation(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("AccommodationSales");
	}
	public AccommodationSales accoomodationSales(){
		return new AccommodationSales();
	}
}