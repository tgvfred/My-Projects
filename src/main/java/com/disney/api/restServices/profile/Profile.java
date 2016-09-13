package com.disney.api.restServices.profile;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.profile.profileService.ProfileService;


public class Profile {
	private RestService restService;
	
	public Profile(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("ProfileService");
	}
	
	public ProfileService profileService(){
		return new ProfileService() ; 
	}	
}
