package com.disney.composite.api.chargeGroup.createChargeGroupsAndPostCharges;

import org.testng.annotations.Test;

import com.disney.api.soapServices.chargeGroup.operations.CreateChargeGroupsAndPostCharges;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCreateChargeGroupsAndPostCharges extends BaseTest{
	
	@Test 
	public void testMinimalInfo(){
		String number = String.valueOf(Randomness.randomNumber(12));
		CreateChargeGroupsAndPostCharges create = new CreateChargeGroupsAndPostCharges("Development", "MinimalInfo");
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setTravelPlanComponentGroupId(number);
		create.setTravelPlanSegmentId(number);
		create.setTravelPlanId(number);
		create.sendRequest();
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred creating a party.", create);
		TestReporter.logAPI(!create.isSuccessful(), create.getFaultString() ,create);
	}	
}
