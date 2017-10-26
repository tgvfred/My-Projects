package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.applicationError.LiloSystemErrorCode;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_Negative extends AccommodationBaseTest {
    private static String env;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        env = environment;
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_emptyRequest() {

        String faultString = "Unexpected Error occurred : searchPackage : Result size too large. [0-9].* rows selected, which exceeds the maximum of 500";

        SearchPackage search = new SearchPackage(env, "Main");
        search.setPackageDescription(BaseSoapCommands.REMOVE_NODE.toString());
        search.setBookingDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.setPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        search.setSalesChannelIDs(BaseSoapCommands.REMOVE_NODE.toString());
        search.setResortArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        // TestReporter.assertEquals(faultString, search.getFaultString(), "Verify that the fault string [" + search.getFaultString() + "] is that which is
        // expected.[" + faultString + "]");
        validateApplicationError(search, LiloSystemErrorCode.UNEXPECTED_ERROR);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_salesChannelIdOnly() {

        // String faultString = "Unexpected Error occurred\\. : searchPackage\\. : No Packages could be found for channelIDs='\\[1\\]' and bookDate='[a-z A-Z
        // 0-9].*' and arriveDate='null' and packageCode='null' and packageDescription='null' and roomOnly=null";
        String faultString = "Unexpected Error occurred : searchPackage : No Packages could be found for channelIDs=\\'\\[1\\]\\' and bookDate='[a-z A-Z 0-9].*' and arriveDate='null' and packageCode='null' and packageDescription='null' and roomOnly=null";
        SearchPackage search = new SearchPackage(env, "Main");
        search.setSalesChannelIDs("1");
        search.setPackageDescription(BaseSoapCommands.REMOVE_NODE.toString());
        search.setBookingDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.setPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        search.setResortArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, LiloSystemErrorCode.UNEXPECTED_ERROR);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_nullPackageCode() {

        String book = Randomness.generateCurrentXMLDate();

        String faultString = "Unexpected Error occurred : searchPackage : No Packages could be found for channelIDs=\\'\\[1\\]\\' and bookDate='[a-z A-Z 0-9].*' and arriveDate='[a-z A-Z 0-9].*' and packageCode=' ' and packageDescription='R Room Only' and roomOnly=null";

        SearchPackage search = new SearchPackage(env, "Main");
        search.setBookingDate(book);
        search.setPackageDescription("R Room Only");
        search.setPackageCode(" ");
        search.setResortArrivalDate(book);
        search.setSalesChannelIDs("1");
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, LiloSystemErrorCode.UNEXPECTED_ERROR);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_resortArrivalDateOnly() {

        // String faultString = "Validation Failed. : Result size too large. [0-9].* rows selected, which exceeds the maximum of 500";
        String faultString = "Unexpected Error occurred : searchPackage : Result size too large. [0-9].* rows selected, which exceeds the maximum of 500";

        SearchPackage search = new SearchPackage(env, "Main");
        search.setResortArrivalDate(Randomness.generateCurrentXMLDate());
        search.setSalesChannelIDs(BaseSoapCommands.REMOVE_NODE.toString());
        search.setPackageDescription(BaseSoapCommands.REMOVE_NODE.toString());
        search.setBookingDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.setPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        // TestReporter.assertEquals(faultString, search.getFaultString(), "Verify that the fault string [" + search.getFaultString() + "] is that which is
        // expected.[" + faultString + "]");
        validateApplicationError(search, LiloSystemErrorCode.UNEXPECTED_ERROR);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_bookingDateOnly() {

        String faultString = "Unexpected Error occurred : searchPackage : Result size too large. [0-9].* rows selected, which exceeds the maximum of 500";

        SearchPackage search = new SearchPackage(env, "Main");
        search.setBookingDate(Randomness.generateCurrentXMLDate());
        search.setPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        search.setResortArrivalDate(Randomness.generateCurrentXMLDate());
        search.setSalesChannelIDs(BaseSoapCommands.REMOVE_NODE.toString());
        search.setPackageDescription(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        // TestReporter.assertEquals(faultString, search.getFaultString(), "Verify that the fault string [" + search.getFaultString() + "] is that which is
        // expected.[" + faultString + "]");
        validateApplicationError(search, LiloSystemErrorCode.UNEXPECTED_ERROR);
    }

}
