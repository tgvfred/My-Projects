package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateGuaranteeStatusHelper;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_UpdateGuaranteeStatus_wdtc_AGENT extends AccommodationBaseTest {

    private String environment;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        isComo.set("false");

    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus" })
    public void testUpdateGuaranteeStatus_wdtc_AGENT() {

        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        setIsWdtcBooking(true);
        bookReservation();

        UpdateGuaranteeStatusHelper helper = new UpdateGuaranteeStatusHelper(environment);

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("AGENT");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        if (Environment.isSpecialEnvironment(environment)) {
            // Validation when running in Latest_CM
            helper.validation(getBook().getTravelPlanId(), "AGENT_GUARANTEED", "GROUP_GUARANTEED", "Y");
        } else {
            // Validation when running in Latest
            helper.validation(getBook().getTravelPlanId(), "DEPOSIT", "AGENT_GUARANTEED", "Y");

        }
    }

}
