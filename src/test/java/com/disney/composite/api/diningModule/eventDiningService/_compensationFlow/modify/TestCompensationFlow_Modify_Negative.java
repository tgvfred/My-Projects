package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.modify;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.DiningErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Book;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Cancel;
import com.disney.api.soapServices.diningModule.eventDiningService.operations.Modify;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class TestCompensationFlow_Modify_Negative extends BaseTest{
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	private ThreadLocal<String> serviceStartDate = new ThreadLocal<String>();
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		serviceStartDate.set(Randomness.generateCurrentXMLDate(Randomness.randomNumberBetween(30, 90)));
		hh = new HouseHold(1);
		book.set(new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book.get().setParty(hh);
		book.get().setFacilityId("90002032");
		book.get().addDetailsByProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		book.get().setServiceStartDateTime(serviceStartDate.get());
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred during booking: " + book.get().getFaultString(), book.get());
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			Cancel cancel = new Cancel(environment, "CancelDiningEvent");
			cancel.setReservationNumber(book.get().getTravelPlanSegmentId());
			cancel.sendRequest();
		}catch(Exception e){}
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_RIMFail(){
		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.get().getTravelPlanId());
		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setReservableResourceId(book.get().getReservableResourceId(), true);
		modify.setServiceStartDate(Randomness.generateCurrentXMLDate(30));
		modify.setFacilityId(book.get().getRequestFacilityId());
		modify.setExistingRRID(book.get().getReservableResourceId());
		modify.setExistingStartDateTime(book.get().getStartTime());
		modify.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), "An error occurred modifying reservation ["+book.get().getTravelPlanSegmentId()+"]:" + modify.getFaultString(), modify);
		validateApplicationError(modify, DiningErrorCode.INVENTORY_MANAGEMENT_SERVICE_FAILURE);
		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) == Integer.parseInt(modify.getInventoryCountAfter()), "Verify the booked inventory count ["+modify.getInventoryCountBefore()+"] increments from the value prior to modifying ["+modify.getInventoryCountAfter()+"].");
		TestReporter.assertTrue(Integer.parseInt(modify.getExistingInventoryCountBefore()) == Integer.parseInt(modify.getExistingInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getExistingInventoryCountBefore()+"] remains the same as the value prior to modifying ["+modify.getExistingInventoryCountAfter()+"].");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_FolioFail(){
		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.get().getTravelPlanId());
		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setReservableResourceId(book.get().getReservableResourceId(), true);
		modify.setServiceStartDate(book.get().getRequestServiceStartDate());
		modify.setExistingRRID(book.get().getReservableResourceId());
		modify.setExistingStartDateTime(book.get().getStartTime());
		modify.setFacilityId("90002032");
		modify.addDetailsByProductName("Hoop-Dee-Doo-Cat 2-1st Show");
		modify.setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices[1]/unitPrices/taxes/revenueType", BaseSoapCommands.REMOVE_NODE.toString());
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponse().contains("Invalid input fields"), "An error occurred modifying reservation ["+book.get().getTravelPlanSegmentId()+"]:" + modify.getFaultString(), modify);
		validateApplicationError(modify, DiningErrorCode.FOLIO_MANAGEMENT_SERVICE_FAILURE);
		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) == Integer.parseInt(modify.getInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getInventoryCountBefore()+"] remains the same as the value prior to modifying ["+modify.getInventoryCountAfter()+"].");
		TestReporter.assertTrue(Integer.parseInt(modify.getExistingInventoryCountBefore()) == Integer.parseInt(modify.getExistingInventoryCountAfter()), "Verify the existing booked inventory count ["+modify.getExistingInventoryCountBefore()+"] remains the same as the value prior to modifying ["+modify.getExistingInventoryCountAfter()+"].");
	}
}