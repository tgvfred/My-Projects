package com.disney.api.restServices.guestLink;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.guestLink.guestLinkService.GuestLinkService;

public class GuestLink {
	private RestService restService;
	
	public GuestLink(RestService restService){
		this.restService = restService;
		this.restService.setMainResource("GuestLinkService");
	}
	
	public GuestLinkService guestLinkService(){
		return new GuestLinkService(restService);
	}

}
