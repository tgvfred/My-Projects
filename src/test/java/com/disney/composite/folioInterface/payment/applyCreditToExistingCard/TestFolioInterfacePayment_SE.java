package com.disney.composite.folioInterface.payment.applyCreditToExistingCard;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.folioInterface.FolioInterfacePayment;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class TestFolioInterfacePayment_SE {
	private String environment;
	private String reservationNumber;
	private ScheduledEventReservation res;
	private FolioInterfacePayment payment;
	private HouseHold party;
	private String paymentScenario = "Pay total amount due with valid visa with incidentals";
	private String creditScenario = "Pay total -$1 with valid Visa, with incidentals with CCV with Express Checkout";
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(2);
		res = new ShowDiningReservation(this.environment, party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
		payment = new FolioInterfacePayment(res);
		payment.makeCardPayment(paymentScenario);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test(groups={"SE"})
	public void testFolioPayment_SE(){
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		payment.applyCreditToExistingCard(creditScenario, null);
	}
}
