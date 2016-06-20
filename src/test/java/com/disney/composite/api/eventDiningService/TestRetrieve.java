package com.disney.composite.api.eventDiningService;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.eventDiningService.operations.Retrieve;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.RetrieveComponentProductIdByFacilityId;
import com.disney.utils.RetrieveProductIdByFacilityId;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestRetrieve {
	// *******************
	// Test Class Fields
	// *******************
	// Defining global variables
	protected ThreadLocal<String> application = new ThreadLocal<String>();
	protected ThreadLocal<String> environment = new ThreadLocal<String>();
	protected ThreadLocal<Datatable> dt = new ThreadLocal<Datatable>();

	/*
	 * Reservation fields
	 */
	protected ThreadLocal<String> TP_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> TPS_ID = new ThreadLocal<String>();
	protected ThreadLocal<String> salesChannel = new ThreadLocal<String>();
	protected ThreadLocal<String> communicationsChannel = new ThreadLocal<String>();
	protected ThreadLocal<String> bookingDate = new ThreadLocal<String>();
	protected ThreadLocal<String> cellNumber = new ThreadLocal<String>();
	protected ThreadLocal<String> phoneNumber = new ThreadLocal<String>();
	protected ThreadLocal<String> email = new ThreadLocal<String>();
	protected ThreadLocal<RetrieveProductIdByFacilityId> prodId = new ThreadLocal<RetrieveProductIdByFacilityId>();
	protected ThreadLocal<RetrieveComponentProductIdByFacilityId> compProdId = new ThreadLocal<RetrieveComponentProductIdByFacilityId>();
	
	/*
	 * EventDining objects
	 */
	protected ThreadLocal<Book> eventDiningBook = new ThreadLocal<Book>();
	protected ThreadLocal<Retrieve> eventDiningRetrieve = new ThreadLocal<Retrieve>();
	protected ThreadLocal<Cancel> cancel = new ThreadLocal<Cancel>();
	protected ThreadLocal<HouseHold> hh = new ThreadLocal<HouseHold>();
	/*
	 * Payment and Settlement fields
	 */
	protected ThreadLocal<String> cardPaymentMethod = new ThreadLocal<String>();
	protected ThreadLocal<String> cardNumber = new ThreadLocal<String>();
	protected ThreadLocal<String> cardExpirationMonth = new ThreadLocal<String>();
	protected ThreadLocal<String> cardExpirationYear = new ThreadLocal<String>();
	protected ThreadLocal<String> cardHolderName = new ThreadLocal<String>();
	protected ThreadLocal<String> folioId = new ThreadLocal<String>();
	protected ThreadLocal<String> cardAddressLine1 = new ThreadLocal<String>();
	protected ThreadLocal<String> cardAddressLine2 = new ThreadLocal<String>();
	protected ThreadLocal<String> cardState = new ThreadLocal<String>();
	protected ThreadLocal<String> cardPostalCode = new ThreadLocal<String>();

	/*
	 * Define a data provider
	 */
	@DataProvider(name = "dataProvider", parallel=true)
	public Object[][] scenarios() {
		application.set("alc");
		dt.set(new Datatable());
		try {return dt.get().getTestScenariosByApp(application.get(), "ModifyGuestDetails_API");}
		catch (Exception e) {e.printStackTrace();}
		return null;
	}

	/*
	 * Define a method to run prior to the @Test method
	 */
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		application.set("alc");
		this.environment.set(environment);
		dt.set(new Datatable());
		dt.get().setVirtualtablePath(Datatable.ALC_MASTER_DATA_PATH);
	}

	/*
	 * Define a method to run after the @Test method
	 */
	@AfterMethod(alwaysRun = true)
	public synchronized void closeSession(ITestResult test) {
		// If the reservation number is not null and is not an empty string, then cancel the reservation
		if (TPS_ID.get() != null && !TPS_ID.get().isEmpty()) {
			cancel.set(new Cancel(environment.get(), "CancelDiningEvent"));
			cancel.get().setReservationNumber(TPS_ID.get());
			cancel.get().sendRequest();
			TestReporter.log("Cancellation Request Status Code: "+ cancel.get().getResponseStatusCode());
		}
	}

	/*
	 * Define the actual test method
	 */
	@Test(dataProvider = "dataProvider", groups = {"api", "regression", "dining", "eventDiningService"})
	public void testRetrieve(String Scenario, String Role,
			String Location, String FacilityName, String FacilityID,
			String DaysOut, String NumAdults, String NumChildren, String Phone,
			String Email, String Modify_Phone, String Modify_Email,
			String Modify_Cell, String SalesChannel,
			String CommunicationChannel, String SourceAccountingCenter,
			String ComponentType, String ComponentID,
			String RevenueClassificationID, String RevenueClassificationName,
			String ProductID, String ProductType, String ServicePeriodID,
			String DiningServiceScenario, String Payment,
			String DiningServiceModifyScenario, String runTest){
		if (runTest.equalsIgnoreCase("true")) {
			/*
			 * Log the test scenario name in the reporter
			 */
			TestReporter.logScenario(Scenario);
			// Define the booking time for the operations
			bookingDate.set(Randomness.generateCurrentXMLDatetime(Integer.parseInt(DaysOut)));
			TestReporter.logStep("Book Event Dining Reservation");
			/*
			 * Book the event dining reservation
			 */
			eventDiningBook.set(new Book(environment.get(), DiningServiceScenario));
			eventDiningBook.get().setSignInLocation(Location);
			for (int i = 1; i <= ComponentType.split(";").length; i++) {
				eventDiningBook.get().setComponentPriceComponentType(ComponentType.split(";")[i - 1], String.valueOf(i));
				eventDiningBook.get().setComponentPriceComponentId(ComponentID.split(";")[i - 1], String.valueOf(i));
				eventDiningBook.get().setComponentPriceRevenueClassificationId(RevenueClassificationID.split(";")[i - 1], String.valueOf(i));
				eventDiningBook.get().setComponentPriceRevenueClassificationName(RevenueClassificationName.split(";")[i - 1], String.valueOf(i));
			}
			eventDiningBook.get().setFacilityId(FacilityID);
			eventDiningBook.get().setFacilityName(FacilityName);
			
			if(Role.toLowerCase().contains("dlr")) prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityID, "", false, false));
			else{
				prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityID, "", false, true));
				eventDiningBook.get().setProductId(prodId.get().getFirstProductId());
				compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), FacilityID)); 
				int compProdIds = (eventDiningBook.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? eventDiningBook.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
				for(int i = 1; i <= compProdIds; i++){
					eventDiningBook.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);
				}
			}
			
			eventDiningBook.get().setProductType(ProductType);
			eventDiningBook.get().setServicePeriosId(ServicePeriodID);
			eventDiningBook.get().setSourceAccountingCenter(SourceAccountingCenter);
			eventDiningBook.get().setComponentUnitPriceDateTime(bookingDate.get());
			eventDiningBook.get().setServiceStartDateTime(bookingDate.get());
			eventDiningBook.get().setSalesChannel(SalesChannel);
			eventDiningBook.get().setCommunicationChannel(CommunicationChannel);
			salesChannel.set(eventDiningBook.get().getSalesChannel());
			communicationsChannel.set(eventDiningBook.get().getCommunicationsChannel());
			sethouseHold();
			eventDiningBook.get().setPrimaryGuestAddress1(hh.get().primaryGuest().primaryAddress().getAddress1());
			eventDiningBook.get().setPrimaryGuestCity(hh.get().primaryGuest().primaryAddress().getCity());
			eventDiningBook.get().setPrimaryGuestCountry(hh.get().primaryGuest().primaryAddress().getCountry());
			eventDiningBook.get().setPrimaryGuestPostalCode(hh.get().primaryGuest().primaryAddress().getZipCode());
			eventDiningBook.get().setPrimaryGuestState(hh.get().primaryGuest().primaryAddress().getStateAbbv());
			eventDiningBook.get().setPrimaryGuestPartyid("0");

			Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
			eventDiningBook.get().sendRequest();
			if(eventDiningBook.get().getResponse().contains("Row was updated or deleted by another transaction")){
				Sleeper.sleep(Randomness.randomNumberBetween(1, 10) * 1000);
				eventDiningBook.get().sendRequest();
			}
			TestReporter.logAPI(!eventDiningBook.get().getResponseStatusCode().equals("200"), "An error occurred while booking.", eventDiningBook.get());
			TP_ID.set(eventDiningBook.get().getTravelPlanId());
			TPS_ID.set(eventDiningBook.get().getTravelPlanSegmentId());			
			
			retreiveReservation();
			TestReporter.assertEquals(TPS_ID.get(), eventDiningRetrieve.get().getReservationNumber(), "The actual resevation number ["+eventDiningRetrieve.get().getReservationNumber()+"] did not match the expected reservation number ["+TPS_ID.get()+"].");
		}
	}
	
	private void sethouseHold(){
		hh.set(new HouseHold(Randomness.randomNumberBetween(1, 4)));
		Sleeper.sleep(Randomness.randomNumberBetween(1, 20) * 1000);
		hh.get().sendToApi(environment.get());
	}

	/**
	 * Method designed to retrieve detail reservation information
	 */
	private void retreiveReservation() {
		TestReporter.logStep("Retrieve Event Dining Reservation");
		eventDiningRetrieve.set(new Retrieve(environment.get(), "RetrieveDiningEvent"));
		eventDiningRetrieve.get().setReservationNumber(TPS_ID.get());
		eventDiningRetrieve.get().setCommunicationsChannel(communicationsChannel.get());
		eventDiningRetrieve.get().setSalesChannel(salesChannel.get());
		eventDiningRetrieve.get().sendRequest();
		TestReporter.logAPI(!eventDiningRetrieve.get().getResponseStatusCode().equals("200"), "An error occurred while trying to retrieve the reservation.", eventDiningRetrieve.get());
	}

}
