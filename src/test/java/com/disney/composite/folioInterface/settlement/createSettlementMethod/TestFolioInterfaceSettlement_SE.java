package com.disney.composite.folioInterface.settlement.createSettlementMethod;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.folioInterface.FolioInterfaceSettlement;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.TableServiceDiningReservation;

public class TestFolioInterfaceSettlement_SE {
	private String environment;
	private ScheduledEventReservation res;
	private HouseHold party;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(2);
		res = new TableServiceDiningReservation(this.environment, party);
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res.getConfirmationNumber() != null)
			if(!res.getConfirmationNumber().isEmpty())
				res.cancel();
	}
	
	@Test(groups={"SE", "api"})
	public void testFolioPayment_SE(){
		TestReporter.logScenario("Create a card settlement for a Scheduled Event reservation.");
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		TestReporter.log("Travel Plan Number: " + res.getTravelPlanId());
		FolioInterfaceSettlement settlement = new FolioInterfaceSettlement(res);
		settlement.createSettlementMethod("Pay total amount due with valid Visa, with incidentals with CCV with Express Checkout");
	}
}
