package com.disney.utils.dataFactory.staging.bookSEReservation;

import java.util.HashMap;
import java.util.Map;

import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations.*;
import com.disney.utils.TestReporter;

public class ScheduledEventsServices {	
	private String authorizationNumber;
	private Map<String, String> alaCarteCancelReasonOptionCodes = new HashMap<String, String>();
	private Map<String, String> alaCarteCancelReasonOptionDescriptions = new HashMap<String, String>();
	private Map<String, String> alaCarteCancelReasonOptionIds = new HashMap<String, String>();
	private int numberOfAlcCancelReasons;
	
	public void checkout(String environment, String tpsId){
		Checkout checkout = new Checkout(environment);
		checkout.setTravelPlanSegmentId(tpsId);
		sendRequestAndValidateResponse(checkout, "An error occurred checking out the reservation ["+tpsId+"]: ");
		TestReporter.assertEquals(tpsId, checkout.getReservationNumber(), "Verify the reservation number in the response ["+checkout.getReservationNumber()+"] matches the expected value ["+tpsId+"].");
	}
	public void generateAuthorizationNumber(String environment){
		GenerateAuthorizationNumber number = new GenerateAuthorizationNumber(environment);
		sendRequestAndValidateResponse(number, "An error occurred generating an authorization number: ");
		authorizationNumber = number.getAuthorizationNumber();
	}
	public String getAuthorizationNumber(){return authorizationNumber;}
	public void massCancel(String environment, String communicationsChannel, String facilityId, String massCancelReasonId, String productChannelId, 
			String salesChannel, String serviceDate, String serviceWindowEnd, String serviceWindowStart, String sourceAccountingCenter){
		MassCancel cancel = new MassCancel(environment);
		cancel.setCommunicationsChannel(communicationsChannel);
		cancel.setFacilityId(facilityId);
		cancel.setMassCancelReasonId(massCancelReasonId);
		cancel.setProductChannelId(productChannelId);
		cancel.setSalesChannel(salesChannel);
		cancel.setServiceDate(serviceDate);
		cancel.setServiceWindowEnd(serviceWindowEnd);
		cancel.setServiceWindowStart(serviceWindowStart);
		cancel.setSourceAccountingCenter(sourceAccountingCenter);
		sendRequestAndValidateResponse(cancel, "An error occurred mass cancelling reservations: ");
	}
	public void massCancelSE(String environment, String reasonText, String tpsId){
		MassCancelSE cancel = new MassCancelSE(environment);
		cancel.setReasonText(reasonText);
		cancel.setTravelPlanSegmentId(tpsId);
		sendRequestAndValidateResponse(cancel, "An error occurred mass cancelling reservations: ");
	}
	public void optimizeInventory(String environment, String communicationsChannel, String freezeId, String inventoryGot, String inventoryWant,  
			String adultCount, String childCount, String infantCount, String partySize, String salesChannel, String tpsId){
		OptimizeInventory optimize = new OptimizeInventory(environment, "Main");
		optimize.setCommunicationChannel(communicationsChannel);
		optimize.setFreezeId(freezeId);
		optimize.setInventoryGot(inventoryGot);
		optimize.setInventoryWant(inventoryWant);
		optimize.setPartymIxAdultCount(adultCount);
		optimize.setPartyMixChildCount(childCount);
		optimize.setPartyMixInfantCount(infantCount);
		optimize.setPartySize(partySize);
		optimize.setSalesChannel(salesChannel);
		optimize.setTravelPlanSegmentId(tpsId);
		sendRequestAndValidateResponse(optimize, "An error occurred optimizing inventory for reservation ["+tpsId+"]: ");
	}
	public void retrieveAlaCarteCancelReasons(String environment){
		RetrieveAlaCarteCancelReasons retrieve = new RetrieveAlaCarteCancelReasons(environment);
		sendRequestAndValidateResponse(retrieve, "An error occurred retrieving all A LA Carte cancel reasons: ");
		alaCarteCancelReasonOptionCodes = retrieve.getAllOptionCodes();
		alaCarteCancelReasonOptionDescriptions = retrieve.getAllOptionDescriptions();
		alaCarteCancelReasonOptionIds = retrieve.getAllOptionIds();
		numberOfAlcCancelReasons = retrieve.getNumberOfAlcCancelReasons();
	}
	public Map<String, String> getAlaCarteCancelReasonOptionCodes(){return alaCarteCancelReasonOptionCodes;}
	public Map<String, String> getAlaCarteCancelReasonOptionDescriptions(){return alaCarteCancelReasonOptionDescriptions;}
	public Map<String, String> getAlaCarteCancelReasonOptionIds(){return alaCarteCancelReasonOptionIds;}
	public int getNumberOfAlcCancelReasons(){return numberOfAlcCancelReasons;}
	
