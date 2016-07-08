package com.disney.composite.api.chargeGroup.createChargeGroupsAndPostCharges;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.chargeGroup.operations.CancelChargeGroup;
import com.disney.api.soapServices.chargeGroup.operations.CreateChargeGroupsAndPostCharges;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestCreateChargeGroupsAndPostCharges_Negative extends BaseTest{
	private String number = String.valueOf(Randomness.randomNumber(12));
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}	
	
	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			CancelChargeGroup cancel = new CancelChargeGroup(environment);
			cancel.setReferenceValue("DREAMS_TCG");
			cancel.setReferenceValue(number);
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	protected CreateChargeGroupsAndPostCharges create(){
		CreateChargeGroupsAndPostCharges create = new CreateChargeGroupsAndPostCharges("Development", "MinimalInfo");
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setTravelPlanComponentGroupId(number);
		create.setTravelPlanSegmentId(number);
		create.setTravelPlanId(number);
		return create;
	}
	
	public class RootChargeGroup extends TestCreateChargeGroupsAndPostCharges_Negative{		
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootDecription(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing Description : Missing description"), create.getFaultString(), create);
		}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void expiredPeriodEndDate(){
			TestReporter.logScenario("Expired Period End Date");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootEndDate(Randomness.generateCurrentXMLDatetime(-45));
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group Period : ChargeGroup period provided is invalid."), create.getFaultString(), create);
		}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group Period : Missing Period"), create.getFaultString(), create);
		}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceName(){
			TestReporter.logScenario("Missing Primary Reference Name");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootPrimaryReferenceName(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference.<null>,"+super.number), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceValue(){
			TestReporter.logScenario("Missing Primary Reference Value");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootPrimaryReferenceValue(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference."+super.number+",<null>"), create.getFaultString(), create);
		}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReference(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingSourceAccountingCenter(){}
	}
	
	public class GuestContainerChargeGroup{
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingDescription(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void expiredPeriodEndDate(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPeriod(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReferenceName(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReferenceValue(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReference(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingSourceAccountingCenter(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingRootReferenceName(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingRootReferenceValue(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingRootReference(){}
	}
	
	public class GuestContainedChargeGroup{
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingDescription(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void expiredPeriodEndDate(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPeriod(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReferenceName(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReferenceValue(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingPrimaryReference(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingSourceAccountingCenter(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingAncestorReferenceName(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingAncestorReferenceValue(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingAncestorReference(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingRootReferenceName(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingRootReferenceValue(){}
//		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
//		public void missingRootReference(){}
	}
}