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

public class TestRetrieve_tp_multipleTcg extends AccommodationBaseTest {

    String tcg;
    String tpsNum2;
    String tcgNum2;
    String tpsId;
    String tpId;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());

        setAddNewGuest(true);
        bookReservation();

        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcg = getBook().getTravelComponentGroupingId();
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().sendRequest();

        tcgNum2 = getBook().getTravelComponentGroupingId();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second TPS: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_tp_multipleTcg() {

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(tpId);
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting retrieve details: " + retrieve.getFaultString(), retrieve);

        // validations
        RetrieveHelper helper = new RetrieveHelper();
        helper.setValidateProfile(false);
        helper.baseValidationMultTCG(getBook(), retrieve);
        helper.tcgValidation(retrieve);

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
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bypassResortDesk");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/dmeAccommodation");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/partyId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/partyId");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/phoneDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/emailDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/primaryGuest/addressDetails/locatorId");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/phoneDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/emailDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanGuests/guest/addressDetails/locatorId");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/phoneDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/emailDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/primaryParty/guest/addressDetails/locatorId");

            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails/locatorId");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/emailDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/phoneDetails/locatorId");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/guestReferences/guest/addressDetails/locatorId");

            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");

        }
    }

}
