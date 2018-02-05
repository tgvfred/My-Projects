package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.autoReinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoReinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestAutoReinstate_nullCommunicationChannel_negative extends AccommodationBaseTest {

    AutoReinstate auto;
    Cancel cancel;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(45);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "autoReinstate", "negative" })
    public void Test_AutoReinstate_nullCommunicationChannel_negative() {

        cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getResponseStatusCode().equals("200"), "Verify that no error occurred cancelling a reservation: " + cancel.getFaultString());

        auto = new AutoReinstate(environment, "Main");
        auto.setCommunicationChannel(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setFreezeId(BaseSoapCommands.REMOVE_NODE.toString());
        auto.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        auto.sendRequest();

        String faultString = "communication Channel is required : null";

        validateApplicationError(auto, AccommodationErrorCode.COMMUNICATION_CHANNEL_REQUIRED);
        TestReporter.assertEquals(faultString, auto.getFaultString(), "Verify that the fault string [" + auto.getFaultString() + "] is that which is expected.[" + faultString + "]");
    }

}
