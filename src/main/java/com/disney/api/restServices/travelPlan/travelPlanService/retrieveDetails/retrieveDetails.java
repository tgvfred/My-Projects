package com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;
import com.disney.api.restServices.travelPlan.travelPlanService.retrieveDetails.request.RetrieveDetailsRequest;

public class retrieveDetails {
	private RestService restService;
	private String resource = "/retrievedetails";
	
	public retrieveDetails(RestService restService, String resource){		
		this.restService = restService;
		this.resource = resource + this.resource;
		
	}
	
	public RestResponse sendGetRequest(String travelPlanId, String travelPlanSegmentId, String retrieveComments, String retrieveGuests, String retrieveRooms, String retrieveHistory, String retrieveGuestAddress, String retrieveTP, String retrieveAcct, String retrieveDeposit, String retrieveLookForTCG ){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (travelPlanId != ""){
			params.add(new BasicNameValuePair("travelPlanId", travelPlanId));
		}
		if (travelPlanSegmentId !=""){
			params.add(new BasicNameValuePair("travelPlanSegmentId", travelPlanSegmentId));	
		}
		params.add(new BasicNameValuePair("retrieve-comments",retrieveComments));
		params.add(new BasicNameValuePair("retrieve-guests",retrieveGuests));
		params.add(new BasicNameValuePair("retrieve-room",retrieveRooms));
		params.add(new BasicNameValuePair("retrieve-history",retrieveHistory));
		params.add(new BasicNameValuePair("retrieve-guest-address",retrieveGuestAddress));
		params.add(new BasicNameValuePair("retrieve-travelPlan-details",retrieveTP));
		params.add(new BasicNameValuePair("retrieve-accounting-info",retrieveAcct));
		params.add(new BasicNameValuePair("retrieve-deposit-payment",retrieveDeposit));
		params.add(new BasicNameValuePair("look-for-TCG",retrieveLookForTCG));
		return restService.sendGetRequest(resource, HeaderType.REST, params);
	}
	
	public RestResponse sendGetRequestWithMissingAuthToken(String travelPlanId, String travelPlanSegmentId, String retrieveComments, String retrieveGuests, String retrieveRooms, String retrieveHistory, String retrieveGuestAddress, String retrieveTP, String retrieveAcct, String retrieveDeposit, String retrieveLookForTCG ){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (travelPlanId != ""){
			params.add(new BasicNameValuePair("travelPlanId", travelPlanId));
		}
		if (travelPlanSegmentId !=""){
			params.add(new BasicNameValuePair("travelPlanSegmentId", travelPlanSegmentId));	
		}
		params.add(new BasicNameValuePair("retrieve-comments",retrieveComments));
		params.add(new BasicNameValuePair("retrieve-guests",retrieveGuests));
		params.add(new BasicNameValuePair("retrieve-room",retrieveRooms));
		params.add(new BasicNameValuePair("retrieve-history",retrieveHistory));
		params.add(new BasicNameValuePair("retrieve-guest-address",retrieveGuestAddress));
		params.add(new BasicNameValuePair("retrieve-travelPlan-details",retrieveTP));
		params.add(new BasicNameValuePair("retrieve-accounting-info",retrieveAcct));
		params.add(new BasicNameValuePair("retrieve-deposit-payment",retrieveDeposit));
		params.add(new BasicNameValuePair("look-for-TCG",retrieveLookForTCG));
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
