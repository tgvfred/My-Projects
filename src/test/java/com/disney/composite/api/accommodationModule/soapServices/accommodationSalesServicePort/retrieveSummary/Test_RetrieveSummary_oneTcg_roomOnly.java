package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnly extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnly() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        // Used to take TPSID in place of TCGID
        if (Environment.isSpecialEnvironment(environment)) {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        } else {
            retrieve.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
        }
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        // This validation will not work bc I am unable to exclude nodes in the new Environment
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
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/phoneDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/emailDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/phoneDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/addressDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/emailDetails");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/purposeOfVisit");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/exchangeFee");
        // clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/dmeAccommodation");
        // TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        // }

    }

}
