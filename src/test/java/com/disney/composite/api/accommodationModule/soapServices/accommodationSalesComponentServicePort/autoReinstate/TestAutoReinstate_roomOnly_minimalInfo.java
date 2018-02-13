package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AutoReinstateHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestAutoReinstate_roomOnly_minimalInfo extends AccommodationBaseTest {

    AutoReinstate auto;
    Cancel cancel;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(20);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate" })
    public void Test_AutoReinstate_roomOnly_minimalInfo() {

        Cancel cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "MainCancel");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        // Add a wait to avoid async issues
        Sleeper.sleep(5000);

        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation." + cancel.getFaultString(), cancel);
        Sleeper.sleep(3000);
        auto = new AutoReinstate(environment, "Main");
        auto.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        auto.sendRequest();
        TestReporter.logAPI(!auto.getResponseStatusCode().equals("200"), "An error occurred while reinstating: " + auto.getFaultString(), auto);

        validations();
    }

    public void validations() {
        AutoReinstateHelper helper = new AutoReinstateHelper(Environment.getBaseEnvironmentName(environment), getBook().getTravelPlanId(), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());

        helper.validateReservationBookedStatus();
        helper.validateCancellationNumber();
        helper.validateReinstateRecord();
        helper.validateRIMInventory();
        helper.validateChargeGroups();
        helper.validateChargeItems();

        int numExpectedRecords = 5;
        helper.validateFolioItems(numExpectedRecords);
    }

}
