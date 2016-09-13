package com.disney.utils.dataFactory.staging.bookSEReservation;

import com.disney.AutomationException;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Arrived;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.NoShow;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Retrieve;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.ValidateBooking;
import com.disney.api.soapServices.availSEModule.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.folioInterface.Folio;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

/**
 * This class contains method implementations that are defined by the ScheduledEventReservation interface.  
 * Also contained are fields and helper methods specific to event dining reservations.
 * @author Justin Phlegar
 * @author Waightstill W Avery
 *
 */
public class EventDiningReservation implements ScheduledEventReservation{
	private HouseHold party;	// Household field containing guests to be used for a reservation
	private String environment;	// Environment under test
	private String travelPlanId;	// Travel Plan ID
	private String confirmationNumber;	// Reservation number, A.K.A. Travel Plan Segment ID
	private String cancellationNumber;	// Cancellation number for a cancelled reservation or a reservation updated to 'No Show'
	private String status;	// Current reservation status
	private String arrivedStatus;	// Status from updating a reservation to 'Arrived'
	private String facilityId;	// Facility ID for the current reservation
	private String productId;	// Product ID for the current reservation
	private String productName;	// Product Name for the current reservation
	private String productType;	//Product Type for the current reservation
	private String servicePeriod;	// Service periods for the current reservation
	private String serviceStartDate;	// Service start date for the current reservation, A.K.A. the date of the reservation, not to be confused with the date that the reservation was booked
	private String bookingScenario = NOCOMPONENTSNOADDONS;	// Default booking scenario, intended to have all extraneous elements (components, add-ons, comments, etc.) removed
	private int numberOfGuests;	// Number of party role guests found in the retrieve response
	private String validateBookingStatus;	// Status from validating the booking
	private String showDiningMethodExceptionMessage = "This method is only valid for show dining reservations and is not intended for event dining reservations.";
	private String retrievedFacilityId;	// Facility ID as it is found in the #retrieve() method response
	private String primaryGuestAge;	//Primary guest address as it is found in the #retrieve() method response; expected to be contained in the first 'partyRole' node 
	private String modifyStatus;	// Status in the response from modify a reservation 
	private String sourceAccountingCenter;	// Source Accounting Center ID
	private String facilityName;	// Facility name for the current reservation
	private String freezeStartDate;
	private String reservableResourceId;
	private boolean isReservableResourceIdSet = false;  
	/*
	 * Travel Agency Fields
	 */
	private String agencyId = "0";	// Travel Agency ID
	private String agencyOdsId = "0";	// Travel Agency ODS ID
	private String guestTravelAgencyId = "0";	// Travel Agency ID associated with the guest
	private String agentId = "0";	// Travel Agent ID
	private String guestAgentId = "0";	// Travel Agent ID associated with the guest
	private String confirmationLocatorValue = "0";	// Travel Agency confirmation locator value
	private String guestConfirmationLocationId = "0";	// Travel Agency confirmation location ID
	
	/*
	 * 
	 */
	private Folio folio;
	public ScheduledEventsServices ses(){
		return new ScheduledEventsServices(environment);
	};
	/**
	 * Constructor that defines the environment under test and defines a default household for the reservation
	 * @param environment - environment under test
	 */
	public EventDiningReservation(String environment){
		this.environment = environment;
		party = new HouseHold(1);
	}
	/**
	 * Constructor that defines the environment under test and uses a predefined household to define the household for the reservation
	 * @param environment - environment under test
	 * @param party - household for the reservation
	 */
	public EventDiningReservation(String environment, HouseHold party){
		this.environment = environment;
		this.party = party;
	}
	

	public Folio folio() {
		if(folio == null) return new Folio(this);
		return folio;
	}

	public Folio folio(String environment) {
		if(folio == null) return new Folio(this, environment);
		return folio;
	}
	/**
	 * Retrieves the environment under test
	 * @return String, environment under test as a
	 */
	@Override public String getEnvironment() {return this.environment;}
	/**
	 * Retrieves the travel plan ID
	 * @return String, current travel plan ID
	 */
	@Override public String getTravelPlanId(){return this.travelPlanId;}
	/**
	 * Retrieves the confirmation number (also known as the reservation number or travel plan segment ID)
	 * @return String, current confirmation number
	 */
	@Override public String getConfirmationNumber(){return this.confirmationNumber;}

