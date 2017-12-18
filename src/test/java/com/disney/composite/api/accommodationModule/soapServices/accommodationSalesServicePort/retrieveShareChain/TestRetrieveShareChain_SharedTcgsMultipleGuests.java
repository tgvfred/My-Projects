package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveShareChain;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.RetrieveShareChainHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveShareChain;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieveShareChain_SharedTcgsMultipleGuests extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment firstBooking;
    private ReplaceAllForTravelPlanSegment secondBooking;
    private int setValidation = 1;
    private String validateTcg;
    private String validateTc;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setArrivalDate(getDaysOut());
        setNights(2);
        setAddNewGuest(true);
        setDepartureDate(getNights());
        setValues();
        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        firstBooking = getBook();

        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(1);
        setArrivalDate(getDaysOut());
        setNights(2);
        setAddNewGuest(true);
        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());
        secondBooking = getBook();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain" })
    public void retrieveSharechain_SharedTcgsMultipleGuests() {

        share(firstBooking.getTravelComponentGroupingId(), secondBooking.getTravelComponentGroupingId());
        sendAndValidateTcg(firstBooking);
        sendAndValidateTcg(secondBooking);

    }

    public void share(String tcg1, String tcg2) {

        TestReporter.logStep("Send share request");
        Share share = new Share(environment);
        share.setTravelComponentGroupingId(firstBooking.getTravelComponentGroupingId());
        share.setRequestNodeValueByXPath("/Envelope/Body/share/request", BaseSoapCommands.ADD_NODE.commandAppend("sharedComponents"));
        share.setRequestNodeValueByXPath("/Envelope/Body/share/request/sharedComponents[2]", BaseSoapCommands.ADD_NODE.commandAppend("travelComponentGroupingId"));
        share.setSecondTravelComponentGroupingId(secondBooking.getTravelComponentGroupingId());
        share.setRoomNumber(BaseSoapCommands.REMOVE_NODE.toString());
        share.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
        share.sendRequest();

        TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "Error sending request", share);
    }

    public void sendAndValidateTcg(ReplaceAllForTravelPlanSegment booking) {

        TestReporter.logStep("Send retrieveShareChain xml");
        RetrieveShareChain retrieve = new RetrieveShareChain(environment, "Main");
        retrieve.setTravelComponentGroupingId(booking.getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "Error sending request", retrieve);

        String sharedRoomDetailXPath = "/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail";
        String guestDetailXPath = "/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail";
        String guestReferenceXPath = "/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails";

        RetrieveShareChainHelper helper = new RetrieveShareChainHelper();
        TestReporter.logStep("Validate two sharedRoomDetail node returned");
        helper.validateNodeCount(retrieve, sharedRoomDetailXPath, 2);
        TestReporter.logStep("Validate one guestDetail node returned per shareRoomDetails");
        helper.validateNodeCount(retrieve, guestDetailXPath, retrieve.getNumberOfResponseNodesByXPath(sharedRoomDetailXPath));
        TestReporter.logStep("Validate that 2 guestReferenceDetails nodes are returned in both sharedRoomDetail nodes");
        helper.validateNodeCount(retrieve, guestReferenceXPath, retrieve.getNumberOfResponseNodesByXPath(sharedRoomDetailXPath) * 2);

        // Validate old vs. new service+
        TestReporter.logStep("Validate old vs new service");
        if (Environment.isSpecialEnvironment(getEnvironment())) {
            RetrieveShareChain clone = (RetrieveShareChain) retrieve.clone();
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
            TestReporter.assertTrue(retrieve.validateResponseNodeQuantity(clone, true),
                    "Validating Response Comparison");
        }

        if (setValidation == 1) {
            validateTc = booking.getTravelComponentId();
            validateTcg = booking.getTravelComponentGroupingId();
            setValidation = 0;
        }

        TestReporter.logStep("Base validations");
        helper.validateTcAndTcg(validateTcg, validateTc, retrieve);
        helper.validateBaseNodes(booking, retrieve);
        helper.validateMultipleRateDetails(environment, retrieve);

    }

}
