package com.disney.composite.api.diningModule.showDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.showDiningService.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	protected Book book;
	protected String TP_ID;
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected String[] defaultExpectedLogs = {"PartyIF;createAndRetrieveParty",
			"FacilityMasterServiceSEI;findFacilityByEnterpriseID",
			"PackagingService;getProducts"};
	protected HouseHold hh = null;
	protected String reservationNumber;
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setTravelPlanSegmentId(TPS_ID.get());
			cancel.sendRequest();			
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBook() {
		TestReporter.logStep("Book a show dining reservation.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBook_DLR(){
		Book book = new Book(environment, "DLRDinnerShowOneAdultOneChild");
		book.setParty(hh);
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(1));	
		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWithVipLevel2() {
		TestReporter.logStep("Book a show dining reservation with VIP Level 2.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setVipLevel("2");
		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWithTaxExemption(){
		TestReporter.logStep("Book a show dining reservation for a Guest with Tax Exemption.");
		hh = new HouseHold(1);
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		book.setTaxExemptDetails("123465789", "Military");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookGuestWithMembership() {
		TestReporter.logStep("Book a show dining reservation for a Guest with a Memebership.");
		Book book = new Book(environment, "GuestWithMembership");
		hh = new HouseHold(1);
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWithDefaultTravelAgency() {
		TestReporter.logStep("Book a show dining reservation with Inventory Override.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.addTravelAgency("99999998");
		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWithInventoryOverride() {
		TestReporter.logStep("Book a show dining reservation with Inventory Override.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setInventoryOverrideReasonId("800060");
		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWith2Adults(){
		TestReporter.logStep("Book a show dining reservation with 2 adults.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold("2 Adults");
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWith4Adults(){
		TestReporter.logStep("Book a show dining reservation with 4 adults.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold("4 Adults");
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWith2Adults2Child(){
		TestReporter.logStep("Book a show dining reservation with 2 adults and 2 children.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold("2 Adults 2 Child");
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		TestReporter.logStep("Book a show dining reservation with 4 adults, 2 children, and 2 infants.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold("4 Adults 2 Child 2 Infant");
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWith12InParty(){
		TestReporter.logStep("Book a show dining reservation with 12 adults.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(12);
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithGuestRequests(){
		TestReporter.logStep("Book a show dining reservation with a Guest Request.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithMultipleGuestRequests(){
		TestReporter.logStep("Book a show dining reservation with multiple Guest Requests.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.sendRequest();

		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithSpecialNeeds(){
		TestReporter.logStep("Book a show dining reservation with a Special Need.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithMultipleSpecialNeeds(){
		TestReporter.logStep("Book a show dining reservation with multiple Special Needs.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithComments(){
		TestReporter.logStep("Book a show dining reservation with an Internal Comment.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithMultipleComments(){
		TestReporter.logStep("Book a show dining reservation with multiple Comments.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService" })
	public void testBookWithMultipleGuestRequestsAndSpecialNeedsAndComments(){
		TestReporter.logStep("Book a show dining reservation with very needy guest.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testAddAllergy(){
		TestReporter.logStep("Book a show dining reservation for a guest with an Allergy.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setAllergies("Egg");

		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testAddTwoAllergies(){
		TestReporter.logStep("Book a show dining reservation for a guest with two Allergies.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setAllergies("Egg");
		book.setAllergies("Corn");

		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "showDiningService", "it4", "s138180" })
	public void testAddAllAllergies(){
		TestReporter.logStep("Book a show dining reservation for a guest with all types of Allergies.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);

		hh = new HouseHold(1);
		book.setParty(hh);
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		
		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy);
		}		
		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "showDiningService"})
	public void testBookWithMultipleResources(){
		TestReporter.logStep("Book a show dining reservation with multiple resources.");
		Book book = new Book(environment, ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		hh = new HouseHold(1);
		book.setParty(hh);
		book.setNumberOfResources("2");

		sendRequestAndvalidateLogs(book);
	}
	private void sendRequestAndvalidateLogs(Book book){
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		TP_ID = book.getTravelPlanId();
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+", TP_ID), "The travel plan ID ["+TP_ID+"] was numeric  as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "The reservation number ["+TPS_ID.get()+"] was numeric  as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "book", false);
		logValidItems.addItem("TravelPlanServiceV3SEI", "create", false);
		logValidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);	
		logValidItems.addItem("FacilityMasterServiceSEI", "findFacilityByEnterpriseID", false);
		logValidItems.addItem("PackagingService", "getProducts", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(book, logValidItems, 10000);
	}
}
