package com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.travelPlanSegment.travelPlanSegmentService.updatePreArrivalInformation.updatePreArrivalInformation;

public class TravelPlanSegmentService {
	private RestService restService;
	private String resource = "/travelplansegment";
	public TravelPlanSegmentService(RestService restService){
		this.restService = restService;
	}
	
	public updatePreArrivalInformation updatePreArrivalInformation() {
		return new updatePreArrivalInformation(restService,resource);
	}
}
