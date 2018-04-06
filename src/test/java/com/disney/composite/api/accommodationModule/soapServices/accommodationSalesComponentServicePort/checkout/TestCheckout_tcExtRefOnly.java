package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCheckout_tcExtRefOnly extends BookDVCCashHelper {
    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setUseDvcResort(true);
        int daysOut = Integer.valueOf(Randomness.randomNumberBetween(50, 60));
        setDaysOut(daysOut);
        setArrivalDate(daysOut);
        setDepartureDate(1);
        setValues("305669", "5A", "10068", "15");
        setUseExistingValues(true);
        setRetrieveAfterBook(false);
        bookDvcReservation("DVC_RM_TPS_ContractInGoodStatus", 1);
        DVCSalesBaseTest.environment = environment;
        Modify modify = new Modify(getFirstBooking());
        modify.setEnvironment(environment);
        modify.setTravelStatus("Checked In");
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "Verify that no error occurred modifying booking: " + modify.getFaultString(), modify);
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "negative", "GCAL" })
    public void testCheckout_tcExtRefOnly() {

        String refType = "RESERVATION";
        String refNumber = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceNumber");
        String refSource = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceSource");

        Checkout checkout = new Checkout(getEnvironment(), "main");
        checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setExternalReferenceType(refType);
        checkout.setExternalReferenceNumber(refNumber);
        checkout.setExternalReferenceSource(refSource);
        checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setCheckoutDate(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.sendRequest();

        String faultString = "INVALID REQUEST ! : ExternalReferences and TCG Id needs to be provided";
        TestReporter.assertEquals(faultString, checkout.getFaultString(), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(checkout, AccommodationErrorCode.INVALID_REQUEST);
    }
}