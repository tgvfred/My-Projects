package com.disney.utils.dataFactory.staging.bookSEReservation;

import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class PreReqBooking {

	
	public ScheduledEventReservation bookEventDining(String env){
		ScheduledEventReservation res = new EventDiningReservation(env);
		res.setParty(new HouseHold(1));
		res.book("BookGuaranteedTS");
		return res;
	}
	
	
	public ScheduledEventReservation markArrived(ScheduledEventReservation res){	
		res.arrived();
		return res;
	}
}
