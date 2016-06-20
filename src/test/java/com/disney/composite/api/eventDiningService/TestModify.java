package com.disney.composite.api.eventDiningService;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.eventDiningService.operations.Book;
import com.disney.api.soapServices.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.eventDiningService.operations.Modify;
import com.disney.api.soapServices.eventDiningService.operations.Retrieve;
import com.disney.utils.Datatable;
import com.disney.utils.Randomness;
import com.disney.utils.RetrieveComponentProductIdByFacilityId;
import com.disney.utils.RetrieveProductIdByFacilityId;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestModify {
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
	protected ThreadLocal<Modify> eventDiningModify = new ThreadLocal<Modify>();
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
	public void testModify(String Scenario, String Role,
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

			/*
			 * Book the event dining reservation
			 */
			TestReporter.logStep("Book Event Dining Reservation");
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
			
			int compProdIds;
			if(Role.toLowerCase().contains("dlr")){
				prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityID, "", false, false));
				eventDiningBook.get().removeComponentPrices();
			}
			else{
				prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityID, "", false, true));
				compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), FacilityID)); 
				compProdIds = (eventDiningBook.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? eventDiningBook.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
				for(int i = 1; i <= compProdIds; i++){eventDiningBook.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);}
			}
			eventDiningBook.get().setProductId(prodId.get().getFirstProductId());
			
			eventDiningBook.get().setProductType(ProductType);
			eventDiningBook.get().setServicePeriosId(ServicePeriodID);
			eventDiningBook.get().setSourceAccountingCenter(SourceAccountingCenter);
			if(!Role.toLowerCase().contains("dlr"))eventDiningBook.get().setComponentUnitPriceDateTime(bookingDate.get());
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
			
			eventDiningBook.get().sendRequest();
			TestReporter.logAPI(!eventDiningBook.get().getResponseStatusCode().equals("200"), "An error occurred during booking.", eventDiningBook.get());
			// Capture the travel plan and reservation numbers
			TP_ID.set(eventDiningBook.get().getTravelPlanId());
			TPS_ID.set(eventDiningBook.get().getTravelPlanSegmentId());

			/*
			 * Modify the reservation by modifying the guest details
			 */
			TestReporter.logStep("Modify Event Dining Reservation");
			eventDiningModify.set(new Modify(environment.get(),	DiningServiceModifyScenario));
			eventDiningModify.get().setTravelPlanId(TP_ID.get());
			eventDiningModify.get().setReservationNumber(TPS_ID.get());
			for (int i = 1; i <= ComponentType.split(";").length; i++) {
				eventDiningModify.get().setComponentPriceComponentType(ComponentType.split(";")[i - 1], String.valueOf(i));
				eventDiningModify.get().setComponentPriceComponentId(ComponentID.split(";")[i - 1], String.valueOf(i));
				eventDiningModify.get().setComponentPriceRevenueClassificationId(RevenueClassificationID.split(";")[i - 1],	String.valueOf(i));
				eventDiningModify.get().setComponentPriceRevenueClassificationName(RevenueClassificationName.split(";")[i - 1],	String.valueOf(i));
			}
			eventDiningModify.get().setFacilityId(FacilityID);
			eventDiningModify.get().setEnterpriseProductId("fx:RemoveNode");
			
			if(!Role.toLowerCase().contains("dlr")){
				prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityID, "", false, true));
				compProdId.set(new RetrieveComponentProductIdByFacilityId(environment.get(), FacilityID)); 
				compProdIds = (eventDiningModify.get().getNumberOfComponentIds() <= compProdId.get().getComponentProductIdsAndTypes().length) ? eventDiningModify.get().getNumberOfComponentIds() : compProdId.get().getComponentProductIdsAndTypes().length;
				for(int i = 1; i <= compProdIds; i++){eventDiningModify.get().setComponentIdAndType(i, compProdId.get().getComponentProductIdsAndTypes()[i-1][0], compProdId.get().getComponentProductIdsAndTypes()[i-1][1]);}
			}
			else prodId.set(new RetrieveProductIdByFacilityId(environment.get(), FacilityID, "", false, false));
			eventDiningModify.get().setProductId(prodId.get().getFirstProductId());
			
			eventDiningModify.get().setProductType(ProductType);
			eventDiningModify.get().setServicePeriosId(ServicePeriodID);
			eventDiningModify.get().setSignInLocation(Location);
			eventDiningModify.get().setComponentUnitPriceDateTime(bookingDate.get());
			eventDiningModify.get().setServiceStartDate(bookingDate.get());
			if (!Modify_Cell.isEmpty())addCell(Modify_Cell);
			if (!Modify_Email.isEmpty())addEmail(Modify_Email);
			if (!Modify_Phone.isEmpty())addPhone(Modify_Phone);			
			eventDiningModify.get().setPrimaryGuestPartyId("0");			
			Sleeper.sleep(Randomness.randomNumberBetween(1, 20) * 1000);
			eventDiningModify.get().sendRequest();
			TestReporter.logAPI(!eventDiningModify.get().getResponseStatusCode().equals("200"), "An error occurred during removing the Add-Ons..", eventDiningModify.get());
			TestReporter.assertTrue(eventDiningModify.get().getResponseStatus().equalsIgnoreCase("success"),"The response from removing add-ons was not that which is expected.");
			
			// Verify that the modifications are returned by the retrieve
			retreiveReservation();
			validateModifications(Modify_Cell, Modify_Phone, Modify_Email);
		}
	}

	/**
	 * Method to add a cell phone number to the reservation
	 * 
	 * @param number - cell phone number to add
	 */
	private void addCell(String number) {
		// If the cell phone node is not to be removed, then enter the cell
		// phone information
		if (number.equalsIgnoreCase("fx:RemoveNode")) {
			try {eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails", number);}
			catch (Exception e) {}
		} else {
			cellNumber.set(number);
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest","fx:AddNode;Node:phoneDetails");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:locatorId");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:guestLocatorId");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:locatorUseType");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:primary");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:deviceType");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:extension");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails","fx:AddNode;Node:number");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/locatorId","0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/guestLocatorId","0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/locatorUseType","PERSONAL");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/primary","false");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/deviceType","CELLULAR");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/extension","0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/number",number);
		}
	}

	/**
	 * Method to add an email to the reservation
	 * 
	 * @param email - email address to add
	 */
	private void addEmail(String email) {
		// If the email node is not to be removed, then enter the email address
		// information
		if (email.equalsIgnoreCase("fx:RemoveNode")) {
			try {eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails",email);}
			catch (Exception e) {}
		} else {
			this.email.set(email);
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest","fx:AddNode;Node:emailDetails");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails","fx:AddNode;Node:locatorId");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails","fx:AddNode;Node:guestLocatorId");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails","fx:AddNode;Node:primary");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails","fx:AddNode;Node:address");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/locatorId","0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/guestLocatorId","0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/primary","false");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/address", email);
		}
	}

	/**
	 * Method to add a home phone to the reservation
	 * 
	 * @param number - phone number to add
	 */
	private void addPhone(String number) {
		/*
		 * For some scenarios, both a cell and home phone will be added. Below
		 * is code to determine if another phone exists on the reservation
		 * request. If so, then the index is adjusted accordingly.
		 */
		String index;
		try {
			eventDiningModify.get().getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails/number");
			index = "2";
		} catch (Exception e) {
			index = "1";
		}
		// If the phone number is not the be removed, then set the phone number
		if (number.equalsIgnoreCase("fx:RemoveNode")) {
			try {eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", number);}
			catch (Exception e) {}
		} else {
			phoneNumber.set(number);
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest","fx:AddNode;Node:phoneDetails");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:locatorId");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:guestLocatorId");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:locatorUseType");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:primary");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:deviceType");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:extension");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]", "fx:AddNode;Node:number");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/locatorId", "0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/guestLocatorId", "0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/locatorUseType", "HOUSEHOLD");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/primary", "false");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/deviceType", "HANDSET");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/extension", "0");
			eventDiningModify.get().setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/phoneDetails["+ index + "]/number", number);
		}
	}

	/**
	 * Method to validate the modifications are returned by the retrieve method
	 * 
	 * @param cell - cell phone value, if any, to validate
	 * @param phone - email value, if any, to validate
	 * @param email - phone value, if any, to validate
	 */
	private void validateModifications(String cell, String phone, String email) {
		// If a cell phone number was added, then validate the value in the response
		TestReporter.logStep("Validate Event Dining Reservation");
		if (!cell.equalsIgnoreCase("fx:RemoveNode")) {
			String locatorUseType = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails[1]/locatorUseType");
			String deviceType = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails[1]/deviceType");
			String number = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails[1]/number");
			TestReporter.assertEquals(locatorUseType, "PERSONAL", "The cell phone locator use type was not [PERSONAL] as expected, but was found to be [" + locatorUseType + "] instead.");
			TestReporter.assertEquals(deviceType,"CELLULAR","The cell phone device type was not [CELLULAR] as expected, but was found to be ["+ deviceType + "] instead.");
			TestReporter.assertEquals(number, cellNumber.get(),"The cell phone number was not [" + cellNumber.get()+ "] as expected, but was found to be [" + number+ "] instead.");
		}
		// If a phone number was added, then validate the value in the response
		if (!phone.equalsIgnoreCase("fx:RemoveNode")) {
			String index;
			try {
				eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails[2]/number");
				index = "2";
			} catch (Exception e) {
				index = "1";
			}
			String locatorUseType = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails["+ index + "]/locatorUseType");
			String deviceType = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails["+ index + "]/deviceType");
			String number = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/phoneDetails["+ index + "]/number");
			TestReporter.assertEquals(locatorUseType,"HOUSEHOLD","The phone locator use type was not [HOUSEHOLD] as expected, but was found to be ["+ locatorUseType + "] instead.");
			TestReporter.assertEquals(deviceType, "HANDSET","The phone device type was not [HANDSET] as expected, but was found to be ["+ deviceType + "] instead.");
			TestReporter.assertEquals(number, phoneNumber.get(),"The phone number was not [" + phoneNumber+ "] as expected, but was found to be [" + number	+ "] instead.");
		}
		// If an email address was added, then validate the value in the response
		if (!email.equalsIgnoreCase("fx:RemoveNode")) {
			String emailAddress = eventDiningRetrieve.get().getResponseNodeValueByXPath("/Envelope/Body/retrieveEventDiningResponse/EventDiningReservation/primaryGuest/emailDetails/address");
			TestReporter.assertEquals(emailAddress, email,"The email address was not [" + this.email+ "] as expected, but was found to be ["+ emailAddress + "] instead.");
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
