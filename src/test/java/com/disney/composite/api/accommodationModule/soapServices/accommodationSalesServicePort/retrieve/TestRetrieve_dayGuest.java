package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.travelPlanModule.travelPlanService.operations.AddGuest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_dayGuest extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("true");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_dayGuest() {

        AddGuest addGuest = new AddGuest(environment, "dayGuest");
        addGuest.setTravelPlanId(getBook().getTravelPlanId());
        addGuest.setLocationId(getLocationId());
        addGuest.setAge(getHouseHold().primaryGuest().getAge());
        addGuest.setAgeType("ADULT");
        addGuest.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        addGuest.setGuestLastName(getHouseHold().primaryGuest().getLastName());

        addGuest.setAccessPeriodEndDate(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        addGuest.setAccessPeriodStartDate(Randomness.generateCurrentXMLDate(getDaysOut()));
        addGuest.setAccessPeriodFacilityId(getFacilityId());
        addGuest.setAccessPeriodResourceNumber(getRoomTypeCode());
        addGuest.sendRequest();

        TestReporter.logAPI(!addGuest.getResponseStatusCode().equals("200"), "An error occurred adding guest", addGuest);

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(getBook().getTravelPlanId());
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        // Old vs New
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            Retrieve clone = (Retrieve) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(getEnvironment()));

            int tries = 0;
            int maxTries = 40;
            boolean success = false;
            tries = 0;
            maxTries = 40;
            success = false;
            do {
                Sleeper.sleep(500);
                clone.sendRequest();
                if (retrieve.getResponseStatusCode().equals("200")) {
                    success = true;
                } else {
                    tries++;
                }
            } while (tries < maxTries && !success);
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"),
                        "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }

    }

}
