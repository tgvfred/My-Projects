package com.disney.api.restServices.accommodation.accommodationBatchService.processMain;

import com.disney.api.restServices.core.RestService;

public class ProcessMain {
    private RestService restService;
    private String resource;

    public ProcessMain(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public void massModify() {
        // resource += "/process-data-views/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massReinstate() {
        // resource += "/process-data-views/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massRemoveGroup() {
        // resource += "/process-data-views/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massRepriceReservation() {
        // resource += "/process-data-views/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    // update to the RoomingListReservationRestRequest object
    public void massRoomingList(String RoomingListReservationRestRequest) {
        // resource += "/process-data-views/mass-roominglist";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

}
