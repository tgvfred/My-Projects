package com.disney.api.restServices.accommodation;

import com.disney.api.restServices.accommodation.accommodationBatchService.AccommodationBatchService;
import com.disney.api.restServices.accommodation.accommodationSales.AccommodationSales;
import com.disney.api.restServices.core.RestService;

public class Accommodation {
    private RestService restService;

    public Accommodation(RestService restService) {
        this.restService = restService;
    }

    public AccommodationBatchService accoomodationBatchService() {
        return new AccommodationBatchService(restService);
    }

    public AccommodationSales accoomodationSales() {
        return new AccommodationSales(restService);
    }
}
