package com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.response;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.response.objects.AccommodationDetail;

public class RetrieveAccommodationDetailsResponse {

    private Integer travelPlanId;
    private List<AccommodationDetail> accommodationDetails = new ArrayList<AccommodationDetail>();

    public Integer getTravelPlanId() {
        return travelPlanId;
    }

    public void setTravelPlanId(Integer travelPlanId) {
        this.travelPlanId = travelPlanId;
    }

    public List<AccommodationDetail> getAccommodationDetails() {
        return accommodationDetails;
    }

    public void setAccommodationDetails(List<AccommodationDetail> accommodationDetails) {
        this.accommodationDetails = accommodationDetails;
    }

}
