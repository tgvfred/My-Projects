package com.disney.composite.api.accommodationModule.soapServices._examples;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;

public class BookInClassWithBundle extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        cancel(getBook().getTravelComponentGroupingId());
    }

    @Test(groups = { "api", "regression", "accommodation", "example" })
    public void bookInClassAndCancelInTestClass() {
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsBundle(true);
        bookReservation();
        cancel(getBook().getTravelComponentGroupingId());
    }
}
