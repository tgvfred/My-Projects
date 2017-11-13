package com.disney.api.restServices;

import com.disney.api.restServices.accommodationSales.AccommodationSales;
import com.disney.api.restServices.core.RestService;

public class AccommodationSalesRest {

    public static AccommodationSales accommodationSales(String environment) {
        RestService restService = new RestService(environment);
        return new AccommodationSales(restService);
    }

}
