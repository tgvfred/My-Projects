package com.disney.api.soapServices.accommodationModule._examples;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class BookAndMakeFirstNightDeposit extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation" })
    public void ookAndMakePayment() {
        makeFirstNightDeposit();
    }
}
