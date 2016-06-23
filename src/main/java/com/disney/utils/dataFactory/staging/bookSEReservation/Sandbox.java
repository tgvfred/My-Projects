package com.disney.utils.dataFactory.staging.bookSEReservation;


import org.testng.annotations.Test;

import com.disney.utils.Randomness;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Sandbox {
//	@Test
	public void testEventDiningWithDefaultHouseHold(){
		HouseHold party = new HouseHold(4);
		party.sendToApi("Grumpy");
		ScheduledEventReservation res = new EventDiningReservation("Grumpy",party);
		res.book("BookGuaranteedTS");
		
		System.out.println("Res Number: " + res.getConfirmationNumber());
	}
	
	//@Test
	public void testEventDiningWithExistingHouseHoldWithMultiAddress(){
		HouseHold party = new HouseHold(1);
		party.primaryGuest().addAddress();
		party.primaryGuest().addEmail();
		
		ScheduledEventReservation res = new EventDiningReservation("Grumpy", party);
		res.book("BookGuaranteedTS");

		System.out.println("Res Number: " + res.getConfirmationNumber());
	}
	
	//@Test
	public void testEventDiningWithExistingLargeHouseHold(){
		HouseHold party = new HouseHold("2 Adults 2 Child");
		party.primaryGuest().addAddress();
		party.primaryGuest().addEmail();
		
		ScheduledEventReservation res = new EventDiningReservation("Grumpy", party);
		res.book("BookGuaranteedTS");
		System.out.println("Res Number: " + res.getConfirmationNumber());
	}
	
	@Test
	public void testEventDiningModifyDateFacility(){
		ScheduledEventReservation res = new EventDiningReservation("Grumpy");
		res.book("BookGuaranteedTS");
//		res.modify().modifyDate(Randomness.generateCurrentXMLDatetime(0));
	}
}
