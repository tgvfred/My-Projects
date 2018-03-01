package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCCashHelper;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnlyDVC extends BookDVCCashHelper {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setRetrieveAfterBook(false);

        setUseDvcResort(true);
        setBook(bookDvcReservation("testBook_MCash", 1));
        setTpId(getFirstBooking().getTravelPlanId());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnlyDVC() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        // Per AmitC, TK-692088, TPS will be the input into the TCG node - 11/14/2017 - WWA
        // if (Environment.isSpecialEnvironment(environment)) {
        // retrieve.setRequestTravelComponentGroupingId(getFirstBooking().getTravelComponentGroupingId());
        // } else {
        retrieve.setRequestTravelComponentGroupingId(getFirstBooking().getTravelPlanSegmentId());
        // }

        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getFirstBooking().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

    }

}
