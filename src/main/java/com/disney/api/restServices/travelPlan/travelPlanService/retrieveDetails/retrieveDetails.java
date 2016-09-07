package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails;

import org.apache.http.Header;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;

public class retrieveDetails {
	private RestService restService;
	private String resource = "/retrieveDetails";
	
	public retrieveDetails(RestService restService, String resource){		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendPutRequest(RetrieveDetailsRequest request){	
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST, json);		
	}
	
	//To submit a REST call without the authorization token information
	public RestResponse sendPutRequestWithMissingAuthToken(RetrieveDetailsRequest request){
		String json = restService.getJsonFromObject(request);
		return restService.sendPutRequest(resource, HeaderType.REST_NOAuth, json);
	}
}
