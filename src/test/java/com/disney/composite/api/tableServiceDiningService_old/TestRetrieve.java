package com.disney.composite.api.tableServiceDiningService_old;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestRetrieve {
	// Defining global variables
	protected String environment;
	protected ScheduledEventReservation res = null;
	protected HouseHold hh = null;
	protected Integer householdSize = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(
		String environment){this.environment = environment;
		householdSize = 2;
		hh = new HouseHold(householdSize);
		hh.sendToApi(this.environment);
		res = new TableServiceDiningReservation(environment, hh);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}

	@AfterMethod(alwaysRun = true)
	public synchronized void closeSession(ITestResult test) {res.cancel();}

	@Test(groups = {"api", "regression", "dining", "eventDiningService"})
	public void testRetrieve(){TestReporter.assertEquals(res.getNumberOfGuests(), householdSize, "The number of guests ["+res.getNumberOfGuests()+"] does not match the number of expected guests ["+householdSize+"].");}
}
