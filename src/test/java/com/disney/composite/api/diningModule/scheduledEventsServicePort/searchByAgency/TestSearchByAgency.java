package com.disney.composite.api.diningModule.scheduledEventsServicePort.searchByAgency;

import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.BaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByAgency;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByAgency extends BaseTest{
	private ScheduledEventReservation book = null;
	
	@BeforeClass(alwaysRun = true)
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
	public void testSearchByAgencyId(){
	//	preReq();
		TestReporter.logStep("Search By Agency ID");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		

		boolean found = false;
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(book.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgencyAndSourceAccountingCenter(){
	//	preReq();
		TestReporter.logStep("Search By Agency ID and Source Accounting Center");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter("3");
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		boolean found = false;
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(book.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgencyAndLastName(){
	//	preReq();
		TestReporter.logStep("Search By Agency ID and Guest Last name");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(book.party().primaryGuest().getLastName());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		boolean found = false;
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(book.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgencyAndStatus_Booked(){
	//	preReq();
		TestReporter.logStep("Search By Agency ID and Reservation status of Booked");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("Booked");
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		boolean found = false;
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(book.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}
	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgencyAndStatus_Arrived(){
		ScheduledEventReservation res = preReq();
		res.arrived();
		TestReporter.logStep("Search By Agency ID and Reservation status of Arrived");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("Arrived");
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		boolean found = false;
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(res.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgencyAndStatus_NoShow(){
		ScheduledEventReservation res = preReq();
		res.noShow();
		TestReporter.logStep("Search By Agency ID and Reservation status of No Show");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("No Show");
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		boolean found = false;
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(res.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByAgencyAndStatus_Cancelled(){
		ScheduledEventReservation res = preReq();
		res.cancel();
		TestReporter.logStep("Search By Agency ID and Reservation status of Cancelled");
		SearchByAgency search = new SearchByAgency(environment, "OnlyAgency");
		search.setAgencyIataNumber("9999999998");
		search.setGuestLastName(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("Cancelled");
		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + search.getFaultString(), search);
		TestReporter.assertGreaterThanZero(search.getNumberOfReservation());
		
		boolean found = false;
		List<SearchByAgency.Reservation> reservations = search.getAllReservations();
		for(int i = 0; i < reservations.size(); i++){
			if(reservations.get(i).getReservationNumber().equals(res.getConfirmationNumber())){
				found =true;
				break;
			}
		}
		
		if(!found) throw new AutomationException("Reservation number ["+book.getConfirmationNumber()+"] was not found it response");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByAgency", false);	
		logItems.addItem("PartyIF", "retrieveParty", false);	
		validateLogs(search, logItems);
	}
	
	
	
	
	private ScheduledEventReservation preReq(){
		ScheduledEventReservation res = new EventDiningReservation(environment, new HouseHold(1));
		res.addTravelAgency("9999999998");
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		return res;
	}
}
