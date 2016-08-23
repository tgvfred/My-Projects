package com.disney.api.restServices.travelPlan.travelPlanService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.retrieveDetails;

public class TravelPlanService {
	private RestService restService;
	private String resource = "/travelPlanService";
	public TravelPlanService(RestService restService){
		this.restService = restService;
	}
	
	public retrieveDetails retrieveDetails() {
		return new retrieveDetails(restService,resource);
	}
}
