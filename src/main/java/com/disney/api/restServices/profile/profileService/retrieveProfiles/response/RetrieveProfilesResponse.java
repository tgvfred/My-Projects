package com.disney.api.restServices.profile.profileService.retrieveProfiles.response;

import java.util.ArrayList;
import java.util.List;

import com.disney.api.restServices.profile.profileService.retrieveProfiles.response.objects.ProfileInfo;

public class RetrieveProfilesResponse {
	private List<ProfileInfo> profileInfos = new ArrayList<ProfileInfo>();

	/**
	* 
	* @return
	* The profileInfos
	*/
	public List<ProfileInfo> getProfileInfos() {
	return profileInfos;
	 }

	/**
	* 
	* @param profileInfos
	* The profileInfos
	*/
	public void setProfileInfos(List<ProfileInfo> profileInfos) {
	this.profileInfos = profileInfos;
	 }
}
