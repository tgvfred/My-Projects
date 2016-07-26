package com.disney.composite.api.chargeGroup.modifyChargeGroups;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.chargeGroup.operations.CreateChargeGroupsAndPostCharges;
import com.disney.api.soapServices.chargeGroup.operations.ModifyChargeGroups;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestModifyChargeGroups extends BaseTest{
	protected String number;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters({"environment"})
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		number = String.valueOf(Randomness.randomNumberBetween(1000, 9999));
		CreateChargeGroupsAndPostCharges create = new CreateChargeGroupsAndPostCharges(environment, "MinimalInfo");
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setTravelPlanComponentGroupId(number);
		create.setTravelPlanSegmentId(number);
		create.setTravelPlanId(number);
		create.sendRequest();
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred creating a charge group.", create);
	}
	
	@Test 
	public void testMinimalInfo(){
		ModifyChargeGroups modify = new ModifyChargeGroups(environment, "MinimalInfo");
		modify.setRootPrimaryReferenceValue(number);
		modify.setGuestContainedPrimaryReferenceValue(number);
		modify.setGuestContainedRootReferenceValue(number);
		modify.setGuestContainerPrimaryReferenceValue(number);
		modify.setGuestContainerRootReferenceValue(number);
		modify.sendRequest();
		TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred modifying a charge group.", modify);
		TestReporter.assertEquals(modify.getReturnParameter(), "success", "The response parameter ["+modify.getReturnParameter()+"] was not [success] as expected.");
		
		LogItems logItems = new LogItems();
		logItems.addItem("ChargeGroupIF", "modifyChargeGroups", false);
		validateLogs(modify, logItems);
	}	
}