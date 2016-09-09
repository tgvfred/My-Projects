package com.disney.composite.api.diningModule.scheduledEventsBatchService;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveStagedReservationsForMassCancellation;
import com.disney.utils.TestReporter;

public class TestRetrieveStagedReservationsForMassCancellation  extends BaseTest{

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_MASS_REINSTATE(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_MASS_REINSTATE");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("MASS_REINSTATE");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_MASS_CANCEL(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_MASS_CANCEL");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("MASS_CANCEL");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_MASS_PRINTCOLLATERAL(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_MASS_PRINTCOLLATERAL");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("MASS_PRINTCOLLATERAL");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_MASS_KTTW(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_MASS_KTTW");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("MASS_KTTW");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}

	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_MASS_CHECKIN(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_MASS_CHECKIN");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("MASS_CHECKIN");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_MASS_MODIFY(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_MASS_MODIFY");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("MASS_MODIFY");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_ROOMINGLIST(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_ROOMINGLIST");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("ROOMINGLIST");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_REMOVEGROUP(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_REMOVEGROUP");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("REMOVEGROUP");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService"})
	public void testRetrieveStagedReservationsForMassCancellation_SE_MASS_CANCEL(){
	
		TestReporter.logScenario("RetrieveStagedReservationsForMassCancellation_SE_MASS_CANCEL");
		RetrieveStagedReservationsForMassCancellation retrieve = new RetrieveStagedReservationsForMassCancellation(environment);
		retrieve.setProcessType("SE_MASS_CANCEL");
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving staged reservations: " + retrieve.getFaultString(), retrieve);
		if(0==retrieve.getNumberOfReservationProcessesReturned()) throw new SkipException("No reservations returned");
	
	}
}
