package com.disney.composite.api.folioModule.chargeGroup.reinstate;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.folioModule.chargeGroup.operations.CancelChargeGroup;
import com.disney.api.soapServices.folioModule.chargeGroup.operations.CreateChargeGroupsAndPostCharges;
import com.disney.api.soapServices.folioModule.chargeGroup.operations.Reinstate;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class TestReinstate_Negative extends BaseTest{
	private String referenceName;
	private String referenceValue;
	private String invalidName = "abcd";
	private String invalidValue = invalidName;
	
	@BeforeClass(alwaysRun=true)
	@Parameters("environment")
	public void testSetup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		cancel(create());
	}
	
	@Override
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
	}
	
	@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
	public void invalidReferenceNameAndType(){
		TestReporter.logScenario("Invalid Reference Name and Type");
		reinstate(invalidName, invalidValue, "Invalid Charge Group reference : Invalid ChargeGroup external reference -  ExternalReference:ExternalReferenceTO[Reference Name = "+invalidName+" Reference Value = "+invalidValue+"] sourceAccountingCenterID = null");
	}
	
	@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
	public void invalidReferenceName(){
		TestReporter.logScenario("Invalid Reference Name");
		reinstate(invalidName, referenceValue, "Invalid Charge Group reference : Invalid ChargeGroup external reference -  ExternalReference:ExternalReferenceTO[Reference Name = "+invalidName+" Reference Value = "+referenceValue+"] sourceAccountingCenterID = null");
		
	}
	@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
	public void invalidReferenceType(){
		TestReporter.logScenario("Invalid Reference Type");
		reinstate(referenceName, invalidValue, "Invalid Charge Group reference : Invalid ChargeGroup external reference -  ExternalReference:ExternalReferenceTO[Reference Name = "+referenceName+" Reference Value = "+invalidValue+"] sourceAccountingCenterID = null");		
	}
	@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
	public void missingReferenceName(){
		TestReporter.logScenario("Missing Reference Name");
		reinstate(BaseSoapCommands.REMOVE_NODE.toString(), referenceValue, "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = null Reference Value = "+referenceValue+"]");
	}
	@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
	public void missingReferenceType(){
		TestReporter.logScenario("Missing Reference Type");
		reinstate(referenceName, BaseSoapCommands.REMOVE_NODE.toString(), "Invalid input fields. : Invalid ChargeGroup ExternalReference-ExternalReferenceTO[Reference Name = "+referenceName+" Reference Value = null]");		
	}
	@Test(groups = {"api", "regression", "folio", "chargeGroupServicePort", "negative"})
	public void invalidStatus(){
		TestReporter.logScenario("Invalid Status");
		CreateChargeGroupsAndPostCharges create = create();
		reinstate(create.getRequestPrimaryReferenceName(), create.getRequestPrimaryReferenceValue(), "Invalid Charge group status : Invalid Transition. Can reinstate only a cancelled charge group UN_EARNED");
		
	}
	
	private CreateChargeGroupsAndPostCharges create(){
		String number = String.valueOf(Randomness.randomNumber(12));
		CreateChargeGroupsAndPostCharges create = new CreateChargeGroupsAndPostCharges("Development", "MinimalInfo");
		create.setGuestFirstName(hh.primaryGuest().getFirstName());
		create.setGuestLastName(hh.primaryGuest().getLastName());
		create.setTravelPlanComponentGroupId(number);
		create.setTravelPlanSegmentId(number);
		create.setTravelPlanId(number);
		create.sendRequest();
		TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred creating a charge group.", create);
		referenceName = create.getRequestPrimaryReferenceName();
		referenceValue = create.getRequestPrimaryReferenceValue();
		return create;
	}
	
	private CancelChargeGroup cancel(CreateChargeGroupsAndPostCharges create){
		CancelChargeGroup cancel = new CancelChargeGroup(environment);
		cancel.setReferenceName(create.getRequestPrimaryReferenceName());
		cancel.setReferenceValue(create.getRequestPrimaryReferenceValue());
		cancel.sendRequest();
		TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the charge group.", cancel);
		return cancel;
	}
	
	private void reinstate(String name, String value, String fault){
		Reinstate reinstate = new Reinstate(environment); 
		reinstate.setReferenceName(name);
		reinstate.setReferenceValue(value);
		reinstate.sendRequest();
		TestReporter.logAPI(!reinstate.getFaultString().contains(fault), reinstate.getFaultString(), reinstate);
	}
}
