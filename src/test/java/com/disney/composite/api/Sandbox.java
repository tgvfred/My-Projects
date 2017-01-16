package com.disney.composite.api;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.BookReservations;
import com.disney.utils.PackageCodes;
import com.disney.utils.Randomness;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Sandbox {

	
	@Test
	public void test(){
		HouseHold hh = new HouseHold(1);
		BookReservations book = new BookReservations("Latest", "RoomOnly_1Adult_TA");
		String startDate = Randomness.generateCurrentXMLDate(-31);
		String endDate = Randomness.generateCurrentXMLDate(-30);

		book.setAreaPeriodStartDate(startDate);
		book.setAreaPeriodEndDate(endDate);
		book.setResortAreaStartDate(startDate);
		book.setResortAreaEndDate(endDate);
		
		PackageCodes pkg = new PackageCodes();
		String packageCode = "B325A";
		book.setPackageCode(packageCode);
		book.setResortCode("1C");
		book.setRoomTypeCode("CA");		

		System.out.println(book.getRequest());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/firstName", hh.primaryGuest().getFirstName());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/lastName", hh.primaryGuest().getLastName());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/addressLine1", hh.primaryGuest().primaryAddress().getAddress1());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/city", hh.primaryGuest().primaryAddress().getCity());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/country", hh.primaryGuest().getAllAddresses().get(0).getCountry());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/postalCode", hh.primaryGuest().primaryAddress().getZipCode());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/roomDetails/roomReservationDetail/guestReferenceDetails/guest/addressDetails/state", hh.primaryGuest().primaryAddress().getState());				
		
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/firstName", hh.primaryGuest().getFirstName());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/lastName", hh.primaryGuest().getLastName());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/addressLine1", hh.primaryGuest().primaryAddress().getAddress1());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/city", hh.primaryGuest().primaryAddress().getCity());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/country", hh.primaryGuest().getAllAddresses().get(0).getCountry());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/postalCode", hh.primaryGuest().primaryAddress().getZipCode());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/addressDetails/state", hh.primaryGuest().primaryAddress().getState());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/phoneDetails/number", hh.primaryGuest().primaryPhone().getNumber());
		book.setRequestNodeValueByXPath("/Envelope/Body/bookReservations/request/roomReservationRequest/travelPlanGuest/emailDetails/address", hh.primaryGuest().primaryEmail().getEmail());
		
		book.sendRequest();
		System.out.println(book.getResponse());
	}
}
