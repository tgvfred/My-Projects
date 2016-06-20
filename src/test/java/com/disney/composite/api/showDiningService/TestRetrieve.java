package com.disney.composite.api.showDiningService;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Retrieve;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.RetrieveComponentProductIdByFacilityId;
import com.disney.utils.RetrieveProductIdByFacilityId;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve {

	// *******************
	// Test Class Fields
	// *******************
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	protected ThreadLocal<RetrieveProductIdByFacilityId> prodId = new ThreadLocal<RetrieveProductIdByFacilityId>();
	protected ThreadLocal<RetrieveComponentProductIdByFacilityId> compProdId = new ThreadLocal<RetrieveComponentProductIdByFacilityId>();
	boolean cancelled = false;
	/*
	 * Reservation fields
	 */
	protected ThreadLocal<String> TP_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();	
	/*
	 * ShowDining object
	 */
	protected ThreadLocal<Book> showDiningBook = new ThreadLocal<Book>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>(); 
	protected ThreadLocal<Retrieve> retrieve = new ThreadLocal<Retrieve>(); 

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

	@AfterMethod(alwaysRun=true)
	public void testCancel() {
		// If the reservation number is not null and is not an empty string, then cancel the reservation
		if(!cancelled){
			if (TPS_ID.get() != null && !TPS_ID.get().isEmpty()) {
				cancel.set(new Cancel(environment.get(), "CancelDiningEvent"));
				cancel.get().setTravelPlanSegmentId(TPS_ID.get());
				cancel.get().sendRequest();
				TestReporter.log("Cancellation confirmation number: " + cancel.get().getCancellationConfirmationNumber());
				cancelled = true;
			}
		}
	}

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "showDiningService"})
	public void testRetrieve(String Scenario, String SignInLocation,
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
			String RemoveScenario, String UpdateScenario, String DaysOut) {
		if (RunTest.equalsIgnoreCase("true")) {
			if(!ServiceStyle.equalsIgnoreCase("TableService")){
				// Log the test scenario name in the reporter
				TestReporter.logScenario(Scenario);

				// Grab the @Test method name and use it to define the test name
				testName.set(new Object() {}.getClass().getEnclosingMethod().getName());

				// Define the booking time for the operations
				bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));

				TestReporter.logStep("Book [" + ServiceStyle + "] Event");
				showDiningBook.set(new com.disney.api.soapServices.showDiningService.operations.Book(environment.get(), BookScenario));
				showDiningBook.get().setSignInLocation(SignInLocation);
				String[] componentTypes = ComponentType.split(";");
				String[] componentIds = ComponentId.split(";");
				String[] revenueClassificationIds = RevenueClassificationId.split(";");
				String[] revenueClassificationNames = RevenueClassificationName.split(";");
				for (int i = 1; i <= componentTypes.length; i++) {
					showDiningBook.get().setComponentPriceComponentType(componentTypes[i - 1], String.valueOf(i));
					showDiningBook.get().setComponentPriceComponentId(componentIds[i - 1], String.valueOf(i));
					showDiningBook.get().setComponentPriceRevenueClassificationId(revenueClassificationIds[i - 1], String.valueOf(i));
					showDiningBook.get().setComponentPriceRevenueClassificationName(revenueClassificationNames[i - 1], String.valueOf(i));
					showDiningBook.get().setComponentUnitPriceDepositDueDateTime(bookingDate.get(), String.valueOf(i));
					showDiningBook.get().setComponentUnitPriceDateTime(bookingDate.get(), String.valueOf(i));
				}
				showDiningBook.get().setServiceDate(bookingDate.get());
				showDiningBook.get().setAddOnComponentUnitPriceDateTime(bookingDate.get());
				showDiningBook.get().setAddOnServiceDate(bookingDate.get());
				showDiningBook.get().setFacilityId(FacilityId);
				showDiningBook.get().setFacilityName(FacilityName);
				
				prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityId, "", false, true));
				ProductId = prodId.get().getFirstProductId();
				showDiningBook.get().setProductId(ProductId);
				compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), FacilityId)); 
				int compProdIds = (showDiningBook.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? showDiningBook.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
				for(int i = 1; i <= compProdIds; i++){
					showDiningBook.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);
				}
				
				showDiningBook.get().setProductType(ProductType);
				showDiningBook.get().setServicePeriosId(ServicePeriodId);
				showDiningBook.get().setAddOnComponentType(AddOnComponentType);
				showDiningBook.get().setAddOnComponentId(AddOnComponentId);
				showDiningBook.get().setAddOnRevenueClassificationId(AddOnRevenueClassificationId);
				showDiningBook.get().setAddOnRevenueClassificaitonName(AddOnRevenueClassificationName);
				showDiningBook.get().setAddOnProductId(AddOnProductId);
				showDiningBook.get().setAddOnProductType(AddOnProductType);
				showDiningBook.get().setAddOnFacilityId(FacilityId);
				showDiningBook.get().setAddOnFacilityName(FacilityName);
				Sleeper.sleep(Randomness.randomNumberBetween(1, 15) * 1000);
				showDiningBook.get().sendRequest();
				if(showDiningBook.get().getResponse().contains("Row was updated or deleted by another transaction")){
					Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
					showDiningBook.get().sendRequest();
				}
				TestReporter.logAPI(!showDiningBook.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", showDiningBook.get());
				TP_ID.set(showDiningBook.get().getTravelPlanId());
				TPS_ID.set(showDiningBook.get().getTravelPlanSegmentId());
				TestReporter.assertTrue(new Regex().match("[0-9]+", TP_ID.get()), "The TP ID ["+TP_ID.get()+"] was not numeric as expected.");
				TestReporter.assertTrue(new Regex().match("[0-9]+", TPS_ID.get()), "The TPS ID ["+TPS_ID.get()+"] was not numeric as expected.");
				
				TestReporter.logStep("Retrieve Show Dining Reservation");
				retrieve.set(new Retrieve(environment.get(), "RetrieveDiningEvent"));
				retrieve.get().setCommunicationsChannel(showDiningBook.get().getCommunicationsChannel());
				retrieve.get().setSalesChannel(showDiningBook.get().getSalesChannel());
				retrieve.get().setReservationNumber(TPS_ID.get());
				retrieve.get().sendRequest();
				TestReporter.logAPI(!retrieve.get().getResponseStatusCode().equals("200"), "An error occurred during retrieval.", retrieve.get());
				TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.get().getPartyId()), "The party ID ["+retrieve.get().getPartyId()+"] was not numeric as expected.");
				TestReporter.assertTrue(new Regex().match("[0-9]+", retrieve.get().getPrimaryGuestId()), "The primary guest ID ["+retrieve.get().getPrimaryGuestId()+"] was not numeric as expected.");				
				TestReporter.assertEquals(TPS_ID.get(), retrieve.get().getReservationNumber(), "The expected reservation number ["+TPS_ID.get()+"] did not match the actual reservation number ["+retrieve.get().getReservationNumber()+"].");
				TestReporter.assertEquals(TP_ID.get(), retrieve.get().getTravelPlanId(), "The expected travel plan ID ["+TP_ID.get()+"] did not match the actual travel plan ID ["+retrieve.get().getTravelPlanId()+"].");
				TestReporter.assertEquals(FacilityId, retrieve.get().getFacilityid(), "The expected facility ID ["+FacilityId+"] did not match the actual facility ID ["+retrieve.get().getFacilityid()+"].");
				TestReporter.assertEquals(ProductId, retrieve.get().getProductId(), "The expected product ID ["+ProductId+"] did not match the actual product ID ["+retrieve.get().getProductId()+"].");
			}
		}
	}
}