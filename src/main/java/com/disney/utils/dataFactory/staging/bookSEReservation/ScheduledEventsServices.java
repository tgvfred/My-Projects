package com.disney.utils.dataFactory.staging.bookSEReservation;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.GenerateAuthorizationNumber;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.OptimizeInventory;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAlaCarteCancelReasons;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAll;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveAllergies;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveBookingStatusValues;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveCancelReasons;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveCelebrations;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveChangeReasons;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveCommentTypes;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveCommunicationChannels;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveDiningReservations;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveGuestRequests;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveInventoryOverideReasons;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveMassCancelReasons;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveReprintReasons;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveReservationsForAutoCheckout;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveRoles;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveSalesChannels;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveSpecialEvents;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveSpecialNeeds;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.RetrieveTaxExemptTypes;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByAgency;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByGuest;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class ScheduledEventsServices {
	protected String environment;
	public ScheduledEventsServices(String environment) {this.environment = environment;}
	
	public GenerateAuthorizationNumber generateAuthorizationNumber(String environment){
		GenerateAuthorizationNumber number = new GenerateAuthorizationNumber(environment);
		sendRequestAndValidateResponse(number, "An error occurred generating an authorization number: ");
		return number;
	}
	public OptimizeInventory optimizeInventory(String environment, String scenario, String communicationsChannel, String freezeId, String inventoryGot, String inventoryWant,  
			String adultCount, String childCount, String infantCount, String partySize, String salesChannel, String tpsId){
		OptimizeInventory optimize = new OptimizeInventory(environment, scenario);
		if(!communicationsChannel.isEmpty())optimize.setCommunicationChannel(communicationsChannel);
		if(!freezeId.isEmpty())optimize.setFreezeId(freezeId);
		if(!inventoryGot.isEmpty())optimize.setInventoryGot(inventoryGot);
		if(!inventoryWant.isEmpty())optimize.setInventoryWant(inventoryWant);
		if(!adultCount.isEmpty())optimize.setPartymIxAdultCount(adultCount);
		if(!childCount.isEmpty())optimize.setPartyMixChildCount(childCount);
		if(!infantCount.isEmpty())optimize.setPartyMixInfantCount(infantCount);
		if(!partySize.isEmpty())optimize.setPartySize(partySize);
		if(!salesChannel.isEmpty())optimize.setSalesChannel(salesChannel);
		if(!tpsId.isEmpty())optimize.setTravelPlanSegmentId(tpsId);
		sendRequestAndValidateResponse(optimize, "An error occurred optimizing inventory for reservation ["+tpsId+"]: ");
		return optimize;
	}
	public RetrieveAlaCarteCancelReasons retrieveAlaCarteCancelReasons(String environment){
		RetrieveAlaCarteCancelReasons retrieve = new RetrieveAlaCarteCancelReasons(environment);
		sendRequestAndValidateResponse(retrieve, "An error occurred retrieving all A LA Carte cancel reasons: ");
		return retrieve;
	}	
	public RetrieveAll retrieveAll(String environment){
		RetrieveAll retrieve = new RetrieveAll(environment);
		sendRequestAndValidateResponse(retrieve, "An error occurred retrieving all: ");
		return retrieve;
	}
	public RetrieveAllergies retrieveAllergies(String environment){
		RetrieveAllergies allergies = new RetrieveAllergies(environment);
		sendRequestAndValidateResponse(allergies, "An error occurred retrieving allergies: ");
		return allergies;
	}
	public RetrieveBookingStatusValues retrieveBookingStatusValues(String environment){
		RetrieveBookingStatusValues retrieveBookingStatusValues = new RetrieveBookingStatusValues(environment);
		sendRequestAndValidateResponse(retrieveBookingStatusValues, "An error occurred retrieving booking status values: ");
		return retrieveBookingStatusValues;
	}
	public RetrieveCancelReasons retrieveCancelReasons(String environment){
		RetrieveCancelReasons retrieveCancelReasons = new RetrieveCancelReasons(environment);
		sendRequestAndValidateResponse(retrieveCancelReasons, "An error occurred retrieving cancel reasons: ");
		return retrieveCancelReasons;
	}
	public RetrieveCelebrations retrieveCelebrations(String environment){
		RetrieveCelebrations retrieveCelebrations = new RetrieveCelebrations(environment);
		sendRequestAndValidateResponse(retrieveCelebrations, "An error occurred retrieving celebrations: ");
		return retrieveCelebrations;
	}
	public RetrieveChangeReasons retrieveChangeReasons(String environment){
		RetrieveChangeReasons retrieveChangeReasons = new RetrieveChangeReasons(environment);
		sendRequestAndValidateResponse(retrieveChangeReasons, "An error occurred retrieving change reasons: ");
		return retrieveChangeReasons;
	}
	public RetrieveCommentTypes retrieveCommentTypes(String environment){
		RetrieveCommentTypes retrieveCommentTypes = new RetrieveCommentTypes(environment);
		sendRequestAndValidateResponse(retrieveCommentTypes, "An error occurred retrieving comment types: ");
		return retrieveCommentTypes;
	}
	public RetrieveCommunicationChannels retrieveCommunicationChannels(String environment){
		RetrieveCommunicationChannels retrieveCommunicationChannels = new RetrieveCommunicationChannels(environment);
		sendRequestAndValidateResponse(retrieveCommunicationChannels, "An error occurred retrieving communication channels: ");
		return retrieveCommunicationChannels;
	}
	public RetrieveDiningReservations retrieveDiningReservations(String environment, String scenario, String facilityId, String reservationDate, 
			String reservationWindowEndTime, String reservationWindowStartTime, String sourceAccountingCenter){
		RetrieveDiningReservations retrieveDiningReservations = new RetrieveDiningReservations(environment, scenario);
		if(!facilityId.isEmpty())retrieveDiningReservations.setFacilityId(facilityId);
		if(!reservationDate.isEmpty())retrieveDiningReservations.setReservationDate(reservationDate);
		if(!reservationWindowEndTime.isEmpty())retrieveDiningReservations.setReservationWindowEndTime(reservationWindowEndTime);
		if(!reservationWindowStartTime.isEmpty())retrieveDiningReservations.setReservationWindowStartTime(reservationWindowStartTime);
		if(!sourceAccountingCenter.isEmpty())retrieveDiningReservations.setSourceAccountingCenter(sourceAccountingCenter);
		sendRequestAndValidateResponse(retrieveDiningReservations, "An error occurred retrieving dining reservations: ");
		return retrieveDiningReservations;
	}
	public RetrieveGuestRequests retrieveGuestRequests(String environment){
		RetrieveGuestRequests retrieveGuestRequests = new RetrieveGuestRequests(environment);
		sendRequestAndValidateResponse(retrieveGuestRequests, "An error occurred retrieving guest requests: ");
		return retrieveGuestRequests;
	}
	public RetrieveInventoryOverideReasons retrieveInventoryOverideReasons(String environment){
		RetrieveInventoryOverideReasons retrieveInventoryOverideReasons = new RetrieveInventoryOverideReasons(environment);
		sendRequestAndValidateResponse(retrieveInventoryOverideReasons, "An error occurred retrieving inventory override reasons: ");
		return retrieveInventoryOverideReasons;
	}
	public RetrieveMassCancelReasons retrieveMassCancelReasons(String environment){
		RetrieveMassCancelReasons retrieveMassCancelReasons = new RetrieveMassCancelReasons(environment);
		sendRequestAndValidateResponse(retrieveMassCancelReasons, "An error occurred retrieving mass cancel reasons: ");
		return retrieveMassCancelReasons;
	}
	public RetrieveReprintReasons retrieveReprintReasons(String environment){
		RetrieveReprintReasons retrieveReprintReasons = new RetrieveReprintReasons(environment);
		sendRequestAndValidateResponse(retrieveReprintReasons, "An error occurred retrieving reprint reasons: ");
		return retrieveReprintReasons;
	}
	public RetrieveReservationsForAutoCheckout retrieveReservationsForAutoCheckout(String environment, String scenario, String dateEqualCondition, 
			String processionDateDaysOut, String sourceAccountingCenter){
		RetrieveReservationsForAutoCheckout retrieveReservationsForAutoCheckout = new RetrieveReservationsForAutoCheckout(environment, scenario);
		if(!dateEqualCondition.isEmpty())retrieveReservationsForAutoCheckout.setDateEqualCondition(dateEqualCondition);
		if(!processionDateDaysOut.isEmpty())retrieveReservationsForAutoCheckout.setProcessDateDaysOut(processionDateDaysOut);
		if(!sourceAccountingCenter.isEmpty())retrieveReservationsForAutoCheckout.setSourceAccountingCenter(sourceAccountingCenter);
		sendRequestAndValidateResponse(retrieveReservationsForAutoCheckout, "An error occurred retrieving reservations for autocheckout: ");
		return retrieveReservationsForAutoCheckout;
	}
	public RetrieveRoles retrieveRoles(String environment){
		RetrieveRoles retrieveRoles = new RetrieveRoles(environment);
		sendRequestAndValidateResponse(retrieveRoles, "An error occurred retrieving roles: ");
		return retrieveRoles;
	}
	public RetrieveSalesChannels retrieveSalesChannels(String environment){
		RetrieveSalesChannels retrieveSalesChannels = new RetrieveSalesChannels(environment);
		sendRequestAndValidateResponse(retrieveSalesChannels, "An error occurred retrieving sales channels: ");
		return retrieveSalesChannels;
	}
	public RetrieveSpecialEvents retrieveSpecialEvents(String environment){
		RetrieveSpecialEvents retrieveSpecialEvents = new RetrieveSpecialEvents(environment);
		sendRequestAndValidateResponse(retrieveSpecialEvents, "An error occurred retrieving special events: ");
		return retrieveSpecialEvents;
	}
	public RetrieveSpecialNeeds retrieveSpecialNeeds(String environment){
		RetrieveSpecialNeeds retrieveSpecialNeeds = new RetrieveSpecialNeeds(environment);
		sendRequestAndValidateResponse(retrieveSpecialNeeds, "An error occurred retrieving special needs: ");
		return retrieveSpecialNeeds;
	}
	public RetrieveTaxExemptTypes retrieveTaxExemptTypes(String environment){
		RetrieveTaxExemptTypes retrieveTaxExemptTypes = new RetrieveTaxExemptTypes(environment);
		sendRequestAndValidateResponse(retrieveTaxExemptTypes, "An error occurred retrieving tax exempt types: ");
		return retrieveTaxExemptTypes;
	}
	public SearchByAgency searchByAgency(String environment, String scenario, String iataNumber, String guestLastName, 
			String reservationStatus, String sourceAccountingCenter){
		SearchByAgency searchByAgency = new SearchByAgency(environment, scenario);
		if(!iataNumber.isEmpty())searchByAgency.setAgencyIataNumber(iataNumber);
		if(!guestLastName.isEmpty())searchByAgency.setGuestLastName(guestLastName);
		if(!reservationStatus.isEmpty())searchByAgency.setReservationStatus(reservationStatus);
		if(!sourceAccountingCenter.isEmpty())searchByAgency.setSourceAccountingCenter(sourceAccountingCenter);
		sendRequestAndValidateResponse(searchByAgency, "An error occurred searching by agency: ");
		return searchByAgency;
	}
	public SearchByGuest searchByGuest(String environment, String scenario, String cancellationNumber, String email, String firstName, String lastName, String odsGuestId,
			String phoneNumber, String postalCode, String reservationNumber, String reservationStatus, String serviceDate, String serviceWindowEndDate, 
			String serviceWindowStartDate, String sourceAccountingCenter){
		SearchByGuest searchByGuest = new SearchByGuest(environment, scenario);
		if(!cancellationNumber.isEmpty())searchByGuest.setCancellationNumber(cancellationNumber);
		if(!email.isEmpty())searchByGuest.setEmail(email);
		if(!firstName.isEmpty())searchByGuest.setFirstName(firstName);
		if(!lastName.isEmpty())searchByGuest.setLastName(lastName);
		if(!odsGuestId.isEmpty())searchByGuest.setGuestOdsIds(odsGuestId);
		if(!phoneNumber.isEmpty())searchByGuest.setPhoneNumber(phoneNumber);
		if(!postalCode.isEmpty())searchByGuest.setPostalCode(postalCode);
		if(!reservationNumber.isEmpty())searchByGuest.setReservationNumber(reservationNumber);
		if(!reservationStatus.isEmpty())searchByGuest.setReservationStatus(reservationStatus);
		if(!serviceDate.isEmpty())searchByGuest.setServiceDate(serviceDate);
		if(!serviceWindowEndDate.isEmpty())searchByGuest.setServiceWindowEnd(serviceWindowEndDate);
		if(!serviceWindowStartDate.isEmpty())searchByGuest.setServiceWindowStart(serviceWindowStartDate);
		if(!sourceAccountingCenter.isEmpty())searchByGuest.setSourceAccountingCenter(sourceAccountingCenter);
		sendRequestAndValidateResponse(searchByGuest, "An error occurred searching by guest: ");
		return searchByGuest;
	}
	public SearchByVenue searchByVenue(String environment, String scenario, String facilityId, String productIds, String reservationStatus, String serviceDate,
			String serviceWindowEndDate, String serviceWindowStartDate, String sourceAccountingCenter){
		SearchByVenue searchByVenue = new SearchByVenue(environment, scenario);
		if(!facilityId.isEmpty())searchByVenue.setFacilityId(facilityId);
		if(!productIds.isEmpty())searchByVenue.setProductIds(productIds);
		if(!reservationStatus.isEmpty())searchByVenue.setReservationStatus(reservationStatus);
		if(!serviceDate.isEmpty())searchByVenue.setServiceDate(serviceDate);
		if(!serviceWindowEndDate.isEmpty())searchByVenue.setServiceWindowEnd(serviceWindowEndDate);
		if(!serviceWindowStartDate.isEmpty())searchByVenue.setServiceWindowStart(serviceWindowStartDate);
		if(!sourceAccountingCenter.isEmpty())searchByVenue.setSourceAccountingCenter(sourceAccountingCenter);
		sendRequestAndValidateResponse(searchByVenue, "An error occurred searching by venue: ");
		return searchByVenue;
	}	
	private void sendRequestAndValidateResponse(BaseSoapService bs, String errorMessage){
		bs.sendRequest();
		TestReporter.logAPI(!bs.getResponseStatusCode().equals("200"), errorMessage + bs.getFaultString(), bs);
	}	
	
	protected ThreadLocal<ScheduledEventsServices> ses = new ThreadLocal<ScheduledEventsServices>();
	private ThreadLocal<Map<String, String>> mapValues = new ThreadLocal<Map<String, String>>();
	
	@BeforeMethod(alwaysRun=true)
	@Parameters({ "environment" })
	public void setup(String environment){
		this.environment = environment;
		ses.set(new ScheduledEventsServices(environment));
	}

	@Test
	public void testRetrieveTaxExemptTypes(){
		TestReporter.logScenario("Retrieve Tax Exempt Types");
		RetrieveTaxExemptTypes retrieve = ses.get().retrieveTaxExemptTypes(environment);
		int number = retrieve.getNumberOfTaxExemptTypes();
		mapValues.set(retrieve.getTaxExemptTypes());
		for(int i = 1; i < number; i++){TestReporter.log("Tax Exempt Type: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveSpecialNeeds(){
		TestReporter.logScenario("Retrieve Special Needs");
		RetrieveSpecialNeeds retrieve = ses.get().retrieveSpecialNeeds(environment);
		int number = retrieve.getNumberOfSpecialNeeds();
		mapValues.set(retrieve.getSpecialNeedsCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Special Needs Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getSpecialNeedsDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Special Needs Description: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getSpecialNeedsIds());
		for(int i = 1; i < number; i++){TestReporter.log("Special Needs Ids: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveSpecialEvents(){
		TestReporter.logScenario("Retrieve Special Events");
		RetrieveSpecialEvents retrieve = ses.get().retrieveSpecialEvents(environment);
		int number = retrieve.getNumberOfSpecialEvents();
		mapValues.set(retrieve.getSpecialEventsCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Special Event Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getSpecialEventsDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Special Event Description: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getSpecialEventsIds());
		for(int i = 1; i < number; i++){TestReporter.log("Special Event Ids: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveSalesChannels(){
		TestReporter.logScenario("Retrieve Sales Channels");
		RetrieveSalesChannels retrieve = ses.get().retrieveSalesChannels(environment);
		int number = retrieve.getNumberOfSalesChannels();
		mapValues.set(retrieve.getSalesChannels());
		for(int i = 1; i < number; i++){TestReporter.log("Sales Channels: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveRoles(){
		TestReporter.logScenario("Retrieve Roles");
		RetrieveRoles retrieve = ses.get().retrieveRoles(environment);
		int number = retrieve.getNumberOfRoles();
		mapValues.set(retrieve.getRoles());
		for(int i = 1; i < number; i++){TestReporter.log("Roles: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveReprintReasons(){
		TestReporter.logScenario("Retrieve Reprint Reasons");
		RetrieveReprintReasons retrieve = ses.get().retrieveReprintReasons(environment);
		int number = retrieve.getNumberOfReprintReasons();
		mapValues.set(retrieve.getReprintReasonCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Reprint Reason Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getReprintReasonDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Reprint Reason Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getReprintReasonIds());
		for(int i = 1; i < number; i++){TestReporter.log("Reprint Reason Ids: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveMassCancelReasons(){
		TestReporter.logScenario("Retrieve Mass Cancel Reasons");
		RetrieveMassCancelReasons retrieve = ses.get().retrieveMassCancelReasons(environment);
		int number = retrieve.getNumberOfMassCancelReasons();
		mapValues.set(retrieve.getMassCancelCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Mass Cancel Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getMassCancelDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Mass Cancel Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getMassCancelIds());
		for(int i = 1; i < number; i++){TestReporter.log("Mass Cancel Ids: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveInventoryOverideReasons(){
		TestReporter.logScenario("Retrieve Inventory Overide Reasons");
		RetrieveInventoryOverideReasons retrieve = ses.get().retrieveInventoryOverideReasons(environment);
		int number = retrieve.getNumberOfInventoryOverideReasons();
		mapValues.set(retrieve.getInventoryOverideReasonsCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Inventory Override Reasons Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getInventoryOverideReasonsDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Inventory Override Reasons Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getInventoryOverideReasonsIds());
		for(int i = 1; i < number; i++){TestReporter.log("Inventory Override Reasons Ids: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveGuestRequests(){
		TestReporter.logScenario("Retrieve Guest Requests");
		RetrieveGuestRequests retrieve = ses.get().retrieveGuestRequests(environment);
		int number = retrieve.getNumberOfGuestRequests();
		mapValues.set(retrieve.getGuestRequestCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Guest Request Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getGuestRequestDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Guest Request Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getGuestRequestIds());
		for(int i = 1; i < number; i++){TestReporter.log("Guest Request Ids: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveCommunicationChannels(){
		TestReporter.logScenario("Retrieve Communication Channels");
		RetrieveCommunicationChannels retrieve = ses.get().retrieveCommunicationChannels(environment);
		int number = retrieve.getNumberOfCommunicationChannels();
		mapValues.set(retrieve.getCommunicationChannels());
		for(int i = 1; i < number; i++){TestReporter.log("Communication Channels: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveCommentTypes(){
		TestReporter.logScenario("Retrieve Comment Types");
		RetrieveCommentTypes retrieve = ses.get().retrieveCommentTypes(environment);
		int number = retrieve.getNumberOfCommentTypes();
		mapValues.set(retrieve.getCommentTypes());
		for(int i = 1; i < number; i++){TestReporter.log("Comment Types: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveChangeReasons(){
		TestReporter.logScenario("Retrieve Change Reasons");
		RetrieveChangeReasons retrieve = ses.get().retrieveChangeReasons(environment);
		int number = retrieve.getNumberOfChangeReasons();
		mapValues.set(retrieve.getChangeReasonCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Change Reason Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getChangeReasonDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Change Reason Descriptions: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveCelebrations(){
		TestReporter.logScenario("Retrieve Celebrations");
		RetrieveCelebrations retrieve = ses.get().retrieveCelebrations(environment);
		int number = retrieve.getNumberOfCelebrations();
		mapValues.set(retrieve.getCelebrationsCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Celebration Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getCelebrationsDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Celebration Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getCelebrationsIds());
		for(int i = 1; i < number; i++){TestReporter.log("Celebration IDs: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveCancelReasons(){
		TestReporter.logScenario("Retrieve Cancel Reasons");
		RetrieveCancelReasons retrieve = ses.get().retrieveCancelReasons(environment);
		int number = retrieve.getNumberOfCancelReasons();
		mapValues.set(retrieve.getCancelReasonCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Cancel Reasons Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getCancelReasonDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Cancel Reasons Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getCancelReasonIds());
		for(int i = 1; i < number; i++){TestReporter.log("Cancel Reasons IDs: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveBookingStatusValues(){
		TestReporter.logScenario("Retrieve Booking Status Values");
		RetrieveBookingStatusValues retrieve = ses.get().retrieveBookingStatusValues(environment);
		int number = retrieve.getNumberOfBookingStatusValues();
		mapValues.set(retrieve.getBookingStatusValues());
		for(int i = 1; i < number; i++){TestReporter.log("Booking Status Values: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveAllergies(){
		TestReporter.logScenario("Retrieve Allergies");
		RetrieveAllergies retrieve = ses.get().retrieveAllergies(environment);
		int number = retrieve.getNumberOfAllergies();
		mapValues.set(retrieve.getAllergies());
		for(int i = 1; i < number; i++){TestReporter.log("Retrieve Allergies: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testRetrieveAll(){
		TestReporter.logScenario("Retrieve All");
		RetrieveAll retrieve = ses.get().retrieveAll(environment);
		
		int number = retrieve.getNumberOfAllergies();
		mapValues.set(retrieve.getAllergies());
		for(int i = 1; i < number; i++){TestReporter.log("Retrieve Allergies: " + mapValues.get().get(String.valueOf(i)));}

		RetrieveAllergies retrieveAllergies = ses.get().retrieveAllergies(environment);
		int numberOfAllergies = retrieveAllergies.getNumberOfAllergies();
		
		TestReporter.assertEquals(number, numberOfAllergies, "Verify that the number of allergies from RetrieveAll ["+number+"] match that which is expected ["+numberOfAllergies+"].");
	}
	@Test
	public void testRetrieveAlaCarteCancelReasons(){
		TestReporter.logScenario("Retrieve Ala Carte Cancel Reasons");
		RetrieveAlaCarteCancelReasons retrieve = ses.get().retrieveAlaCarteCancelReasons(environment);
		int number = retrieve.getNumberOfAlcCancelReasons();
		mapValues.set(retrieve.getAllOptionCodes());
		for(int i = 1; i < number; i++){TestReporter.log("Retrieve Ala Carte Cancel Reasons Codes: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getAllOptionDescriptions());
		for(int i = 1; i < number; i++){TestReporter.log("Retrieve Ala Carte Cancel Reasons Descriptions: " + mapValues.get().get(String.valueOf(i)));}
		mapValues.set(retrieve.getAllOptionIds());
		for(int i = 1; i < number; i++){TestReporter.log("Retrieve Ala Carte Cancel Reasons IDs: " + mapValues.get().get(String.valueOf(i)));}
	}
	@Test
	public void testGenerateAuthorizationNumber(){
		TestReporter.logScenario("Generate Authorization Number");
		GenerateAuthorizationNumber generate = ses.get().generateAuthorizationNumber(environment);
		String number = generate.getAuthorizationNumber();
		TestReporter.assertTrue(Regex.match("[0-9]+", number), "Verify the authorization number ["+number+"] is numeric.");
	}
	
	@Test
	public void testSearchByVenue(){
		TestReporter.logScenario("Search By Venue");
		String facilityId = "266442";
		String serviceDate = Randomness.generateCurrentXMLDatetime(1);
		String bookingStatus = "Booked";
		String TPS_ID;
		
		HouseHold hh = new HouseHold(1);
		Book res = new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		res.setParty(hh);
		res.setFacilityId(facilityId);
		res.setServiceStartDateTime(serviceDate);
		res.sendRequest();
		TestReporter.logAPI(!res.getResponseStatusCode().equals("200"), "An error occurred booking a table service reservation: " + res.getFaultString(), res);
		TPS_ID = res.getTravelPlanSegmentId();
		
		SearchByVenue searchByVenue = ses.get().searchByVenue(environment, "Main", facilityId, "", bookingStatus, serviceDate, BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.assertTrue(searchByVenue.getResponse().contains(TPS_ID), "Verify the reservation number ["+TPS_ID+"] is contained in the response.");
		
		try{
			Cancel cancel = new Cancel(environment, "Main");
			cancel.setReservationNumber(TPS_ID);
			cancel.sendRequest();
		}catch(Exception e){}
	}
	@Test
	public void testSearchByGuest(){
		TestReporter.logScenario("Search By Guest");
		HouseHold hh = new HouseHold(1);
		hh.sendToApi(environment);
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		SearchByGuest searchByGuest = ses.get().searchByGuest(environment, "Main", "", "", "", "", "", "", "", res.getConfirmationNumber(), "", "", "", "", "");
		TestReporter.assertTrue(searchByGuest.getResponse().contains(res.getConfirmationNumber()), "Verify the reservation number ["+res.getConfirmationNumber()+"] is contained in the response.");
		
		try{
			res.cancel();
		}catch(Exception e){}					
	}
	@Test
	public void testSearchByAgency(){
		String iataNumber = "9999999998";
		TestReporter.logScenario("Search By Agency");
		ScheduledEventReservation book = new EventDiningReservation(environment);
		book.setParty(new HouseHold(1));
		book.addTravelAgency("9999999998");
		book.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		SearchByAgency searchByAgency = ses.get().searchByAgency(environment, "OnlyAgency", iataNumber, BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString(), BaseSoapCommands.REMOVE_NODE.toString());
		TestReporter.logAPI(!searchByAgency.getResponseStatusCode().equals("200"), "An error occurred during retrieval." + searchByAgency.getFaultString(), searchByAgency);
		TestReporter.assertTrue(searchByAgency.getNumberOfReservation() > 0, "Verify that reservations were returned for IATA ["+iataNumber+"]");
		try{book.cancel();}
		catch (Exception e){}
	}
	@Test
	public void testRetrieveReservationsForAutoCheckout(){
		TestReporter.logScenario("Retrieve Reservations For Auto Checkout");
		RetrieveReservationsForAutoCheckout retrieve = ses.get().retrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW", "!=", "", "");
		int number = retrieve.getNumberOfReservations();
		TestReporter.assertTrue(number > 0, "Verify that reservations were returned.");
	}
	@Test
	public void testRetrieveDiningReservations(){
		String facilityId = "266442";
		TestReporter.logScenario("Retrieve Dining Reservations");
		RetrieveDiningReservations retrieve = ses.get().retrieveDiningReservations(environment, "Main", facilityId, "", "", "", "");
		int number = retrieve.getNumberOfReservations();
		TestReporter.logStep("["+number+"] reservations were returned.");
	}
	@Test
	public void testOptimizeInventory(){
		String date = Randomness.generateCurrentXMLDate(1);
		TestReporter.logScenario("Optimize Inventory");
		HouseHold hh = new HouseHold(1);
		ScheduledEventReservation res = new EventDiningReservation(environment, hh);		
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		
		// Retrieve the 'inventoryGot' value 
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rsBaseInfo = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(res.getConfirmationNumber()) + " AND PROD_TYP_NM = 'RESERVABLE_RESOURCE_COMPONENT'"));
		Recordset rsResourceId= new Recordset(db.getResultSet(Dreams.getTcReservableResourceID(rsBaseInfo.getValue("TC_ID"))));
		String inventoryGot = rsResourceId.getValue("RSRC_INVTRY_TYP_CD");
		
		// Retrieve the 'inventoryWant' value
		Database availDb = new OracleDatabase(environment, Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByFacilityAndDate(res.getFacilityId(), date)));
		String inventoryWant = rsInventory.getValue("Resource_ID");
		OptimizeInventory retrieve = ses.get().optimizeInventory(environment, "Main", "", "", inventoryGot, inventoryWant, hh.numberOfAdults(), hh.numberOfChildren(), hh.numberOfInfants(), String.valueOf(hh.getAllGuests().size()), "", res.getConfirmationNumber());
		TestReporter.assertTrue(retrieve.isSuccessful(), "Verify that the inventory optimization was successful.");
		
		try{res.cancel();
		}catch(Exception e){}
	}
}
