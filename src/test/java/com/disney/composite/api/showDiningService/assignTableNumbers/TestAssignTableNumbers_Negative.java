package com.disney.composite.api.showDiningService.assignTableNumbers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.operations.AssignTableNumbers;
import com.disney.api.soapServices.showDiningService.operations.Cancel;
import com.disney.composite.BaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestAssignTableNumbers_Negative extends BaseTest{
	protected String tableNumber = String.valueOf(Randomness.randomNumberBetween(1, 99));
	protected ScheduledEventReservation res = null;
	
	@Override
	@BeforeTest(alwaysRun=true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res = new ShowDiningReservation(this.environment, hh);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
	
	@AfterMethod
	public void teardown(){
		if(res != null)
			if(!res.getConfirmationNumber().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(res.getConfirmationNumber());
				cancel.sendRequest();
			}
	}

    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingReservationNumber(){	
		TestReporter.logStep("Test Missing Reservation Number");
		AssignTableNumbers assign = assign();
		assign.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0", assign);
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingPartySize(){	
		TestReporter.logStep("Test Missing Party Size");
		AssignTableNumbers assign = assign();
		assign.setPartySize(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE.", assign);
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingInventoryDetails(){
		TestReporter.logStep("Test Missing Inventory Details");
		AssignTableNumbers assign = assign();
		assign.setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/inventoryDetails", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("INVALID REQUEST ! : INVENTORY DETAILS MISSING. CANNOT ASSIGN TABLE NUMBERS !", assign);
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingSalesChannel(){
		TestReporter.logStep("Test Missing Sales Channel");
		AssignTableNumbers assign = assign();
		assign.setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null", assign);
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingCommunicationChannel(){
		TestReporter.logStep("Test Missing Communications Channel");
		AssignTableNumbers assign = assign();
		assign.setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null", assign);
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidReservationNumber(){
    	String number = "1234";
		TestReporter.logStep("Test Invalid Reservation Number Using a String");
		AssignTableNumbers assign = assign();
		assign.setTravelPlanSegmentId(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number, assign);
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidPartySize(){
    	String size = Randomness.randomNumber(3);
		TestReporter.logStep("Test Invalid Party Size Using a Large Party");
		AssignTableNumbers assign = assign();
		assign.setPartySize(size);
		sendRequestAndValidateFaultString("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE.", assign);
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidSalesChannel(){
    	String channel = Randomness.randomString(4);
		TestReporter.logStep("Test Invalid Sales Channel");
		AssignTableNumbers assign = assign();
		assign.setSalesChannel(channel);
		sendRequestAndValidateFaultString("Sales Channel is required : null", assign);
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidCommunicationChannel(){
    	String channel = Randomness.randomString(4);
		TestReporter.logStep("Test Invalid Communications Channel");
		AssignTableNumbers assign = assign();
		assign.setCommunicationsChannel(channel);
		sendRequestAndValidateFaultString("communication Channel is required : null", assign);
	}
    
    private AssignTableNumbers assign(){
		AssignTableNumbers assign = new AssignTableNumbers(environment, "Main");
		assign.setPartySize(String.valueOf(hh.getAllGuests().size()));
		assign.setTravelPlanSegmentId(res.getConfirmationNumber());
		assign.setTableNumber(tableNumber);
		return assign;
    }
    
    private void sendRequestAndValidateFaultString(String fault, AssignTableNumbers assign){
		assign.sendRequest();
    	TestReporter.logAPI(!assign.getFaultString().contains(fault), assign.getFaultString() ,assign);
		logItems(assign);
    }
	
	private void logItems(AssignTableNumbers assign){		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "assignTableNumbers", true);
		validateLogs(assign, logValidItems, 10000);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(assign, logInvalidItems);		
	}
}
