package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.modify;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Modify;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestModify_Negative_CheckedInReservation extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        bookReservation();

        CheckInHelper helper = new CheckInHelper(Environment.getBaseEnvironmentName(getEnvironment()), getBook());
        helper.checkIn(getLocationId(), getDaysOut(), getNights(), getFacilityId());

        getBook().setTravelPlanId(getBook().getTravelPlanId());
        getBook().setTravelPlanSegementId(getBook().getTravelPlanSegmentId());
        getBook().setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        getBook().setTravelComponentId(getBook().getTravelComponentId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modify", "negative" })
    public void testModify_Negative_CheckedInReservation() {
        String errorMessage = "Checked In Accommodation cannot be modified : Accommodation Sales Modify does not support checked in reservations";
        Modify modify = new Modify(getBook());
        modify.sendRequest();
        validateError(modify, AccommodationErrorCode.CANNOT_MODIFY_CHECKEDIN_ACCOMMADATION, errorMessage);
    }

    private void validateError(Modify modify, ApplicationErrorCode error, String errorMessage) {
        TestReporter.logAPI(!modify.getFaultString().trim().toLowerCase().contains(errorMessage.trim().toLowerCase()), "Validate expected error message [ " + errorMessage + " ] is returned in response [ " + modify.getFaultString() + " ]", modify);
        validateApplicationError(modify, error);
    }
}
