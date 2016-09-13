package com.disney.api.restServices.profile.profileService.retrieveProfiles;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.disney.api.restServices.core.RestResponse;
import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.core.Headers.HeaderType;

public class retrieveProfiles {
	private RestService restService;
	private String resource = "/retrieveprofiles";
	
	public retrieveProfiles(RestService restService, String resource){		
		this.restService = restService;
		this.resource = resource + this.resource;	
	}
	
	public RestResponse sendGetRequest(String enterpriseFacilityId, String roomTypeCode, String packageId, String retrieveOnlyDefaultProfiles, String includeInactiveProfiles, String profileType){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("enterpriseFacilityId", enterpriseFacilityId));
		params.add(new BasicNameValuePair("roomTypeCode",roomTypeCode));
		params.add(new BasicNameValuePair("packageId",packageId));
		params.add(new BasicNameValuePair("retrieveOnlyDefaultProfiles",retrieveOnlyDefaultProfiles));
		params.add(new BasicNameValuePair("includeInactiveProfiles",includeInactiveProfiles));
		params.add(new BasicNameValuePair("profileType",profileType));
		return restService.sendGetRequest(resource, HeaderType.REST, params);
	}
	public RestResponse sendGetRequestWithMissingAuthToken(String enterpriseFacilityId, String roomTypeCode, String packageId, String retrieveOnlyDefaultProfiles, String includeInactiveProfiles, String profileType){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("enterpriseFacilityId", enterpriseFacilityId));
		params.add(new BasicNameValuePair("roomTypeCode",roomTypeCode));
		params.add(new BasicNameValuePair("packageId",packageId));
		params.add(new BasicNameValuePair("retrieveOnlyDefaultProfiles",retrieveOnlyDefaultProfiles));
		params.add(new BasicNameValuePair("includeInactiveProfiles",includeInactiveProfiles));
		params.add(new BasicNameValuePair("profileType",profileType));
		return restService.sendGetRequest(resource, HeaderType.REST_NOAuth, params);
	}
}
