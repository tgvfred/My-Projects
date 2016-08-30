package com.disney.composite.api.diningModule.scheduledEventsBatchService._compensationFlow._invalid.checkout;

import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations.RetrieveReservationsForAutoCheckout;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCompensationFlow_Checkout_Negative extends BaseTest{
	private String date;
	private Map<String, String> reservations = new HashMap<String, String>();
	private int maxDaysOut = 45;
	
	@Override
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		TestReporter.logScenario("RetrieveReservationsForAutoCheckout");
		RetrieveReservationsForAutoCheckout retrieve = new RetrieveReservationsForAutoCheckout(environment, "ForAcctCntrSE_WDW");
		int daysOut = 0;
		do{
			daysOut++;
			date = Randomness.generateCurrentXMLDatetime(daysOut);
			retrieve.setProcessDate(date);
			retrieve.sendRequest();
			TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving reservations for auto checkout: " + retrieve.getFaultString(), retrieve);
			reservations = retrieve.getAllReservationNumbers();
		}while(reservations.size() == 0 && daysOut <= maxDaysOut);
		TestReporter.assertTrue(reservations.size() > 0, "Verify reservations were returned between ["+Randomness.generateCurrentXMLDatetime(0)+"] and ["+Randomness.generateCurrentXMLDatetime(maxDaysOut)+"].");
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative", "compensation"})
	public void TestCompensationFlow_Checkout_Negative_DineFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
	
	@Test(groups = {"api", "regression", "dining", "scheduledEventsBatchService", "negative", "compensation"})
	public void TestCompensationFlow_Checkout_Negative_FolioFail(){
		throw new SkipException("The testing solution for this scenario has not been determined.");
	}
}