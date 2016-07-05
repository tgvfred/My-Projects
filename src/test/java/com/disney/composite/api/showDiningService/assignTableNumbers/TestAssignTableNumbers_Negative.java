package com.disney.composite.api.showDiningService.assignTableNumbers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
	protected ThreadLocal<ScheduledEventReservation> res = new ThreadLocal<ScheduledEventReservation>();
	protected ThreadLocal<AssignTableNumbers> assign = new ThreadLocal<AssignTableNumbers>();

	@Override
	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
		res.set(new ShowDiningReservation(this.environment, hh));
		res.get().book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		assign.set(new AssignTableNumbers(environment, "Main"));
		assign.get().setPartySize(String.valueOf(hh.getAllGuests().size()));
		assign.get().setTravelPlanSegmentId(res.get().getConfirmationNumber());
		assign.get().setTableNumber(tableNumber);
	}
	
	@AfterMethod
	public void teardown(){
		if(res != null)
			if(!res.get().getConfirmationNumber().isEmpty()){
				Cancel cancel = new Cancel(environment, "CancelDiningEvent");
				cancel.setTravelPlanSegmentId(res.get().getConfirmationNumber());
				cancel.sendRequest();
			}
	}

    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingReservationNumber(){	
		TestReporter.logStep("Test Missing Reservation Number");
		assign.get().setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH 0");
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingPartySize(){	
		TestReporter.logStep("Test Missing Party Size");
		assign.get().setPartySize(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE.");
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingInventoryDetails(){
		TestReporter.logStep("Test Missing Inventory Details");
		assign.get().setRequestNodeValueByXPath("/Envelope/Body/assignTableNumbers/assignTableNumbersRequest/inventoryDetails", BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("INVALID REQUEST ! : INVENTORY DETAILS MISSING. CANNOT ASSIGN TABLE NUMBERS !");
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingSalesChannel(){
		TestReporter.logStep("Test Missing Sales Channel");
		assign.get().setSalesChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("Sales Channel is required : null");
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void missingCommunicationChannel(){
		TestReporter.logStep("Test Missing Communications Channel");
		assign.get().setCommunicationsChannel(BaseSoapCommands.REMOVE_NODE.toString());
		sendRequestAndValidateFaultString("communication Channel is required : null");
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidReservationNumber(){
    	String number = Randomness.randomNumber(4);
		TestReporter.logStep("Test Invalid Reservation Number Using a String");
		assign.get().setTravelPlanSegmentId(number);
		sendRequestAndValidateFaultString("RECORD NOT FOUND : NO RESERVATION FOUND WITH "+number);
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidPartySize(){
    	String size = Randomness.randomNumber(3);
		TestReporter.logStep("Test Invalid Party Size Using a Large Party");
		assign.get().setPartySize(size);
		sendRequestAndValidateFaultString("Invalid PartyMix. Please send valid partymix : INVALID PARTY SIZE.");
    }
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidSalesChannel(){
    	String channel = Randomness.randomString(4);
		TestReporter.logStep("Test Invalid Sales Channel");
		assign.get().setSalesChannel(channel);
		sendRequestAndValidateFaultString("Sales Channel is required : null");
	}
    
    @Test(groups = {"api", "regression", "dining", "showDiningService", "negative"})
    public void invalidCommunicationChannel(){
    	String channel = Randomness.randomString(4);
		TestReporter.logStep("Test Invalid Communications Channel");
		assign.get().setCommunicationsChannel(channel);
		sendRequestAndValidateFaultString("communication Channel is required : null");
	}
    
    private void sendRequestAndValidateFaultString(String fault){
		assign.get().sendRequest();
    	TestReporter.logAPI(!assign.get().getFaultString().contains(fault), assign.get().getFaultString() ,assign.get());
		logItems();
    }
	
	private void logItems(){		
		LogItems logValidItems = new LogItems();
		logValidItems.addItem("ShowDiningServiceIF", "assignTableNumbers", true);
		validateLogs(assign.get(), logValidItems);
		
		LogItems logInvalidItems = new LogItems();
		logInvalidItems.addItem("UpdateInventory", "updateInventory", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "updateInventoryForScheduledEvents", false);
		logInvalidItems.addItem("AccommodationInventoryRequestComponentServiceIF", "retrieveAssignmentOwner", false);
		logInvalidItems.addItem("PartyIF", "retrieveParty", false);
		validateNotInLogs(assign.get(), logInvalidItems);		
	}
}
