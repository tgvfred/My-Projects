package com.disney.composite.api.diningModule.eventDiningService._compensationFlow.modify;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
	
	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		book.set(new Book(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book.get().setParty(hh);
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
		hh = new HouseHold("2 Adults");
		Modify modify = new Modify(environment, ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		modify.setTravelPlanId(book.get().getTravelPlanId());
		modify.setReservationNumber(book.get().getTravelPlanSegmentId());
		modify.setParty(hh);
		modify.setFreezeIdForError(Randomness.randomAlphaNumeric(36));
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID"), modify.getFaultString() ,modify);
		TestReporter.assertTrue(Integer.parseInt(modify.getInventoryCountBefore()) == Integer.parseInt(modify.getInventoryCountAfter()), "Verify the booked inventory count ["+modify.getInventoryCountAfter()+"] for reservable resource ID ["+modify.getReservableResourceId()+"] does not increment from the count prior to booking ["+modify.getInventoryCountBefore()+"]");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}

	@Test(groups = {"api", "regression", "dining", "eventDiningService", "negtive", "compensation"})
	public void TestCompensationFlow_Modify_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}