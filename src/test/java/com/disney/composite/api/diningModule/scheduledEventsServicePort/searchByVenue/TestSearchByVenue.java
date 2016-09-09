package com.disney.composite.api.diningModule.scheduledEventsServicePort.searchByVenue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestSearchByVenue extends BaseTest{
	private String facilityId = "266442";
	private String productId = "";
	private String serviceDate = Randomness.generateCurrentXMLDatetime(1);
	private String bookingStatus = "Booked";
	private String TPS_ID;
	private Book res; 

	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res.setParty(hh);
		res.setFacilityId(facilityId);
		res.setServiceStartDateTime(serviceDate);
		res.sendRequest();
		TestReporter.logAPI(!res.getResponseStatusCode().equals("200"), "An error occurred booking a reservation: " + res.getFaultString(), res);
		TPS_ID = res.getTravelPlanSegmentId();
		productId= res.getRequestProductId();
	}
	
	@AfterClass(alwaysRun = true)
	public void closeSession() {
		if(TPS_ID != null)
			if(!TPS_ID.isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setReservationNumber(TPS_ID);
				cancel.sendRequest();
			}
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_FacilityAndServiceDate(){
		TestReporter.logStep("Search By Venue: Facility ID: " + facilityId + " and Service Start Date");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(facilityId);
		search.setServiceDate(serviceDate);
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_FacilityAndServiceTime(){
		TestReporter.logStep("Search By Venue: Facility ID: " + facilityId + " and Service Start and End time");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(facilityId);
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(res.getRequestServiceStartDate());
		search.setServiceWindowStart(res.getRequestServiceStartDate());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_FacilityAndServiceDateAndReservationStatus(){
		TestReporter.logStep("Search By Venue: Facility ID: " + facilityId + ", Service Start Date and Reservation Status");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(facilityId);
		search.setServiceDate(serviceDate);
		search.setReservationStatus("Booked");
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_FacilityAndServiceStartEndTimeAndReservationStatus(){
		TestReporter.logStep("Search By Venue: Facility ID: " + facilityId + ", Service Start and End Time and Reservation Status");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(facilityId);
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("Booked");
		search.setServiceWindowEnd(res.getRequestServiceStartDate());
		search.setServiceWindowStart(res.getRequestServiceStartDate());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}
	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_ProductAndServiceDate(){
		TestReporter.logStep("Search By Venue: Product ID: " + productId + " and Service Start Date");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(productId);
		search.setServiceDate(serviceDate);
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_ProductAndServiceTime(){
		TestReporter.logStep("Search By Venue: Product ID: " + productId + " and Service Start and End time");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(productId);
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(res.getRequestServiceStartDate());
		search.setServiceWindowStart(res.getRequestServiceStartDate());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_ProductAndServiceDateAndReservationStatus(){
		TestReporter.logStep("Search By Venue: Product ID: " + productId + ", Service Start Date and Reservation Status");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(productId);
		search.setServiceDate(serviceDate);
		search.setReservationStatus("Booked");
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_ProductAndServiceStartEndTimeAndReservationStatus(){
		TestReporter.logStep("Search By Venue: Product ID: " + productId + ", Service Start and End Time and Reservation Status");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(productId);
		search.setServiceDate(BaseSoapCommands.REMOVE_NODE.toString());
		search.setReservationStatus("Booked");
		search.setServiceWindowEnd(res.getRequestServiceStartDate());
		search.setServiceWindowStart(res.getRequestServiceStartDate());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue_MultipleProductsAndServiceTime(){
		TestReporter.logStep("Search By Venue: Product ID: " + productId + ", Service Start and End Time and Reservation Status");
		SearchByVenue search = new SearchByVenue(environment, "Main");
		search.setFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		search.setProductIds(productId);
		search.setProductIds("124005");
		search.setProductIds("126216");
		search.setServiceDate(serviceDate);
		search.setReservationStatus(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowEnd(BaseSoapCommands.REMOVE_NODE.toString());
		search.setServiceWindowStart(BaseSoapCommands.REMOVE_NODE.toString());
		search.setSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
		searchAndValidate(search);
	}
	private void searchAndValidate(SearchByVenue search){

		search.sendRequest();
		TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred during the search."+ search.getFaultString(), search);
		TestReporter.assertTrue(search.getResponse().contains(TPS_ID), "Verify the response contains the reservation number ["+TPS_ID+"] for facility ID ["+facilityId+"] on the service date ["+serviceDate+"].");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "searchByVenue", false);
		logItems.addItem("FolioServiceIF", "retrieveFolioPaymentSummary", false);
		logItems.addItem("PartyIF", "retrieveParty", false);
		logItems.addItem("PartyIF", "retrievePartyBasicInformation", false);		
		validateLogs(search, logItems, 10000);
	}
}