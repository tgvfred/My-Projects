package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_wdtcWithTickets extends AccommodationBaseTest {

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
        setIsWdtcBooking(true);
        setAddTickets(true);
        bookReservation();
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            Cancel cancel = new Cancel(Environment.getBaseEnvironmentName(environment), "Main");
            cancel.setCancelDate(Randomness.generateCurrentXMLDate());
            cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
            cancel.sendRequest();
        } catch (Exception | AssertionError e) {
            System.out.println();
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_wdtcWithTickets() {
        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        // Per AmitC, TK-692088, TPS will be the input into the TCG node - 11/14/2017 - WWA
        // if (Environment.isSpecialEnvironment(environment)) {
        // retrieve.setRequestTravelComponentGroupingId(book.getTravelComponentGroupingId());
        // } else {
        retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        // }

        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        TestReporter.logStep("Verify Ticket Group node is found.");
        TestReporter.assertTrue(retrieve.getTicketGroup() != null, "Ticket Group found! [" + retrieve.getTicketGroup() + "] ");
    }
}