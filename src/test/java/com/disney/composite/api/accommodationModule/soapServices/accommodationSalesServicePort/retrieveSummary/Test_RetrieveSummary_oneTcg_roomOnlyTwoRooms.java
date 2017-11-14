package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyTwoRooms extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment book;
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

        book = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(getEnvironment()), "book2AdultsAndTwoRoom");
        book.sendRequest();
        book.getResponse();
        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "Verify no error occurred booking a res: " + book.getFaultString(), book);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnlyTwoRooms() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");

        // Per AmitC, TK-692088, TPS will be the input into the TCG node - 11/14/2017 - WWA
        // if (Environment.isSpecialEnvironment(environment)) {
        // retrieve.setRequestTravelComponentGroupingIdIndexAdd("1", book.getTravelPlanSegmentId());
        // retrieve.setRequestTravelComponentGroupingIdIndexAdd("2", book.getTravelComponentGroupingId());
        // retrieve.setRequestTravelComponentGroupingIdIndexAdd("3", book.getResponseNodeValueByXPath("//replaceAllForTravelPlanSegmentResponse/response/roomDetails[2]/travelComponentGroupingId"));
        // // retrieve.setRequestTravelComponentGroupingId(book.getTravelComponentGroupingId());
        // } else {
        retrieve.setRequestTravelComponentGroupingId(book.getTravelPlanSegmentId());
        // }

        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + book.getTravelComponentGroupingId() + "]", retrieve);

        TestReporter.logStep("Verify two accommodationsSummaryDetails nodes are returned");
        TestReporter.assertTrue(retrieve.getAccommodationsSummaryDetails().equals(two), "Two accommodationsSummaryDetails nodes found! First TCGID is [" + retrieve.getTravelComponentGroupingId("1") + "] & Second TCGID is [" + retrieve.getTravelComponentGroupingId("2") + "]");

    }

}
