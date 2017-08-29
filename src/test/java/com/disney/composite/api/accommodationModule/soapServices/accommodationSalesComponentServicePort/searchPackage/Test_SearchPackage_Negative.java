package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;

public class Test_SearchPackage_Negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_emptyRequest() {

        String faultString = "INVALID REQUEST! : SalesChannel provided is NULL! !";

        SearchPackage search = new SearchPackage(environment, "Main");
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, AccommodationErrorCode.ROOM_DETAIL_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_salesChannelIdOnly() {

        // String faultString = "Unexpected Error occurred\\. : searchPackage\\. : No Packages could be found for channelIDs='\\[1\\]' and bookDate='[a-z A-Z
        // 0-9].*' and arriveDate='null' and packageCode='null' and packageDescription='null' and roomOnly=null";
        String faultString = "Unexpected Error occurred : searchPackage : No Packages could be found for channelIDs=\\'\\[1\\]\\' and bookDate='[a-z A-Z 0-9].*' and arriveDate='null' and packageCode='null' and packageDescription='null' and roomOnly=null";
        SearchPackage search = new SearchPackage(environment, "Main");
        search.setSalesChannelIDs("1");
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, AccommodationErrorCode.UNEXPECTED_ERROR_OCCURRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_nullPackageCode() {

        String book = Randomness.generateCurrentXMLDate();

        String faultString = "Unexpected Error occurred : searchPackage : No Packages could be found for channelIDs=\\'\\[1\\]\\' and bookDate='[a-z A-Z 0-9].*' and arriveDate='[a-z A-Z 0-9].*' and packageCode=' ' and packageDescription='R Room Only' and roomOnly=null";

        SearchPackage search = new SearchPackage(environment, "Main");
        search.setBookingDate(book);
        search.setPackageDescription("R Room Only");
        search.setPackageCode(" ");
        search.setResortArrivalDate(book);
        search.setSalesChannelIDs("1");
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, AccommodationErrorCode.UNEXPECTED_ERROR_OCCURRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_resortArrivalDateOnly() {

        // String faultString = "Validation Failed. : Result size too large. [0-9].* rows selected, which exceeds the maximum of 500";
        String faultString = "INVALID REQUEST! : SalesChannel provided is NULL! !";

        SearchPackage search = new SearchPackage(environment, "Main");
        search.setResortArrivalDate(Randomness.generateCurrentXMLDate());
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, AccommodationErrorCode.ROOM_DETAIL_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage", "negative" })
    public void testSearchPackage_bookingDateOnly() {

        String faultString = "INVALID REQUEST! : SalesChannel provided is NULL! !";

        SearchPackage search = new SearchPackage(environment, "Main");
        search.setBookingDate(Randomness.generateCurrentXMLDate());
        search.sendRequest();

        TestReporter.assertTrue(Regex.match(faultString.replaceAll("\\s", ""), search.getFaultString().replaceAll("\\s", "")), "Regex Validation Passed");
        validateApplicationError(search, AccommodationErrorCode.ROOM_DETAIL_MISSING);
    }

}
