package com.disney.composite.api.folioModule.chargeGroup.createChargeGroupsAndPostCharges;

import org.testng.annotations.Test;

import com.disney.api.soapServices.folioModule.chargeGroup.operations.CreateChargeGroupsAndPostCharges;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;

public class TestCreateChargeGroupsAndPostCharges extends BaseTest{
	
	@Test 
	public void testMinimalInfo(){
		String number = String.valueOf(Randomness.randomNumber(12));
		CreateChargeGroupsAndPostCharges create = new CreateChargeGroupsAndPostCharges(environment, "MinimalInfo");
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setTravelPlanComponentGroupId(number);
		create.setTravelPlanSegmentId(number);
		create.setTravelPlanId(number);
		create.sendRequest();
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), create.getFaultString(), create);
		TestReporter.logAPI(!create.isSuccessful(), create.getFaultString() ,create);

		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "createChargeGroupsAndPostCharges", false);
		validateLogs(create, logItems);
	}	
}