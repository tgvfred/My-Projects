package com.disney.composite.folioInterface.payment.postCheckPayment;

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
		if(reservationNumber != null)
			if(!reservationNumber.isEmpty())
				res.cancel();
	}
	
	@Test(groups={"SE"})
	public void testFolioPayment_SE(){
		TestReporter.log("Reservation Number: " + res.getConfirmationNumber());
		FolioInterfacePayment payment = new FolioInterfacePayment(res);
		payment.makeCheckPayment();
	}
}
