package com.disney.composite.api.scheduledEventsServicePort.retrieveAll;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.scheduledEventsServicePort.operations.RetrieveAll;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieveAll extends BaseTest{
	protected RetrieveAll retrieveAll;
	
	@Override
	@BeforeClass
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		TestReporter.logStep("Retrieve All");
		retrieveAll = new RetrieveAll(environment);
		retrieveAll.sendRequest();
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testRetrieveAll(){
		TestReporter.logAPI(!retrieveAll.getResponseStatusCode().equals("200"), "An error occurred during retrieval: " + retrieveAll.getFaultString(), retrieveAll);
		TestReporter.assertGreaterThanZero(retrieveAll.getNumberOfAllergies());
	}	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_Allergies(){		
		TestReporter.assertTrue(retrieveAll.getNumberOfAllergies() > 0, "Verify allergies are returned.");
		TestReporter.assertTrue(retrieveAll.getAllergies().size() == retrieveAll.getNumberOfAllergies(), "Verify the number of allergies is ["+retrieveAll.getNumberOfAllergies()+"].");
		reportValues("Allergies", retrieveAll.getNumberOfAllergies(), retrieveAll.getAllergies());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_BookingStatusValues(){
		TestReporter.assertTrue(retrieveAll.getNumberOfBookingStatusValues() > 0, "Verify booking status values are returned.");
		TestReporter.assertTrue(retrieveAll.getBookingStatusValues().size() == retrieveAll.getNumberOfBookingStatusValues(), "Verify the number of booking status values is ["+retrieveAll.getNumberOfBookingStatusValues()+"].");
		reportValues("Booking Status Values", retrieveAll.getNumberOfBookingStatusValues(), retrieveAll.getBookingStatusValues());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_CancelReasons(){
		TestReporter.assertTrue(retrieveAll.getNumberOfCancelReasons() > 0, "Verify cancel reasons are returned.");
		TestReporter.assertTrue(retrieveAll.getCancelReasonCodes().size() == retrieveAll.getNumberOfCancelReasons(), "Verify the number of cancel reason codes is ["+retrieveAll.getNumberOfCancelReasons()+"].");
		reportValues("Cancel Reason Codes", retrieveAll.getNumberOfCancelReasons(), retrieveAll.getCancelReasonCodes());
		TestReporter.assertTrue(retrieveAll.getCancelReasonDescriptions().size() == retrieveAll.getNumberOfCancelReasons(), "Verify the number of cancel reason descriptions is ["+retrieveAll.getNumberOfCancelReasons()+"].");
		reportValues("Cancel Reason Descriptions", retrieveAll.getNumberOfCancelReasons(), retrieveAll.getCancelReasonDescriptions());
		TestReporter.assertTrue(retrieveAll.getCancelReasonIds().size() == retrieveAll.getNumberOfCancelReasons(), "Verify the number of cancel reason ids is ["+retrieveAll.getNumberOfCancelReasons()+"].");
		reportValues("Cancel Reason IDs", retrieveAll.getNumberOfCancelReasons(), retrieveAll.getCancelReasonIds());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_ChangeReasons(){
		TestReporter.assertTrue(retrieveAll.getNumberOfChangeReasons() > 0, "Verify change reasons are returned.");
		TestReporter.assertTrue(retrieveAll.getChangeReasonCodes().size() == retrieveAll.getNumberOfChangeReasons(), "Verify the number of change reason codes is ["+retrieveAll.getNumberOfChangeReasons()+"].");
		reportValues("Change Reason Codes", retrieveAll.getNumberOfChangeReasons(), retrieveAll.getChangeReasonCodes());
		TestReporter.assertTrue(retrieveAll.getChangeReasonDescriptions().size() == retrieveAll.getNumberOfChangeReasons(), "Verify the number of change reason descriptions is ["+retrieveAll.getNumberOfChangeReasons()+"].");
		reportValues("Change Reason Description", retrieveAll.getNumberOfChangeReasons(), retrieveAll.getChangeReasonDescriptions());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_CommunicationChannels(){
		TestReporter.assertTrue(retrieveAll.getNumberOfCommunicationChannels() > 0, "Verify communication channels are returned.");
		TestReporter.assertTrue(retrieveAll.getCommunicationChannels().size() == retrieveAll.getNumberOfCommunicationChannels(), "Verify the number of change reason codes is ["+retrieveAll.getNumberOfCommunicationChannels()+"].");
		reportValues("Communication Channels", retrieveAll.getNumberOfCommunicationChannels(), retrieveAll.getCommunicationChannels());	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_GuestRequests(){
		TestReporter.assertTrue(retrieveAll.getNumberOfGuestRequests() > 0, "Verify guest requests are returned.");
		TestReporter.assertTrue(retrieveAll.getGuestRequestCodes().size() == retrieveAll.getNumberOfGuestRequests(), "Verify the number of guest request codes is ["+retrieveAll.getNumberOfGuestRequests()+"].");
		reportValues("Guest Requests", retrieveAll.getNumberOfGuestRequests(), retrieveAll.getGuestRequestCodes());
		TestReporter.assertTrue(retrieveAll.getGuestRequestDescriptions().size() == retrieveAll.getNumberOfGuestRequests(), "Verify the number of guest request descriptions is ["+retrieveAll.getNumberOfGuestRequests()+"].");
		reportValues("Guest Requests", retrieveAll.getNumberOfGuestRequests(), retrieveAll.getGuestRequestDescriptions());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_MassCancelReasons(){
		TestReporter.assertTrue(retrieveAll.getNumberOfMassCancelReasons() > 0, "Verify mass cancel reasons are returned.");
		TestReporter.assertTrue(retrieveAll.getMassCancelCodes().size() == retrieveAll.getNumberOfMassCancelReasons(), "Verify the number of mass cancel reason codes is ["+retrieveAll.getNumberOfMassCancelReasons()+"].");
		reportValues("Mass Cancel Reasons", retrieveAll.getNumberOfMassCancelReasons(), retrieveAll.getMassCancelCodes());
		TestReporter.assertTrue(retrieveAll.getMassCancelDescriptions().size() == retrieveAll.getNumberOfMassCancelReasons(), "Verify the number of mass cancel reason descriptions is ["+retrieveAll.getNumberOfMassCancelReasons()+"].");
		reportValues("Mass Cancel Reasons", retrieveAll.getNumberOfMassCancelReasons(), retrieveAll.getMassCancelDescriptions());
		TestReporter.assertTrue(retrieveAll.getMassCancelIds().size() == retrieveAll.getNumberOfMassCancelReasons(), "Verify the number of mass cancel reason ids is ["+retrieveAll.getNumberOfMassCancelReasons()+"].");
		reportValues("Mass Cancel Reasons", retrieveAll.getNumberOfMassCancelReasons(), retrieveAll.getMassCancelIds());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_ReprintReasons(){
		TestReporter.assertTrue(retrieveAll.getNumberOfReprintReasons() > 0, "Verify reprint reasons are returned.");
		TestReporter.assertTrue(retrieveAll.getReprintReasonCodes().size() == retrieveAll.getNumberOfReprintReasons(), "Verify the number of reprint reason codes is ["+retrieveAll.getNumberOfReprintReasons()+"].");
		reportValues("Reprint Reasons", retrieveAll.getNumberOfReprintReasons(), retrieveAll.getReprintReasonCodes());
		TestReporter.assertTrue(retrieveAll.getReprintReasonDescriptions().size() == retrieveAll.getNumberOfReprintReasons(), "Verify the number of reprint reason descriptions is ["+retrieveAll.getNumberOfReprintReasons()+"].");
		reportValues("Reprint Reasons", retrieveAll.getNumberOfReprintReasons(), retrieveAll.getReprintReasonDescriptions());
		TestReporter.assertTrue(retrieveAll.getReprintReasonIds().size() == retrieveAll.getNumberOfReprintReasons(), "Verify the number of reprint reason ids is ["+retrieveAll.getNumberOfReprintReasons()+"].");
		reportValues("Reprint Reasons", retrieveAll.getNumberOfReprintReasons(), retrieveAll.getReprintReasonIds());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_Roles(){
		TestReporter.assertTrue(retrieveAll.getNumberOfRoles() > 0, "Verify roles are returned.");
		TestReporter.assertTrue(retrieveAll.getRoles().size() == retrieveAll.getNumberOfRoles(), "Verify the number of roles is ["+retrieveAll.getNumberOfRoles()+"].");
		reportValues("Roles", retrieveAll.getNumberOfRoles(), retrieveAll.getRoles());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_SalesChannels(){
		TestReporter.assertTrue(retrieveAll.getNumberOfSalesChannels() > 0, "Verify sales channels are returned.");
		TestReporter.assertTrue(retrieveAll.getSalesChannels().size() == retrieveAll.getNumberOfSalesChannels(), "Verify the number of sales channels is ["+retrieveAll.getNumberOfSalesChannels()+"].");
		reportValues("Sales Channels", retrieveAll.getNumberOfSalesChannels(), retrieveAll.getSalesChannels());	
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_SpecialEvents(){
		TestReporter.assertTrue(retrieveAll.getNumberOfSpecialEvents() > 0, "Verify special events are returned.");
		TestReporter.assertTrue(retrieveAll.getSpecialEventsCodes().size() == retrieveAll.getNumberOfSpecialEvents(), "Verify the number of special event codes is ["+retrieveAll.getNumberOfSpecialEvents()+"].");
		reportValues("Special Events", retrieveAll.getNumberOfSpecialEvents(), retrieveAll.getSpecialEventsCodes());
		TestReporter.assertTrue(retrieveAll.getSpecialEventsDescriptions().size() == retrieveAll.getNumberOfSpecialEvents(), "Verify the number of special event descriptions is ["+retrieveAll.getNumberOfSpecialEvents()+"].");
		reportValues("Special Events", retrieveAll.getNumberOfSpecialEvents(), retrieveAll.getSpecialEventsDescriptions());
		TestReporter.assertTrue(retrieveAll.getSpecialEventsIds().size() == retrieveAll.getNumberOfSpecialEvents(), "Verify the number of special event ids is ["+retrieveAll.getNumberOfSpecialEvents()+"].");
		reportValues("Special Events", retrieveAll.getNumberOfSpecialEvents(), retrieveAll.getSpecialEventsIds());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_SpecialNeeds(){
		TestReporter.assertTrue(retrieveAll.getNumberOfSpecialNeeds() > 0, "Verify special events are returned.");
		TestReporter.assertTrue(retrieveAll.getSpecialNeedsCodes().size() == retrieveAll.getNumberOfSpecialNeeds(), "Verify the number of special needs codes is ["+retrieveAll.getNumberOfSpecialNeeds()+"].");
		reportValues("Special Needs", retrieveAll.getNumberOfSpecialNeeds(), retrieveAll.getSpecialNeedsCodes());
		TestReporter.assertTrue(retrieveAll.getSpecialNeedsDescriptions().size() == retrieveAll.getNumberOfSpecialNeeds(), "Verify the number of special needs descriptions is ["+retrieveAll.getNumberOfSpecialNeeds()+"].");
		reportValues("Special Needs", retrieveAll.getNumberOfSpecialNeeds(), retrieveAll.getSpecialNeedsDescriptions());
		TestReporter.assertTrue(retrieveAll.getSpecialNeedsIds().size() == retrieveAll.getNumberOfSpecialNeeds(), "Verify the number of special needs ids is ["+retrieveAll.getNumberOfSpecialNeeds()+"].");
		reportValues("Special Needs", retrieveAll.getNumberOfSpecialNeeds(), retrieveAll.getSpecialNeedsIds());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_TaxExemptTypes(){
		TestReporter.assertTrue(retrieveAll.getNumberOfTaxExemptTypes() > 0, "Verify tax exempt types are returned.");
		TestReporter.assertTrue(retrieveAll.getTaxExemptTypes().size() == retrieveAll.getNumberOfTaxExemptTypes(), "Verify the number of tax exempt types is ["+retrieveAll.getNumberOfTaxExemptTypes()+"].");
		reportValues("Tax Exempt Types", retrieveAll.getNumberOfTaxExemptTypes(), retrieveAll.getTaxExemptTypes());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_CommentTypes(){
		TestReporter.assertTrue(retrieveAll.getNumberOfCommentTypes() > 0, "Verify comment types are returned.");
		TestReporter.assertTrue(retrieveAll.getCommentTypes().size() == retrieveAll.getNumberOfCommentTypes(), "Verify the number of comment types is ["+retrieveAll.getNumberOfCommentTypes()+"].");
		reportValues("Comment Types", retrieveAll.getNumberOfCommentTypes(), retrieveAll.getCommentTypes());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_InventoryOverideReasons(){
		TestReporter.assertTrue(retrieveAll.getNumberOfInventoryOverideReasons() > 0, "Verify inventory overide reason codes are returned.");
		TestReporter.assertTrue(retrieveAll.getInventoryOverideReasonsCodes().size() == retrieveAll.getNumberOfInventoryOverideReasons(), "Verify the number of overide inventory reason codes is ["+retrieveAll.getNumberOfInventoryOverideReasons()+"].");
		reportValues("Inventory Overide Reasons", retrieveAll.getNumberOfInventoryOverideReasons(), retrieveAll.getInventoryOverideReasonsCodes());
		TestReporter.assertTrue(retrieveAll.getInventoryOverideReasonsDescriptions().size() == retrieveAll.getNumberOfInventoryOverideReasons(), "Verify the number of overide inventory reason descriptions is ["+retrieveAll.getNumberOfInventoryOverideReasons()+"].");
		reportValues("Inventory Overide Reasons", retrieveAll.getNumberOfInventoryOverideReasons(), retrieveAll.getInventoryOverideReasonsDescriptions());
		TestReporter.assertTrue(retrieveAll.getInventoryOverideReasonsIds().size() == retrieveAll.getNumberOfInventoryOverideReasons(), "Verify the number of overide inventory reason ids is ["+retrieveAll.getNumberOfInventoryOverideReasons()+"].");
		reportValues("Inventory Overide Reasons", retrieveAll.getNumberOfInventoryOverideReasons(), retrieveAll.getInventoryOverideReasonsIds());
	}
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_AlaCarteCancelReasons(){
		TestReporter.assertTrue(retrieveAll.getNumberOfAlaCarteCancelReasons() > 0, "Verify Ala Carte Cancel Reasons are returned.");
		TestReporter.assertTrue(retrieveAll.getAlaCarteCancelReasonsCodes().size() == retrieveAll.getNumberOfAlaCarteCancelReasons(), "Verify the number of Ala Carte Cancel reason codes is ["+retrieveAll.getNumberOfAlaCarteCancelReasons()+"].");
		reportValues("Ala Carte Cancel Reasons", retrieveAll.getNumberOfAlaCarteCancelReasons(), retrieveAll.getAlaCarteCancelReasonsCodes());
		TestReporter.assertTrue(retrieveAll.getAlaCarteCancelReasonsDescriptions().size() == retrieveAll.getNumberOfAlaCarteCancelReasons(), "Verify the number of Ala Carte Cancel descriptions is ["+retrieveAll.getNumberOfAlaCarteCancelReasons()+"].");
		reportValues("Ala Carte Cancel Reasons", retrieveAll.getNumberOfAlaCarteCancelReasons(), retrieveAll.getAlaCarteCancelReasonsDescriptions());
		TestReporter.assertTrue(retrieveAll.getAlaCarteCancelReasonsIds().size() == retrieveAll.getNumberOfAlaCarteCancelReasons(), "Verify the number of Ala Carte Cancel ids is ["+retrieveAll.getNumberOfAlaCarteCancelReasons()+"].");
		reportValues("Ala Carte Cancel Reasons", retrieveAll.getNumberOfAlaCarteCancelReasons(), retrieveAll.getAlaCarteCancelReasonsIds());
	}	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void retrieveAll_Celebrations(){
		TestReporter.assertTrue(retrieveAll.getNumberOfCelebrations() > 0, "Verify Celebrations are returned.");
		TestReporter.assertTrue(retrieveAll.getCelebrationsCodes().size() == retrieveAll.getNumberOfCelebrations(), "Verify the number of Celebrations codes is ["+retrieveAll.getNumberOfCelebrations()+"].");
		reportValues("Celebrations", retrieveAll.getNumberOfCelebrations(), retrieveAll.getCelebrationsCodes());
		TestReporter.assertTrue(retrieveAll.getCelebrationsDescriptions().size() == retrieveAll.getNumberOfCelebrations(), "Verify the number of Celebration descriptions is ["+retrieveAll.getNumberOfCelebrations()+"].");
		reportValues("Celebrations", retrieveAll.getNumberOfCelebrations(), retrieveAll.getCelebrationsDescriptions());
		TestReporter.assertTrue(retrieveAll.getCelebrationsIds().size() == retrieveAll.getNumberOfCelebrations(), "Verify the number of Celebration ids is ["+retrieveAll.getNumberOfCelebrations()+"].");
		reportValues("Celebrations", retrieveAll.getNumberOfCelebrations(), retrieveAll.getCelebrationsIds());
	}	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void validateLogs(){
		LogItems logItems = new LogItems();
		logItems.addItem("ScheduledEventsServiceIF", "retrieveAll", false);	
		logItems.addItem("ProfileComponentServiceIF", "retrieveProfilesByRoutingType", false);	
		logItems.addItem("PartyIF", "getOptions", false);		
		logItems.addItem("FolioConfigurationServiceIF", "getOptions", false);	
		validateLogs(retrieveAll, logItems);
	}
	
	private void reportValues(String valueName, int size, Map<String, String> values){
		for(int i = 0; i < size; i++){
			TestReporter.log(valueName + ": " + values.get(String.valueOf(i)));
		}
	}
}