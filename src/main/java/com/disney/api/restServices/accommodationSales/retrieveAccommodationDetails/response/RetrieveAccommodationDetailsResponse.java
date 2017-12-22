package com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.response;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.response.objects.AccommodationDetail;

public class RetrieveAccommodationDetailsResponse {

    private Long travelPlanId;
    private List<AccommodationDetail> accommodationDetails = new ArrayList<AccommodationDetail>();

    public Long getTravelPlanId() {
        return travelPlanId;
    }

    public void setTravelPlanId(Long travelPlanId) {
        this.travelPlanId = travelPlanId;
    }

    public List<AccommodationDetail> getAccommodationDetails() {
        return accommodationDetails;
    }

    public void setAccommodationDetails(List<AccommodationDetail> accommodationDetails) {
        this.accommodationDetails = accommodationDetails;
    }

}
