package com.disney.api.restServices.accommodation.accommodationBatchService.job;

import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;

public class Job {
    private RestService restService;
    private String resource = "/job/{jobname}/process-main/{processId}";

    public Job(RestService restService, String resource) {
        this.restService = restService;
        this.resource = resource + this.resource;
    }

    public RestResponse processMain(String jobName, String processId) {
        String url = resource.replace("{jobname}", jobName).replace("{processId}", processId);
        return restService.sendPostRequest(url, HeaderType.REST, null, null);
    }
}
