package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.staging.bookSEReservation.ScheduledEventReservation;
import com.disney.utils.dataFactory.staging.bookSEReservation.ShowDiningReservation;

public class Test_RetrieveSummary_oneTcg_dining extends AccommodationBaseTest {
    private ScheduledEventReservation diningRes;

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            diningRes.cancel();
        } catch (Exception e) {

        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_dining() {

        ScheduledEventReservation dining = new ShowDiningReservation(getEnvironment().toLowerCase().replace("_cm", ""));
        dining.setTravelPlanId(getBook().getTravelPlanId());
        dining.book(ScheduledEventReservation.ONECOMPONENTSNOADDONS);

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        if (Environment.isSpecialEnvironment(environment)) {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        } else {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        }
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

    }

}
