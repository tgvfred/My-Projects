package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchAccommodationsForShare;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchAccommodationsForShare;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class Test_SearchAccommodationsForShare_ROSameDay_Positive extends AccommodationBaseTest {

    @Test
    public void test_SearchAccommodationsForShare_ROSameDay_Positive() {

        SearchAccommodationsForShare search = new SearchAccommodationsForShare(environment, "Main");
    }
}
