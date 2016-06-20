package com.disney.composite.api.eventDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.eventDiningService.operations.ValidateBooking;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.RetrieveProductIdByFacilityId;
import com.disney.utils.TestReporter;

public class TestValidateBooking {
	// Defining global variables
	protected ThreadLocal<String> testName = new ThreadLocal<String>();
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> datatable = new ThreadLocal<Datatable>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<RetrieveProductIdByFacilityId> prodId = new ThreadLocal<RetrieveProductIdByFacilityId>();
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

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "eventDiningService"})
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
			TestReporter.logStep("Validate Event Dining Booking");
			validate.set(new ValidateBooking(environment.get(), "Main"));
			validate.get().setFacilityId(FacilityId);
			
			prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityId, "", false, true));
			validate.get().setProductId(prodId.get().getFirstProductId());			
			
			validate.get().setServiceStartDate(bookingDate.get());
			validate.get().setServicePeriodId(ServicePeriodId);
			validate.get().sendRequest();
			TestReporter.logAPI(!validate.get().getResponseStatusCode().equals("200"), "An error occurred in validating a booking.", validate.get());
			try{TestReporter.assertEquals(validate.get().getStopReservation(),"false", "The 'Stop Reservation' value ["+validate.get().getStopReservation()+"] was not 'false' as expected.");}
			catch(XPathNotFoundException e){throw new XPathNotFoundException("An xpath exception occurred extracting the 'Stop Reservation' from the response. \nResponse:\n" + validate.get().getResponse());}
		}
	}
}
