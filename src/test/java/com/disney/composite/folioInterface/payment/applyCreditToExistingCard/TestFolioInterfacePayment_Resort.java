package com.disney.composite.folioInterface.payment.applyCreditToExistingCard;

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
	private String reservationNumber;
	private String paymentScenario = "Pay total amount due with valid visa with incidentals";
	private String creditScenario = "Pay total -$1 with valid Visa, with incidentals with CCV with Express Checkout";
	private Reservation res;
	private FolioInterfacePayment payment;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new GenerateReservation().bookResortReservation().BEACH_CLUB(this.environment);
		res.quickBook();
		reservationNumber = res.getTravelPlanSegmentId();
		payment = new FolioInterfacePayment(res);
		payment.makeCardPayment(paymentScenario);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancelAccommodation();
	}
	
	@Test(groups={"resort"})
	public void testFolioPayment_Resort(){
		TestReporter.log("Reservation Number: " + reservationNumber);
		payment.applyCreditToExistingCard(creditScenario, null);
	}
}