package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_twoSharedRoomDetails_NoOverlap extends AccommodationBaseTest {

    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates" })
    public void Test_CalculateUnsharedRates_twoSharedRoomDetails_noOverlap() {

        calculate = new CalculateUnsharedRates(environment, "NoOverlap");
        calculate.sendRequest();
        System.out.print(calculate.getRequest());
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates.", calculate);

        validateNumberShareChainsResponseNodes();
        validateNumberShareRoomDetailsResponseNodes();
        validateFirstShareChain();
        validateUnsharedAccommodation();
        validateUnsharedAccommodationSharedRoom();

        if (Environment.isSpecialEnvironment(environment)) {
            CalculateUnsharedRates clone = (CalculateUnsharedRates) calculate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(calculate, true), "Validating Response Comparison");

        }

    }

    public void validateNumberShareChainsResponseNodes() {
        TestReporter.logStep("Validate the number of shareChains response nodes");
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains"), 2, "Verify that there were two response nodes returned for the ShareChain node.");
        TestReporter.assertAll();
    }

    public void validateNumberShareRoomDetailsResponseNodes() {
        TestReporter.logStep("Validate the number of shareRoomDetails response nodes");
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails"), 2, "Verify that there was one response node returned for the shareRoomDetails node.");
        TestReporter.assertAll();
    }

    public void validateFirstShareChain() {
        TestReporter.logStep("Validate both share chains in the response node.");
        String resortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/startDate");
        String resortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/endDate");
        String sharedRoomResortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/sharedRoomDetail/resortPeriod/startDate");
        String sharedRoomResortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/sharedRoomDetail/resortPeriod/endDate");
        String TC1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/travelComponentId");
        String TC2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/travelComponentId");
        String TCG1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/travelComponentGroupingId");
        String TCG2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/travelComponentGroupingId");
        String TPS1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/travelPlanSegmentId");
        String TPS2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/travelPlanSegmentId");
        String addtlCharge = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/basePrice");
        String date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/date");
        String dayCount = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/pointsValue");
        String addtlCharge2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/basePrice");
        String date2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/date");
        String dayCount2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/dayCount");
        String overidden2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/overidden");
        String shared2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/shared");
        String netPrice2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue2 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/pointsValue");

        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsStartDate(), resortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getShareChainUnSharedRoomDetailsStartDate() + "] that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsEndDate(), resortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getShareChainUnSharedRoomDetailsEndDate() + "] that which is expected [" + resortPeriodEndDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsStartDateNoOverlap(), sharedRoomResortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getShareChainUnSharedRoomDetailsStartDateNoOverlap() + "] that which is expected [" + sharedRoomResortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsEndDateNoOverlap(), sharedRoomResortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getShareChainUnSharedRoomDetailsEndDateNoOverlap() + "] that which is expected [" + sharedRoomResortPeriodEndDate + "].");

        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCId(), TC1, "Verify that the response returns the TC id [" + calculate.getShareChainUnSharedRoomDetailsTCId() + "] that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCIdNoOverlap(), TC2, "Verify that the response returns the TC id [" + calculate.getShareChainUnSharedRoomDetailsTCIdNoOverlap() + "] that which is expected [" + TC2 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCGId(), TCG1, "Verify that the response returns the TCG id [" + calculate.getShareChainUnSharedRoomDetailsTCGId() + "] that which is expected [" + TCG1 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCGIdNoOverlap(), TCG2, "Verify that the response returns the TCG id [" + calculate.getShareChainUnSharedRoomDetailsTCGIdNoOverlap() + "] that which is expected [" + TCG2 + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommTPSId(), TPS1, "Verify that the response returns the TPS id [" + calculate.getUnSharedAccommTPSId() + "] that which is expected [" + TPS1 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTPSIdNoOverlap(), TPS2, "Verify that the response returns the TPS id [" + calculate.getShareChainUnSharedRoomDetailsTPSIdNoOverlap() + "] that which is expected [" + TPS2 + "].");

        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalCharge(), addtlCharge, "Verify that the response returns the additional charge [" + calculate.getUnsharedRoomDetailsAdditionalCharge() + "] that which is expected [" + addtlCharge + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalChargeOveridden(), addtlChargeOver, "Verify that the response returns the additional charge overidden [" + calculate.getUnsharedRoomDetailsAdditionalChargeOveridden() + "] that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsBasePrice(), basePrice, "Verify that the response returns the tcId [" + calculate.getUnsharedRoomDetailsBasePrice() + "] that which is expected [" + basePrice + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDate(), date, "Verify that the date [" + calculate.getUnsharedRoomDetailsDate() + "] that which is expected [" + date + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDayCount(), dayCount, "Verify that the response returns the day count [" + calculate.getUnsharedRoomDetailsDayCount() + "] that which is expected [" + dayCount + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsOveridden(), overidden, "Verify that the response returns the overidden status [" + calculate.getUnsharedRoomDetailsOveridden() + "] that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsShared(), shared, "Verify that the response returns the shared status [" + calculate.getUnsharedRoomDetailsShared() + "] that which is expected [" + shared + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsNetPrice(), netPrice, "Verify that the response returns the net price [" + calculate.getUnsharedRoomDetailsNetPrice() + "] that which is expected [" + netPrice + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsPointsValue(), pointsValue, "Verify that the response returns the points value [" + calculate.getUnsharedRoomDetailsPointsValue() + "] that which is expected [" + pointsValue + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalChargeNoOverlap(), addtlCharge2, "Verify that the response returns the additional charge [" + calculate.getUnsharedRoomDetailsAdditionalChargeNoOverlap() + "] that which is expected [" + addtlCharge2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalChargeOveriddenNoOverlap(), addtlChargeOver2, "Verify that the response returns the additional charge overidden [" + calculate.getUnsharedRoomDetailsAdditionalChargeOveriddenNoOverlap() + "] that which is expected [" + addtlChargeOver2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsBasePriceNoOverlap(), basePrice2, "Verify that the response returns the tcId [" + calculate.getUnsharedRoomDetailsBasePriceNoOverlap() + "] that which is expected [" + basePrice2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDateNoOverlap(), date2, "Verify that the date [" + calculate.getUnsharedRoomDetailsDateNoOverlap() + "] that which is expected [" + date2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDayCountNoOverlap(), dayCount2, "Verify that the response returns the day count [" + calculate.getUnsharedRoomDetailsDayCountNoOverlap() + "] that which is expected [" + dayCount2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsOveriddenNoOverlap(), overidden2, "Verify that the response returns the overidden status [" + calculate.getUnsharedRoomDetailsOveriddenNoOverlap() + "] that which is expected [" + overidden2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsSharedNoOverlap(), shared2, "Verify that the response returns the shared status [" + calculate.getUnsharedRoomDetailsSharedNoOverlap() + "] that which is expected [" + shared2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsNetPriceNoOverlap(), netPrice2, "Verify that the response returns the net price [" + calculate.getUnsharedRoomDetailsNetPriceNoOverlap() + "] that which is expected [" + netPrice2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsPointsValueNoOverlap(), pointsValue2, "Verify that the response returns the points value [" + calculate.getUnsharedRoomDetailsPointsValueNoOverlap() + "] that which is expected [" + pointsValue2 + "].");
        TestReporter.assertAll();
    }

    public void validateUnsharedAccommodation() {
        TestReporter.logStep("Validate the unsharedAccommodation/unsharedRoom in the response node.");

        String resortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/resortPeriod/startDate");
        String resortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/resortPeriod/endDate");
        String TC1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentId");
        String TCG1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentGroupingId");
        String TPS1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/travelPlanSegmentId");

        TestReporter.softAssertEquals(calculate.getUnSharedAccommStartDate(), resortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getUnSharedAccommStartDate() + "] that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommEndDate(), resortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getUnSharedAccommEndDate() + "] that which is expected [" + resortPeriodEndDate + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommUnSharedRoomDetailsTCId(), TC1, "Verify that the response returns the TC id [" + calculate.getUnSharedAccommUnSharedRoomDetailsTCId() + "] that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommUnSharedRoomDetailsTCGId(), TCG1, "Verify that the response returns the TCG id [" + calculate.getUnSharedAccommUnSharedRoomDetailsTCGId() + "] that which is expected [" + TCG1 + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommTPSId(), TPS1, "Verify that the response returns the TPS id [" + calculate.getUnSharedAccommTPSId() + "] that which is expected [" + TPS1 + "].");
        TestReporter.assertAll();
    }

    public void validateUnsharedAccommodationSharedRoom() {
        TestReporter.logStep("Validate the unsharedAccommodation/sharedRoom in the response node.");

        String resortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/resortPeriod/startDate");
        String resortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/resortPeriod/endDate");
        String TC1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/travelComponentId");
        String TCG1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/travelComponentGroupingId");
        String TPS1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/travelPlanSegmentId");

        TestReporter.softAssertEquals(calculate.getSharedAccommStartDate(), resortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getUnSharedAccommStartDate() + "] that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getSharedAccommEndDate(), resortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getUnSharedAccommEndDate() + "] that which is expected [" + resortPeriodEndDate + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommSharedRoomDetailsTCId(), TC1, "Verify that the response returns the TC id [" + calculate.getUnSharedAccommSharedRoomDetailsTCId() + "] that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommSharedRoomDetailsTCGId(), TCG1, "Verify that the response returns the TCG id [" + calculate.getUnSharedAccommSharedRoomDetailsTCGId() + "] that which is expected [" + TCG1 + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedAccommTPSId(), TPS1, "Verify that the response returns the TPS id [" + calculate.getUnSharedAccommTPSId() + "] that which is expected [" + TPS1 + "].");
        TestReporter.assertAll();
    }
}
