package com.disney.utils.dataFactory;


import org.testng.annotations.Test;

import com.disney.api.soapServices.dvcModule.membershipService.operations.SearchMemberships;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents;
import com.disney.utils.dataFactory.folioInterface.FolioInterfacePayment;
import com.disney.utils.dataFactory.folioInterface.FolioInterfaceSettlement;
import com.disney.utils.dataFactory.guestFactory.HouseHold;
import com.disney.utils.dataFactory.staging.bookSEReservation.ActivityEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.EventDiningReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class sandbox {
	
	@Test
	public void test(){
		
		HouseHold party = new HouseHold(1);
		ScheduledEventReservation res = new ShowDiningReservation("Stage", party);
		res.setFacilityName("Pioneer Hall");
		res.setProductName("Hoop-Dee-Doo-Cat 1-1st Show");
		res.book(ScheduledEventReservation.NOCOMPONENTSNOADDONS);
	//	FolioInterfacePayment folio = new FolioInterfacePayment(res);
	//	folio.makeFullCardPayment();
		System.out.println(res.getConfirmationNumber());
	}
}
