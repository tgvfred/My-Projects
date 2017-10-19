package com.disney.api.restServices.accommodation.accommodationBatchService;

import com.disney.api.restServices.accommodation.accommodationBatchService.job.Job;
import com.disney.api.restServices.accommodation.accommodationBatchService.processData.ProcessData;
import com.disney.api.restServices.accommodation.accommodationBatchService.processDataViews.ProcessDataViews;
import com.disney.api.restServices.accommodation.accommodationBatchService.processMain.ProcessMain;
import com.disney.api.restServices.accommodation.accommodationBatchService.travelComponentGroupings.TravelComponentGroupings;
import com.disney.api.restServices.accommodation.accommodationBatchService.travelPlanSegments.TravelPlanSegments;
import com.disney.api.restServices.core.RestService;

public class AccommodationBatchService {
    private RestService restService;
    private String resource = "/v1";

    public AccommodationBatchService(RestService restService) {
        this.restService.setMainResource("REST_AccommodationBatchService");
        this.restService = restService;
    }

    public Job job() {
        return new Job(restService, resource);
    }

    public ProcessData processData() {
        return new ProcessData(restService, resource);
    }

    public ProcessDataViews processDataViews() {
        return new ProcessDataViews(restService, resource);
    }

    public ProcessMain processMain() {
        return new ProcessMain(restService, resource);
    }

    public TravelComponentGroupings travelComponentGroupings() {
        return new TravelComponentGroupings(restService, resource);
    }

    public TravelPlanSegments travelPlanSegments() {
        return new TravelPlanSegments(restService, resource);
    }

}