	/**
	 * Retrieves the cancellation number of a cancelled reservation, or a reservation that has been updated to 'No Show'
	 * @return String, cancellation number
	 */
	@Override public String getCancellationNumber(){return this.cancellationNumber;}
	/**
	 * Retrieves the current household
	 * @return String, current household
	 */
	@Override public HouseHold party(){return this.party;}
	/**
	 * Sets the current household
	 */
	@Override public void setParty(HouseHold party){this.party = party;}
	/**
	 * Retrieves the booking status of the current reservation
	 * @return String, booking status
	 */
	@Override public String getStatus(){return this.status;}
	/**
	 * Retrieves the status from the response from updating a reservation to 'Arrived'
	 * @return String, status from updating a reservation to 'Arrived'
	 */
	@Override public String getArrivedStatus(){return this.arrivedStatus;}
	/**
	 * Retrieves the facility ID of the current reservation
	 * @return String, facility of the current reservation
	 */
	@Override public String getFacilityId(){return this.facilityId;}
	/**
	 * Retrieves the facility ID of the current reservation
	 * @return String, facility of the current reservation
	 */
	@Override public String getFacilityName(){return this.facilityName;}
	/**
	 * Sets the facility name of the current reservation
	 * @param - facility name of the current reservation
	 */
	@Override public void setFacilityName(String name){this.facilityName = name;}
	/**
	 * Retrieves the product ID of the current reservation
	 * @return String, product ID of the current reservation
	 */
	@Override public String getProductId(){return this.productId;}
	/**
	 * Retrieves the product name of the current reservation
	 * @return String, product name of the current reservation
	 */
	@Override public String getProductName(){return this.productId;}
	/**
	 * Retrieves the product type of the current reservation
	 * @return String, product type of the current reservation
	 */
	@Override public String getProductType(){return this.productType;}
	/**
	 * Retrieves the service period ID of the current reservation
	 * @return String, service period ID of the current reservation
	 */
	@Override public String getServicePeriodId(){return this.servicePeriod;}
	/**
	 * Retrieves the service start date of the current reservation
	 * @return String, service start date of the current reservation
	 */
	@Override public String getServiceStartDate(){return this.serviceStartDate;}
	/**
	 * Sets the service start date of the current reservation
	 * @return String, service start date of the current reservation
	 */
	@Override public void setServiceStartDate(String date){this.serviceStartDate = date;}
	/**
	 * Retrieves the number of guests of the current reservation
	 * @return int, number of guests of the current reservation
	 */
	@Override public int getNumberOfGuests(){return this.numberOfGuests;}
	/**
	 * Retrieves the status from validating a booking
	 * @return String, validation status
	 */
	@Override public String getValidateBookingStatus(){return this.validateBookingStatus;}
	/**
	 * Throws an automation exception as this method is only intended for show dining reservations
	 */
	@Override public String getTableNumber(){throw new AutomationException(showDiningMethodExceptionMessage);}
	/**
	 * Throws an automation exception as this method is only intended for show dining reservations
	 */
	@Override public String getAssignTableNumberStatus(){throw new AutomationException(showDiningMethodExceptionMessage);}
	/**
	 * Throws an automation exception as this method is only intended for show dining reservations
	 */
	@Override public String getPrintTicketStatus(){throw new AutomationException(showDiningMethodExceptionMessage);}
	/**
	 * Throws an automation exception as this method is only intended for show dining reservations
	 */
	@Override public String getReprintTicketStatus(){throw new AutomationException(showDiningMethodExceptionMessage);}
	/**
	 * Set the facility ID for the current reservation
	 * @param facilityId - facility ID for the current reservation
	 */
	@Override public void setFacilityId(String facilityId){this.facilityId = facilityId;}
	/**
	 * Set the product ID for the current reservation
	 * @param productId - product ID for the current reservation
	 */
	@Override public void setProductId(String productId){this.productId = productId;}
	/**
	 * Set the product name for the current reservation
	 * @param productId - product name for the current reservation
	 */
	@Override public void setProductName(String productName){this.productName = productName;}
	/**
	 * Set the product type for the current reservation
	 * @param productType - product type for the current reservation
	 */
	@Override public void setProductType(String productType){this.productType = productType;}
	/** 
	 * Retrieves the facility ID from the #retrieve() response
	 * @return String, the facility ID from the #retrieve() response 
	 */
	@Override public String getRetrieveResponseFacilityID(){return this.retrievedFacilityId;}
	@Override public String getReservableResourceId(){return this.reservableResourceId;}	
	/**
	 * Sets the booking scenario
	 */
	@Override public void setBookingScenario(String scenario){this.bookingScenario = scenario;}
	/**
	 * Returns the primary guest age
	 */
	@Override
	public String getPrimaryGuestAge() {return this.primaryGuestAge;}
	/**
	 * Retrieve the status from the response of modifying a reservation
	 * @return String, status from modifying a reservation
	 */
	@Override
	public String getModifyResponseStatus(){return this.modifyStatus;}
	@Override public void setTravelPlanId(String tpId) {travelPlanId= tpId;}
	@Override public void setSourceAccountingCenter(String sac) {sourceAccountingCenter = sac;}
	@Override public String getSourceAccountingCenter() {return sourceAccountingCenter;}
	@Override public String getTravelAgencyId(){return agencyId;}	
	@Override public void setFreezeStartDate(String startDate){	this.freezeStartDate = startDate;}

