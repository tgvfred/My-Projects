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
import com.disney.utils.Regex;
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
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootDecription(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing Description : Missing description"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void expiredPeriodEndDate(){
			TestReporter.logScenario("Expired Period End Date");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootEndDate(Randomness.generateCurrentXMLDatetime(-45));
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group Period : ChargeGroup period provided is invalid."), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/period", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group Period : Missing Period"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
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
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference.DREAMS_TP,<null>"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReference(){
			TestReporter.logScenario("Missing Primary Reference");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/rootChargeGroupRequest/primaryReference", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : Missing primary reference"), create.getFaultString(), create);				
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingSourceAccountingCenter(){
			TestReporter.logScenario("Missing Source Accounting Center");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRootSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Missing required fields. : SourceAccountingCenterID is required in ChargeGroupRequest. SourceAccountingCenterID=null"), create.getFaultString(), create);
		}
	}
	
	public class GuestContainerChargeGroup extends TestCreateChargeGroupsAndPostCharges_Negative{
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainerDecription(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing Description : Missing description"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/period", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group Period : Missing Period"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceName(){
			TestReporter.logScenario("Missing Primary Reference Name");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainerPrimaryReferenceName(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference.<null>,"+super.number), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceValue(){
			TestReporter.logScenario("Missing Primary Reference Value");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainerPrimaryReferenceValue(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference.DREAMS_TPS,<null>"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReference(){
			TestReporter.logScenario("Missing Primary Reference");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/primaryReference", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : Missing primary reference"), create.getFaultString(), create);			
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingSourceAccountingCenter(){
			TestReporter.logScenario("Missing Source Accounting Center");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainerSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Missing required fields. : GuestChargeGroupContainerRequest is invalid. RootReference:DREAMS_TP,"+super.number+" SourceAccountingCenterID:null"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingRootReference(){
			TestReporter.logScenario("Missing Root Reference");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainerChargeGroupRequest/rootReference", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			boolean match = Regex.match("Missing required fields. : GuestChargeGroupContainerRequest is invalid. RootReference:null SourceAccountingCenterID:[0-9]+ ResponsibleParty:[0-9]+", "Missing required fields. : GuestChargeGroupContainerRequest is invalid. RootReference:null SourceAccountingCenterID:3 ResponsibleParty:127313850");
			TestReporter.logAPI(!match, create.getFaultString(), create);
		}
	}
	
	public class GuestContainedChargeGroup extends TestCreateChargeGroupsAndPostCharges_Negative{
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainedDecription(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing Description : Missing description"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/period", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group Period : Missing Period"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceName(){
			TestReporter.logScenario("Missing Primary Reference Name");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainedPrimaryReferenceName(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference.<null>,"+super.number), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceValue(){
			TestReporter.logScenario("Missing Primary Reference Value");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainedPrimaryReferenceValue(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue both required for PrimaryReference.DREAMS_TCG,<null>"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReference(){
			TestReporter.logScenario("Missing Primary Reference");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/primaryReference", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Invalid Charge Group - missing primary reference : Missing primary reference"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingSourceAccountingCenter(){
			TestReporter.logScenario("Missing Source Accounting Center");
			CreateChargeGroupsAndPostCharges create = create();
			create.setGuestContainedSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Missing required fields. : SourceAccountingCenterID is required in ChargeGroupRequest. SourceAccountingCenterID=null"), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingAncestorReference(){
			TestReporter.logScenario("Missing Ancestor");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/ancestor", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Missing required fields. : Invalid GuestChargeGroupContainedRequest. Ancestor, Root, GuaranteeType and TransactionFacility are required fields."), create.getFaultString(), create);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingRootReference(){
			TestReporter.logScenario("Missing Ancestor");
			CreateChargeGroupsAndPostCharges create = create();
			create.setRequestNodeValueByXPath("/Envelope/Body/createChargeGroupsAndPostCharges/guestContainedChargeGroupRequest/root", BaseSoapCommands.REMOVE_NODE.toString());
			create.sendRequest();
			TestReporter.logAPI(!create.getFaultString().contains("Missing required fields. : Invalid GuestChargeGroupContainedRequest. Ancestor, Root, GuaranteeType and TransactionFacility are required fields."), create.getFaultString(), create);
		}
	}
}