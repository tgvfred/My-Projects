package com.disney.api.restServices.accommodation.accommodationBatchService.processDataViews;

import com.disney.api.restServices.core.RestService;

public class ProcessDataViews {
    private RestService restService;
    private String resource;

    public ProcessDataViews(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public void processData(String processId) {
        // resource += "/process-main/" + processId + "/processData";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

    public void processMainViews() {
        // resource += "/process-main/process-ain-iews";
        // String json = restService.getJsonFromObject(request);
        // return restService.sendPostRequest(resource, HeaderType.REST, json);
    }

}
