package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestRetrieve_Negative extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());

        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_invalidLocationId() {

        String fault = "Location Id is Mandatory : Missing Required Parameters";
        Retrieve retrieve = new Retrieve(environment);
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelPlanId(getBook().getTravelPlanId());
        retrieve.setSiebelTravelPlanId("0");
        retrieve.setLocationId("0");
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.LOCATION_ID_IS_MANDATORY);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_invalidTpAndTps() {

        String fault = "Required parameters are missing : Missing Required Parameters";
        Retrieve retrieve = new Retrieve(environment);
        retrieve.setTravelPlanSegmentId("0");
        retrieve.setTravelPlanId("0");
        retrieve.setSiebelTravelPlanId("0");
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_invalidTps() {

        String fault = "RECORD NOT FOUND : No TravelPlan Found !";
        Retrieve retrieve = new Retrieve(environment);
        retrieve.setTravelPlanSegmentId("18384489184");
        retrieve.setTravelPlanId(getBook().getTravelPlanId());
        retrieve.setSiebelTravelPlanId("0");
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.RECORD_NOT_FOUND_EXCEPTION);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_invalidTp() {

        String fault = "RECORD NOT FOUND : No TravelPlan Found !";
        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.setTravelPlanId("46476544747");
        retrieve.setLocationId("51");
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.RECORD_NOT_FOUND_EXCEPTION);

    }

}
