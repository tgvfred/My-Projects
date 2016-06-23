package com.disney.composite.SEReservationGenerator.showDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
public class TestAssignTableNumbers {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private HouseHold party;
	private String tableNumber = String.valueOf(Randomness.randomNumberBetween(1, 99));
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(4);
		book();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test
	public void testAssignTableNumbers_DefaultTableNumber(){
		tableNumber = res.getTableNumber();
		res.assignTableNumbers();
		TestReporter.assertEquals(res.getAssignTableNumberStatus(), "SUCCESS", "The status ["+res.getStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(tableNumber, res.getTableNumber(), "The table number ["+res.getTableNumber()+"] did not match the expected table number ["+tableNumber+"].");
	}
	
	@Test
	public void testAssignTableNumbers(){
		res.assignTableNumbers(tableNumber);
		TestReporter.assertEquals(res.getAssignTableNumberStatus(), "SUCCESS", "The status ["+res.getStatus()+"] was not 'SUCCESS' as expected.");
		TestReporter.assertEquals(tableNumber, res.getTableNumber(), "The table number ["+res.getTableNumber()+"] did not match the expected table number ["+tableNumber+"].");
	}
	
	private void book(){
		res = new ShowDiningReservation(environment,party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		reservationNumber = res.getConfirmationNumber();
	}
}