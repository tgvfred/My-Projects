package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestGetOptionDetail_Negative extends BaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail", "negative" })
    public void TestGetOptionDetail_nullOptionKeyVal() {
        String fault = "INVALID REQUEST ! : Option key value not provided";
        String deviceType = "CANCEL_REASON";
        TestReporter.logScenario("Test - Get Option Detail  - Null Option Key Value");

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        // validation
        // of removing optionKeyValue
        getOptionDetail.setOptionKeyVal(BaseSoapCommands.REMOVE_NODE.toString());
        getOptionDetail.setAccommodationSalesOptionsEnum(deviceType);
        getOptionDetail.sendRequest();

        TestReporter.logAPI(!getOptionDetail.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + getOptionDetail.getFaultString() + " ]", getOptionDetail);
        validateApplicationError(getOptionDetail, AccommodationErrorCode.INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail", "negative" })
    public void TestGetOptionDetail_nullAccommodationSalesOptionsEnum() {

        String fault = "INVALID REQUEST ! : The OptionType parameter passed to getOptions operation is null";
        String optionKeyValue = "3";
        TestReporter.logScenario("Test - Get Option Detail - Null Accommodation Sales Options Enum");

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        // validation
        // of removing AccommodationSalesOptionsEnum
        getOptionDetail.setAccommodationSalesOptionsEnum(BaseSoapCommands.REMOVE_NODE.toString());
        getOptionDetail.setOptionKeyVal(optionKeyValue);
        getOptionDetail.sendRequest();

        TestReporter.logAPI(!getOptionDetail.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + getOptionDetail.getFaultString() + " ]", getOptionDetail);
        validateApplicationError(getOptionDetail, AccommodationErrorCode.INVALID_REQUEST);
    }

}