	/**
	 * Defines the facility ID, service start date, service period, and product ID for the current 
	 * reservation and invokes a method that books the reservation
	 * @param facilityID - facility ID for the current reservation
	 * @param startDate - service start date for the current reservation
	 * @param servicePeriod - service period ID for the current reservation
	 * @param productId - product ID for the current reservation
	 */

	
	@Override
	public void book(String facilityID, String startDate, String servicePeriod, String productId) {
		this.facilityId = facilityID;
		this.serviceStartDate = startDate;
		this.servicePeriod = servicePeriod;
		this.productId = productId;
		book();
	}

	/**
	 * Defines the booking scenario.  Also defines the facility ID, service start date, service period, 
	 * and product ID for the current reservation; these values are pulled from the predefined scenario 
	 * request.  Finally, a method is invoked that books the reservation.
	 * @param eventDiningBookScenario - defines the booking scenario for the current reservation
	 */
	@Override
	public void book(String eventDiningBookScenario) {
		Book eventDiningBook = new Book(getEnvironment(), eventDiningBookScenario);
		this.bookingScenario = eventDiningBookScenario;
		this.facilityId = eventDiningBook.getRequestFacilityId();
		this.serviceStartDate = eventDiningBook.getRequestServiceStartDate();
		this.servicePeriod = eventDiningBook.getRequestServicePeriodId();
		this.productId = eventDiningBook.getRequestProductId();
		book();
	}
	
	/**
	 * This method uses predefined values to construct the booking SOAP request.  A reservable resource is 
	 * identified using the current facility ID, then the book request is sent.  Upon successful booking, a 
	 * retrieval is performed to allow information to be retrieved for validation purposes.
	 */
	private void book(){
		Book eventDiningBook = new Book(getEnvironment(), this.bookingScenario);
		eventDiningBook.setParty(party());		
		eventDiningBook.setServicePeriodId(getServicePeriodId());   //PROD.ENTRPRS_PROD_ID
		eventDiningBook.setServiceStartDateTime(getServiceStartDate());
		eventDiningBook.addDetailsByFacilityNameAndProductName(facilityName, productName);
		if(!agencyId.equals("0")){eventDiningBook.addTravelAgency(agencyId, agencyOdsId, guestTravelAgencyId, agentId, guestAgentId, confirmationLocatorValue, guestConfirmationLocationId);}	
		if(travelPlanId != null) eventDiningBook.setTravelPlanId(travelPlanId);

		TestReporter.logStep("Book an Event dining reservation");
		eventDiningBook.sendRequest();
		if(!serviceStartDate.equals(eventDiningBook.getRequestServiceStartDate())) serviceStartDate = eventDiningBook.getRequestServiceStartDate();
		if(eventDiningBook.getResponse().contains("Row was updated or deleted by another transaction")|| 
				eventDiningBook.getResponse().contains("Error Invoking  Folio Management Service  :   existingRootChargeBookEvent :Unexpected Error occurred : createChargeGroupsAndPostCharges : ORA-00001: unique constraint (FOLIO.CHRG_GRP_GST_PK) violated")||
				eventDiningBook.getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint ") || 
				eventDiningBook.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
			Sleeper.sleep(Randomness.randomNumberBetween(3, 10) * 1000);
			eventDiningBook.setFreezeId();
			eventDiningBook.sendRequest();
		}
		TestReporter.logAPI(!eventDiningBook.getResponseStatusCode().equals("200"), "An error occurred booking an event dining service reservation: " + eventDiningBook.getFaultString(), eventDiningBook);
		this.travelPlanId = eventDiningBook.getTravelPlanId();
		this.confirmationNumber = eventDiningBook.getTravelPlanSegmentId();
		this.sourceAccountingCenter = eventDiningBook.getSourceAccountingCenter();
		this.reservableResourceId = eventDiningBook.getRequestReservableResourceId();
		TestReporter.log("Travel Plan ID: " + getTravelPlanId());
		TestReporter.log("Reservation Number: " + getConfirmationNumber());
		retrieve();
	}

