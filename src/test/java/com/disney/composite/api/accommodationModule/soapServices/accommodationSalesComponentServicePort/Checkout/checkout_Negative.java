package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.Checkout;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResm;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class checkout_Negative extends AccommodationBaseTest {
	
	 @Test(groups = { "api", "regression", "checkout", "accommodation", "negative" })
	 public void TestCheckout_booked(){
		 
	String faultString = "INVALID REQUEST ! :  during AccommodationSalesService.checkout() - No Checked-In Accommodations found with the External Reference#4612616";
	String tcgId = getBook().getTravelComponentGroupingId();
	String refType = "RESERVATION";
	String refNumber = "4612616";
	String refSource = "Accovia";
	
	
	Checkout checkout = new Checkout(environment, "main");
	checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
	checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
	checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());;
	checkout.setTravelComponentGroupingId(tcgId);
	checkout.setExternalReferenceType(refType);
	checkout.setExternalReferenceNumber(refNumber);
	checkout.setExternalReferenceSource(refSource);
	checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());;
	checkout.setCheckoutDate(BaseSoapCommands.REMOVE_NODE.toString());
	checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
	checkout.sendRequest();
	
	TestReporter.assertTrue(checkout.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
    validateApplicationError(checkout, LiloResm.INVALID_REQUEST);

	 }

	 @Test(groups = { "api", "regression", "checkout", "accommodation", "negative" })
	 public void TestCheckout_cancelled (){
		 String faultString = "INVALID REQUEST ! :  during AccommodationSalesService.checkout() - No Checked-In Accommodations found with the External Reference#4612616";
			String tcgId = getBook().getTravelComponentGroupingId();
			String refType = "RESERVATION";
			String refNumber = "4612616";
			String refSource = "Accovia";
			
			
			Checkout checkout = new Checkout(environment, "main");
			checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
			checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
			checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());;
			checkout.setTravelComponentGroupingId(tcgId);
			checkout.setExternalReferenceType(refType);
			checkout.setExternalReferenceNumber(refNumber);
			checkout.setExternalReferenceSource(refSource);
			checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());;
			checkout.setCheckoutDate(BaseSoapCommands.REMOVE_NODE.toString());
			checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
			checkout.sendRequest();
			
			TestReporter.assertTrue(checkout.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
		    validateApplicationError(checkout, LiloResm.INVALID_REQUEST);
 
	 }
	 
	 @Test(groups = { "api", "regression", "checkout", "accommodation", "negative" })
	 public void TestCheckout_nullExtRefDetail(){
		 
		String faultString = "External Reference is required : External Reference Number is missing !";
		String tcgId = getBook().getTravelComponentGroupingId();
		String status = "false";
	
		Checkout checkout = new Checkout(environment, "main");
		checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());;
		checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());;
		checkout.setIsBellServiceRequired(status);
		checkout.setIsSameRoomNumberAssigned(status);
		checkout.setTravelComponentGroupingId(tcgId);
		checkout.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setExternalReferenceNumber(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setExternalReferenceSource(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.setCheckoutDate(BaseSoapCommands.REMOVE_NODE.toString());
		checkout.sendRequest();
		 
		TestReporter.assertTrue(checkout.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(checkout, LiloResm.EXTERNAL_REFERENCE_REQUIRED);
    
	 }
}
