package com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request;

import java.util.ArrayList;
import java.util.List;
import com.disney.api.restServices.travelPlan.travelPlanService.modifyGuests.request.objects.GuestReferenceDetail;

public class ModifyGuestsRequest {
	private List<GuestReferenceDetail> guestReferenceDetail = new ArrayList<GuestReferenceDetail>();
	/**
	* 
	* @return 
	 * @return
	* The guestReferenceDetail
	*/
	public void addGuestReferenceDetail(){
		guestReferenceDetail.add(new GuestReferenceDetail());
	}
	
	public ModifyGuestsRequest(){
		guestReferenceDetail.add(new GuestReferenceDetail());
	}
	
	public List<GuestReferenceDetail> getGuestReferenceDetail() {
	return guestReferenceDetail;
	 }

	/**
	* 
	* @param guestReferenceDetail
	* The guestReferenceDetail
	*/
	public void setGuestReferenceDetail(List<GuestReferenceDetail> guestReferenceDetail) {
	this.guestReferenceDetail = guestReferenceDetail;
	 }
}
