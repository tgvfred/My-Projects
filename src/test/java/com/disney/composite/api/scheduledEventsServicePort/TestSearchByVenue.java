package com.disney.composite.api.scheduledEventsServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.scheduledEventsServicePort.operations.SearchByVenue;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestSearchByVenue {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	protected ThreadLocal<SearchByVenue> search = new ThreadLocal<SearchByVenue>();
	/*
	 * Reservation fields
	 */
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<String> serviceWindowEnd = new ThreadLocal<String>();
	protected ThreadLocal<String> serviceWindowStart = new ThreadLocal<String>();
	boolean cancelled = false;
	protected ThreadLocal<String> facilityId = new ThreadLocal<String>();
	protected ThreadLocal<String> sourceAccountingCenterId = new ThreadLocal<String>();
	/*
	 * EventDining objects
	 */
	protected ThreadLocal<Book> eventDiningBook = new ThreadLocal<Book>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>();
	int iteration = 0;;
	int maxIterations = 1;

	@DataProvider(name = "dataProvider", parallel=false)
	public Object[][] scenarios() {
		application.set("alc");
		datatable.set(new Datatable());
		try {return datatable.get().getTestScenariosByApp(application.get(),	"ALC_CreateViewDSTSTermIDValid_API");
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		datatable.set(new Datatable());
		application.set("alc");
		this.environment.set(environment);
		datatable.get().setVirtualtablePath(Datatable.ALC_MASTER_DATA_PATH);
	}

	@AfterMethod(alwaysRun = true)
	public void testSearchByVenue() {
		// If the reservation number is not null and is not an empty string, then cancel the reservation
		if(!cancelled){
			if (TPS_ID.get() != null && !TPS_ID.get().isEmpty()) {
				cancel.set(new Cancel(environment.get(), "CancelDiningEvent"));
				cancel.get().setReservationNumber(TPS_ID.get());
				cancel.get().sendRequest();
				TestReporter.logAPI(!cancel.get().getResponseStatusCode().equals("200"), "An error occurred during cancellation.", cancel.get());
				TestReporter.assertTrue(new Regex().match("[0-9]+", cancel.get().getCancellationConfirmationNumber()), "The cancellation confirmation number ["+cancel.get().getCancellationConfirmationNumber()+"] was not numeric as expected.");
				cancelled = true;
			}
		}
	}

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "scheduledEventsServicePort"})
	public void testSearchByVenue(String Scenario, String SignInLocation,
			String Child, String Remove, String ServiceStyle,
			String PaymentOrSettlement, String ComponentType,
			String ComponentId, String RevenueClassificationId,
			String RevenueClassificationName, String FacilityId,
			String FacilityName, String ProductId, String ProductType,
			String ServicePeriodId, String AddOnComponentType,
			String AddOnComponentId, String AddOnRevenueClassificationId,
			String AddOnRevenueClassificationName, String AddOnProductId,
			String AddOnProductType, String UpdateAddOnComponentId,
			String UpdateAddOnProductId, String PaymentSettlementScenario,
			String RunTest, String RemoveComponentType,
			String RemoveComponentId, String RemoveRevenueClassificationId,
			String RemoveRevenueClassificationName, String RemoveFacilityid,
			String RemoveProductId, String RemoveEnterpriseId,
			String RemoveProductType, String RemoveServiceperiod,
			String SourceAccountingCenter, String BookScenario,
			String RemoveScenario, String UpdateScenario, String DaysOut){

		// Log the test scenario name in the reporter
		TestReporter.logScenario(Scenario);

		// Grab the @Test method name and use it to define the test name
		testName.set(new Object() {}.getClass().getEnclosingMethod().getName());

		if(iteration < maxIterations){
			if (ServiceStyle.equalsIgnoreCase("TableService")) {
				TestReporter.logStep("Book Event Dining Reservation");
				// Define the booking time for the operations
				bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));
				serviceWindowEnd.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt("30")));
				serviceWindowStart.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt("-30")));
	
				TestReporter.logStep("Book [" + ServiceStyle + "] Event");
				
				eventDiningBook.set(new Book(environment.get(), BookScenario));
				eventDiningBook.get().setSignInLocation(SignInLocation);
				for (int i = 1; i <= ComponentType.split(";").length; i++) {
					eventDiningBook.get().setComponentPriceComponentType(ComponentType.split(";")[i - 1], String.valueOf(i));
					eventDiningBook.get().setComponentPriceComponentId(ComponentId.split(";")[i - 1], String.valueOf(i));
					eventDiningBook.get().setComponentPriceRevenueClassificationId(RevenueClassificationId.split(";")[i - 1],	String.valueOf(i));
					eventDiningBook.get().setComponentPriceRevenueClassificationName(RevenueClassificationName.split(";")[i - 1], String.valueOf(i));
				}
				eventDiningBook.get().setFacilityId(FacilityId);
				eventDiningBook.get().setFacilityName(FacilityName);
				eventDiningBook.get().setProductId(ProductId);
				eventDiningBook.get().setProductType(ProductType);
				eventDiningBook.get().setServicePeriosId(ServicePeriodId);
				eventDiningBook.get().setAddOnComponentType(AddOnComponentType);
				eventDiningBook.get().setAddOnComponentId(AddOnComponentId);
				eventDiningBook.get().setAddOnRevenueClassificationId(AddOnRevenueClassificationId);
				eventDiningBook.get().setAddOnRevenueClassificaitonName(AddOnRevenueClassificationName);
				eventDiningBook.get().setAddOnProductId(AddOnProductId);
				eventDiningBook.get().setAddOnProductType(AddOnProductType);
				eventDiningBook.get().setSourceAccountingCenter(SourceAccountingCenter);
				eventDiningBook.get().setAddOnComponentUnitPriceDateTime(bookingDate.get());
				eventDiningBook.get().setAddOnServiceStartDateTime(bookingDate.get());
				eventDiningBook.get().setComponentUnitPriceDateTime(bookingDate.get());
				eventDiningBook.get().setServiceStartDateTime(bookingDate.get());
				Sleeper.sleep(Randomness.randomNumberBetween(1, 10));
				eventDiningBook.get().sendRequest();
				TestReporter.logAPI(!eventDiningBook.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", eventDiningBook.get());
				TPS_ID.set(eventDiningBook.get().getTravelPlanSegmentId());
				sourceAccountingCenterId.set(eventDiningBook.get().getSourceAccountingCenter());
				facilityId.set(eventDiningBook.get().getRequestFacilityId());
				iteration++;

				TestReporter.logStep("Search By Venue");
				search.set(new SearchByVenue(environment.get(), "Main"));
				search.get().setServiceWindowEnd(serviceWindowEnd.get());
				search.get().setServiceWindowStart(serviceWindowStart.get());
				search.get().sendRequest();
				TestReporter.logAPI(!search.get().getResponseStatusCode().equals("200"), "An error occurred during the search.", search.get());
				TestReporter.assertTrue(search.get().getEventReservations().getLength() > 0, "No reservations were returned for facility id ["+facilityId.get()+"].");
			}
		}
	}
}
