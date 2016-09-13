package com.disney.api.restServices.travelPlanSegment;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlan.travelPlanService.TravelPlanService;
import com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.TravelPlanSegmentService;

public class TravelPlanSegment {
	private RestService restService;
	
	public TravelPlanSegment(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("TravelPlanSegmentService");
	}
	
	public TravelPlanSegmentService travelPlanSegmentService(){
		return new TravelPlanSegmentService(restService) ; 
	}
}
