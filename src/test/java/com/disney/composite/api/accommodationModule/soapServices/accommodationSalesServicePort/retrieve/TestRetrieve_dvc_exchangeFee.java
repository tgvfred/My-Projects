package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieve;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Retrieve;
import com.disney.api.soapServices.accommodationModule.helpers.RetrieveHelper;
import com.disney.api.soapServices.dvcModule.dvcSalesService.helpers.BookDVCRtHelper;
import com.disney.api.soapServices.dvcModule.pointsService.operations.RetrievePointBalanceSummary;
import com.disney.api.soapServices.dvcModule.pointsService.operations.RetrievePointBalanceSummary.PointSummary;
import com.disney.utils.Environment;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.guestFactory.DVCMember;

public class TestRetrieve_dvc_exchangeFee extends BookDVCRtHelper {

    private Map<Integer, Integer> adjustedPointSummaries = new HashMap<>();
    private DVCMember member;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(removeCM(environment));
        setUseRtResort(true);

        member = new DVCMember("testBookWithPay_RT", removeCM(environment));
        RetrievePointBalanceSummary retrievePointBalanceSummary = new RetrievePointBalanceSummary(removeCM(environment), "MinimalInfo");
        retrievePointBalanceSummary.setMembershipId(member.getMemberNumber());
        retrievePointBalanceSummary.sendRequest();
        Map<Integer, PointSummary> pointSummaries = retrievePointBalanceSummary.getAllPointSummaries();

        setUseNonZeroPoints(true);
        bookDvcReservation("testBookWithPay_RT", 1);

        for (Entry<Integer, PointSummary> entry : pointSummaries.entrySet()) {
            adjustedPointSummaries.put(entry.getKey(), Integer.parseInt(entry.getValue().getCommittedPoints()) + Integer.parseInt(getPointsPaid()));
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieve" })
    public void testRetrieve_dvc_exchangeFee() {

        Retrieve retrieve = new Retrieve(environment, "ByTP_ID");
        retrieve.setTravelPlanId(getFirstBooking().getTravelPlanId());
        retrieve.setLocationId(getLocationId());
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred calling retrieve", retrieve);

        RetrieveHelper helper = new RetrieveHelper();
        helper.baseValidationDVC(getFirstBooking(), retrieve);
        helper.dvcMembershipValidations(retrieve, member);

        TestReporter.assertTrue(!retrieve.getExchangeFee().isEmpty(), "Verify an exchange fee is returned from the retrieve [" + retrieve.getExchangeFee() + "]");

        // Old vs New
        if (Environment.isSpecialEnvironment(getEnvironment())) {

            Retrieve clone = (Retrieve) retrieve.clone();
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
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/exchangeFee");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/bypassResortDesk[text()='false']");
            clone.addExcludedXpathValidations("/Envelope/Body/retrieveResponse/travelPlanInfo/travelPlanSegments/componentGroupings/accommodation/dmeAccommodation[text()='false']");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(retrieve, true), "Validating Response Comparison");
        }

    }

}
