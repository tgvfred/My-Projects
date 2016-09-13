package com.disney.api.restServices.guestLink.guestLinkService;

import com.disney.api.restServices.core.RestService;
import com.disney.api.restServices.guestLink.guestLinkService.retrieveLinkedTransGuestIdByTransGuestId.linkedTransGuestIdByTransGuestId;
public class GuestLinkService {
	private RestService restService;
	private String resource = "/guestlink";
	public GuestLinkService(RestService restService){
		this.restService = restService;
	}
	public linkedTransGuestIdByTransGuestId LinkedTransGuestIdByTransGuestId(){
		return new linkedTransGuestIdByTransGuestId(restService,resource);
	}
}
