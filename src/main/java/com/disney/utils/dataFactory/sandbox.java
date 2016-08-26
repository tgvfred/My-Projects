package com.disney.utils.dataFactory;


import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.test.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class sandbox {
	
	@Test
	public void test(){
		/*
		HouseHold party = new HouseHold(1);
		ScheduledEventReservation res = new ShowDiningReservation("Stage_CM", party);
		res.setFacilityName("Pioneer Hall");
		res.setProductName("Hoop-Dee-Doo-Cat 1-1st Show");
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res.folio("Stage").payment().makeFullCardPayment();
		System.out.println(res.getConfirmationNumber());*/
		//TestReporter.setDebugLevel(1);
		Book book = new Book("Stage_CM", ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(new HouseHold(2));
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(19));
		book.setReservableResourceId("7277b7e0-226e-453b-8524-7f4edf312ccd");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show", "4cafc352-62d4-4e88-98f3-16474db25aa7");
		book.sendRequest();

		TestReporter.logAPI(!book.getResponseStatusCode().contains("200"), book.getFaultString() ,book);
	}
}
