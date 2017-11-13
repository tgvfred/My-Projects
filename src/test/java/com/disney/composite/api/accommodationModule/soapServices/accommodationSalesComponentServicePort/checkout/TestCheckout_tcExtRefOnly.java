package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestCheckout_tcExtRefOnly extends AccommodationBaseTest {
    private CheckInHelper helper;
    private String locVar;

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        locVar = environment;
        bookReservation();
    }

    @Test(groups = { "api", "regression", "checkout", "Accommodation", "negative" })
    public void testCheckout_tcExtRefOnly() {
        helper = new CheckInHelper(locVar, getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        String refType = "RESERVATION";
        String refNumber = getExternalRefNumber();
        String refSource = getExternalRefSource();

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

        String faultString = "INVALID REQUEST! : ExternalReferences and TCG Id needs to be provided";
        TestReporter.assertEquals(faultString, checkout.getFaultString(), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(checkout, AccommodationErrorCode.INVALID_REQUEST);
    }
}