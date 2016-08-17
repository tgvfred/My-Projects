package com.disney.utils.dataFactory.staging.bookSEReservation;

import com.disney.AutomationException;
import com.disney.api.soapServices.availSEModule.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Arrived;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Cancel;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.NoShow;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Retrieve;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.ValidateBooking;
import com.disney.utils.Randomness;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

/**
 * This class contains method implementations that are defined by the ScheduledEventReservation interface.  
 * Also contained are fields and helper methods specific to table service dining reservations.
 * @author Justin Phlegar
 * @author Waightstill W Avery
 *
 */
public class TableServiceDiningReservation implements ScheduledEventReservation {
	private HouseHold party;	// Household field containing guests to be used for a reservation
	private String environment;	// Environment under test
	private String travelPlanId;	// Travel Plan ID
	private String confirmationNumber;	// Reservation number, A.K.A. Travel Plan Segment ID
	private String cancellationNumber;	// Cancellation number for a cancelled reservation or a reservation updated to 'No Show'
	private String status;	// Current reservation status
	private String arrivedStatus;	// Status from updating a reservation to 'Arrived'
	private String facilityId;	// Facility ID for the current reservation
	private String servicePeriod;	// Service periods for the current reservation
	private String serviceStartDate;	// Service start date for the current reservation, A.K.A. the date of the reservation, not to be confused with the date that the reservation was booked
	private String bookingScenario = NOCOMPONENTSNOADDONS;	// Default booking scenario, intended to have all extraneous elements (components, add-ons, comments, etc.) removed
	private int numberOfGuests;	// Number of party role guests found in the retrieve response
	private String validateBookingStatus;	// Status from validating the booking
	private String productIdExceptionMessage = "The Table Service Dining Service does not expect product IDs in the SOAP requests.  Any attempt to manipulate or interact with product IDs for this service will result in an AutomationException.";
	private String showDiningMethodExceptionMessage = "This method is only valid for show dining reservations and is not intended for event dining reservations.";
	private String retrievedFacilityId;	// Facility ID as it is found in the #retrieve() method response
	private String primaryGuestAge;	//Primary guest address as it is found in the #retrieve() method response; expected to be contained in the first 'partyRole' node
	private String modifyStatus;	// Status in the response from modify a reservation
	private String sourceAccountingCenter;	// Source Accounting Center ID  
	private String facilityName;	// Facility name for the current reservation
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
	public ScheduledEventsServices ses(){
		return new ScheduledEventsServices(environment);
	};
	
	/**
	 * Constructor that defines the environment under test and defines a default household for the reservation
	 * @param environment - environment under test
	 */
	public TableServiceDiningReservation(String environment){
		this.environment = environment;
		party = new HouseHold(1);
	}
	/**
	 * Constructor that defines the environment under test and uses a predefined household to define the household for the reservation
	 * @param environment - environment under test
	 * @param party - household for the reservation
	 */
	public TableServiceDiningReservation(String environment, HouseHold party){
		this.environment = environment;
		this.party = party;
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
	@Override public String getProductId(){throw new AutomationException(productIdExceptionMessage);}
	/**
	 * Retrieves the product type of the current reservation
	 * @return String, product type of the current reservation
	 */
	@Override public String getProductType(){throw new AutomationException(productIdExceptionMessage);}
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
	 * Throws an automation exception as Table Service reservations do not expect product types
	 */
	@Override public void setFacilityId(String facilityId) {throw new AutomationException(productIdExceptionMessage);}
	/**
	 * Throws an automation exception as Table Service reservations do not expect product types
	 */
	@Override public void setProductId(String productId) {throw new AutomationException(productIdExceptionMessage);}
	/**
	 * Throws an automation exception as Table Service reservations do not expect product types
	 */
	@Override public void setProductType(String productType) {throw new AutomationException(productIdExceptionMessage);}
	/** 
	 * Retrieves the facility ID from the #retrieve() response
	 * @return String, the facility ID from the #retrieve() response 
	 */
	@Override public String getRetrieveResponseFacilityID(){return this.retrievedFacilityId;}
	/**
	 * Sets the booking scenario
	 */
	@Override public void setBookingScenario(String scenario){this.bookingScenario = scenario;}
	/**
	 * Returns the primary guest age
	 */
	@Override public String getPrimaryGuestAge() {return this.primaryGuestAge;}
	@Override public void setSourceAccountingCenter(String sac) {sourceAccountingCenter = sac;}
	@Override public String getSourceAccountingCenter() {return sourceAccountingCenter;}
	@Override public String getTravelAgencyId(){return agencyId;}
	/**
	 * Retrieve the status from the response of modifying a reservation
	 * @return String, status from modifying a reservation
	 */
	public String getModifyResponseStatus(){return this.modifyStatus;}
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
		Book book = new Book(getEnvironment(), eventDiningBookScenario);
		this.bookingScenario = eventDiningBookScenario;
		this.facilityId = book.getRequestFacilityId();
		this.serviceStartDate = book.getRequestServiceStartDate();
		this.servicePeriod = book.getRequestServicePeriodId();
		book();
	}
	
