package com.disney.api.restServices.profile.profileService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.profile.profileService.retrieveProfiles.retrieveProfiles;

public class ProfileService {
	private RestService restService;
	private String resource = "/profile";
	
	public retrieveProfiles retrieveProfiles() {
		return new retrieveProfiles(restService,resource);
	}
	
}
