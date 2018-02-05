package com.disney.api.restServices.accommodationSales;

import com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.retrieveAccommodationDetails;
import com.disney.api.restServices.accommodationSales.updateComments.updateComments;
import com.disney.api.restServices.core.RestService;

public class AccommodationSales {
    private RestService restService;
    private String resource = "/v1";

    public AccommodationSales(RestService restService) {
        this.restService = restService;
        this.restService.setMainResource("REST_AccommodationSalesService");
    }

    public updateComments updateComments() {
        return new updateComments(restService, resource);
    }

    public retrieveAccommodationDetails retrieveAccommodationDetails() {

        return new retrieveAccommodationDetails(restService, resource);
    }
}
