package com.disney.composite.folioInterface.payment.postCheckPayment;

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
	private Reservation res;
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("environment")
	public void setup(String environment){
		this.environment = environment;
		res = new GenerateReservation().bookResortReservation().BEACH_CLUB(this.environment);
		res.quickBook();
		reservationNumber = res.getTravelPlanSegmentId();
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
		FolioInterfacePayment payment = new FolioInterfacePayment(res);
		payment.makeCheckPayment();
	}	
}