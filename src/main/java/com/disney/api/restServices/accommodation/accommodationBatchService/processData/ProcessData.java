package com.disney.api.restServices.accommodation.accommodationBatchService.processData;

import com.disney.api.restServices.accommodation.accommodationBatchService.processData.request.MassCancelRequest;
import com.disney.api.restServices.accommodation.accommodationBatchService.processData.request.UpdateProcessStatusRequest;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;

public class ProcessData {
    private RestService restService;
    private String resource;

    public ProcessData(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public RestResponse processData(UpdateProcessStatusRequest request) {
        resource += "/process-data";
        String json = restService.getJsonFromObject(request);
        return restService.sendPutRequest(resource, HeaderType.REST, json);
    }

    public RestResponse massCancel(MassCancelRequest request) {
        resource += "/process-data/mass-cancel";
        String json = restService.getJsonFromObject(request);
        return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massDetail(String processId) {
        // resource += "/process-data/mass-detail/" + processId;
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massModify() {
        // resource += "/process-data/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massReinstate() {
        // resource += "/process-data/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massRemoveGroup() {
        // resource += "/process-data/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void massRepriceReservation() {
        // resource += "/process-data/mass-modify";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    // update to the RoomingListReservationRestRequest object
    public void massRoomingList(String RoomingListReservationRestRequest) {
        // resource += "/process-data/mass-roominglist";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    // update the UpdateRoomingListRestRequest object
    public void massRoomingList(int UpdateRoomingListRestRequest) {
        // resource += "/process-data/mass-roominglist";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPutRequest(resource, HeaderType.REST, json);
    }

}
