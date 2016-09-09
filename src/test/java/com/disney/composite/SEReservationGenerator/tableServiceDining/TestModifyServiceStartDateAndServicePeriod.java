package com.disney.composite.SEReservationGenerator.tableServiceDining;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

/**
 * This test class tests the ability to generate a user-defined household and use that household to book an event dining reservation
 * @author Justin Phlegar
 *
 */
public class TestModifyServiceStartDateAndServicePeriod extends BaseTest{
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	private String serviceStartDate = Randomness.generateCurrentXMLDatetime(60);
	private String servicePeriod = "272257";
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{res.get().cancel();}
		catch(Exception e){}
	}
	
	@Test
	public void testModifyServiceStartDateAndServicePeriod(){
		res.set(new TableServiceDiningReservation(ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book();
		res.get().modify().modifyServiceStartDateAndServicePeriod(serviceStartDate, servicePeriod);
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
	
	@Test
	public void testModifyServiceStartDateAndServicePeriod_UpdatePartyRoles(){
		hh.sendToApi(environment);
		res.set(new TableServiceDiningReservation(ScheduledEventReservation.NOCOMPONENTSNOADDONS));
		book();		
		res.get().modify().modifyServiceStartDateAndServicePeriod(serviceStartDate, servicePeriod);
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
	
	private void book(){
		res.set(new TableServiceDiningReservation(environment, hh));
		res.get().book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getTravelPlanId()), "The travel plan ID ["+res.get().getTravelPlanId()+"] was not numeric as expected.");
		TestReporter.assertTrue(Regex.match("[0-9]+", res.get().getConfirmationNumber()), "The travel plan ID ["+res.get().getConfirmationNumber()+"] was not numeric as expected.");
		TestReporter.assertEquals(res.get().getStatus(), "Booked", "The reservation status ["+res.get().getStatus()+"] was not 'Booked' as expected.");
	}
}