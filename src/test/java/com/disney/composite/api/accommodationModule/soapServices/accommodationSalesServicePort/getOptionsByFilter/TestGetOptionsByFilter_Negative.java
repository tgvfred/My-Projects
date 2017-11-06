package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionsByFilter;

import static com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode.INVALID_REQUEST;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionsByFilter;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestGetOptionsByFilter_Negative extends BaseTest {
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter", "negative" })
    public void testGetOptionsByFilter_NullAccommodationSalesOptionsEnum() {
        TestReporter.logScenario("Test - Get Options By Filter - Null AccommodationSalesOptionsEnum");

        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/accommodationSalesOptionsEnum", BaseSoapCommands.REMOVE_NODE.toString());
        sendRequestAndValidateApplicationError(getOptionsByFilter, INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter", "negative" })
    public void testGetOptionsByFilter_EmptyAccommodationSalesOptionsEnum() {
        TestReporter.logScenario("Test - Get Options By Filter - Empty AccommodationSalesOptionsEnum");

        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/accommodationSalesOptionsEnum", BaseSoapCommands.BLANK.toString());
        sendRequestAndValidateApplicationError(getOptionsByFilter, INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter", "negative" })
    public void testGetOptionsByFilter_REGION_WithNoFilter() {
        TestReporter.logScenario("Test - Get Options By Filter - REGION With No Filter");

        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setAccommodationSalesOptionsEnum("REGION");
        sendRequestAndValidateApplicationError(getOptionsByFilter, INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter", "negative" })
    public void testGetOptionsByFilter_COMMUNICATIONCHANNELS_WithFilter() {
        TestReporter.logScenario("Test - Get Options By Filter - COMMUNICATIONCHANNELS With Filter");

        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setAccommodationSalesOptionsEnum("COMMUNICATIONCHANNELS");
        getOptionsByFilter.addOptionFilters("key", "value");
        sendRequestAndValidateApplicationError(getOptionsByFilter, INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter", "negative" })
    public void testGetOptionsByFilter_SALESCHANNELS_WithFilter() {
        TestReporter.logScenario("Test - Get Options By Filter - SALESCHANNELS With Filter");

        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setAccommodationSalesOptionsEnum("SALESCHANNELS");
        getOptionsByFilter.addOptionFilters("key", "value");
        sendRequestAndValidateApplicationError(getOptionsByFilter, INVALID_REQUEST);
    }

    /*
     * Utility Functions
     */
    private void sendRequestAndValidateApplicationError(GetOptionsByFilter getOptionsByFilter, ApplicationErrorCode error) {
        getOptionsByFilter.sendRequest();
        TestReporter.logAPI(getOptionsByFilter.getResponseStatusCode().equals("200"), "The request was incorrectly successful", getOptionsByFilter);
        validateApplicationError(getOptionsByFilter, error);
    }
}
