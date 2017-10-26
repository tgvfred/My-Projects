package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveShareChain;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveShareChain;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestRetrieveShareChain_Negative extends BaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain", "negative" })
    public void retrieveSharechain_nullRequest() {

        String faultString = "Required parameters are missing";

        RetrieveShareChain retrieve = new RetrieveShareChain(environment, "Main");
        retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveShareChain/request", BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getFaultString().contains(faultString), "Validate correct fault string [ " + faultString + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain", "negative" })
    public void retrieveSharechain_nullTcgId() {

        String faultString = "Required parameters are missing";

        RetrieveShareChain retrieve = new RetrieveShareChain(environment, "Main");
        retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveShareChain/request/travelComponentGroupingId", BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getFaultString().contains(faultString), "Validate correct fault string [ " + faultString + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain", "negative" })
    public void retrieveSharechain_InvalidTcgId() {

        String faultString = "Required parameters are missing";

        RetrieveShareChain retrieve = new RetrieveShareChain(environment, "Main");
        retrieve.setTravelComponentGroupingId("-1");
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getFaultString().contains(faultString), "Validate correct fault string [ " + faultString + " ] exists. Found [ " + retrieve.getFaultString() + " ]", retrieve);
        validateApplicationError(retrieve, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }
}