	public void retrieveAll(String environment){
		RetrieveAll retrieve = new RetrieveAll(environment);
		sendRequestAndValidateResponse(retrieve, "An error occurred retrieving all: ");
		alaCarteCancelReasonOptionCodes = retrieve.getAlaCarteCancelReasonsCodes();
		alaCarteCancelReasonOptionDescriptions = retrieve.getAlaCarteCancelReasonsDescriptions();
		alaCarteCancelReasonOptionIds = retrieve.getAlaCarteCancelReasonsIds();
		retrieve.getAllergies();
		retrieve.getBookingStatusValues();
		retrieve.getCancelReasonCodes();
		retrieve.getCancelReasonDescriptions();
		retrieve.getCancelReasonIds();
		retrieve.getCelebrationsCodes();
		retrieve.getCelebrationsDescriptions();
		retrieve.getCelebrationsIds();
		retrieve.getChangeReasonCodes()
		retrieve.getChangeReasonDescriptions();
		retrieve.getCommentTypes();
		retrieve.getCommunicationChannels();
		retrieve.getGuestRequestCodes();
		retrieve.getGuestRequestDescriptions();
		retrieve.getGuestRequestIds();
		retrieve.getInventoryOverideReasonsCodes();
		retrieve.getInventoryOverideReasonsDescriptions();
		retrieve.getInventoryOverideReasonsIds();
		retrieve.getMassCancelCodes();
		retrieve.getMassCancelDescriptions();
		retrieve.getMassCancelIds();
		numberOfAlcCancelReasons = retrieve.getNumberOfAlaCarteCancelReasons();
		numberOfAllergies = retrieve.getNumberOfAllergies();
		numberOfBookingStatusValues = retrieve.getNumberOfBookingStatusValues();
		numberOfCancelReasons = retrieve.getNumberOfCancelReasons();
		numberOfCelebrations = retrieve.getNumberOfCelebrations();
		numberOfChangeReasons = retrieve.getNumberOfChangeReasons();
		numberOfCommentTypes = retrieve.getNumberOfCommentTypes();
		numberOfCommunicationChannels = retrieve.getNumberOfCommunicationChannels();
		numberOfInventoryOverideReasons = retrieve.getNumberOfInventoryOverideReasons();
		numberOfMassCancelReasons = retrieve.getNumberOfMassCancelReasons();
		numberOfReprintReasons = retrieve.getNumberOfReprintReasons();
		numberOfRoles = retrieve.getNumberOfRoles();
		numberOfSalesChannels = retrieve.getNumberOfSalesChannels();
		numberOfSpecialEvents = retrieve.getNumberOfSpecialEvents();
		numberOfSpecialNeeds = retrieve.getNumberOfSpecialNeeds();
		numberOfTaxExemptTypes = retrieve.getNumberOfTaxExemptTypes();
	}
	public void retrieveAllergies(String environment){}
	public void retrieveBookingStatusValues(String environment){}
	public void retrieveCancelReasons(String environment){}
	public void retrieveCelebrations(String environment){}
	public void retrieveChangeReasons(String environment){}
	public void retrieveCommentTypes(String environment){}
	public void retrieveCommunicationChannels(String environment){}
	public void retrieveDiningReservations(String environment){}
	public void retrieveGuestRequests(String environment){}
	public void retrieveInventoryOverideReasons(String environment){}
	public void retrieveMassCancelReasons(String environment){}
	public void retrieveReprintReasons(String environment){}
	public void retrieveReservationsForAutoCheckout(String environment){}
	public void retrieveRoles(String environment){}
	public void retrieveSalesChannels(String environment){}
	public void retrieveSpecialEvents(String environment){}
	public void retrieveSpecialNeeds(String environment){}
	public void retrieveTaxExemptTypes(String environment){}
	public void searchByAgency(String environment){}
	public void searchByGuest(String environment){}
	public void searchByVenue(String environment){}
	
	private void sendRequestAndValidateResponse(BaseSoapService bs, String errorMessage){
		bs.sendRequest();
		TestReporter.logAPI(!bs.getResponseStatusCode().equals("200"), errorMessage + bs.getFaultString(), bs);
	}
}
