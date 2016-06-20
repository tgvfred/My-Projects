package com.disney.composite.api.eventDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.eventDiningService.operations.NoShow;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.RetrieveComponentProductIdByFacilityId;
import com.disney.utils.RetrieveProductIdByFacilityId;
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
	protected ThreadLocal<String> salesChannel = new ThreadLocal<String>();
	protected ThreadLocal<String> communicationsChannel = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<RetrieveProductIdByFacilityId> prodId = new ThreadLocal<RetrieveProductIdByFacilityId>();
	protected ThreadLocal<RetrieveComponentProductIdByFacilityId> compProdId = new ThreadLocal<RetrieveComponentProductIdByFacilityId>();
	/*
	 * EventDining objects
	 */
	protected ThreadLocal<Book> eventDiningBook = new ThreadLocal<Book>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>(); 
	protected ThreadLocal<NoShow> noShow = new ThreadLocal<NoShow>();

	@DataProvider(name = "dataProvider", parallel=true)
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

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "eventDiningService"})
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
			book(ServiceStyle, BookScenario, SignInLocation, ComponentType, ComponentId,RevenueClassificationId, RevenueClassificationName, 
					FacilityId, FacilityName, ProductId, ProductType, ServicePeriodId, AddOnComponentType, AddOnComponentId, AddOnRevenueClassificationId,
					AddOnRevenueClassificationName, AddOnProductId, AddOnProductType, SourceAccountingCenter, DaysOut);
			TestReporter.logStep("Update Reservation to 'No Show'");
			noShow.set(new NoShow(environment.get(), "Main"));
			noShow.get().setCommunicationChannel(communicationsChannel.get());
			noShow.get().setSalesChannel(salesChannel.get());
			noShow.get().setReservationNumber(TPS_ID.get());
			noShow.get().sendRequest();
			TestReporter.logAPI(!noShow.get().getResponseStatusCode().equals("200"), "An error occurred in updating the reservation to 'NoShow'.", noShow.get());
			try{TestReporter.assertTrue(new Regex().match("[0-9]+", noShow.get().getCancellationNumber()), "The number ["+noShow.get().getCancellationNumber()+"] was not all numeric as expected.");}
			catch(XPathNotFoundException e){throw new XPathNotFoundException("An xpath exception occurred extracting the cancellation number from the response. \nResponse:\n" + noShow.get().getResponse());}
		}
	}
	private void book(String ServiceStyle, String BookScenario, String SignInLocation, String ComponentType, String ComponentId,
			String RevenueClassificationId, String RevenueClassificationName, String FacilityId, String FacilityName, String ProductId,
			String ProductType, String ServicePeriodId, String AddOnComponentType, String AddOnComponentId, String AddOnRevenueClassificationId,
			String AddOnRevenueClassificationName, String AddOnProductId, String AddOnProductType, String SourceAccountingCenter, String DaysOut){
		TestReporter.logStep("Book Event Dining Reservation");
		// Define the booking time for the operations
		bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));

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
		
		prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityId, "", false, true));
		eventDiningBook.get().setProductId(prodId.get().getFirstProductId());
		compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), FacilityId)); 
		int compProdIds = (eventDiningBook.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? eventDiningBook.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
		for(int i = 1; i <= compProdIds; i++){eventDiningBook.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);}
		
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
		salesChannel.set(eventDiningBook.get().getSalesChannel());
		communicationsChannel.set(eventDiningBook.get().getCommunicationsChannel());
		Sleeper.sleep(Randomness.randomNumberBetween(1, 10));
		eventDiningBook.get().sendRequest();
		TestReporter.logAPI(!eventDiningBook.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", eventDiningBook.get());
		TPS_ID.set(eventDiningBook.get().getTravelPlanSegmentId());
	}
}
