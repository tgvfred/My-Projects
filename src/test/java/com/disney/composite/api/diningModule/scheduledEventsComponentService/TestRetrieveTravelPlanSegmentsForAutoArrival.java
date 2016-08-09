package com.disney.composite.api.diningModule.scheduledEventsComponentService;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.scheduledEventsComponentService.operations.AutoArrived;
import com.disney.api.soapServices.diningModule.scheduledEventsComponentService.operations.RetrieveTravelPlanSegmentsForAutoArrival;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations.RetrieveDetailsByTravelPlanId;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.RetrieveComponentProductIdByFacilityId;
import com.disney.utils.RetrieveProductIdByFacilityId;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieveTravelPlanSegmentsForAutoArrival {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	/*
	 * Reservation fields
	 */
	protected ThreadLocal<String> TP_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<String> salesChannel = new ThreadLocal<String>();
	protected ThreadLocal<String> communicationsChannel = new ThreadLocal<String>();
	protected ThreadLocal<RetrieveProductIdByFacilityId> prodId = new ThreadLocal<RetrieveProductIdByFacilityId>();
	protected ThreadLocal<RetrieveComponentProductIdByFacilityId> compProdId = new ThreadLocal<RetrieveComponentProductIdByFacilityId>();
	boolean cancelled = false;
	/*
	 * EventDining objects
	 */
	protected ThreadLocal<Book> eventDiningBook = new ThreadLocal<Book>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>();
	protected ThreadLocal<RetrieveDetailsByTravelPlanId> eventDiningRetrieve = new ThreadLocal<RetrieveDetailsByTravelPlanId>();
	protected ThreadLocal<AutoArrived> auto = new ThreadLocal<AutoArrived>();
	int iteration = 0;;
	int maxIterations = 1;
	/*
	 * ScehduledsEventsComponentService
	 */
	protected ThreadLocal<RetrieveTravelPlanSegmentsForAutoArrival> retrieve = new ThreadLocal<RetrieveTravelPlanSegmentsForAutoArrival>();
	
	@DataProvider(name = "dataProvider", parallel=false)
	public Object[][] scenarios() {
		application.set("alc");
		datatable.set(new Datatable());
		try {return datatable.get().getTestScenariosByApp(application.get(),	"ALC_CreateViewDSTSTermIDValid_API");}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment) {
		datatable.set(new Datatable());
		application.set("alc");
		this.environment.set(environment);
		datatable.get().setVirtualtablePath(Datatable.ALC_MASTER_DATA_PATH);
	}

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "ScheduledEventsComponentService"})
	public void testRetrieveTravelPlanSegmentsForAutoArrival(String Scenario, String SignInLocation,
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
		TestReporter.setDebugLevel(1);
		// Grab the @Test method name and use it to define the test name
		testName.set(new Object() {}.getClass().getEnclosingMethod().getName());

		if(environment.get().toLowerCase().contains("_cm")){
			throw new SkipException("The Service#operation [ScheduledEventsComponentService#RetrieveDetailsByTravelPlanId] does not exist in the ["+environment.get()+"] environment.");
		}

		if(iteration < maxIterations){
			if (ServiceStyle.equalsIgnoreCase("TableService")) {
				// Define the booking time for the operations
				bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));
	
				TestReporter.logStep("Book [" + ServiceStyle + "] Event");
				TestReporter.logStep("Book Event Dining Service Reservation");
				
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
				
				prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityId, "", false, true));
				eventDiningBook.get().setProductId(prodId.get().getFirstProductId());
				compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), FacilityId)); 
				int compProdIds = (eventDiningBook.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? eventDiningBook.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
				for(int i = 1; i <= compProdIds; i++){
					eventDiningBook.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);
				}
				
				eventDiningBook.get().setProductType(ProductType);
				eventDiningBook.get().setServicePeriodId(ServicePeriodId);
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
				eventDiningBook.get().setPrimaryGuestSuffix(BaseSoapCommands.REMOVE_NODE.toString());
				Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
				eventDiningBook.get().sendRequest();
				if(eventDiningBook.get().getResponse().contains("Row was updated or deleted by another transaction")){
					Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
					eventDiningBook.get().sendRequest();			
				}
				TestReporter.logAPI(!eventDiningBook.get().getResponseStatusCode().equals("200"), "An error occurred during booking: " + eventDiningBook.get().getFaultString(), eventDiningBook.get());
//				TestReporter.assertEquals(eventDiningBook.get().getResponseStatusCode(), "200",	"An error occurred during booking. \nRequest:"+eventDiningBook.get().getRequest()+"\nResponse:\n" + eventDiningBook.get().getResponse());
				TPS_ID.set(eventDiningBook.get().getTravelPlanSegmentId());
				TP_ID.set(eventDiningBook.get().getTravelPlanId());
				salesChannel.set(eventDiningBook.get().getSalesChannel());
				communicationsChannel.set(eventDiningBook.get().getCommunicationsChannel());
				iteration++;
				retreiveReservation();

				TestReporter.logStep("Update Event Dining Service Reservation to 'Auto Arrived'");
				auto.set(new AutoArrived(environment.get(), "Main"));
				auto.get().setTpsId(TPS_ID.get());
				auto.get().sendRequest();
				TestReporter.logAPI(!auto.get().getResponseStatusCode().equals("200"), "An error occurred setting the reservation to 'Auto Arrived'.", auto.get());
//				TestReporter.assertEquals(auto.get().getResponseStatusCode(), "200",	"An error occurred setting the reservation to 'Auto Arrived'. \nRequest:"+auto.get().getRequest()+"\nResponse:\n" + auto.get().getResponse());				

				TestReporter.logStep("Retrieve Travel Plan Segments for Auto Arrival");
				retrieve.set(new RetrieveTravelPlanSegmentsForAutoArrival(environment.get(), "Main"));
				retrieve.get().setProcessDate(bookingDate.get());
				retrieve.get().setSourceAccountingCenter(eventDiningRetrieve.get().getSalesOrdersSourceAccountingCenterId());
				retrieve.get().sendRequest();
				TestReporter.logAPI(!retrieve.get().getResponseStatusCode().equals("200"), "An error occurred while retrieving.", retrieve.get());
//				TestReporter.assertEquals(retrieve.get().getResponseStatusCode(), "200",	"An error occurred while retrieving.\nRequest:"+retrieve.get().getRequest()+"\nResponse:\n" + retrieve.get().getResponse());
				TestReporter.assertTrue(retrieve.get().getAllReturnNodes().getLength() > 0, "No travel plan segments were returned for processing date ["+bookingDate.get()+"] and source accounting center ["+eventDiningRetrieve.get().getSalesOrdersSourceAccountingCenterId()+"].");
			}
		}
	}

	/**
	 * Method designed to retrieve detail reservation information
	 */
	private void retreiveReservation() {
		TestReporter.logStep("Retrieve Event Dining Service Reservation");
		eventDiningRetrieve.set(new RetrieveDetailsByTravelPlanId(environment.get(), "Main"));
		eventDiningRetrieve.get().setTravelPlanId(TP_ID.get());
		eventDiningRetrieve.get().setCommunicationsChannel(communicationsChannel.get());
		eventDiningRetrieve.get().setSalesChannel(salesChannel.get());
		eventDiningRetrieve.get().sendRequest();
		TestReporter.logAPI(!eventDiningRetrieve.get().getResponseStatusCode().equals("200"), "An error occurred while trying to retrieve the reservation.", eventDiningRetrieve.get());
//		TestReporter.assertEquals(eventDiningRetrieve.get().getResponseStatusCode(), "200", "An error occurred while trying to retrieve the reservation.");
	}
}
