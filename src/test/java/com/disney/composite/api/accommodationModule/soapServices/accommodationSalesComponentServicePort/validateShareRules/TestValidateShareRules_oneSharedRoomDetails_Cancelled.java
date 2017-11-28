package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestValidateShareRules_oneSharedRoomDetails_Cancelled extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("true");
        daysOut.set(0);
        nights.set(2);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setIsWdtcBooking(false);
        setValues();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules", "negative" })
    public void testValidateShareRules_oneSharedRoomDetails_Cancelled() {

        Cancel cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.setExternalReferenceNumber(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceNumber"));
        cancel.setExternalReferenceSource(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/externalReferences/externalReferenceSource"));

        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);

        String faultString = "Travel Status is invalid for Share : Travel Status should be Booked, Checking In or Checked In.";

        ValidateShareRules validate = new ValidateShareRules(environment, "Main");
        validate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        validate.setTravelComponentId(getBook().getTravelComponentId());
        validate.setTravelStatus("Cancelled");
        validate.sendRequest();

        TestReporter.assertTrue(validate.getFaultString().contains(faultString), "Verify that the fault string [" + validate.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(validate, AccommodationErrorCode.INVALID_TRAVEL_STATUS_FOR_SHARE);

    }
}
