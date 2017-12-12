package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class Test_SearchAccommodationsForShare_ROSameDay_NullTravelPlanViewTO extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "searchAccommodationsForShare", "negative" })
    public void test_SearchAccommodationsForShare_ROSameDay_NullTravelPlanViewTO() {

        String faultString = "Search Request Not Valid";

        SearchAccommodationsForShare search = new SearchAccommodationsForShare(environment, "Main");
        search.setTravelPlanViewTO(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();

        TestReporter.logAPI(!search.getFaultString().contains(faultString), "Validate correct fault string [ " + faultString + " ] exists. Found [ " + search.getFaultString() + " ]", search);
        validateApplicationError(search, AccommodationErrorCode.INVALID_SEARCH_CRITERIA);

    }
}
