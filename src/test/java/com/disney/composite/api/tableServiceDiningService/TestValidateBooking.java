package com.disney.composite.api.tableServiceDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.tableServiceDiningServicePort.operations.ValidateBooking;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestValidateBooking {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<ReservableResourceByFacilityID> rr = new ThreadLocal<ReservableResourceByFacilityID>();
	/*
	 * EventDining objects
	 */
	protected ThreadLocal<ValidateBooking> validate = new ThreadLocal<ValidateBooking>();

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
	public void testValidateBooking(String Scenario, String SignInLocation,
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
		bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));
		
		if (ServiceStyle.equalsIgnoreCase("TableService")) {
			TestReporter.logStep("Retrieve Reservable Resource Facility By Facility ID");
			rr.set(new ReservableResourceByFacilityID(environment.get(), "Main")); 
			rr.get().setFacilityId(FacilityId);
			rr.get().sendRequest();
			TestReporter.logAPI(!rr.get().getResponseStatusCode().equals("200"), "An error occurred retrieving a reservable resource.", rr.get());
			rr.get().getReservableResources();
			TestReporter.assertEquals(rr.get().getResponseStatusCode(), "200", "An error occured retrieving a reservable resource.\nRequest:\n"+rr.get().getRequest()+"\nResponse:\n"+rr.get().getResponse());
			
			TestReporter.logStep("Validate Show Dining Booking");
			validate.set(new ValidateBooking(environment.get(), "Main"));
			validate.get().setFacilityId(FacilityId);
			validate.get().setProductId(ProductId);
			validate.get().setServiceStartDate(bookingDate.get());
			validate.get().setServicePeriodId(ServicePeriodId);
			validate.get().setReservableResourceId(rr.get().getFirstReservableResourceId());
			validate.get().sendRequest();
			TestReporter.logAPI(!validate.get().getResponseStatusCode().equals("200"), "An error occurred in validating a booking.", validate.get());
			try{
				TestReporter.assertEquals(validate.get().getStopReservation(),"false", "The 'Stop Reservation' value ["+validate.get().getStopReservation()+"] was not 'false' as expected.");
			}catch(XPathNotFoundException e){
				throw new XPathNotFoundException("An xpath exception occurred extracting the 'Stop Reservation' from the response. \nResponse:\n" + validate.get().getResponse());
			}
		}
	}
}
