package com.disney.utils.dataFactory.staging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.disney.utils.dataFactory.staging.bookResortReservation.BookResortReservation;

public class StageResortReservations {
	String environment = "Bashful";
	int reservationsToMake = 10;
	String[] resorts = {	/*"AULANI_RESORT",
							"AULANI_VILLAS",
							"BAY_LAKE_TOWERS",
							"BEACH_CLUB",
							"BOARDWALK_INN",
							"BOARDWALK_VILLAS",
							"CONTEMPORARY",
							"GRAND_FLORIDIAN",
							*/"GRAND_FLORIDIAN_VILLAS",
							"VERO_BEACH"
						};
	
	@Test
	public void bookReservation(){
		Reservation reservation = null;
		
		for(int currentResort = 0 ; currentResort < resorts.length ; currentResort++){
			Reporter.log(resorts[currentResort] + "</br>");
			for(int currentReservation = 0 ; currentReservation  < reservationsToMake ; currentReservation++){
				try {
					Class<?> clazz = Class.forName("com.disney.utils.dataFactory.staging.bookResortReservation.BookResortReservation");
					Method method = clazz.getDeclaredMethod(resorts[currentResort], String.class);
					reservation = (Reservation) method.invoke(new BookResortReservation(), environment);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				
				reservation.setNumberOfAdults(1);
				reservation.setPrimaryGuestLastName("AutomationStaging");
				reservation.quickBook();
				reservation.assignRoomToReservation();
				reservation.checkInReservation();
				//reservation.makePayment();
				
				Reporter.log("&nbsp;&nbsp;Booking: " + (currentReservation + 1) + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Travel Plan ID: " + reservation.getTravelPlanId() + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Reservation ID: " + reservation.getTravelPlanSegmentId() + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Party ID: " + reservation.getPartyId() + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Guest ID: " + reservation.getGuestId() + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Assigned Room: " + reservation.getRoomNumber() + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Remaining Balance: " + reservation.getBalanceDue() + "</br>");
				Reporter.log("&nbsp;&nbsp;&nbsp;&nbsp;Folio ID: " + reservation.getFolioId() + "</br></br>");
			}
		}
	}
}
