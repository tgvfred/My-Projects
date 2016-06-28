package com.disney.composite.folioInterface.payment.makeFullCardPayment;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.folioInterface.FolioInterfacePayment;
import com.disney.utils.dataFactory.staging.GenerateReservation;
import com.disney.utils.dataFactory.staging.Reservation;

public class TestFolioInterfacePayment_Resort {
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
		TestReporter.logScenario("Make payment in full with a card to Resort reservation.");
		TestReporter.log("Reservation Number: " + res.getTravelPlanSegmentId());
		TestReporter.log("Travel Plan Number: " + res.getTravelPlanId());
		FolioInterfacePayment payment = new FolioInterfacePayment(res);
		payment.makeFullCardPayment();
	}
}