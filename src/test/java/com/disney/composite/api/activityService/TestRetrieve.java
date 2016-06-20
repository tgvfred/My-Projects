package com.disney.composite.api.activityService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.activityServicePort.operations.Book;
import com.disney.api.soapServices.activityServicePort.operations.Cancel;
import com.disney.api.soapServices.activityServicePort.operations.Retrieve;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.RetrieveComponentProductIdByFacilityId;
import com.disney.utils.RetrieveProductIdByFacilityId;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieve {
	// *******************
	// Test Class Fields
	// *******************
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	protected ThreadLocal<String> Scenario = new ThreadLocal<String>();
	protected ThreadLocal<String> SignInLocation = new ThreadLocal<String>();
	protected ThreadLocal<String> ComponentType = new ThreadLocal<String>();
	protected ThreadLocal<String> ComponentId = new ThreadLocal<String>();
	protected ThreadLocal<String> RevenueClassificationId = new ThreadLocal<String>();
	protected ThreadLocal<String> RevenueClassificationName = new ThreadLocal<String>();
	protected ThreadLocal<String> FacilityId = new ThreadLocal<String>();
	protected ThreadLocal<String> FacilityName = new ThreadLocal<String>();
	protected ThreadLocal<String> PaymentSettlementScenario = new ThreadLocal<String>();
	protected ThreadLocal<String> SourceAccountingCenter = new ThreadLocal<String>();
	protected ThreadLocal<String> BookScenario = new ThreadLocal<String>();
	protected ThreadLocal<String> DaysOut = new ThreadLocal<String>();
	protected ThreadLocal<String> ProductId = new ThreadLocal<String>();
	protected ThreadLocal<String> ProductType = new ThreadLocal<String>();
	protected ThreadLocal<String> ServicePeriodId = new ThreadLocal<String>();
	protected ThreadLocal<String> TravelPlan = new ThreadLocal<String>();
	protected ThreadLocal<String> SalesChannel = new ThreadLocal<String>();
	protected ThreadLocal<String> CommunicationsChannel = new ThreadLocal<String>();
	protected ThreadLocal<String[]> arrTravelPlan = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrSignInLocation = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrComponentType = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrComponentId = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrRevenueClassificationId = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrRevenueClassificationName = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrFacilityId = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrFacilityName = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrProductId = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrProductType = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrServicePeriodId = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrSourceAccountingCenter = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrSalesChannel = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrCommunicationsChannel = new ThreadLocal<String[]>();
	protected ThreadLocal<String[]> arrBookScenario = new ThreadLocal<String[]>();
	protected ThreadLocal<RetrieveProductIdByFacilityId> prodId = new ThreadLocal<RetrieveProductIdByFacilityId>();
	protected ThreadLocal<RetrieveComponentProductIdByFacilityId> compProdId = new ThreadLocal<RetrieveComponentProductIdByFacilityId>();
	/*
	 * Reservation fields
	 */
	protected ThreadLocal<String> TP_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	/*
	 * Activity Event objects
	 */
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<Retrieve> retrieve = new ThreadLocal<Retrieve>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>();
	/*
	 * Household and Address objects
	 */
	protected ThreadLocal<HouseHold> household = new ThreadLocal<HouseHold>();
	protected ThreadLocal<Address> address = new ThreadLocal<Address>();

	@DataProvider(name = "dataProvider", parallel=true)
	public Object[][] scenarios() {
		application.set("alc");
		datatable.set(new Datatable());
		try {
			// Defining what table to run from the virtual tables
			return datatable.get().getTestScenariosByApp(application.get(),	"CreateViewCARTermIDValid_API");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		if (TPS_ID.get() != null)
			if (!TPS_ID.get().isEmpty())
				cancelActivityEvent(false);
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		application.set("alc");
		this.environment.set(environment);
		datatable.set(new Datatable());
		datatable.get().setVirtualtablePath(Datatable.ALC_MASTER_DATA_PATH);
	}

	/**
	 * This test is designed to book a children's reservation, cancel the
	 * reservation and book a recreation reservation using the same travel plan.
	 * This is designed to replicate the rebook workflow in the UI.
	 * 
	 * @param Scenario- test scenario name
	 * @param Role- role used in the booking
	 * @param TravelPlan- indicator that triggers the reuse of an existing travel plan
	 * @param SignInLocation- location used for the booking
	 * @param ComponentType- event component type
	 * @param ComponentId- event component id
	 * @param RevenueClassificationId- event revenue classification id
	 * @param RevenueClassificationName- even revenue classification name
	 * @param FacilityId- event facility id
	 * @param FacilityName- event facility name
	 * @param ProductId- evennt product id
	 * @param ProductType- event product type
	 * @param ServicePeriodId- event service period
	 * @param PaymentSettlementScenario- payment scenario
	 * @param RunTest- flag to determine if an iteration is to be executed
	 * @param SourceAccountingCenter- source accounting center for the booking
	 * @param BookScenario- booking scenario used in the datatable
	 * @param DaysOut- days out used to determine the booking date
	 * @param SalesChannel- event sales channel
	 * @param CommunicationsChannel- event communications channel
	 */
	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "activity", "activityService"})
	public void testRetrieve(String Scenario, String Role,
			String TravelPlan, String SignInLocation, String ComponentType,
			String ComponentId, String RevenueClassificationId,
			String RevenueClassificationName, String FacilityId,
			String FacilityName, String ProductId, String ProductType,
			String ServicePeriodId, String PaymentSettlementScenario,
			String RunTest, String SourceAccountingCenter, String BookScenario,
			String DaysOut, String SalesChannel, String CommunicationsChannel) {
		if (RunTest.equalsIgnoreCase("true")) {
			defineGlobalVariables(Scenario, Role, TravelPlan, SignInLocation,
					ComponentType, ComponentId, RevenueClassificationId,
					RevenueClassificationName, FacilityId, FacilityName,
					ProductId, ProductType, ServicePeriodId,
					PaymentSettlementScenario, RunTest, SourceAccountingCenter,
					BookScenario, DaysOut, SalesChannel, CommunicationsChannel);

			// Log the test scenario name in the reporter
			TestReporter.logScenario(this.Scenario.get());

			// Grab the @Test method name and use it to define the test name
			testName.set(new Object() {}.getClass().getEnclosingMethod().getName());

			// Loop over the data, first booking a childrne's event, then
			// booking a recreation event
			for (int bookings = 0; bookings < arrTravelPlan.get().length; bookings++) {
				// Define the booking time for the operations
				bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(this.DaysOut.get())));

				TestReporter.logStep("Book " + determineBookingType(bookings)+ " Activity");
				/*
				 * book the reservation
				 */
				bookActivity(bookings);
			}
		}
	}

	/**
	 * This method is used to determine the type of booking based on the stage
	 * of the iteration
	 * 
	 * @param iteration - determines the stage of the iteration
	 * @return - booking type
	 */
	private String determineBookingType(int iteration) {
		String bookingType = "";
		switch (iteration) {
			case 0:
				bookingType = "Children's";
				break;
			case 1:
				bookingType = "Recreation";
				break;
			default:
				break;
		}

		return bookingType;
	}

	/**
	 * This method cancels the activity reservation
	 * 
	 * @param testForFailure - boolean, determines if a failure should be thrown if the cancellation fails
	 */
	private void cancelActivityEvent(boolean testForFailure) {
		TestReporter.logStep("Cancel Activity Reservation");
		cancel.set(new Cancel(environment.get(), "CancelDiningEvent"));
		cancel.get().setReservationNumber(TPS_ID.get());
		cancel.get().sendRequest();
		if (testForFailure) {
			TestReporter.logAPI(!cancel.get().getResponseStatusCode().equals("200"), "A error occurred during cancellation.", cancel.get());
			TestReporter.log("Activity Event Cancellation Confirmation Number: "+ cancel.get().getCancellationConfirmationNumber());
		}
	}

	/**
	 * This method takes the test parameters and assigns them to global
	 * variables for use by other methods. Further, it splits variables that are
	 * delimited by a semicolon so that the data can be iterated over in the
	 * test.
	 * 
	 * @param Scenario - test scenario name
	 * @param Role - role used in the booking
	 * @param TravelPlan - indicator that triggers the reuse of an existing travel plan
	 * @param SignInLocation - location used for the booking
	 * @param ComponentType - event component type
	 * @param ComponentId - event component id
	 * @param RevenueClassificationId - event revenue classification id
	 * @param RevenueClassificationName - even revenue classification name
	 * @param FacilityId - event facility id
	 * @param FacilityName - event facility name
	 * @param ProductId - evennt product id
	 * @param ProductType - event product type
	 * @param ServicePeriodId - event service period
	 * @param PaymentSettlementScenario - payment scenario
	 * @param RunTest - flag to determine if an iteration is to be executed
	 * @param SourceAccountingCenter - source accounting center for the booking
	 * @param BookScenario - booking scenario used in the datatable
	 * @param DaysOut - days out used to determine the booking date
	 * @param SalesChannel - event sales channel
	 * @param CommunicationsChannel - event communications channel
	 */
	private void defineGlobalVariables(String Scenario, String Role,
			String TravelPlan, String SignInLocation, String ComponentType,
			String ComponentId, String RevenueClassificationId,
			String RevenueClassificationName, String FacilityId,
			String FacilityName, String ProductId, String ProductType,
			String ServicePeriodId, String PaymentSettlementScenario,
			String RunTest, String SourceAccountingCenter, String BookScenario,
			String DaysOut, String SalesChannel, String CommunicationsChannel) {
		this.Scenario.set(Scenario);
		this.SignInLocation.set(SignInLocation);
		this.TravelPlan.set(TravelPlan);
		this.ComponentType.set(ComponentType);
		this.ComponentId.set(ComponentId);
		this.RevenueClassificationId.set(RevenueClassificationId);
		this.RevenueClassificationName.set(RevenueClassificationName);
		this.FacilityId.set(FacilityId);
		this.FacilityName.set(FacilityName);
		this.PaymentSettlementScenario.set(PaymentSettlementScenario);
		this.SourceAccountingCenter.set(SourceAccountingCenter);
		this.BookScenario.set(BookScenario);
		this.DaysOut.set(DaysOut);
		this.ProductId.set(ProductId);
		this.ProductType.set(ProductType);
		this.ServicePeriodId.set(ServicePeriodId);
		this.SalesChannel.set(SalesChannel);
		this.CommunicationsChannel.set(CommunicationsChannel);

		arrTravelPlan.set(this.TravelPlan.get().split(";"));
		arrSignInLocation.set(this.SignInLocation.get().split(";"));
		arrComponentType.set(this.ComponentType.get().split(";"));
		arrComponentId.set(this.ComponentId.get().split(";"));
		arrRevenueClassificationId.set(this.RevenueClassificationId.get().split(";"));
		arrRevenueClassificationName.set(this.RevenueClassificationName.get().split(";"));
		arrFacilityId.set(this.FacilityId.get().split(";"));
		arrFacilityName.set(this.FacilityName.get().split(";"));
		arrProductId.set(this.ProductId.get().split(";"));
		arrProductType.set(this.ProductType.get().split(";"));
		arrServicePeriodId.set(this.ServicePeriodId.get().split(";"));
		arrSourceAccountingCenter.set(this.SourceAccountingCenter.get().split(";"));
		arrSalesChannel.set(this.SalesChannel.get().split(";"));
		arrCommunicationsChannel.set(this.CommunicationsChannel.get().split(";"));
		arrBookScenario.set(this.BookScenario.get().split(";"));
	}

	/**
	 * This method contains all method calls to actually book the activity
	 * reservation
	 * 
	 * @param booking - used to determine if an existing travel plan is to be used
	 *            with a second booking
	 */
	private void bookActivity(int booking) {
		TestReporter.logStep("Book Activity Reservation");
		book.set(new Book(environment.get(), arrBookScenario.get()[booking]));
		book.get().setActivitySignInLocation(arrSignInLocation.get()[booking]);
		book.get().setActivityComponentPriceComponentType(arrComponentType.get()[booking], "1");
		book.get().setActivityComponentPriceComponentId(arrComponentId.get()[booking], "1");
		book.get().setActivityComponentPriceRevenueClassificationId( arrRevenueClassificationId.get()[booking], "1");
		book.get().setActivityComponentPriceRevenueClassificationName( arrRevenueClassificationName.get()[booking], "1");
		if (!arrTravelPlan.get()[booking].equalsIgnoreCase("Existing")) {
			book.get().setActivityTravelPlanId(arrTravelPlan.get()[booking]);
		} else {
			book.get().setActivityTravelPlanId(TP_ID.get());
		}
		book.get().setActivityFacilityId(arrFacilityId.get()[booking]);
		book.get().setActivityFacilityName(arrFacilityName.get()[booking]);
		
		prodId.set(new RetrieveProductIdByFacilityId(environment.get(), arrFacilityId.get()[booking], "ActivityProduct", true, true));
		book.get().setActivityProductId(prodId.get().getFirstProductId());
		compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), arrFacilityId.get()[booking])); 
		int compProdIds = (book.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? book.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
		for(int i = 1; i <= compProdIds; i++){
			book.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);
		}		
		
		book.get().setActivityProductType(arrProductType.get()[booking]);
		book.get().setActivityServicePeriosId(arrServicePeriodId.get()[booking]);
		book.get().setActivitySourceAccountingCenter(arrSourceAccountingCenter.get()[booking]);
		book.get().setActivityComponentUnitPriceDateTime(bookingDate.get());
		book.get().setActivityServiceStartDateTime(bookingDate.get());
		if (Scenario.get().toLowerCase().contains("international")) {
			generateGuest();
			book.get().setPrimaryGuestPostalCode(address.get().getZipCode());
			book.get().setPrimaryGuestAddress1(address.get().getAddress1());
			book.get().setPrimaryGuestCountry(address.get().getCountry());
			book.get().setPrimaryGuestState(address.get().getStateAbbv());
			book.get().setPrimaryGuestCity(address.get().getCity());
		}
		Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
		book.get().sendRequest();
		if(book.get().getResponse().contains("Row was updated or deleted by another transaction")){
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
			book.get().sendRequest();			
		}
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "A error occurred during booking.", book.get());
		TP_ID.set(book.get().getActivityTravelPlanId());
		TPS_ID.set(book.get().getActivityTravelPlanSegmentId());

		retreiveReservation(booking);
	}

	/**
	 * Generates a guest
	 */
	private void generateGuest() {
		household.set(new HouseHold(1));
		household.get().sendToApi(environment.get());
		address.set(household.get().primaryGuest().primaryAddress());
	}

	/**
	 * This method retrieves the activity reservatino details
	 * 
	 * @param booking - indicates the stage of the iteration and is used to assign
	 *            the appropriate data
	 */
	private void retreiveReservation(int booking) {
		TestReporter.logStep("Retrieve Activity Reservation");
		retrieve.set(new Retrieve(environment.get(), "RetrieveDiningEvent"));
		retrieve.get().setReservationNumber(TPS_ID.get());
		retrieve.get().setCommunicationsChannel(arrCommunicationsChannel.get()[booking]);
		retrieve.get().setSalesChannel(arrSalesChannel.get()[booking]);
		retrieve.get().sendRequest();
		TestReporter.logAPI(!retrieve.get().getResponseStatusCode().equals("200"), "An error occurred while trying to retrieve the reservation.", retrieve.get());
		validateFacility(booking);
	}

	/**
	 * Validates that the correct facility was used in the booking
	 * 
	 * @param booking - indicates the stage of the iteration and is used to assign
	 *            the appropriate data
	 */
	private void validateFacility(int booking) {
		TestReporter.logStep("Validate Activity Reservation Facility");
		String actualFacilityId;
		actualFacilityId = retrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveActivityComponentResponse/activityReservation/activity/facilityId");
		TestReporter.assertEquals(arrFacilityId.get()[booking], actualFacilityId, "The actual facility id [" + actualFacilityId	+ "] did not match the expected facility id ["+ arrFacilityId.get()[booking] + "].");
	}
}