package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_nullRequest_Negative extends AccommodationBaseTest {

    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates", "negative" })
    public void Test_CalculateUnsharedRates_nullRequest_Negative() {

        calculate = new CalculateUnsharedRates(environment, "Main_2");
        calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request", BaseSoapCommands.REMOVE_NODE.toString());
        calculate.sendRequest();

        String faultString = "Required parameters are missing : Missing Required Parameters";

        validateApplicationError(calculate, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);

        TestReporter.assertEquals(calculate.getFaultString(), faultString, "Verify that the fault string [" + calculate.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

}
