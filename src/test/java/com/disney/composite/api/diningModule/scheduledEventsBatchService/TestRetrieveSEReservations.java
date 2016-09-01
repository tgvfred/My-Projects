package com.disney.composite.api.diningModule.scheduledEventsBatchService;

import org.testng.annotations.Test;

import com.disney.api.soapServices.SoapException;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveSEReservations;
import com.disney.composite.BaseTest;
import com.disney.utils.TestReporter;

public class TestRetrieveSEReservations  extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveSEReservations_Today(){
	
		TestReporter.logScenario("testRetrieveSEReservations_Today");
		RetrieveSEReservations retrieve = new RetrieveSEReservations(environment);
		retrieve.setProcessDateDaysOut("0");
		retrieve.setDateEqualCondition("=");	
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving SE reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservations()) throw new SoapException("No reservations returned");
	
	}
	

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveSEReservations_Tomorrow(){
	
		TestReporter.logScenario("testRetrieveSEReservations_Tomorrow");
		RetrieveSEReservations retrieve = new RetrieveSEReservations(environment);
		retrieve.setProcessDateDaysOut("1");
		retrieve.setDateEqualCondition("=");	
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving SE reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservations()) throw new SoapException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveSEReservations_NotToday(){
	
		TestReporter.logScenario("testRetrieveSEReservations_NotToday");
		RetrieveSEReservations retrieve = new RetrieveSEReservations(environment);
		retrieve.setProcessDateDaysOut("0");
		retrieve.setDateEqualCondition("!=");	
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving SE reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservations()) throw new SoapException("No reservations returned");
	
	}
	
}
