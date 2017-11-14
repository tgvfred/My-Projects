package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_twoTcg_roomOnly extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment book;
    private ReplaceAllForTravelPlanSegment book1;

    private Integer two = 2;

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
        setValues(environment);
        bookReservation();

        book = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(environment), "RoomOnlyNoTickets");
        book.sendRequest();

        book1 = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(environment), "RoomOnlyNoTickets");
        book1.sendRequest();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_twoTcg_roomOnly() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main2TCG");

        // Per AmitC, TK-692088, TPS will be the input into the TCG node - 11/14/2017 - WWA
        // if (Environment.isSpecialEnvironment(environment)) {
        // retrieve.setRequestTravelComponentGroupingIdIndex("1", book.getTravelComponentGroupingId());
        // retrieve.setRequestTravelComponentGroupingIdIndex("2", book1.getTravelComponentGroupingId());
        // } else {
        retrieve.setRequestTravelComponentGroupingIdIndex("1", book.getTravelPlanSegmentId());
        retrieve.setRequestTravelComponentGroupingIdIndex("2", book1.getTravelPlanSegmentId());
        // }

        int tries = 0;
        int maxTries = 20;
        boolean success = false;
        do {
            Sleeper.sleep(1000);
            try {
                retrieve.sendRequest();
                tries++;
                retrieve.getResponseNodeValueByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails[1]/travelPlanSegmentId");
                success = true;
            } catch (XPathNotFoundException e) {
            }
        } while (tries < maxTries && !success);

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + book.getTravelComponentGroupingId() + "]", retrieve);

        TestReporter.logStep("Verify two AccommodationsSummaryDetails node is found & that two different TPS IDs are found.");
        TestReporter.assertTrue(retrieve.getAccommodationsSummaryDetails().equals(two), "Number of AccommodationsSummaryDetails nodes found is [" + retrieve.getAccommodationsSummaryDetails() + "]! and the two TPS IDs are [" + retrieve.getTravelPlanSegmentId("1") + "] & [" + retrieve.getTravelPlanSegmentId("2") + "]");

    }

}
