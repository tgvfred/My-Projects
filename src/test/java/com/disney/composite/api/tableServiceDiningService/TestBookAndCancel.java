package com.disney.composite.api.tableServiceDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestBookAndCancel {
	protected String environment = null;
	protected ScheduledEventReservation res;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {this.environment = environment;}

	@Test(dependsOnMethods = {"testBook"}, groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testCancel() {
		res.cancel();
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getCancellationNumber()), "The cancellation number ["+res.getCancellationNumber()+"] was not numeric as expected.");
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testBook(){
		res = new TableServiceDiningReservation(environment);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] was not numeric as expected.");
	}
}
