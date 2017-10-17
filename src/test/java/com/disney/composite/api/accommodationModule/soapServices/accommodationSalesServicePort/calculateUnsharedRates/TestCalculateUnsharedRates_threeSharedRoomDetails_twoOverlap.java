package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_threeSharedRoomDetails_twoOverlap extends AccommodationBaseTest {
    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates" })
    public void Test_CalculateUnsharedRates_threeSharedRoomDetails_twoOverlap() {
        calculate = new CalculateUnsharedRates(environment, "ThreeOverlap");
        modifyRequest();
        calculate.sendRequest();
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates: " + calculate.getFaultString(), calculate);

        validateNumberShareChainsResponseNodes();
        validateNumberShareRoomDetailsResponseNodes();
        validateShareChains();
        validateUnsharedAccommodation();

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
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains[1]/shareRoomDetails"), 1, "Verify that there was one response node returned for the shareRoomDetails node");
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains[2]/shareRoomDetails"), 2, "Verify that there was two response node returned for the shareRoomDetails node.");
        TestReporter.assertAll();
    }

    public void validateShareChains() {
        TestReporter.logStep("Validate both share chains in the response node.");
        String shareChainIndex = "1";
        String shareRoomIndex = "1";
        String resortPeriodStartDate1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/startDate").split("T")[0];
        String resortPeriodEndDate1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/endDate").split("T")[0];
        String TC1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/travelComponentId");
        String TCG1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/travelComponentGroupingId");
        String TPS1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/travelPlanSegmentId");
        String addtlCharge = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/basePrice");
        String date = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/date").split("T")[0];
        String dayCount = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/pointsValue");
        String responseStartDate = calculate.getShareChainUnSharedRoomDetailsStartDate(shareChainIndex, shareRoomIndex).split("T")[0];
        String responseEndDate = calculate.getShareChainUnSharedRoomDetailsEndDate(shareChainIndex, shareRoomIndex).split("T")[0];
        String responseTc = calculate.getShareChainUnSharedRoomDetailsTCId(shareChainIndex, shareRoomIndex);
        String responseTcg = calculate.getShareChainUnSharedRoomDetailsTCGId(shareChainIndex, shareRoomIndex);
        String responseTps = calculate.getShareChainUnSharedRoomDetailsTPSId(shareChainIndex, shareRoomIndex);
        String responseAdditionalCharge = calculate.getUnsharedRoomDetailsAdditionalCharge(shareChainIndex, shareRoomIndex);
        String responseAdditionalChargeOverride = calculate.getUnsharedRoomDetailsAdditionalChargeOveridden(shareChainIndex, shareRoomIndex);
        String responseBasePrice = calculate.getUnsharedRoomDetailsBasePrice(shareChainIndex, shareRoomIndex);
        String responseDate = calculate.getUnsharedRoomDetailsDate(shareChainIndex, shareRoomIndex).split("T")[0];
        String responseDayCount = calculate.getUnsharedRoomDetailsDayCount(shareChainIndex, shareRoomIndex);
        String responseOveridden = calculate.getUnsharedRoomDetailsOveridden(shareChainIndex, shareRoomIndex);
        String responseShared = calculate.getUnsharedRoomDetailsShared(shareChainIndex, shareRoomIndex);
        String responseNetPrice = calculate.getUnsharedRoomDetailsNetPrice(shareChainIndex, shareRoomIndex);
        String responsePointsValue = calculate.getUnsharedRoomDetailsPointsValue(shareChainIndex, shareRoomIndex);
        TestReporter.softAssertEquals(resortPeriodStartDate1, responseStartDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] start date [" + responseStartDate + "] is that which is expected [" + resortPeriodStartDate1 + "].");
        TestReporter.softAssertEquals(resortPeriodEndDate1, responseEndDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] end date [" + responseEndDate + "] is that which is expected [" + resortPeriodEndDate1 + "].");
        TestReporter.softAssertEquals(TC1, responseTc, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TC ID [" + responseTc + "] is that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(TCG1, responseTcg, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TCG ID [" + responseTcg + "] is that which is expected [" + TCG1 + "].");
        TestReporter.softAssertEquals(TPS1, responseTps, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TPS ID [" + responseTps + "] is that which is expected [" + TPS1 + "].");
        TestReporter.softAssertEquals(addtlCharge, responseAdditionalCharge, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail additional charge [" + responseAdditionalCharge + "] is that which is expected [" + addtlCharge + "].");
        TestReporter.softAssertEquals(addtlChargeOver, responseAdditionalChargeOverride, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail additional charge override [" + responseAdditionalChargeOverride + "] is that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertEquals(basePrice, responseBasePrice, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail base price [" + responseBasePrice + "] is that which is expected [" + basePrice + "].");
        TestReporter.softAssertEquals(date, responseDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail date [" + responseDate + "] is that which is expected [" + date + "].");
        TestReporter.softAssertEquals(dayCount, responseDayCount, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail day count [" + responseDayCount + "] is that which is expected [" + dayCount + "].");
        TestReporter.softAssertEquals(overidden, responseOveridden, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail overriden [" + responseOveridden + "] is that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(shared, responseShared, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail shared [" + responseShared + "] is that which is expected [" + shared + "].");
        TestReporter.softAssertEquals(netPrice, responseNetPrice, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail net price [" + responseNetPrice + "] is that which is expected [" + netPrice + "].");
        TestReporter.softAssertEquals(pointsValue, responsePointsValue, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail points value [" + responsePointsValue + "] is that which is expected [" + pointsValue + "].");

        shareChainIndex = "2";
        shareRoomIndex = "1";
        String resortPeriodStartDate2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/resortPeriod/startDate").split("T")[0];
        String resortPeriodEndDate2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/resortPeriod/endDate").split("T")[0];
        String TC2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/travelComponentId");
        String TCG2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/travelComponentGroupingId");
        String TPS2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/travelPlanSegmentId");
        String addtlCharge2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/basePrice");
        String date2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[2]/unSharedRoomDetail/rateDetails/date").split("T")[0];
        String dayCount2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/dayCount");
        String overidden2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/overidden");
        String shared2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/shared");
        String netPrice2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue2 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/rateDetails/pointsValue");
        responseStartDate = calculate.getShareChainUnSharedRoomDetailsStartDate(shareChainIndex, shareRoomIndex).split("T")[0];
        responseEndDate = calculate.getShareChainUnSharedRoomDetailsEndDate(shareChainIndex, shareRoomIndex).split("T")[0];
        responseTc = calculate.getShareChainUnSharedRoomDetailsTCId(shareChainIndex, shareRoomIndex);
        responseTcg = calculate.getShareChainUnSharedRoomDetailsTCGId(shareChainIndex, shareRoomIndex);
        responseTps = calculate.getShareChainUnSharedRoomDetailsTPSId(shareChainIndex, shareRoomIndex);
        responseAdditionalCharge = calculate.getUnsharedRoomDetailsAdditionalCharge(shareChainIndex, shareRoomIndex);
        responseAdditionalChargeOverride = calculate.getUnsharedRoomDetailsAdditionalChargeOveridden(shareChainIndex, shareRoomIndex);
        responseBasePrice = calculate.getUnsharedRoomDetailsBasePrice(shareChainIndex, shareRoomIndex);
        responseDate = calculate.getUnsharedRoomDetailsDate(shareChainIndex, shareRoomIndex).split("T")[0];
        responseDayCount = calculate.getUnsharedRoomDetailsDayCount(shareChainIndex, shareRoomIndex);
        responseOveridden = calculate.getUnsharedRoomDetailsOveridden(shareChainIndex, shareRoomIndex);
        responseShared = calculate.getUnsharedRoomDetailsShared(shareChainIndex, shareRoomIndex);
        responseNetPrice = calculate.getUnsharedRoomDetailsNetPrice(shareChainIndex, shareRoomIndex);
        responsePointsValue = calculate.getUnsharedRoomDetailsPointsValue(shareChainIndex, shareRoomIndex);
        TestReporter.softAssertEquals(resortPeriodStartDate2, responseStartDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] start date [" + responseStartDate + "] is that which is expected [" + resortPeriodStartDate2 + "].");
        TestReporter.softAssertEquals(resortPeriodEndDate2, responseEndDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] end date [" + responseEndDate + "] is that which is expected [" + resortPeriodEndDate2 + "].");
        TestReporter.softAssertEquals(TC2, responseTc, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TC ID [" + responseTc + "] is that which is expected [" + TC2 + "].");
        TestReporter.softAssertEquals(TCG2, responseTcg, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TCG ID [" + responseTcg + "] is that which is expected [" + TCG2 + "].");
        TestReporter.softAssertEquals(TPS2, responseTps, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TPS ID [" + responseTps + "] is that which is expected [" + TPS2 + "].");
        TestReporter.softAssertEquals(addtlCharge2, responseAdditionalCharge, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail additional charge [" + responseAdditionalCharge + "] is that which is expected [" + addtlCharge2 + "].");
        TestReporter.softAssertEquals(addtlChargeOver2, responseAdditionalChargeOverride, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail additional charge override [" + responseAdditionalChargeOverride + "] is that which is expected [" + addtlChargeOver2 + "].");
        TestReporter.softAssertEquals(basePrice2, responseBasePrice, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail base price [" + responseBasePrice + "] is that which is expected [" + basePrice2 + "].");
        TestReporter.softAssertEquals(date2, responseDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail date [" + responseDate + "] is that which is expected [" + date2 + "].");
        TestReporter.softAssertEquals(dayCount2, responseDayCount, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail day count [" + responseDayCount + "] is that which is expected [" + dayCount2 + "].");
        TestReporter.softAssertEquals(overidden2, responseOveridden, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail overriden [" + responseOveridden + "] is that which is expected [" + overidden2 + "].");
        TestReporter.softAssertEquals(shared2, responseShared, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail shared [" + responseShared + "] is that which is expected [" + shared2 + "].");
        TestReporter.softAssertEquals(netPrice2, responseNetPrice, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail net price [" + responseNetPrice + "] is that which is expected [" + netPrice2 + "].");
        TestReporter.softAssertEquals(pointsValue2, responsePointsValue, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail points value [" + responsePointsValue + "] is that which is expected [" + pointsValue2 + "].");

        shareChainIndex = "2";
        shareRoomIndex = "2";
        String resortPeriodStartDate3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/resortPeriod/startDate").split("T")[0];
        String resortPeriodEndDate3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/resortPeriod/endDate").split("T")[0];
        String TC3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/travelComponentId");
        String TCG3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/travelComponentGroupingId");
        String TPS3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/travelPlanSegmentId");
        String addtlCharge3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/basePrice");
        String date3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/date").split("T")[0];
        String dayCount3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/dayCount");
        String overidden3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/overidden");
        String shared3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/shared");
        String netPrice3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue3 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[3]/unSharedRoomDetail/rateDetails/pointsValue");
        responseStartDate = calculate.getShareChainUnSharedRoomDetailsStartDate(shareChainIndex, shareRoomIndex).split("T")[0];
        responseEndDate = calculate.getShareChainUnSharedRoomDetailsEndDate(shareChainIndex, shareRoomIndex).split("T")[0];
        responseTc = calculate.getShareChainUnSharedRoomDetailsTCId(shareChainIndex, shareRoomIndex);
        responseTcg = calculate.getShareChainUnSharedRoomDetailsTCGId(shareChainIndex, shareRoomIndex);
        responseTps = calculate.getShareChainUnSharedRoomDetailsTPSId(shareChainIndex, shareRoomIndex);
        responseAdditionalCharge = calculate.getUnsharedRoomDetailsAdditionalCharge(shareChainIndex, shareRoomIndex);
        responseAdditionalChargeOverride = calculate.getUnsharedRoomDetailsAdditionalChargeOveridden(shareChainIndex, shareRoomIndex);
        responseBasePrice = calculate.getUnsharedRoomDetailsBasePrice(shareChainIndex, shareRoomIndex);
        responseDate = calculate.getUnsharedRoomDetailsDate(shareChainIndex, shareRoomIndex).split("T")[0];
        responseDayCount = calculate.getUnsharedRoomDetailsDayCount(shareChainIndex, shareRoomIndex);
        responseOveridden = calculate.getUnsharedRoomDetailsOveridden(shareChainIndex, shareRoomIndex);
        responseShared = calculate.getUnsharedRoomDetailsShared(shareChainIndex, shareRoomIndex);
        responseNetPrice = calculate.getUnsharedRoomDetailsNetPrice(shareChainIndex, shareRoomIndex);
        responsePointsValue = calculate.getUnsharedRoomDetailsPointsValue(shareChainIndex, shareRoomIndex);
        TestReporter.softAssertEquals(resortPeriodStartDate3, responseStartDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] start date [" + responseStartDate + "] is that which is expected [" + resortPeriodStartDate3 + "].");
        TestReporter.softAssertEquals(resortPeriodEndDate3, responseEndDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] end date [" + responseEndDate + "] is that which is expected [" + resortPeriodEndDate3 + "].");
        TestReporter.softAssertEquals(TC3, responseTc, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TC ID [" + responseTc + "] is that which is expected [" + TC3 + "].");
        TestReporter.softAssertEquals(TCG3, responseTcg, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TCG ID [" + responseTcg + "] is that which is expected [" + TCG3 + "].");
        TestReporter.softAssertEquals(TPS3, responseTps, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] TPS ID [" + responseTps + "] is that which is expected [" + TPS3 + "].");
        TestReporter.softAssertEquals(addtlCharge3, responseAdditionalCharge, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail additional charge [" + responseAdditionalCharge + "] is that which is expected [" + addtlCharge3 + "].");
        TestReporter.softAssertEquals(addtlChargeOver3, responseAdditionalChargeOverride, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail additional charge override [" + responseAdditionalChargeOverride + "] is that which is expected [" + addtlChargeOver3 + "].");
        TestReporter.softAssertEquals(basePrice3, responseBasePrice, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail base price [" + responseBasePrice + "] is that which is expected [" + basePrice3 + "].");
        TestReporter.softAssertEquals(date3, responseDate, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail date [" + responseDate + "] is that which is expected [" + date3 + "].");
        TestReporter.softAssertEquals(dayCount3, responseDayCount, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail day count [" + responseDayCount + "] is that which is expected [" + dayCount3 + "].");
        TestReporter.softAssertEquals(overidden3, responseOveridden, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail overriden [" + responseOveridden + "] is that which is expected [" + overidden3 + "].");
        TestReporter.softAssertEquals(shared3, responseShared, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail shared [" + responseShared + "] is that which is expected [" + shared3 + "].");
        TestReporter.softAssertEquals(netPrice3, responseNetPrice, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail net price [" + responseNetPrice + "] is that which is expected [" + netPrice3 + "].");
        TestReporter.softAssertEquals(pointsValue3, responsePointsValue, "Verify that the share chain [" + shareChainIndex + "] share room index [" + shareRoomIndex + "] rate detail points value [" + responsePointsValue + "] is that which is expected [" + pointsValue3 + "].");

        TestReporter.assertAll();
    }

    public void validateUnsharedAccommodation() {
        TestReporter.logStep("Validate the unshared accommodation response node.");
        String resortPeriodStartDate1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/startDate").split("T")[0];
        String resortPeriodEndDate1 = calculate.getRequestNodeValueByXPath("//unSharedChain[1]/shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/endDate").split("T")[0];

        String TC1 = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/travelComponentId");
        String TCG1 = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/travelComponentGroupingId");
        String TPS1 = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/travelPlanSegmentId");

        String addtlChargeOver = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String date = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/date").split("T")[0];
        String dayCount = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/shared");
        String pointsValue = calculate.getRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/pointsValue");

        String responseStartDate = calculate.getUnsharedAccommodationSharedRoomDetailStartDate().split("T")[0];
        String responseEndDate = calculate.getUnsharedAccommodationSharedRoomDetailEndDate().split("T")[0];
        String responseTc = calculate.getUnsharedAccommodationSharedRoomDetailTC();
        String responseTcg = calculate.getUnsharedAccommodationSharedRoomDetailTCG();
        String responseTps = calculate.getUnsharedAccommodationUnSharedRoomDetailsTPSId();
        String responseAdditionalChargeOverride = calculate.getUnsharedAccommodationSharedRoomDetailAdditionalChargeOverridden("1");
        String responseDate = calculate.getUnsharedAccommodationSharedRoomDetailDate("1").split("T")[0];
        String responseDayCount = calculate.getUnsharedAccommodationSharedRoomDetailDayCount("1");
        String responseOveridden = calculate.getUnsharedAccommodationSharedRoomDetailOveridden("1");
        String responseShared = calculate.getUnsharedAccommodationSharedRoomDetailShared("1");
        String responsePointsValue = calculate.getUnsharedAccommodationSharedRoomDetailPointsValue("1");
        TestReporter.softAssertEquals(resortPeriodStartDate1, responseStartDate, "Verify that the unshared accommodation start date [" + responseStartDate + "] is that which is expected [" + resortPeriodStartDate1 + "].");
        TestReporter.softAssertEquals(resortPeriodEndDate1, responseEndDate, "Verify that the unshared accommodation end date [" + responseEndDate + "] is that which is expected [" + resortPeriodEndDate1 + "].");
        TestReporter.softAssertEquals(TC1, responseTc, "Verify that the unshared accommodation TC ID [" + responseTc + "] is that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(TCG1, responseTcg, "Verify that the unshared accommodation TCG ID [" + responseTcg + "] is that which is expected [" + TCG1 + "].");
        TestReporter.softAssertEquals(TPS1, responseTps, "Verify that the unshared accommodation TPS ID [" + responseTps + "] is that which is expected [" + TPS1 + "].");
        TestReporter.softAssertEquals(addtlChargeOver, responseAdditionalChargeOverride, "Verify that the unshared accommodation rate detail additional charge override [" + responseAdditionalChargeOverride + "] is that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertEquals(date, responseDate, "Verify that the unshared accommodation rate detail date [" + responseDate + "] is that which is expected [" + date + "].");
        TestReporter.softAssertEquals(dayCount, responseDayCount, "Verify that the unshared accommodation rate detail day count [" + responseDayCount + "] is that which is expected [" + dayCount + "].");
        TestReporter.softAssertEquals(overidden, responseOveridden, "Verify that the unshared accommodation rate detail overriden [" + responseOveridden + "] is that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(shared, responseShared, "Verify that the unshared accommodation rate detail shared [" + responseShared + "] is that which is expected [" + shared + "].");
        TestReporter.softAssertEquals(pointsValue, responsePointsValue, "Verify that the unshared accommodation rate detail points value [" + responsePointsValue + "] is that which is expected [" + pointsValue + "].");
        TestReporter.assertAll();
    }

    private void modifyRequest() {
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/modificationDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/unSharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/sharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/sharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/sharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/sharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/sharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[1]/sharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate());

        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/modificationDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/unSharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/sharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/sharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/sharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/sharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/sharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[2]/sharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate(2));

        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/modificationDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/unSharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/sharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/sharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/sharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate(2));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/sharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/sharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(3));
        calculate.setRequestNodeValueByXPath("//shareRoomDetails[3]/sharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate(2));

        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/unSharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/bookingDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/checkOutDateTime", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/modificationDate", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/rateDetails/date", Randomness.generateCurrentXMLDate());
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/rateDetails/dayCount", "0");
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/resortPeriod/endDate", Randomness.generateCurrentXMLDate(1));
        calculate.setRequestNodeValueByXPath("//unsharedAccomadation/sharedRoomDetail/resortPeriod/startDate", Randomness.generateCurrentXMLDate());
    }
}