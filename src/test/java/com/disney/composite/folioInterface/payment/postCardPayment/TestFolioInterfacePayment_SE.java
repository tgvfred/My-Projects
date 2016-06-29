package com.disney.composite.folioInterface.payment.postCardPayment;

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
	private ScheduledEventReservation res;
	private HouseHold party;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		party = new HouseHold(2);
		res = new ShowDiningReservation(this.environment, party);
		res.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);
	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		if(res.getConfirmationNumber() != null)
			if(!res.getConfirmationNumber().isEmpty())
				res.cancel();
	}
	
	@Test(groups={"SE", "api"})
	public void testFolioPayment_SE(){
		TestReporter.logScenario("Make card payment to Scheduled Event reservation.");
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		TestReporter.log("Travel Plan Number: " + res.getTravelPlanId());
		FolioInterfacePayment payment = new FolioInterfacePayment(res);
		payment.makeCardPayment("Pay total amount due with valid visa with incidentals");
	}
}