	/**
	 * This method uses predefined values to construct the booking SOAP request.  A reservable resource is 
	 * identified using the current facility ID, then the book request is sent.  Upon successful booking, a 
	 * retrieval is performed to allow information to be retrieved for validation purposes.
	 */
	private void book(){
		TestReporter.logStep("Book an table service dining reservation.");
		Book book = new Book(getEnvironment(), this.bookingScenario);
		book.setParty(party());		
		book.setFacilityId(getFacilityId());		//FAC.FAC_ID
		book.setServicePeriosId(getServicePeriodId());   //PROD.ENTRPRS_PROD_ID
		book.setServiceStartDateTime(getServiceStartDate());
		if(facilityName != null)
			if(!facilityName.isEmpty()) book.setFacilityName(facilityName);
		if(!agencyId.equals("0")){book.addTravelAgency(agencyId, agencyOdsId, guestTravelAgencyId, agentId, guestAgentId, confirmationLocatorValue, guestConfirmationLocationId);}	

		if(!environment.equalsIgnoreCase("Development") && !environment.equalsIgnoreCase("Latest_CM")){
			ReservableResourceByFacilityID resource = new ReservableResourceByFacilityID(getEnvironment(), "Main");
			resource.setFacilityId(getFacilityId());
			resource.sendRequest();
			resource.getReservableResources();
			book.setReservableResourceId(resource.getFirstReservableResourceId());			
		}

		Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
		book.sendRequest();
		if(book.getResponse().contains("Row was updated or deleted by another transaction") || 
				book.getResponse().contains("Error Invoking  Folio Management Service  :   existingRootChargeBookEvent :Unexpected Error occurred : createChargeGroupsAndPostCharges : ORA-00001: unique constraint (FOLIO.CHRG_GRP_GST_PK) violated")||
				book.getResponse().contains("Inconsitent Data : Booking Date Not Found for CampusId")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
			book.sendRequest();
		}
		TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred booking an table service dining service reservation: " + book.getFaultString(), book);
		this.travelPlanId = book.getTravelPlanId();
		this.confirmationNumber = book.getTravelPlanSegmentId();
		TestReporter.log("Travel Plan ID: " + getTravelPlanId());
		TestReporter.log("Reservation Number: " + getConfirmationNumber());
		retrieve();
	}

	/**
	 * Cancels the current reservation and performs a retrieval to allow information to be retrieved for validation purposes.
	 */
	@Override
	public void cancel() {
		TestReporter.logStep("Cancel an table service dining reservation.");
		Cancel cancel = new Cancel(getEnvironment(), "Main");
		cancel.setReservationNumber(getConfirmationNumber());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling an table service dining service reservation: " + cancel.getFaultString(), cancel);
		this.cancellationNumber = cancel.getCancellationConfirmationNumber();
		retrieve();
	}

	/**
	 * Updates the reservation to 'Arrived' and performs a retrieval to allow information to be retrieved for validation purposes.
	 */
	@Override
	public void arrived() {
		TestReporter.logStep("Update an table service dining reservation to [Arrived].");
		Arrived arrived = new Arrived(getEnvironment(), "Main");
		arrived.setReservationNumber(getConfirmationNumber());
		arrived.sendRequest();
		TestReporter.logAPI(!arrived.getResponseStatusCode().equals("200"), "An error occurred updating an table service dining service reservation to [Arrived]: " + arrived.getFaultString(), arrived);
		this.arrivedStatus = arrived.getArrivalStatus();
		retrieve();
	}

	/**
	 * Updates the reservation to 'No Show', captures the cancellation number, and performs a retrieval to allow information to be retrieved for validation purposes
	 */
	@Override
	public void noShow() {
		TestReporter.logStep("Update an table service dining reservation to [No Show].");
		NoShow noShow = new NoShow(getEnvironment(), "Main");
		noShow.setReservationNumber(getConfirmationNumber());
		noShow.sendRequest();
		TestReporter.logAPI(!noShow.getResponseStatusCode().equals("200"), "An error occurred updating an table service dining service reservation to [No Show]: " + noShow.getFaultString(), noShow);
		this.cancellationNumber = noShow.getCancellationNumber();
		retrieve();
	}
	
	/**
	 * Performs a retrieval to allow information to be retrieved for validation purposes.
	 */
	@Override
	public void retrieve(){		
		TestReporter.logStep("Retrieve an table service dining reservation.");
		Retrieve retrieve = new Retrieve(getEnvironment(), "Main");
		retrieve.setReservationNumber(getConfirmationNumber());
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving an table service dining service reservation", retrieve);
		this.status = retrieve.getStatus();
		numberOfGuests = retrieve.getNumberOfGuests();
		retrievedFacilityId = retrieve.getResponseFacilityId();
		primaryGuestAge = retrieve.getPrimaryGuestAge();
	}

