package com.disney.composite.api.tableServiceDiningService;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestArrived {
	protected String environment = null;
	protected ScheduledEventReservation res = null;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(String environment) {
		this.environment = environment;
		res = new TableServiceDiningReservation(this.environment);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TestReporter.assertTrue(new Regex().match("[0-9]+", res.getTravelPlanId()), "The travel plan ID ["+res.getTravelPlanId()+"] was not numeric as expected.");
	}

	@Test(groups = {"api", "regression", "dining", "tableServiceDiningService"})
	public void testArrived(){
		res.arrived();
		TestReporter.assertEquals(res.getStatus(), "Arrived", "The reservation status ["+res.getStatus()+"] was not 'Arrived' as expected.");
	}
}
