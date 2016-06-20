package com.disney.composite.api.tableServiceDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Book;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.Cancel;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.NoShow;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestNoShow {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	/*
	 * Reservation fields
	 */
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	boolean cancelled = false;
	/*
	 * EventDining objects
	 */
	protected ThreadLocal<Book> book = new ThreadLocal<Book>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>();
	protected ThreadLocal<NoShow> noShow = new ThreadLocal<NoShow>();
	int iteration = 0;;
	int maxIterations = 1;

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
	public void setup(String environment) {
		datatable.set(new Datatable());
		application.set("alc");
		this.environment.set(environment);
		datatable.get().setVirtualtablePath(Datatable.ALC_MASTER_DATA_PATH);
	}

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testNoShow(String Scenario, String SignInLocation,
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

		if (ServiceStyle.equalsIgnoreCase("TableService")) {
			// Define the booking time for the operations
			bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));

			TestReporter.logStep("Book [" + ServiceStyle + "] Event");
			
			book.set(new Book(environment.get(), BookScenario));
			book.get().setSignInLocation(SignInLocation);
			for (int i = 1; i <= ComponentType.split(";").length; i++) {
				book.get().setComponentPriceComponentType(ComponentType.split(";")[i - 1], String.valueOf(i));
				book.get().setComponentPriceComponentId(ComponentId.split(";")[i - 1], String.valueOf(i));
				book.get().setComponentPriceRevenueClassificationId(RevenueClassificationId.split(";")[i - 1],	String.valueOf(i));
				book.get().setComponentPriceRevenueClassificationName(RevenueClassificationName.split(";")[i - 1], String.valueOf(i));
			}
			book.get().setFacilityId(FacilityId);
			book.get().setFacilityName(FacilityName);
			book.get().setServicePeriosId(ServicePeriodId);
			book.get().setAddOnComponentType(AddOnComponentType);
			book.get().setAddOnComponentId(AddOnComponentId);
			book.get().setAddOnRevenueClassificationId(AddOnRevenueClassificationId);
			book.get().setAddOnRevenueClassificaitonName(AddOnRevenueClassificationName);
			book.get().setAddOnProductId(AddOnProductId);
			book.get().setAddOnProductType(AddOnProductType);
			book.get().setSourceAccountingCenter(SourceAccountingCenter);
			book.get().setAddOnComponentUnitPriceDateTime(bookingDate.get());
			book.get().setAddOnServiceStartDateTime(bookingDate.get());
			book.get().setComponentUnitPriceDateTime(bookingDate.get());
			book.get().setServiceStartDateTime(bookingDate.get());
			Sleeper.sleep(Randomness.randomNumberBetween(1, 10));
			book.get().sendRequest();
			TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", book.get());
			TPS_ID.set(book.get().getTravelPlanSegmentId());
			TestReporter.assertTrue(new Regex().match("[0-9]+", TPS_ID.get()), "The TPS_ID ["+TPS_ID.get()+"] was not numeric as expected.");
			iteration++;

			TestReporter.logStep("Update Show Dining Reservation to [No Show]");
			noShow.set(new NoShow(environment.get(), "Main"));
			noShow.get().setReservationNumber(TPS_ID.get());
			noShow.get().sendRequest();
			TestReporter.logAPI(!noShow.get().getResponseStatusCode().equals("200"), "An error occurred in updating the reservation to 'NoShow'.", noShow.get());
			try{
				TestReporter.assertTrue(new Regex().match("[0-9]+", noShow.get().getCancellationNumber()), "The number ["+noShow.get().getCancellationNumber()+"] was not all numeric as expected.");
			}catch(XPathNotFoundException e){
				throw new XPathNotFoundException("An xpath exception occurred extracting the cancellation number from the response. \nResponse:\n" + noShow.get().getResponse());
			}
		}
	}
}
