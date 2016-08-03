package com.disney.composite.api.scheduledEventsServicePort.searchByAgency;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByAgency;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByAgency extends BaseTest{
	private ScheduledEventReservation book = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void preReq_BookReservation(String environment) {
		this.environment = environment;

		book = new EventDiningReservation(environment);
		book.setParty(new HouseHold(1));
		book.addTravelAgency("9999999998");
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	

	@AfterClass(alwaysRun = true)
	public synchronized void closeSession() {
		try{book.cancel();}
		catch (Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgency(){
		TestReporter.logStep("Search By Agency");
		SearchByAgency searchByAgency = new SearchByAgency(environment, "OnlyAgency");
		searchByAgency.setAgencyIataNumber("9999999998");
		searchByAgency.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		searchByAgency.sendRequest();
		TestReporter.logAPI(!searchByAgency.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + searchByAgency.getFaultString(), searchByAgency);
		TestReporter.assertGreaterThanZero(searchByAgency.getNumberOfReservation());
		
		List<SearchByAgency.Reservation> reservations = searchByAgency.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			System.out.println("Reporting reservation ["+String.valueOf(i)+"]");
			TestReporter.logStep("Reservation "+String.valueOf(i)+": ");
			TestReporter.log("IATA Number: " + reservations.get(i).getAgencyIataNumber());
			TestReporter.log("Agency Name: " + reservations.get(i).getAgencyName());
			TestReporter.log("Cancellation Number: " + reservations.get(i).getCancellationNumber());
			TestReporter.log("Primary Guest First Name: " + reservations.get(i).getPrimaryGuestFirstName());
			TestReporter.log("Primary Guest Last Name: " + reservations.get(i).getPrimaryGuestLastName());
			TestReporter.log("Product Type Name: " + reservations.get(i).getProductTypeName());
			TestReporter.log("Product ID: " + reservations.get(i).getProductId());
			TestReporter.log("Enterprise Product ID: " + reservations.get(i).getEnterpriseProductId());
			TestReporter.log("Reservation Number: " + reservations.get(i).getReservationNumber());
			TestReporter.log("Reservation Status: " + reservations.get(i).getReservationStatus());
			TestReporter.log("Service date: " + reservations.get(i).getServiceDate());
			TestReporter.log("Ticket Issued: " + reservations.get(i).getTicketIssued());
			TestReporter.log("VIP Level: " + reservations.get(i).getVipLevel());
			TestReporter.log("Service Period ID: " + reservations.get(i).getServicePeriodId());
			TestReporter.log("Book Date: " + reservations.get(i).getBookDate());
			TestReporter.log("Special Dietary Needs: " + reservations.get(i).getSpecialDietaryNeeds());
			TestReporter.log("Extra Care Required: " + reservations.get(i).getExtraCareRequired());
			TestReporter.log("Paid In Full: " + reservations.get(i).getPaidInFull());
			TestReporter.log("Party Size: " + reservations.get(i).getPartySize());
		}
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(searchByAgency, logItems);
	}
}
