package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.RetrieveSettlementMethodsRequest;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;

public class retrieveDetails {
	private RestService restService;
	private String resource = "/retrievedetails";
	
	public retrieveDetails(RestService restService, String resource){		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendGetRequest(String referenceName, String referenceValue, String travelPlanId, String travelPlanSegmentId, String SiebelId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (referenceName != ""){
			params.add(new BasicNameValuePair("referenceName", referenceName));
		}
		if (referenceValue !=""){
			params.add(new BasicNameValuePair("referenceValue", referenceValue));
		}
		if (travelPlanId != ""){
			params.add(new BasicNameValuePair("travelPlanId", travelPlanId));
		}
		if (travelPlanSegmentId !=""){
			params.add(new BasicNameValuePair("travelPlanSegmentId", travelPlanSegmentId));	
		}
		if (SiebelId != ""){
			params.add(new BasicNameValuePair("SiebelId", SiebelId));
		}
		return restService.sendGetRequest(resource, HeaderType.REST, params);
	}
	
	public RestResponse sendGetRequestWithMissingAuthToken(String referenceName, String referenceValue, String travelPlanId, String travelPlanSegmentId, String SiebelId){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (referenceName != ""){
			params.add(new BasicNameValuePair("referenceName", referenceName));
		}
		if (referenceValue !=""){
			params.add(new BasicNameValuePair("referenceValue", referenceValue));
		}
		if (travelPlanId != ""){
			params.add(new BasicNameValuePair("travelPlanId", travelPlanId));
		}
		if (travelPlanSegmentId !=""){
			params.add(new BasicNameValuePair("travelPlanSegmentId", travelPlanSegmentId));	
		}
		if (SiebelId != ""){
			params.add(new BasicNameValuePair("SiebelId", SiebelId));
		}
		return restService.sendGetRequest(resource, HeaderType.REST_NOAuth, params);
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
