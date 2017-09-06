package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestReinstate_nullCommunicationChannel_Negative extends AccommodationBaseTest {

    Reinstate reinstate;

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
        isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodationsales", "negative" })
    public void Test_Reinstate_nullCommunicationChannel_Negative() {

        reinstate = new Reinstate(environment, "Main_2");
        reinstate.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        reinstate.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        reinstate.setCommChannel(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.sendRequest();

        String faultstring = "communication Channel is required : null";

        validateApplicationError(reinstate, LiloResmErrorCode.COMMUNICATION_CHANNEL_REQUIRED);

        TestReporter.assertEquals(faultstring, reinstate.getFaultString(), "Verify that the fault string [" + reinstate.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