	/**
	 * Cancels the current reservation and performs a retrieval to allow information to be retrieved for validation purposes.
	 */
	@Override
	public void cancel() {
		TestReporter.logStep("Cancel an event dining reservation.");
		Cancel cancel = new Cancel(getEnvironment(), "CancelDiningEvent");
		cancel.setReservationNumber(getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling an event dining service reservation", cancel);
		this.cancellationNumber = cancel.getCancellationConfirmationNumber();
		this.reservableResourceId = null;
		retrieve();
	}

	/**
	 * Updates the reservation to 'Arrived' and performs a retrieval to allow information to be retrieved for validation purposes.
	 */
	@Override
	public void arrived() {
		TestReporter.logStep("Update an event dining reservation to [Arrived].");
		Arrived arrived = new Arrived(getEnvironment(), "Main");
		arrived.setReservationNumber(getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred updating an event dining service reservation to [Arrived]", arrived);
		this.arrivedStatus = arrived.getArrivalStatus();
		retrieve();
	}

	/**
	 * Updates the reservation to 'No Show', captures the cancellation number, and performs a retrieval to allow information to be retrieved for validation purposes
	 */
	@Override
	public void noShow() {
		TestReporter.logStep("Update an event dining reservation to [No Show].");
		NoShow noShow = new NoShow(getEnvironment(), "Main");
		noShow.setReservationNumber(getConfirmationNumber());
		noShow.sendRequest();
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred updating an event dining service reservation to [No Show]", noShow);
		this.cancellationNumber = noShow.getCancellationNumber();
		this.reservableResourceId =null;
		retrieve();
	}
	
	/**
	 * Performs a retrieval to allow information to be retrieved for validation purposes.
	 */
	@Override
	public void retrieve(){		
		TestReporter.logStep("Retrieve an Event dining reservation for Reservation ["+getConfirmationNumber()+"]");
		Retrieve retrieve = new Retrieve(getEnvironment(), "RetrieveDiningEvent");
		retrieve.setReservationNumber(getConfirmationNumber());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving an event dining service reservation", retrieve);
		this.status = retrieve.getStatus();
		try{
			numberOfGuests = retrieve.getNumberOfGuests();
		}catch(XPathNotFoundException e){}
		retrievedFacilityId = retrieve.getResponseFacilityId();
		primaryGuestAge = retrieve.getPrimaryGuestAge();
		party().primaryGuest().setPartyId(retrieve.getPartyId());
		party().primaryGuest().setGuestId(retrieve.getGuestId());
	}

	/**
	 * Uses predefined values to invoke a method that defines values for a travel agency that is intended to be added to the current reservation
	 */
	@Override public void addTravelAgency(){
		agencyId = "99999998";
		addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");
	}
	/**
	 * Uses user-defined values to invoke a method that defines values for a travel agency that is intended to be added to the current reservation
	 */
	@Override public void addTravelAgency(String agencyId){addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");}	
	/**
	 * Defines values for a travel agency that is intended to be added to the current reservation
	 */
	@Override
	public void addTravelAgency(String agencyIataNumber, String agencyOdsId, String guestTravelAgencyId, String agentId, String guestAgentId, String confirmationLocatorValue, String guestConfirmationLocationId){
		this.agencyId =  agencyIataNumber;
		this.agencyOdsId =  agencyOdsId;
		this.guestTravelAgencyId =  guestTravelAgencyId;
		this.agentId =  agentId;
		this.guestAgentId =  guestAgentId;
		this.confirmationLocatorValue =  confirmationLocatorValue;
		this.guestConfirmationLocationId =  guestConfirmationLocationId;	
	}
	
	/**
	 * Generates a book request for the current scenario and uses that information to validate the book scenario data
	 */
	@Override
	public void validateBooking(){
		TestReporter.logStep("Validate an Event Dining Booking");
		Book eventDiningBook = new Book(getEnvironment(), this.bookingScenario);
		ValidateBooking validate = new ValidateBooking(getEnvironment(), "Main");
		validate.setFacilityId(eventDiningBook.getRequestFacilityId());
		validate.setProductId(eventDiningBook.getRequestProductId());		
		validate.setServiceStartDate(eventDiningBook.getRequestServiceStartDate());
		validate.setServicePeriodId(eventDiningBook.getRequestServicePeriodId());
		validate.sendRequest();
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating an event dining service reservation", validate);
		validateBookingStatus = validate.getStopReservation();
	}
	/**
	 * Defines the current book scenario, then invokes a method that validates the book scenario data
	 */
	@Override
	public void validateBooking(String scenario){
		this.bookingScenario = scenario;
		validateBooking();
	}

	// Dummy show dining methods, required due to implementing the scheduled events interface
	@Override public void assignTableNumbers() {throw new AutomationException(showDiningMethodExceptionMessage);}
	@Override public void assignTableNumbers(String tableNumber) {throw new AutomationException(showDiningMethodExceptionMessage);}
	@Override public void printTicket() {throw new AutomationException(showDiningMethodExceptionMessage);}
	@Override public void reprintTicket() {throw new AutomationException(showDiningMethodExceptionMessage);}
	/**
	 * Defines the Modify sub-class that is to be used to contain implementations of methods defined in a generic Modify interface.
	 */
	@Override public Modify modify(){return new ModifyEventDining();}	
	
	//******************************************************************************************************************
	//******************************************************************************************************************
	//******************************************************************************************************************
	//***********************************				Modify Sub-Class			************************************
	//******************************************************************************************************************
	//******************************************************************************************************************
	//******************************************************************************************************************
	
	/**
	 * This class contains method implementations that are defined by the Modify interface.  
	 * Also contained are fields and helper methods specific to modifying event dining reservations.
	 * @author Waightstill W Avery
	 *
	 */
	private class ModifyEventDining implements Modify{
		private String modifyScenario = NOCOMPONENTSNOADDONS;	// Default booking scenario, intended to have all extraneous elements (components, add-ons, comments, etc.) removed
		
		/**
		 * This method uses predefined values to construct the modify SOAP request.  A reservable resource is 
		 * identified using the current facility ID, then the modify request is sent.  Upon successful modification, a retrieval 
		 * is performed to allow information to be retrieved for validation purposes.
		 */
		private void modify(){
			com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify modify = new com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify(getEnvironment(), modifyScenario);
			modify.setReservationNumber(getConfirmationNumber());
			modify.setTravelPlanId(getTravelPlanId());

			modify.addDetailsByFacilityNameAndProductName(facilityName, productName);
			modify.setReservableResourceId(reservableResourceId);
			modify.sendRequest();
			if(modify.getResponse().contains("Row was updated or deleted by another transaction")){
				Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
				modify.sendRequest();
			}
			TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred modifying an event dining service reservation: " + modify.getFaultString(), modify);
			retrieve();
		}
		
		/**
		 * Defines the service start date, and invokes a method to modify the current reservation
		 * @param serviceStartDate - service start date for the current reservation
		 */
		@Override
		public void modifyServiceStartDate(String serviceStartDate) {
			EventDiningReservation.this.serviceStartDate = serviceStartDate;
			TestReporter.logStep("Modify Event Dining Reservation Date to ["+getServiceStartDate()+"].");
			modify();
		}		
		/**
		 * Defines the service start date and scenario, and invokes a method to modify the current reservation
		 * @param serviceStartDate - service start date for the current reservation
		 */
		@Override
		public void modifyServiceStartDate(String serviceStartDate, String scenario) {
			EventDiningReservation.this.serviceStartDate = serviceStartDate;
			this.modifyScenario = scenario;
			TestReporter.logStep("Modify Event Dining Reservation Date to ["+getServiceStartDate()+"].");
			modify();
		}
		/**
		 * Defines the service start date and service period, and invokes a method to modify the current reservation
		 * @param serviceStartDate - service start date for the current reservation
		 * @param servicePeriod - service period for the current reservation
		 */
		@Override
		public void modifyServiceStartDateAndServicePeriod(String serviceStartDate, String servicePeriod) {
			EventDiningReservation.this.servicePeriod = servicePeriod;
			TestReporter.logStep("Modify Event Dining Reservation Service Period to ["+getServicePeriodId()+"].");
			modifyServiceStartDate(serviceStartDate);
		}	
		/**
		 * Defines the service start date and service period, and invokes a method to modify the current reservation
		 * @param serviceStartDate - service start date for the current reservation
		 * @param servicePeriod - service period for the current reservation
		 * @param scenario - modification scenario
		 */
		@Override 
		public void modifyServiceStartDateAndServicePeriod(String serviceStartDate, String servicePeriod, String scenario){
			EventDiningReservation.this.servicePeriod = servicePeriod;
			this.modifyScenario = scenario;
			TestReporter.logStep("Modify Event Dining Reservation Service Period to ["+getServicePeriodId()+"].");
			modifyServiceStartDate(serviceStartDate);
		}	
		/**
		 * Defines the facility ID, and invokes a method to modify the current reservation
		 * @param facilityId - facility ID for the current reservation
		 */
		@Override
		public void modifyFacility(String facilityId) {throw new AutomationException("For Event Dining Service, the facility ID cannot be modified without a product ID.  Try the modify method [[[[modifyFacility(String facilityId, String productId)]]]]");}		
		/**
		 * Defines the facility ID and product ID, and invokes a method to modify the current reservation
		 * @param facilityId - facility ID for the current reservation
		 * @param productId - product ID for the current reservation
		 */
		@Override
		public void modifyFacility(String facilityId, String productId) {
			TestReporter.logStep("Modify Event Dining Reservation Facility ID ["+facilityId+"] and Product ID ["+productId+"].");
			cancel();
			EventDiningReservation.this.facilityId = facilityId;
			EventDiningReservation.this.productId = productId;
			book();
		}		
		/**
		 * Defines the household, and invokes a method to modify the current reservation
		 * @param party - user-defined household to be used for the current reservation
		 */
		@Override
		public void modifyPartyMix(HouseHold party) {
			TestReporter.logStep("Modify Event Dining Rservation Party Mix");
			EventDiningReservation.this.party = party;	
			modify();
		}	
		/**
		 * Defines the household and modify scenario, and invokes a method to modify the current reservation
		 * @param party - user-defined household to be used for the current reservation
		 * @param scenario - defines the modify scenario for the current reservation
		 */
		@Override
		public void modifyPartyMix(HouseHold party, String scenario) {
			TestReporter.logStep("Modify Event Dining Rservation Party Mix");
			this.modifyScenario = scenario;
			EventDiningReservation.this.party = party;	
			modify();
		}
		/**
		 * Defines the modify scenario.  Also defines the facility ID, service start date, service period, 
		 * and product ID for the current reservation; these values are pulled from the predefined scenario 
		 * request.  Finally, a method is invoked that modifies the reservation.
		 * @param scenario - defines the modify scenario for the current reservation
		 */
		@Override
		public void modifyScenario(String scenario) {
			TestReporter.logStep("Modify Event Dining Reservation Scenario From ["+this.modifyScenario+"] to ["+scenario+"].");
			this.modifyScenario = scenario;
			com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify modify = new com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify(getEnvironment(), modifyScenario);
			EventDiningReservation.this.serviceStartDate = modify.getRequestServiceStartDate();
			EventDiningReservation.this.servicePeriod = modify.getRequestServicePeriodId();
			EventDiningReservation.this.facilityId = modify.getRequestFacilityId();
			EventDiningReservation.this.productId = modify.getRequestProductId();
			modify();
		}
		/**
		 * Sets the modification scenario
		 * @param scenario - scenario to use for the modification
		 */
		@Override public void setModifyScenario(String scenario){this.modifyScenario = scenario;}
	}
}
