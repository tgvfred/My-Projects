package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.reinstate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Reinstate;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.Cancel;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestReinstate_nullRoomDetails_Negative extends AccommodationBaseTest {

    Reinstate reinstate;
    Cancel cancel;
    String env;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(Environment.getBaseEnvironmentName(environment));
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
    public void Test_Reinstate_nullRoomDetails_Negative() {

        cancel = new Cancel(environment, "Main");
        cancel.setCancelDate(Randomness.generateCurrentXMLDate());
        cancel.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        cancel.sendRequest();
        TestReporter.assertTrue(cancel.getResponseStatusCode().equals("200"), "Verify that no error occurred cancelling a reservation: " + cancel.getFaultString());

        reinstate = new Reinstate(env, "Main_RoomDetails");
        reinstate.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        reinstate.sendRequest();

        String faultstring = "Travel Component Grouping not found : Travel Component grouping Not Found for TCGId - TPSId";

        validateApplicationError(reinstate, AccommodationErrorCode.TRAVEL_COMPONENT_GROUPING_NOT_FOUND);

        TestReporter.assertEquals(faultstring, reinstate.getFaultString(), "Verify that the fault string [" + reinstate.getFaultString() + "] is that which is expected.[" + faultstring + "]");

    }
}
