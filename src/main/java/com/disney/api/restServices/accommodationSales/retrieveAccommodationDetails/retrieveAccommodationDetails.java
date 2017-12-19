package com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails;

import com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.request.RetrieveAccommodationDetailsRequest;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;

public class retrieveAccommodationDetails {

    private RestService restService;
    private String resource = "/details";

    public retrieveAccommodationDetails(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public RestResponse sendPostRequest(RetrieveAccommodationDetailsRequest request) {
        String json = restService.getJsonFromObject(request);
        return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

}
