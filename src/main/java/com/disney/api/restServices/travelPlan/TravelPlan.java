package com.disney.api.restServices.travelPlan;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlan.travelPlanService.TravelPlanService;

public class TravelPlan {
	private RestService restService;
	
	public TravelPlan(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("TravelPlan");
	}
	
	public TravelPlanService travelPlanService(){
		return new TravelPlanService(restService) ; 
	}
}
