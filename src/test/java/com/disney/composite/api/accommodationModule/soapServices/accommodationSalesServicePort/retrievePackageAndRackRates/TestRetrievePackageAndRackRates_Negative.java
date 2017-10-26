package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePackageAndRackRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePackageAndRackRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestRetrievePackageAndRackRates_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates", "negative" })
    public void TestRetrievePackageAndRackRates_invalidAccommComponentId() {

        String fault = "No Accommodation Component found. : NO ACCOMMODATION FOUND WITH COMPONENT ID#";

        TestReporter.logScenario("Test - RetrievePackageAndRackRates  - invalidAccommComponentId");
        RetrievePackageAndRackRates retrievePackage = new RetrievePackageAndRackRates(environment, "Main");
        retrievePackage.setaccomComponentId("12392006679999");
        retrievePackage.setPackageCode("I795T");
        retrievePackage.setTravelPlanSegementId("472921932212");
        retrievePackage.sendRequest();
        TestReporter.logAPI(!retrievePackage.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrievePackage.getFaultString() + " ]", retrievePackage);
        validateApplicationError(retrievePackage, AccommodationErrorCode.NO_ACCOMMODATION_FOUND);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates", "negative" })
    public void TestRetrievePackageAndRackRates_NegativeAccommComponentId() {

        String fault = "Required parameters are missing : null";

        TestReporter.logScenario("Test - RetrievePackageAndRackRates - NegativeAccommComponentId");
        RetrievePackageAndRackRates retrievePackage = new RetrievePackageAndRackRates(environment, "Main");
        retrievePackage.setaccomComponentId("-1239200667");
        retrievePackage.setPackageCode("I795T");
        retrievePackage.setTravelPlanSegementId("472921932212");
        retrievePackage.sendRequest();
        TestReporter.logAPI(!retrievePackage.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrievePackage.getFaultString() + " ]", retrievePackage);
        validateApplicationError(retrievePackage, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates", "negative" })
    public void TestRetrievePackageAndRackRates_NegativeTPS() {

        String fault = "Required parameters are missing : null";

        TestReporter.logScenario("Test - RetrievePackageAndRackRates - Negative");
        RetrievePackageAndRackRates retrievePackage = new RetrievePackageAndRackRates(environment, "Main");
        retrievePackage.setaccomComponentId("1239200667");
        retrievePackage.setPackageCode("I795T");
        retrievePackage.setTravelPlanSegementId("-472921932212");
        retrievePackage.sendRequest();
        TestReporter.logAPI(!retrievePackage.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrievePackage.getFaultString() + " ]", retrievePackage);
        validateApplicationError(retrievePackage, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePackageAndRackRates", "negative" })
    public void TestRetrievePackageAndRackRates_NullRequest() {

        String fault = "Required parameters are missing : null";

        TestReporter.logScenario("Test - RetrievePackageAndRackRates - Null Request");
        RetrievePackageAndRackRates retrievePackage = new RetrievePackageAndRackRates(environment, "Main");
        retrievePackage.setaccomComponentId(BaseSoapCommands.REMOVE_NODE.toString());
        retrievePackage.setPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        retrievePackage.setTravelPlanSegementId(BaseSoapCommands.REMOVE_NODE.toString());
        retrievePackage.sendRequest();
        TestReporter.logAPI(!retrievePackage.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + retrievePackage.getFaultString() + " ]", retrievePackage);
        validateApplicationError(retrievePackage, AccommodationErrorCode.MISSING_REQUIRED_PARAM_EXCEPTION);
    }
}
