package com.disney.composite.api.diningModule.eventDiningService.book;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.folioInterface.FolioInterfaceSettlement;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestBook extends BaseTest{
	// Defining global variables
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();

	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(TPS_ID.get());
			cancel.sendRequest();			
		}catch(Exception e){}
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithGuestRequests(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");

		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");

		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleGuestRequests(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");

		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithSpecialNeeds(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");

		sendRequestAndvalidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleSpecialNeeds(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");

		sendRequestAndvalidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleGuestRequestsAndSpecialNeedsAndComments(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
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
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBook_DLR(){
		Book book = new Book(environment, "DLRTableServiceOneChild");
		hh = new HouseHold(1);
		hh.primaryGuest().setAge("10");
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDatetime(Randomness.randomNumberBetween(1, 60)));
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}


	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithVipLevel2(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setVipLevel("2");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithInventoryOverride(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setInventoryOverrideReasonId("800060");

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookGuestWithMembership(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "GuestWithMembership");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithDefaultTravelAgency(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.addTravelAgency("99999998");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithTaxExemption(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setTaxExemptDetails("123465789", "Military");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithComponents(){	
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.setFacilityName("Biergarten Restaurant");
		res.setProductName("Biergarten Lunch");
		res.book("NoComponentsNoAddOns");
		TPS_ID.set(res.getConfirmationNumber());
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})

	public void testBookWith2Adults(){
		hh = new HouseHold("2 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults(){
		hh = new HouseHold("4 Adults");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith2Adults2Child(){
		hh = new HouseHold("2 Adults 2 Child");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith4Adults2Child2Infant(){
		hh = new HouseHold("4 Adults 2 Child 2 Infant");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWith12InParty(){
		hh = new HouseHold(12);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		sendRequestAndvalidateLogs(book);
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithLargeRequest(){
		Book book = new Book(environment, "GuestWithMembership");
		book.setParty( new HouseHold(12));
		book.addDetailsByFacilityNameAndProductName("Pioneer Hall", "Hoop-Dee-Doo-Cat 1-1st Show");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.BOOSTER_SEAT_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.REQUEST_TWO_HIGH_CHAIRS_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeGuestRequests.HIGH_CHAIR_ID, "GuestRequest");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.HEARING_LOSS_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.LIMITED_MOBILITY_ID, "SeSpecialNeed");
		book.setProfileDetailIdAndType(ServiceConstants.SeSpecialNeeds.OXYGEN_TANK_USE_ID, "SeSpecialNeed");
		book.setComments("Internal Comments", "Internal");
		book.setComments("More Internal Comments", "External");
		book.setTaxExemptDetails("123465789", "Military");
		book.addTravelAgency("99999998");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().contains("200"), "An error occurred retrieving allergies: " + retrieveAllergies.getFaultString() ,retrieveAllergies);

		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy);
		}

		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithAddOn(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "TableServiceAddOn");
		book.setParty(hh);

		sendRequestAndvalidateLogs(book);
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddAllergy(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg");

		sendRequestAndvalidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddTwoAllergies(){
		hh = new HouseHold("1 Adult");
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.setAllergies("Egg");
		book.setAllergies("Corn");		

		sendRequestAndvalidateLogs(book);
	}
	

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "it4", "s138180" })
	public void testAddAllAllergies(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Pioneer Hall", "Hoop-Dee-Doo-Cat 1-1st Show");
		RetrieveAllergies retrieveAllergies = new RetrieveAllergies(environment);
		retrieveAllergies.sendRequest();
		TestReporter.logAPI(!retrieveAllergies.getResponseStatusCode().contains("200"), "An error occurred retrieving allergies: " + retrieveAllergies.getFaultString() ,retrieveAllergies);

		for(String allergy : retrieveAllergies.getAllergies().values()){
			book.setAllergies(allergy);
		}

		sendRequestAndvalidateLogs(book);
	}

	@Test
	public void testTrueDiningReservation(){
		Book book = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		book.setParty(new HouseHold(1));
		book.setServiceStartDateTime(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(15, 25)));
		book.setReservableResourceId("BA054CBB-D573-C672-BE95-173042178DBE");
		book.addDetailsByFacilityNameAndProductName("The Hollywood Brown Derby", "Brown Derby Lunch F! 1st Show");
		book.addSpecialEventByProductName("Fantasmic! Viewing 1st Show");
		sendRequestAndvalidateLogs(book);  //Debug: could not freeze
	}
	
	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testBookWithMultipleResources(){
		hh = new HouseHold(1);
		Book book = new Book(environment, "NoComponentsNoAddOns");
		book.setParty(hh);
		book.addDetailsByFacilityNameAndProductName("Biergarten Restaurant", "Biergarten Lunch");
		book.setNumberOfResources("2");
		sendRequestAndvalidateLogs(book);
	}
	
	private void sendRequestAndvalidateLogs(Book book){
		book.sendRequest();
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.getFaultString(), book);
		TPS_ID.set(book.getTravelPlanSegmentId());
		TestReporter.assertTrue(Regex.match("[0-9]+", book.getTravelPlanId()), "The travel plan ID ["+book.getTravelPlanId()+"] was numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", TPS_ID.get()), "The reservation number ["+TPS_ID.get()+"] was numeric as expected.");
		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "createInventory", false);
		logValidItems.addItem("ChargeGroupIF", "createChargeGroupAndPostCharges", false);
		logValidItems.addItem("PartyIF", "createAndRetrieveParty", false);	
		logValidItems.addItem("TravelPlanServiceV3", "create", false);
		logValidItems.addItem("UpdateInventory", "updateInventory", false);
		validateLogs(book, logValidItems, 10000);
	}
}