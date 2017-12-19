package com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.request;

import java.util.ArrayList;
import java.util.List;

public class RetrieveAccommodationDetailsRequest {
    private List<Integer> travelPlanIds = new ArrayList<Integer>();

    public List<Integer> getTravelPlanIds() {
    return travelPlanIds;
    }

    public void setTravelPlanIds(List<Integer> travelPlanIds) {
    this.travelPlanIds = travelPlanIds;
    }
}
