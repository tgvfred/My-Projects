package com.disney.api.restServices.travelPlan;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlan.travelPlanService.TravelPlanService;

public class TravelPlan {
	private RestService restService;
	
	public TravelPlan(RestService restService){
		this.restService = restService;
	}
	
	public TravelPlanService TravelPlanService(){
		return null;
		/**return new TravelPlanService(restService) ; **/
	}
}
