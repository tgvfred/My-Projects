package com.disney.composite.folioInterface.payment.makeCardPaymentCardOnFile;

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
	private String paymentScenario = "Pay total amount due with valid visa with incidentals";
	private Reservation res;
	private FolioInterfacePayment payment;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new GenerateReservation().bookResortReservation().BEACH_CLUB(this.environment);
		res.quickBook();
		payment = new FolioInterfacePayment(res);
		payment.makeCardPayment(paymentScenario);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res.getTravelPlanSegmentId() != null)
			if(!res.getTravelPlanSegmentId().isEmpty())
				res.cancelAccommodation();
	}
	
	@Test(groups={"resort", "api"})
	public void testFolioPayment_Resort(){
		TestReporter.logScenario("Make card payment with card on file to Resort reservation.");
		TestReporter.log("Reservation Number: " + res.getTravelPlanSegmentId());
		TestReporter.log("Travel Plan Number: " + res.getTravelPlanId());
		payment.makeCardPaymentCardOnFile(paymentScenario);
	}
}