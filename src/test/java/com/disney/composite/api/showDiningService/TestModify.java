package com.disney.composite.api.showDiningService;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.showDiningService.operations.Book;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.api.soapServices.showDiningService.operations.Modify;
import com.disney.api.soapServices.showDiningService.operations.Retrieve;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;

/**
 * This class contains all fields, methods and classes required to test
 * 
 * @author Venkatesh A
 * @version 1/11/2016 Venkatesh A - Original
 */

public class TestModify {
	// *******************
	// Test Class Fields
	// *******************
	// Defining global variables
	private String application = "alc";
	private String environment = "";
	private Datatable datatable = new Datatable();
	/*
	 * Reservation fields
	 */
	private String TP_ID;
	private String TPS_ID;
	private String salesChannel;
	private String communicationsChannel;
	private String bookingDate;
	private boolean cancelRes;
	/*
	 * ShowDining object
	 */
	private Book showDiningBook;
	private Retrieve showDiningRetrieve;
	private Modify showDiningModify;
	private Cancel cancel;

	@DataProvider(name = "dataProvider")
	public Object[][] scenarios() {
		try {return datatable.getTestScenariosByApp(application,"AddOns_UpdateRemove_API");}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.application = "alc";
		this.environment = environment;
		datatable.setVirtualtablePath(Datatable.ALC_MASTER_DATA_PATH);
	}
	
	
	@AfterMethod(alwaysRun = true)
	public synchronized void closeSession(ITestResult test) {
		if(cancelRes){
			if (TPS_ID != null && !TPS_ID.isEmpty()) {
				cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(TPS_ID);
				cancel.sendRequest();
				TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred during cancellation.", cancel);
				TestReporter.assertTrue(new Regex().match("[0-9]+", cancel.getCancellationConfirmationNumber()), "The cancellation confirmation number was not numeric as expected.");
			}
		}
	}

	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "showDiningService"})
	public void AddOns_UpdateRemove(String Scenario, String SignInLocation,
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
				cancelRes = true;
				// Log the test scenario name in the reporter
				TestReporter.logScenario(Scenario);

				TestReporter.logStep("Book Show Dining Reservation");
				// Define the booking time for the operations
				bookingDate = Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut));
				
				showDiningBook = new Book(environment, BookScenario);
				showDiningBook.setSignInLocation(SignInLocation);
				String[] componentTypes = ComponentType.split(";");
				String[] componentIds = ComponentId.split(";");
				String[] revenueClassificationIds = RevenueClassificationId.split(";");
				String[] revenueClassificationNames = RevenueClassificationName.split(";");
				for (int i = 1; i <= componentTypes.length; i++) {
					showDiningBook.setComponentPriceComponentType(componentTypes[i - 1], String.valueOf(i));
					showDiningBook.setComponentPriceComponentId(componentIds[i - 1], String.valueOf(i));
					showDiningBook.setComponentPriceRevenueClassificationId(revenueClassificationIds[i - 1], String.valueOf(i));
					showDiningBook.setComponentPriceRevenueClassificationName(revenueClassificationNames[i - 1], String.valueOf(i));
					showDiningBook.setComponentUnitPriceDepositDueDateTime(	bookingDate, String.valueOf(i));
					showDiningBook.setComponentUnitPriceDateTime(bookingDate, String.valueOf(i));
				}
				showDiningBook.setServiceDate(bookingDate);
				showDiningBook.setAddOnComponentUnitPriceDateTime(bookingDate);
				showDiningBook.setAddOnServiceDate(bookingDate);
				showDiningBook.setFacilityId(FacilityId);
				showDiningBook.setFacilityName(FacilityName);
				showDiningBook.setProductId(ProductId);
				showDiningBook.setProductType(ProductType);
				showDiningBook.setServicePeriosId(ServicePeriodId);
				showDiningBook.setAddOnComponentType(AddOnComponentType);
				showDiningBook.setAddOnComponentId(AddOnComponentId);
				showDiningBook.setAddOnRevenueClassificationId(AddOnRevenueClassificationId);
				showDiningBook.setAddOnRevenueClassificaitonName(AddOnRevenueClassificationName);
				showDiningBook.setAddOnProductId(AddOnProductId);
				showDiningBook.setAddOnProductType(AddOnProductType);
				showDiningBook.setAddOnFacilityId(FacilityId);
				showDiningBook.setAddOnFacilityName(FacilityName);
				Sleeper.sleep(Randomness.randomNumberBetween(1, 15) * 1000);
				showDiningBook.sendRequest();
				if(showDiningBook.getResponse().contains("Row was updated or deleted by another transaction")){
					Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
					showDiningBook.sendRequest();
				}
				TestReporter.logAPI(!showDiningBook.getResponseStatusCode().equals("200"), "An error occurred while trying to book a Show Dining reservation.", showDiningBook);
				salesChannel = showDiningBook.getSalesChannel();
				communicationsChannel = showDiningBook.getCommunicationsChannel();
				TP_ID = showDiningBook.getTravelPlanId();
				TPS_ID = showDiningBook.getTravelPlanSegmentId();
				TestReporter.log("TP ID: " + TP_ID);
				TestReporter.log("TPS ID: " + TPS_ID);
				retreiveReservation(ServiceStyle);
				/*
				 * Modify
				 */
				TestReporter.logStep("Modify Show Dining Reservation");
				showDiningModify = new com.disney.api.soapServices.showDiningService.operations.Modify(environment, UpdateScenario);
				showDiningModify.setTravelPlanId(TP_ID);
				showDiningModify.setReservationNumber(TPS_ID);
				showDiningModify.setFacilityId(FacilityId);
				showDiningModify.setServicePeriosId(ServicePeriodId);
				showDiningModify.setSignInLocation(SignInLocation);
				showDiningModify.setAddOnComponentId(UpdateAddOnComponentId);
				showDiningModify.setAddOnComponentType(AddOnComponentType);
				showDiningModify.setAddOnFacilityId(FacilityId);
				showDiningModify.setAddOnFacilityName(FacilityName);
				showDiningModify.setAddOnProductId(UpdateAddOnProductId);
				showDiningModify.setAddOnProductType(ProductType);
				showDiningModify.setAddOnRevenueClassificaitonName(AddOnRevenueClassificationName);
				showDiningModify.setAddOnRevenueClassificationId(AddOnRevenueClassificationId);
				showDiningModify.setServiceStartDateTime(bookingDate);
				showDiningModify.setComponentUnitPriceStartDateTime(bookingDate);
				showDiningModify.setAddOnServiceStartDate(bookingDate);
				showDiningModify.sendRequest();
				TestReporter.logAPI(!showDiningModify.getResponseStatusCode().equals("200"), "An error occurred during modifying the Add-Ons.", showDiningModify);
				TestReporter.assertEquals(showDiningModify.getResponseStatus(), "SUCCESS", "The actual modify response status code ["+showDiningModify.getResponseStatus()+"] was not [SUCCESS] as expected.");
				
				retreiveReservation(ServiceStyle);
				validateAddOnAdded(ServiceStyle);
			}else{
				cancelRes = false;
			}
		}
	}

	private void retreiveReservation(String ServiceStyle) {
		TestReporter.logStep("Retrieve Show Dining Reservation");
		showDiningRetrieve = new com.disney.api.soapServices.showDiningService.operations.Retrieve(	environment, "RetrieveDiningEvent");
		showDiningRetrieve.setReservationNumber(TPS_ID);
		showDiningRetrieve.setCommunicationsChannel(communicationsChannel);
		showDiningRetrieve.setSalesChannel(salesChannel);
		showDiningRetrieve.sendRequest();
		TestReporter.logAPI(!showDiningRetrieve.getResponseStatusCode().equals("200"), "An error occurred while trying to retrieve the reservation.", showDiningRetrieve);
	}

	private boolean getAddOnStatus(String ServiceStyle) {
		boolean addOnPresent;
		try {
			showDiningRetrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/addOnComponents/componentPrices/componentType");
			addOnPresent = true;
		} catch (Exception e) {
			addOnPresent = false;
		}
		return addOnPresent;
	}

	private void validateAddOnAdded(String ServiceStyle) {
		TestReporter.logStep("Validate Show Dining Reservation"); 
		TestReporter.assertTrue(getAddOnStatus(ServiceStyle), "The AddOn was not found to have been added.");
	}
}
