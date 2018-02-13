package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.checkout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.DVCSalesBaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.Checkout;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.dvcModule.dvcSalesService.accommodationSales.operations.Cancel;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class Checkout_Negative extends BookDVCCashHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setUseDvcResort(true);
        setValues("305669", "5A", "10068", "15");
        setUseExistingValues(true);
        setRetrieveAfterBook(false);
        bookDvcReservation("DVC_RM_TPS_ContractInGoodStatus", 1);
        DVCSalesBaseTest.environment = environment;

    }

    @Test(groups = { "api", "regression", "checkout", "accommodation", "negative", "GCAL" })
    public void TestCheckout_booked() {

        // if (Environment.isSpecialEnvironment(environment)) {
        // if (true) {
        // throw new SkipException("Response states Invalid Booking Type, Fix is in progress");
        // }
        // }

        String faultString = "INVALID REQUEST! : No Checked-In Accommodations found with the External Reference#";
        String tcgId = getFirstBooking().getTravelComponentGroupingId();
        String refType = "RESERVATION";
        String refNumber = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceNumber");
        String refSource = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceSource");

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
        validateApplicationError(checkout, AccommodationErrorCode.INVALID_REQUEST);

    }

    @Test(groups = { "api", "regression", "checkout", "accommodation", "negative", "GCAL" })
    public void TestCheckout_cancelled() {

        String refNumber = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceNumber");
        String refSource = getFirstBooking().getRequestNodeValueByXPath("//externalReferenceSource");
        TestReporter.logScenario("Cancel");

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getFirstBooking().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);
        TestReporter.assertNotNull(cancel.getCancellationNumber(), "The response contains a cancellation number");

        String faultString = "INVALID REQUEST! : No Checked-In Accommodations found with the External Reference#";
        String tcgId = getFirstBooking().getTravelComponentGroupingId();
        String refType = "RESERVATION";

        TestReporter.logScenario("Cancelled Checkout");
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
        validateApplicationError(checkout, AccommodationErrorCode.INVALID_REQUEST);

    }

    @Test(groups = { "api", "regression", "checkout", "accommodation", "negative", "GCAL" })
    public void TestCheckout_nullExtRefDetail() {
        String faultString = "External Reference is required : External Reference Number is missing !";
        String tcgId = getFirstBooking().getTravelComponentGroupingId();
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
        validateApplicationError(checkout, AccommodationErrorCode.EXTERNAL_REFERENCE_NUMBER_REQUIRED);

    }
}
