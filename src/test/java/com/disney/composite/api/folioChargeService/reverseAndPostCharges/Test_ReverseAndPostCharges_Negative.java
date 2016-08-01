package com.disney.composite.api.folioChargeService.reverseAndPostCharges;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.AutomationException;
import com.disney.api.soapServices.folioCharge.operations.PostCharges;
import com.disney.api.soapServices.folioCharge.operations.ReverseAndPostCharges;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;

public class Test_ReverseAndPostCharges_Negative extends BaseTest{
	@BeforeClass
	@Parameters({ "environment" })
	@Override
	public void setup(@Optional String environment){
		TestReporter.setDebugLevel(1);
		this.environment = "Stage";
	}
	
	@Test
	public void testReverseAndPostCharges_NoChargeDetailAmount(){
		TestReporter.logStep("Reverse Charge and Post new one");
		ReverseAndPostCharges reverseAndPostCharges = new ReverseAndPostCharges(environment, "MinimalInfo");
		
		System.out.println(reverseAndPostCharges.getRequest());
		reverseAndPostCharges.sendRequest();
		
		
	}
}
