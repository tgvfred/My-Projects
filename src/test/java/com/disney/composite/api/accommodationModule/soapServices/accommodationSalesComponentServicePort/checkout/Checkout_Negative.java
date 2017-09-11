package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class Checkout_Negative extends AccommodationBaseTest {

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
        bookReservation();
    }

    @Test(groups = { "api", "regression", "checkout", "accommodation", "negative" })
    public void TestCheckout_booked() {

        if (Environment.isSpecialEnvironment(environment)) {
            if (true) {
                throw new SkipException("Response states Invalid Booking Type, Fix is in progress");
            }
        }

        String faultString = "INVALID REQUEST ! :  during AccommodationSalesService.checkout() - No Checked-In Accommodations found with the External Reference#4612616";
        String tcgId = getBook().getTravelComponentGroupingId();
        String refType = "RESERVATION";
        String refNumber = "4612616";
        String refSource = "Accovia";

        Checkout checkout = new Checkout(environment, "main");
        checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());

        checkout.setTravelComponentGroupingId(tcgId);
        checkout.setExternalReferenceType(refType);
        checkout.setExternalReferenceNumber(refNumber);
        checkout.setExternalReferenceSource(refSource);
        checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());

        checkout.setCheckoutDate(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.sendRequest();

        TestReporter.assertTrue(checkout.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(checkout, LiloResmErrorCode.INVALID_REQUEST);

    }

    @Test(groups = { "api", "regression", "checkout", "accommodation", "negative", "debug" })
    public void TestCheckout_cancelled() {

        if (Environment.isSpecialEnvironment(environment)) {
            if (true) {
                throw new SkipException("Response states Invalid Booking Type, Fix is in progress");
            }
        }

        TestReporter.logScenario("Cancel");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));
        cancel.setExternalReferenceCode("Accovia");
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        String extRefValue = getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber");
        String extRefSource = getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource");
        String tcgId = getBook().getTravelComponentGroupingId();
        String refType = "RESERVATION";

        // latest faultString
        String faultString = "INVALID REQUEST ! :  during AccommodationSalesService.checkout() - No Checked-In Accommodations found with the External Reference#" + extRefValue;

        // CM faultString
        // String faultString = "INVALID REQUEST ! : during AccommodationSalesService.checkout() - No Checked-In Accommodations found with the External Reference#4612616";

        TestReporter.logScenario("Cancelled Checkout");
        Checkout checkout = new Checkout(environment, "main");
        checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsBellServiceRequired(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setIsSameRoomNumberAssigned(BaseSoapCommands.REMOVE_NODE.toString());

        checkout.setTravelComponentGroupingId(tcgId);
        checkout.setExternalReferenceType(refType);
        checkout.setExternalReferenceNumber(extRefValue);
        checkout.setExternalReferenceSource(extRefSource);
        checkout.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());

        checkout.setCheckoutDate(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
        checkout.sendRequest();

        TestReporter.assertTrue(checkout.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + checkout.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(checkout, LiloResmErrorCode.INVALID_REQUEST);

    }

    @Test(groups = { "api", "regression", "checkout", "accommodation", "negative" })
    public void TestCheckout_nullExtRefDetail() {

        if (Environment.isSpecialEnvironment(environment)) {
            if (true) {
                throw new SkipException("Response states Invalid Booking Type, Fix is in progress");
            }
        }

        String faultString = "External Reference is required : External Reference Number is missing !";
        String tcgId = getBook().getTravelComponentGroupingId();
        String status = "false";

        Checkout checkout = new Checkout(environment, "main");
        checkout.setEarlyCheckOutReason(BaseSoapCommands.REMOVE_NODE.toString());

        checkout.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());

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
        // validateApplicationError(checkout, AccommodationErrorCode.EXTERNAL_REFERENCE_NUMBER_REQUIRED);

        // latest validation error code
        validateApplicationError(checkout, LiloResmErrorCode.EXTERNAL_REFERENCE_REQUIRED);

    }
}
