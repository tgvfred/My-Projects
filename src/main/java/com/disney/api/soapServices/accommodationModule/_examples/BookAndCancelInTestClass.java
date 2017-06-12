package com.disney.api.soapServices.accommodationModule._examples;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;

public class BookAndCancelInTestClass extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        cancel();
    }

    @Test(groups = { "api", "regression", "accommodation" })
    public void bookAndCancelInTestClass() {
        setDaysOut(Randomness.randomNumberBetween(1, 12));
        setNights(Randomness.randomNumberBetween(3, 5));
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        bookReservation();
    }
}
