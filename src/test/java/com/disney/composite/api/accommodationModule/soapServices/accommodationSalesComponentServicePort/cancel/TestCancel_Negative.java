package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.cancel;

import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND;
import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.ACCOMMODATION_MUST_BE_BOOKED_TO_CANCEL;
import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED;
import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.utils.TestReporter;

public class TestCancel_Negative extends AccommodationBaseTest {
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "negative" })
    public void testCancel_nullRequest() {
        TestReporter.logScenario("Test - Cancel - Null Request");

        Cancel cancel = new Cancel(environment);
        cancel.sendRequest();

        validateApplicationError(cancel, REQUIRED_PARAMETERS_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "negative" })
    public void testCancel_nullExternalReferenceSourceAndCode() {
        TestReporter.logScenario("Test - Cancel - Null External Reference Source and Code");

        Cancel cancel = new Cancel(environment);
        cancel.setExternalReferenceNumber("1234567890");
        cancel.setExternalReferenceType("Type");
        cancel.sendRequest();

        validateApplicationError(cancel, EXTERNAL_REFERENCE_SOURCE_OR_CODE_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "negative" })
    public void testCancel_tcgLessThanZero() {
        TestReporter.logScenario("Test - Cancel - TCG Less Than Zero");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId("-1");
        cancel.sendRequest();

        validateApplicationError(cancel, REQUIRED_PARAMETERS_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "negative" })
    public void testCancel_alreadyCancelled() {
        TestReporter.logScenario("Test - Cancel - Already Cancelled");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        cancel.sendRequest();

        validateApplicationError(cancel, ACCOMMODATION_MUST_BE_BOOKED_TO_CANCEL);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "negative" })
    public void testCancel_invalidTcg() {
        TestReporter.logScenario("Test - Cancel - Invalid TCG");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId("9999999999999");
        cancel.sendRequest();

        validateApplicationError(cancel, ACCOMMODATIONS_NOT_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "cancel", "negative" })
    public void testCancel_checkedIn() {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();

        CheckInHelper helper = new CheckInHelper(getEnvironment(), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        TestReporter.logScenario("Test - Cancel - Checked In");

        Cancel cancel = new Cancel(environment);
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();

        validateApplicationError(cancel, ACCOMMODATION_MUST_BE_BOOKED_TO_CANCEL);
    }
}
