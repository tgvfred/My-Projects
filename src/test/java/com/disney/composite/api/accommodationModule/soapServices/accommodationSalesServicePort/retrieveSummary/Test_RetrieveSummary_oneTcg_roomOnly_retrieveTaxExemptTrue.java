package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveSummary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveSummary;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class Test_RetrieveSummary_oneTcg_roomOnly_retrieveTaxExemptTrue extends AccommodationBaseTest {

    private String environment;

    private ReplaceAllForTravelPlanSegment book;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void testBefore(String environment) {
        this.environment = environment;

        book = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(getEnvironment()), "RoomOnlyNoTicketsTaxExempt");
        book.sendRequest();
        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a res: " + book.getFaultString(), book);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetrieveSummary" })
    public void testRetrieveSummary_oneTcg_roomOnly_retrieveTaxExemptTrue() {

        RetrieveSummary retrieve = new RetrieveSummary(environment, "Main");
        retrieve.setRequestRetrieveTaxExempt("true");
        if (Environment.isSpecialEnvironment(environment)) {
            retrieve.setRequestTravelComponentGroupingIdIndexAdd("1", book.getTravelPlanSegmentId());
            retrieve.setRequestTravelComponentGroupingIdIndexAdd("2", book.getTravelComponentGroupingId());
            // retrieve.setRequestTravelComponentGroupingId(book.getTravelComponentGroupingId());
        } else {
            retrieve.setRequestTravelComponentGroupingId(book.getTravelPlanSegmentId());
        }
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + book.getTravelComponentGroupingId() + "]: " + retrieve.getFaultString(), retrieve);

        TestReporter.logStep("Verify Tax Exempt Details are found.");
        TestReporter.assertTrue(retrieve.getTaxExemptCertificateNumber().equals("1"), "Tax Exempt Certificate Number Found [1]! ");
        TestReporter.assertTrue(retrieve.getTaxExemptType().equals("Military"), "Tax Exempt Type Found [Military]!");

        // Old vs New Validation
        if (Environment.isSpecialEnvironment(environment)) {
            RetrieveSummary clone = (RetrieveSummary) retrieve.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            // clone.setRequestTravelComponentGroupingId(getBook().getTravelPlanSegmentId());
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/phoneDetails");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/emailDetails");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/phoneDetails");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/addressDetails");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/guest/emailDetails");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/phoneDetails");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/addressDetails");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences[2]/guest/emailDetails");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/purposeOfVisit");
            clone.addExcludedBaselineXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/guestReferences/purposeOfVisit");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveSummaryResponse/accommodationsSummaryDetails/accommodationDetail/dmeAccommodation");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }
    }

}
