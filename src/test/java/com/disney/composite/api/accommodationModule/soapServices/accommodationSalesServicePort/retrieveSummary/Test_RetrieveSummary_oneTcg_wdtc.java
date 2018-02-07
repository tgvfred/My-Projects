package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_wdtc extends AccommodationBaseTest {

    private String environment;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        this.environment = environment;
        isComo.set("false");

    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(getBook().getTravelComponentGroupingId());
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_wdtc() {

        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues();
        setIsWdtcBooking(true);
        bookReservation();

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        // Per AmitC, TK-692088, TPS will be the input into the TCG node - 11/14/2017 - WWA
        // if (Environment.isSpecialEnvironment(environment)) {
        // retrieve.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        // } else {
        retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        // }

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            try {
                retrieve.sendRequest();
                tries++;
                retrieve.getRoomOnlyStatus();
                success = true;
            } catch (XPathNotFoundException e) {
            }
        } while (tries < maxTries && !success);
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        TestReporter.logStep("Verify roomOnly node is false.");
        TestReporter.assertTrue(retrieve.getRoomOnlyStatus().equals("false"), "Room Only node returns false! ");

        // Old vs New Validation
        // if (Environment.isSpecialEnvironment(environment)) {
        // RetrieveSummary clone = (RetrieveSummary) retrieve.clone();
        // clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
        // clone.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        // clone.sendRequest();
        // if (!clone.getResponseStatusCode().equals("200")) {
        // TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
        // }
        // clone.addExcludedBaselineAttributeValidations("@xsi:nil");
        // clone.addExcludedBaselineAttributeValidations("@xsi:type");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Header");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/phoneDetails");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/emailDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/phoneDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/emailDetails");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/phoneDetails");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/addressDetails");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/emailDetails");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/purposeOfVisit");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/purposeOfVisit");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/exchangeFee");
        // clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/dmeAccommodation");
        // TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        // }
    }

}
