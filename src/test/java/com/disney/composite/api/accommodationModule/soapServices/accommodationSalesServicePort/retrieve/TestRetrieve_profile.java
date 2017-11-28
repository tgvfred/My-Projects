package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_profile extends AccommodationBaseTest {

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
        setAddProfile(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_profile() {

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");

        retrieve.setTravelPlanId(getBook().getTravelPlanId());
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting retrieve details: " + retrieve.getFaultString(), retrieve);

        RetrieveHelper helper = new RetrieveHelper();
        helper.baseValidation(getBook(), retrieve);
        helper.sqlTPSConfirmationDetails(environment, getBook().getTravelPlanSegmentId(), retrieve);
        int NumberOfProfiles = retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/profiles");

        for (int i = 2; i <= NumberOfProfiles; i++) {
            TestReporter.assertTrue(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/code").equals(retrieve.getProfileCodeRetrieveRQ(i)), "The profile code in the booking request [" + getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/code") + "]  matches the retrieve response [" + retrieve.getProfileCodeRetrieveRQ(i) + "]");
            TestReporter.assertTrue(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/id").equals(retrieve.getProfileIDRetrieveRQ(i)), "The profile code in the booking request [" + getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/id") + "]  matches the retrieve response [" + retrieve.getProfileIDRetrieveRQ(i) + "]");
            TestReporter.assertTrue(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/profileType").equals(retrieve.getProfileTypeRetrieveRQ(i)), "The profile code in the booking request [" + getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/profileType") + "]  matches the retrieve response [" + retrieve.getProfileTypeRetrieveRQ(i) + "]");
            TestReporter.assertTrue(getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/routings/name").equals(retrieve.getProfileRoutingNameRetrieveRQ(i)), "The profile code in the booking request [" + getBook().getResponseNodeValueByXPath("/Envelope/Body/replaceAllForTravelPlanSegmentResponse/response/roomDetails/roomReservationDetail/profiles/routings/name") + "]  matches the retrieve response [" + retrieve.getProfileRoutingNameRetrieveRQ(i) + "]");

        }

        // TestReporter.assertTrue(, description);
        // clone validations
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

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/doNotMailIndicator");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/doNotPhoneIndicator");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/active");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bypassResortDesk[text()='false']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/dmeAccommodation[text()='false']");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");

        }

    }

}
