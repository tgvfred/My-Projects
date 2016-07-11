package com.disney.composite.api.showDiningService.modify;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

/**
 * This class contains all fields, methods and classes required to test
 * 
 * @author Venkatesh A
 * @version 1/11/2016 Venkatesh A - Original
 */

public class TestModify extends BaseTest{
	protected HouseHold hh = null;
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
	@AfterMethod
	public void teardown(){
		if(TPS_ID.get() != null)
			if(!TPS_ID.get().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(TPS_ID.get());
				cancel.sendRequest();
			}
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testModify() {
		TestReporter.logScenario("Modify an existing reservation by increasing the party mix by 1 adult");
		HouseHold hh2 = new HouseHold(hh.getAllGuests().size() + 1);
		sendRequestAndvalidateLogs(book(), hh2);
	}	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWith2Adults(){
		TestReporter.logStep("Book a show dining reservation with 2 adults.");
		HouseHold hh2 = new HouseHold("2 Adults");
		sendRequestAndvalidateLogs(book(), hh2);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWith4Adults(){
		TestReporter.logStep("Book a show dining reservation with 4 adults.");
		HouseHold hh2 = new HouseHold("4 Adults");
		sendRequestAndvalidateLogs(book(), hh2);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWith2Adults2Child(){
		TestReporter.logStep("Book a show dining reservation with 2 adults and 2 children.");
		HouseHold hh2 = new HouseHold("2 Adults 2 Child");
		sendRequestAndvalidateLogs(book(), hh2);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testModifyWith12InParty(){
		TestReporter.logStep("Book a show dining reservation with 12 adults.");
		HouseHold hh2 = new HouseHold(12);
		sendRequestAndvalidateLogs(book(), hh2);
	}
	
	private Book book(){
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setParty(hh);
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking", book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		return book;
	}
	
	private void sendRequestAndvalidateLogs(Book book, HouseHold hh){
		TestReporter.log("Reservation Number: " + TPS_ID.get());
		Modify modify = new Modify(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.getTravelPlanId());
		modify.setReservationNumber(TPS_ID.get());
		modify.setParty(hh);
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred during modification", modify);
		TestReporter.assertEquals(modify.getResponseStatus(), "SUCCESS", "The status ["+modify.getResponseStatus()+"] was not 'SUCCESS' as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "modify", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3SEI", "updateOrder", false);
		logValidItems.addItem("ChargeGroupIF", "modifyGuestContainedChargeGroup", false);
		logValidItems.addItem("ChargeGroupIF", "modifyGuestContainerChargeGroup", false);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logValidItems.addItem("PartyIF", "retrieveParties", false);
		logValidItems.addItem("PartyIF", "retrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logValidItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		logValidItems.addItem("TravelPlanServiceCrossReferenceV3", "updateOrder", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(modify, logValidItems, 10000);
	}
}
