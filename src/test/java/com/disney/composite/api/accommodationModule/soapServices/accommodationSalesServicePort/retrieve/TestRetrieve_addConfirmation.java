package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.helpers.UpdateItineraryConfirmationHelper;
import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.ManageConfirmationRecipient;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_addConfirmation extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_addConfirmation() {

        Retrieve retrieve = new Retrieve(environment, "ByTPS_ID");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        ManageConfirmationRecipient manage = new ManageConfirmationRecipient(getEnvironment(), "PhoneAndEmail");
        manage.setTpsId(getBook().getTravelPlanSegmentId());
        manage.setConfirmationType(UpdateItineraryConfirmationHelper.EMAIL);
        manage.setDefaultConfirmationIndicator("true");
        manage.setGuestFirstName(getHouseHold().primaryGuest().getFirstName());
        manage.setGuestLastName(getHouseHold().primaryGuest().getLastName());
        manage.setGuestMiddleName(getHouseHold().primaryGuest().getMiddleName());
        manage.setPhoneNumber(getHouseHold().primaryGuest().primaryPhone().getNumber());
        manage.setEmailAddress(getHouseHold().primaryGuest().primaryEmail().getEmail());
        manage.sendRequest();
        TestReporter.assertTrue(manage.getResponseStatusCode().equals("200"), "Verify that no error occurred managing confirmation recipient: " + manage.getFaultString());

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