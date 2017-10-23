package com.disney.api.restServices.accommodation.accommodationBatchService.travelComponentGroupings;

import com.disney.api.restServices.core.RestService;

public class TravelComponentGroupings {
    private RestService restService;
    private String resource;

    public TravelComponentGroupings(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public void autoCancel(String tcgId) {
        // resource += "/travelcomponentgroupings/"+ tcgID;
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPutRequest(resource, HeaderType.REST, json);
    }

    public void packageComponentIfno(String tcgId) {
        // resource += "/travelcomponentgroupings/"+ tcgID + "/package-component-Info";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

}
