package com.disney.api.restServices.travelPlan.travelPlanService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.retrieveDetails;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.modifyGuests;

public class TravelPlanService {
	private RestService restService;
	private String resource = "/travelplan";
	public TravelPlanService(RestService restService){
		this.restService = restService;
	}
	
	public retrieveDetails retrieveDetails() {
		return new retrieveDetails(restService,resource);
	}
	
	public modifyGuests modifyGuests(){
		return new modifyGuests(restService,resource);
	}
}
