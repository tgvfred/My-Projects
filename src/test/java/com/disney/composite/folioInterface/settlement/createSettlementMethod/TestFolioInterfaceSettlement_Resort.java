package com.disney.composite.folioInterface.settlement.createSettlementMethod;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.folioInterface.FolioInterfaceSettlement;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestFolioInterfaceSettlement_Resort {
	private String environment;
	private Reservation res;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new GenerateReservation().bookResortReservation().BEACH_CLUB(this.environment);
		res.quickBook();
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res.getTravelPlanSegmentId() != null)
			if(!res.getTravelPlanSegmentId().isEmpty())
				res.cancelAccommodation();
	}
	
	@Test(groups={"resort", "api"})
	public void testFolioPayment_Resort(){
		TestReporter.logScenario("Create a card settlement for a Resort reservation.");
		TestReporter.log("Reservation Number: " + res.getTravelPlanSegmentId());
		TestReporter.log("Travel Plan Number: " + res.getTravelPlanId());
		FolioInterfaceSettlement settlement = new FolioInterfaceSettlement(res);
		settlement.createSettlementMethod("Pay total amount due with valid Visa, with incidentals with CCV with Express Checkout");
	}
}