package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveShareChain;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.helpers.RetrieveShareChainHelper;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveShareChain;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestRetrieveShareChain_SingleTpsMultipleTcgs extends AccommodationBaseTest {
    private String firstTcg;
    private ReplaceAllForTravelPlanSegment firstBook;
    private ReplaceAllForTravelPlanSegment secondBook;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();

        firstBook = getBook();

        setEnvironment(environment);
        isComo.set("false");
        setTpId(getBook().getTravelPlanId());
        setTpsId(getBook().getTravelPlanSegmentId());
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        isComo.set("false");
        bookReservation();

        secondBook = getBook();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain" })
    public void retrieveSharechain_SingleTpsMultipleTcgs() {

        runAndValidate(firstBook);
        runAndValidate(secondBook);

    }

    public void runAndValidate(ReplaceAllForTravelPlanSegment book) {

        RetrieveShareChain retrieve = new RetrieveShareChain(environment, "Main");
        retrieve.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "Error sending request", retrieve);

        String sharedRoomDetailXPath = "/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/sharedRoomDetail";
        String guestDetailXPath = "/Envelope/Body/retrieveShareChainResponse/shareRoomDetails/guestDetail";

        RetrieveShareChainHelper helper = new RetrieveShareChainHelper();
        TestReporter.logStep("Validate one sharedRoomDetail node returned");
        helper.validateNodeCount(retrieve, sharedRoomDetailXPath, 1);
        TestReporter.logStep("Validate one guestDetail node returned");
        helper.validateNodeCount(retrieve, guestDetailXPath, 1);

        // Validate old vs. new service
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

        TestReporter.logStep("Base validations");
        helper.validateBaseNodes(book, retrieve);
        helper.validateGuestDetails(book, retrieve);
        helper.validateRateDetails(environment, retrieve);
    }
}
