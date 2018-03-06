package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import org.testng.annotations.Test;

import com.disney.api.mq.sbc.OfferQuery;
import com.disney.api.mq.sbc.RoomInventoryDecrement;
import com.disney.api.mq.sbc.RoomQuote;
import com.disney.api.mq.sbc.RoomRes;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

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
        HouseHold hh = new HouseHold(1);
        hh.sendToApi(environment);

        String startDayOut = "10";
        String lengthOfStay = "2";
        OfferQuery offer = new OfferQuery(environment, "WDW", "OfferQueryRQ_WDW_Dolphin", "offerQuery");

        // Getting rid of any empty nodes and sending request
        offer.removeEmptyGroups(offer);
        offer.sendRequest();

        TestReporter.logAPI(!offer.isSuccess(), "Failed to get Room Offer", offer);

        // Requesting Room Quote
        RoomQuote room = new RoomQuote(environment, "WDW", "Main");
        room.setPackageCode(offer.getFirstAvailablePackageCode());
        room.setFromDateInDaysOut(startDayOut);
        room.setToDateInDaysOut(startDayOut, lengthOfStay);
        room.setResortCode(offer.getFirstAvailableResort()); // Test failing at
                                                             // this step
        room.setRoomType(offer.getFirstAvailableRoom());
        room.sendRequest();
        TestReporter.logAPI(!room.isSuccess(), "Failed to get Room Quote", room);

        // Requesting Freeze Room
        RoomInventoryDecrement avail = new RoomInventoryDecrement(environment, "WDW");
        avail.setLOS("3");
        avail.setStartDate(Randomness.generateCurrentXMLDate(Integer.valueOf(startDayOut)));
        avail.setEndDate(Randomness.generateCurrentXMLDate(Integer.valueOf(startDayOut) + Integer.valueOf(lengthOfStay)));
        avail.setPackageCode(offer.getFirstAvailablePackageCode());
        avail.setResortCode(offer.getFirstAvailableResort());
        avail.setRoomType(offer.getFirstAvailableRoom());
        avail.sendRequest();
        TestReporter.logAPI(!avail.isSuccess(), "Failed to get Freeze Room", avail);

        // Requesting Room Reservation
        RoomRes res = new RoomRes(environment, "WDW", "Main");
        res.setFreezeId(avail.getFreezeID());
        res.setArrivalDaysOut(startDayOut);
        res.setDepartureDaysOut(startDayOut, lengthOfStay);
        res.setResortCode(offer.getFirstAvailableResort());
        res.setRoomType(offer.getFirstAvailableRoom());
        res.setDepositDueAmount(room.getMinimumDepositAmount());
        res.setBalanceDueAmount(room.getNetAmount());
        res.setPackageCode(offer.getFirstAvailablePackageCode());
        res.setHouseholdInfo(hh);
        res.sendRequest();
        TestReporter.logAPI(!res.isSuccess(), "Failed to get Book Room", res);
        Sleeper.sleep(2000);
        tpID = res.getItineraryId();
        firstName = hh.primaryGuest().getFirstName();
        lastName = hh.primaryGuest().getLastName();
        address = hh.primaryGuest().primaryAddress().getAddress1();
        email = hh.primaryGuest().primaryEmail().getEmail();
        phone = hh.primaryGuest().primaryPhone().getNumber();

    }

}
