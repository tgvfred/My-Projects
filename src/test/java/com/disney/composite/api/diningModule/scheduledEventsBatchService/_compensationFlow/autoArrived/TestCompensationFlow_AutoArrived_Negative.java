package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow.autoArrived;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveTravelPlanSegmentsForAutoArrival;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCompensationFlow_AutoArrived_Negative extends BaseTest{
	private String date = Randomness.generateCurrentXMLDatetime(1);
	private NodeList reservations;
	private String reservation;
	private String sourceAccountingCenter = "3";
	private String rrId;
	private String startDateTime;

	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		TestReporter.logScenario("RetrieveTravelPlanSegmentsForAutoArrival");
		RetrieveTravelPlanSegmentsForAutoArrival  retrieve = new RetrieveTravelPlanSegmentsForAutoArrival(environment, "Main");
		retrieve.setProcessDate(date);
		retrieve.setSourceAccountingCenter(sourceAccountingCenter);
		retrieve.sendRequest();
		TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto arrival: " + retrieve.getFaultString(), retrieve);
		if(reservation == null)reservations = retrieve.getAllReservationNumbers();
		TestReporter.assertTrue(reservations.getLength() > 0, "No reservations were returned for the date ["+date+"]");
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "compensation"})
	public void TestCompensationFlow_AutoArrived_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "compensation"})
	public void TestCompensationFlow_AutoArrived_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}