	/**
	 * Uses predefined values to invoke a method that defines values for a travel agency that is intended to be added to the current reservation
	 */
	@Override public void addTravelAgency(){addTravelAgency("99999998", "0", "0", "0", "0", "0", "0");}
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
		Book book = new Book(getEnvironment(), this.bookingScenario);
		ValidateBooking validate = new ValidateBooking(getEnvironment(), "Main");
		validate.setFacilityId(book.getRequestFacilityId());
		validate.setProductId(BaseSoapCommands.REMOVE_NODE.toString());		
		validate.setServiceStartDate(book.getRequestServiceStartDate());
		validate.setServicePeriodId(book.getRequestServicePeriodId());
		validate.sendRequest();
		TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating an table service dining service reservation", validate);
		validateBookingStatus = validate.getStopReservation();
	}

	// Dummy show dining methods, required due to implementing the scheduled events interface
	@Override public void assignTableNumbers() {throw new AutomationException(showDiningMethodExceptionMessage);}
	@Override public void assignTableNumbers(String tableNumber) {throw new AutomationException(showDiningMethodExceptionMessage);}
	@Override public void printTicket() {throw new AutomationException(showDiningMethodExceptionMessage);}
	@Override public void reprintTicket() {throw new AutomationException(showDiningMethodExceptionMessage);}
	/**
	 * Defines the current book scenario, then invokes a method that validates the book scenario data
	 */
	@Override
	public void validateBooking(String scenario){
		this.bookingScenario = scenario;
		validateBooking();
	}
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
	 * Also contained are fields and helper methods specific to modifying table service dining reservations.
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
			com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Modify modify = new com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Modify(getEnvironment(), modifyScenario);
			modify.setReservationNumber(getConfirmationNumber());
			modify.setTravelPlanId(getTravelPlanId());

			ReservableResourceByFacilityID resource = new ReservableResourceByFacilityID(getEnvironment(), "Main");
			resource.setFacilityId(getFacilityId());
			resource.sendRequest();
			resource.getReservableResources();
			
			modify.setReservableResourceId(resource.getFirstReservableResourceId());
			modify.setParty(party());
			modify.setFacilityId(getFacilityId());
			modify.setServiceStartDate(getServiceStartDate());
			modify.setServicePeriodId(getServicePeriodId());
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
			modify.sendRequest();
			if(modify.getResponse().contains("Row was updated or deleted by another transaction")){
				Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
				modify.sendRequest();
			}
			TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred modifying an table service dining service reservation", modify);
			retrieve();
		}
		
		/**
		 * Defines the service start date, and invokes a method to modify the current reservation
		 * @param serviceStartDate - service start date for the current reservation
		 */
		@Override 
		public void modifyServiceStartDate(String serviceStartDate) {
			TableServiceDiningReservation.this.serviceStartDate = serviceStartDate;
			TestReporter.logStep("Modify Event Dining Reservation Date to ["+getServiceStartDate()+"].");
			modify();
		}		
		/**
		 * Defines the service start date and scenario, and invokes a method to modify the current reservation
		 * @param serviceStartDate - service start date for the current reservation
		 */
		@Override 
		public void modifyServiceStartDate(String serviceStartDate, String scenario) {
			TableServiceDiningReservation.this.serviceStartDate = serviceStartDate;
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
			TableServiceDiningReservation.this.servicePeriod = servicePeriod;
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
			TableServiceDiningReservation.this.servicePeriod = servicePeriod;
			this.modifyScenario = scenario;
			TestReporter.logStep("Modify Event Dining Reservation Service Period to ["+getServicePeriodId()+"].");
			modifyServiceStartDate(serviceStartDate);
		}		
		/**
		 * Defines the facility ID and product ID, and invokes a method to modify the current reservation
		 * @param facilityId - facility ID for the current reservation
		 * @param productId - product ID for the current reservation
		 */
		@Override
		public void modifyFacility(String facilityId) {
			TestReporter.logStep("Modify Event Dining Reservation Facility ID ["+facilityId+"].");
			cancel();
			TableServiceDiningReservation.this.facilityId = facilityId;
			book();
		}		
		/**
		 * Defines the facility ID and product ID, and invokes a method to modify the current reservation
		 * @param facilityId - facility ID for the current reservation
		 * @param productId - product ID for the current reservation
		 */
		@Override
		public void modifyFacility(String facilityId, String productId) {throw new AutomationException(productIdExceptionMessage + "Try the modify method [[[[modifyFacility(String facilityId)]]]]");}		
		/**
		 * Defines the household, and invokes a method to modify the current reservation
		 * @param party - user-defined household to be used for the current reservation
		 */
		@Override
		public void modifyPartyMix(HouseHold party) {
			TestReporter.logStep("Modify Event Dining Rservation Party Mix");
			TableServiceDiningReservation.this.party = party;	
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
			TableServiceDiningReservation.this.party = party;	
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
			com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Modify modify = new com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations.Modify(getEnvironment(), modifyScenario);
			TableServiceDiningReservation.this.serviceStartDate = modify.getRequestServiceStartDate();
			TableServiceDiningReservation.this.servicePeriod = modify.getRequestServicePeriodId();
			TableServiceDiningReservation.this.facilityId = modify.getRequestFacilityId();
			modify();
		}
		/**
		 * Sets the modification scenario
		 * @param scenario - scenario to use for the modification
		 */
		@Override public void setModifyScenario(String scenario){this.modifyScenario = scenario;}
	}
}
