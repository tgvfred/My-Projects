package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.Test;

import com.disney.api.helpers.OfferQueryHelper;
import com.disney.api.helpers.RoomResHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieve_SbcRes_roomOnly extends AccommodationBaseTest {

    private String tpID;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phone;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_SbcRes_roomOnly() {

        roomOnlyBooking();

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(tpID);
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        RetrieveHelper helper = new RetrieveHelper();
        helper.setFlag(true);
        helper.setValidateProfile(false);
        helper.baseValidation(getBook(), retrieve);

        TestReporter.softAssertEquals(firstName, retrieve.getFirstName(), "Verify the first name [" + retrieve.getFirstName() + "] matches the expected [" + firstName + "]");
        TestReporter.softAssertEquals(lastName, retrieve.getLastName(), "Verify the last name [" + retrieve.getLastName() + "] matches the expected [" + lastName + "]");
        TestReporter.softAssertEquals(phone, retrieve.getPhone(), "Verify the guest phone [" + retrieve.getPhone() + "] matches the expected [" + phone + "]");
        TestReporter.softAssertEquals(address, retrieve.getAddress(), "Verify the guest address [" + retrieve.getAddress() + "] matches the expected [" + address + "]");
        TestReporter.softAssertEquals(email, retrieve.getEmail(), "Verify the email [" + retrieve.getEmail() + "] matches the expected [" + email + "]");

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
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bypassResortDesk[text()='false']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/dmeAccommodation[text()='false']");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }

    }

    public void roomOnlyBooking() {
        OfferQueryHelper offer = new OfferQueryHelper(Environment.getBaseEnvironmentName(getEnvironment()), "WDW", "RoomOnly", false);
        RoomResHelper res = new RoomResHelper(Environment.getBaseEnvironmentName(getEnvironment()), "WDW", "Main", "1 Adult", offer.resortCode, offer.roomType, offer.packageCode);
        Sleeper.sleep(2000);
        tpID = res.getRoomRes().getItineraryId();
        firstName = res.getGuest().primaryGuest().getFirstName();
        lastName = res.getGuest().primaryGuest().getLastName();
        address = res.getGuest().primaryGuest().getAllAddresses().get(0).getAddress1();
        email = res.getGuest().primaryGuest().getAllEmails().get(0).getEmail();
        phone = res.getGuest().primaryGuest().getAllPhones().get(0).getNumber();

    }

}
