package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an show dining reservation
 * @author Justin Phlegar
 *
 */
public class TestAssignTableNumbers extends BaseTest{
	private ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private ThreadLocal<HouseHold> party = new ThreadLocal<HouseHold>();
	private ThreadLocal<String> tableNumber = new ThreadLocal<String>();
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party.set(new HouseHold(4));
		book();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testAssignTableNumbers_DefaultTableNumber(){
		tableNumber.set(res.get().getTableNumber());
		res.get().assignTableNumbers();
		TestReporter.assertEquals(res.get().getAssignTableNumberStatus(), "SUCCESS", "The status ["+res.get().getStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(tableNumber, res.get().getTableNumber(), "The table number ["+res.get().getTableNumber()+"] did not match the expected table number ["+tableNumber+"].");
	}
	
	@Test
	public void testAssignTableNumbers(){
		tableNumber.set(String.valueOf(Randomness.randomNumberBetween(1, 99)));
		res.get().assignTableNumbers(tableNumber.get());
		TestReporter.assertEquals(res.get().getAssignTableNumberStatus(), "SUCCESS", "The status ["+res.get().getStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(tableNumber, res.get().getTableNumber(), "The table number ["+res.get().getTableNumber()+"] did not match the expected table number ["+tableNumber+"].");
	}
	
	private void book(){
		res.set(new ShowDiningReservation(environment, party.get()));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
}