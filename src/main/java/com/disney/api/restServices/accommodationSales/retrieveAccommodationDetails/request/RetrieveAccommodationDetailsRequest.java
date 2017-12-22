package com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.request;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAccommodationDetailsRequest {
    private List<Long> travelPlanIds = new ArrayList<Long>();

    public List<Long> getTravelPlanIds() {
        return travelPlanIds;
    }

    public void setTravelPlanIds(List<Long> travelPlanIds) {
        this.travelPlanIds = travelPlanIds;
    }
}
