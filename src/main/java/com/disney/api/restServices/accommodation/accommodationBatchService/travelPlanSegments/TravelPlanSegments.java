package com.disney.api.restServices.accommodation.accommodationBatchService.travelPlanSegments;

import com.disney.api.restServices.core.RestService;

public class TravelPlanSegments {
    private RestService restService;
    private String resource;

    public TravelPlanSegments(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public void getDDRAHAccommodationDetail() {
        // resource += "travelplansegmentsy";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

}
