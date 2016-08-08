package com.disney.composite.api.chargeGroup.modifyChargeGroups;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.applicationError.FolioErrorCode;
import com.disney.api.soapServices.chargeGroup.operations.CancelChargeGroup;
import com.disney.api.soapServices.chargeGroup.operations.CreateChargeGroupsAndPostCharges;
import com.disney.api.soapServices.chargeGroup.operations.ModifyChargeGroups;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestModifyChargeGroups_Negative extends BaseTest{
	protected ThreadLocal<String> number = new ThreadLocal<String>();
	protected CreateChargeGroupsAndPostCharges create = null;
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters({"environment"})
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		number.set(Randomness.randomNumber(12));
		if(number.get().startsWith("0")) number.set(number.get().replaceFirst("0", "1"));
		CreateChargeGroupsAndPostCharges create = new CreateChargeGroupsAndPostCharges(this.environment, "MinimalInfo");
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setTravelPlanComponentGroupId(number.get());
		create.setTravelPlanSegmentId(number.get());
		create.setTravelPlanId(number.get());
		create.sendRequest();
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred creating a charge group.", create);
	}
	
	@AfterTest(alwaysRun=true)
	public void teardown(){
		try{
			CancelChargeGroup cancel = new CancelChargeGroup(this.environment);
			cancel.setReferenceValue("DREAMS_TCG");
			cancel.setReferenceValue(number.get());
			cancel.sendRequest();
		}catch(Exception e){}
	}
	
	protected ModifyChargeGroups create(){
		ModifyChargeGroups modify = new ModifyChargeGroups(this.environment, "MinimalInfo");
		modify.setGuestFirstName(hh.primaryGuest().getFirstName());
		modify.setGuestLastName(hh.primaryGuest().getLastName());
		modify.setTravelPlanComponentGroupId(number.get());
		modify.setTravelPlanSegmentId(number.get());
		modify.setTravelPlanId(number.get());
		return modify;
	}
	
	public class RootChargeGroup extends TestModifyChargeGroups_Negative{		
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			ModifyChargeGroups modify = create();
			modify.setRootDescription(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_DESC);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing Description : Invalid Charge Group - missing description"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void expiredPeriodEndDate(){
			TestReporter.logScenario("Expired Period End Date");
			ModifyChargeGroups modify = create();
			modify.setRootPeriodEndDate(Randomness.generateCurrentXMLDatetime(-45));
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PERIOD);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group Period : ChargeGroup period provided is invalid."), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			ModifyChargeGroups modify = create();
			modify.removeRootPeriod();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PERIOD);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group Period : Invalid Charge Group - missing Period"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceName(){
			TestReporter.logScenario("Missing Primary Reference Name");
			ModifyChargeGroups modify = create();
			modify.setRootPrimaryReferenceName(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue is required for PrimaryReference.<null>,"+super.number.get()), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceValue(){
			TestReporter.logScenario("Missing Primary Reference Value");
			ModifyChargeGroups modify = create();
			modify.setRootPrimaryReferenceValue(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue is required for PrimaryReference.DREAMS_TP,<null>"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReference(){
			TestReporter.logScenario("Missing Primary Reference");
			ModifyChargeGroups modify = create();
			modify.removeRootPrimaryReference();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : Invalid Charge Group - missing primary reference"), modify.getFaultString(), modify);				
		}
	}
	
	public class GuestContainerChargeGroup extends TestModifyChargeGroups_Negative{
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			ModifyChargeGroups modify = create();
			modify.setGuestContainerDescription(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_DESC);						
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing Description : Invalid Charge Group - missing description"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainerPeriod();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PERIOD);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group Period : Invalid Charge Group - missing Period"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceName(){
			TestReporter.logScenario("Missing Primary Reference Name");
			ModifyChargeGroups modify = create();
			modify.setGuestContainerPrimaryReferenceName(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();			

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue is required for PrimaryReference.<null>,"+super.number.get()), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceValue(){
			TestReporter.logScenario("Missing Primary Reference Value");
			ModifyChargeGroups modify = create();
			modify.setGuestContainerPrimaryReferenceValue(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue is required for PrimaryReference.DREAMS_TPS,<null>"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReference(){
			TestReporter.logScenario("Missing Primary Reference");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainerPrimaryReference();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : Invalid Charge Group - missing primary reference"), modify.getFaultString(), modify);			
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingSourceAccountingCenter(){
			TestReporter.logScenario("Missing Source Accounting Center");
			ModifyChargeGroups modify = create();
			modify.setGuestContainerSourceAccountingCenter(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.REQ_FIELD_MISSING);
			TestReporter.logAPI(!modify.getFaultString().contains("Missing required fields. : Invalid GuestChargeGroupContainerRequest. RootReference, SourceAccountingCenterID and ResponsibleParty fields are required."), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingRootReference(){
			TestReporter.logScenario("Missing Root Reference");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainerRootReference();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.REQ_FIELD_MISSING);
			TestReporter.logAPI(!modify.getFaultString().contains("Missing required fields. : Invalid GuestChargeGroupContainerRequest. RootReference, SourceAccountingCenterID and ResponsibleParty fields are required."), modify.getFaultString(), modify);
		}
	}
	
	public class GuestContainedChargeGroup extends TestModifyChargeGroups_Negative{
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingDescription(){
			TestReporter.logScenario("Missing Description");
			ModifyChargeGroups modify = create();
			modify.setGuestContainedDescription(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_DESC);						
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing Description : Invalid Charge Group - missing description"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPeriod(){
			TestReporter.logScenario("Missing Period");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainedPeriod();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PERIOD);						
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group Period : Invalid Charge Group - missing Period"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceName(){
			TestReporter.logScenario("Missing Primary Reference Name");
			ModifyChargeGroups modify = create();
			modify.setGuestContainedPrimaryReferenceName(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();			

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue is required for PrimaryReference.<null>,"+super.number.get()), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReferenceValue(){
			TestReporter.logScenario("Missing Primary Reference Value");
			ModifyChargeGroups modify = create();
			modify.setGuestContainedPrimaryReferenceValue(BaseSoapCommands.REMOVE_NODE.toString());
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);			
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : ReferenceName and ReferenceValue is required for PrimaryReference.DREAMS_TCG,<null>"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingPrimaryReference(){
			TestReporter.logScenario("Missing Primary Reference");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainedPrimaryReference();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.INV_CHRG_GRP_PRI_REF);			
			TestReporter.logAPI(!modify.getFaultString().contains("Invalid Charge Group - missing primary reference : Invalid Charge Group - missing primary reference"), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingAncestorReference(){
			TestReporter.logScenario("Missing Ancestor");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainedAncestor();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.REQ_FIELD_MISSING);			
			TestReporter.logAPI(!modify.getFaultString().contains("Missing required fields. : Invalid GuestChargeGroupContainedRequest. Ancestor, Root, GuaranteeType and TransactionFacility are required fields."), modify.getFaultString(), modify);
		}
		@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
		public void missingRootReference(){
			TestReporter.logScenario("Missing Ancestor");
			ModifyChargeGroups modify = create();
			modify.removeGuestContainedRoot();
			modify.sendRequest();

			validateApplicationError(modify, FolioErrorCode.REQ_FIELD_MISSING);			
			TestReporter.logAPI(!modify.getFaultString().contains("Missing required fields. : Invalid GuestChargeGroupContainedRequest. Ancestor, Root, GuaranteeType and TransactionFacility are required fields."), modify.getFaultString(), modify);
		}
	}
}