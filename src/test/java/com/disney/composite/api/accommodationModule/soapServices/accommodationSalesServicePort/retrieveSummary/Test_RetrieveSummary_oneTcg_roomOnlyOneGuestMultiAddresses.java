package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyOneGuestMultiAddresses extends AccommodationBaseTest {
    private ReplaceAllForTravelPlanSegment book;

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

        book = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(environment), "ROMultiAddr");
        // book = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(environment), "RoomOnlyNoTicketsMultiAddr");
        book.sendRequest();
        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "Verify that no error occurred while booking: " + book.getFaultString(), book);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnlyOneGuestMultiAddresses() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        // Per AmitC, TK-692088, TPS will be the input into the TCG node - 11/14/2017 - WWA
        // if (Environment.isSpecialEnvironment(environment)) {
        // retrieve.setRequestTravelComponentGroupingIdIndexAdd("1", book.getTravelPlanSegmentId());
        // retrieve.setRequestTravelComponentGroupingIdIndexAdd("2", book.getTravelComponentGroupingId());
        // // retrieve.setRequestTravelComponentGroupingId(book.getTravelComponentGroupingId());
        // } else {
        retrieve.setRequestTravelComponentGroupingId(book.getTravelPlanSegmentId());
        // }

        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + book.getTravelComponentGroupingId() + "]", retrieve);

        TestReporter.logStep("Verify Multiple Addresses are found.");
        TestReporter.assertTrue(retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails") == 2, "Number of Addresses Found [2]! ");

    }

}
