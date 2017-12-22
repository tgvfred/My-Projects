package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestReinstate_tpsOnly_Negative extends AccommodationBaseTest {

    Reinstate reinstate;
    Cancel cancel;
    String env;

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
        env = environment;
    }

    @Test(groups = { "api", "regression", "reinstate", "accommodation", "accommodationsales", "negative" })
    public void Test_Reinstate_tpsOnly_Negative() {

        Sleeper.sleep(3000);
        cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getResponseStatusCode().equals("200"), "Verify that no error occurred cancelling a reservation: " + cancel.getFaultString());

        Sleeper.sleep(3000);
        reinstate = new Reinstate(env, "Main_2");
        reinstate.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.setReinstateReasonCode(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.setIsCancelFeeWaived(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.setLocation(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        reinstate.setCommChannel(BaseSoapCommands.REMOVE_NODE.toString());
        reinstate.sendRequest();

        String faultstring = "Travel Component Grouping not found : Travel Component grouping Not Found for TCGId - TPSId";

        validateApplicationError(reinstate, AccommodationErrorCode.TRAVEL_COMPONENT_GROUPING_NOT_FOUND);

        TestReporter.assertEquals(faultstring, reinstate.getFaultString(), "Verify that the fault string [" + reinstate.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }

}
