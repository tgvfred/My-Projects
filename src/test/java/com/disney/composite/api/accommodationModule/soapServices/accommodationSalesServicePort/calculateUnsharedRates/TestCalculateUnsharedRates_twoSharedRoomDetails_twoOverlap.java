package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_twoSharedRoomDetails_twoOverlap extends AccommodationBaseTest {
    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates" })
    public void Test_CalculateUnsharedRates_twoSharedRoomDetails_twoOverlap() {

        calculate = new CalculateUnsharedRates(environment, "TwoOverlap");
        calculate.sendRequest();
        System.out.print(calculate.getRequest());
        System.out.print(calculate.getResponse());
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates.", calculate);

        validateNumberShareChainsResponseNodes();
        validateNumberShareRoomDetailsResponseNodes();
        validateFirstShareChain();
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
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains"), 1, "Verify that there were two response nodes returned for the ShareChain node.");
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

        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsStartDate("1", "1").split("T")[0], resortPeriodStartDate, "Verify that the response returns the start date [" + calculate.getShareChainUnSharedRoomDetailsStartDate("1", "1").split("T")[0] + "] that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsEndDate("1", "1").split("T")[0], resortPeriodEndDate, "Verify that the response returns the end date [" + calculate.getShareChainUnSharedRoomDetailsEndDate("1", "1").split("T")[0] + "] that which is expected [" + resortPeriodEndDate + "].");

        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCId(), TC1, "Verify that the response returns the TC id [" + calculate.getShareChainUnSharedRoomDetailsTCId() + "] that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCId("1", "2"), TC2, "Verify that the response returns the TC id no overlap [" + calculate.getShareChainUnSharedRoomDetailsTCId("1", "2") + "] that which is expected [" + TC2 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCGId(), TCG1, "Verify that the response returns the TCG id [" + calculate.getShareChainUnSharedRoomDetailsTCGId() + "] that which is expected [" + TCG1 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTCGId("1", "2"), TCG2, "Verify that the response returns the TCG id no overlap [" + calculate.getShareChainUnSharedRoomDetailsTCGId("1", "2") + "] that which is expected [" + TCG2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedAccommodationUnSharedRoomDetailsTPSId(), TPS1, "Verify that the response returns the TPS id [" + calculate.getUnsharedAccommodationUnSharedRoomDetailsTPSId() + "] that which is expected [" + TPS1 + "].");
        TestReporter.softAssertEquals(calculate.getShareChainUnSharedRoomDetailsTPSId("1", "2"), TPS2, "Verify that the response returns the TPS id no overlap [" + calculate.getShareChainUnSharedRoomDetailsTPSId("1", "2") + "] that which is expected [" + TPS2 + "].");

        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalCharge(), addtlCharge, "Verify that the response returns the additional charge [" + calculate.getUnsharedRoomDetailsAdditionalCharge() + "] that which is expected [" + addtlCharge + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalChargeOveridden(), addtlChargeOver, "Verify that the response returns the additional charge overidden [" + calculate.getUnsharedRoomDetailsAdditionalChargeOveridden() + "] that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsBasePrice(), basePrice, "Verify that the response returns the base price [" + calculate.getUnsharedRoomDetailsBasePrice() + "] that which is expected [" + basePrice + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDate().split("T")[0], date, "Verify that the date [" + calculate.getUnsharedRoomDetailsDate().split("T")[0] + "] that which is expected [" + date + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDayCount(), dayCount, "Verify that the response returns the day count [" + calculate.getUnsharedRoomDetailsDayCount() + "] that which is expected [" + dayCount + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsOveridden(), overidden, "Verify that the response returns the overidden status [" + calculate.getUnsharedRoomDetailsOveridden() + "] that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsShared(), shared, "Verify that the response returns the shared status [" + calculate.getUnsharedRoomDetailsShared() + "] that which is expected [" + shared + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsNetPrice(), netPrice, "Verify that the response returns the net price [" + calculate.getUnsharedRoomDetailsNetPrice() + "] that which is expected [" + netPrice + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsPointsValue(), pointsValue, "Verify that the response returns the points value [" + calculate.getUnsharedRoomDetailsPointsValue() + "] that which is expected [" + pointsValue + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalCharge("1", "1"), addtlCharge2, "Verify that the response returns the additional charge [" + calculate.getUnsharedRoomDetailsAdditionalCharge("1", "1") + "] that which is expected [" + addtlCharge2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalChargeOveridden("1", "1"), addtlChargeOver2, "Verify that the response returns the additional charge overidden [" + calculate.getUnsharedRoomDetailsAdditionalChargeOveridden("1", "1") + "] that which is expected [" + addtlChargeOver2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsBasePrice("1", "1"), basePrice2, "Verify that the response returns the base price [" + calculate.getUnsharedRoomDetailsBasePrice("1", "1") + "] that which is expected [" + basePrice2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDate("1", "2").split("T")[0], date2, "Verify that the date [" + calculate.getUnsharedRoomDetailsDate("1", "2").split("T")[0] + "] that which is expected [" + date2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDayCount("1", "1"), dayCount2, "Verify that the response returns the day count [" + calculate.getUnsharedRoomDetailsDayCount("1", "1") + "] that which is expected [" + dayCount2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsOveridden("1", "1"), overidden2, "Verify that the response returns the overidden status [" + calculate.getUnsharedRoomDetailsOveridden("1", "1") + "] that which is expected [" + overidden2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsShared("1", "1"), shared2, "Verify that the response returns the shared status [" + calculate.getUnsharedRoomDetailsShared("1", "1") + "] that which is expected [" + shared2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsNetPrice("1", "1"), netPrice2, "Verify that the response returns the net price [" + calculate.getUnsharedRoomDetailsNetPrice("1", "1") + "] that which is expected [" + netPrice2 + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsPointsValue("1", "1"), pointsValue2, "Verify that the response returns the points value [" + calculate.getUnsharedRoomDetailsPointsValue("1", "1") + "] that which is expected [" + pointsValue2 + "].");
        TestReporter.assertAll();
    }

    public void validateUnsharedAccommodation() {
        String resortPeriodStartDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/resortPeriod/startDate");
        String resortPeriodEndDate = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/resortPeriod/endDate");
        String TC1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentId");
        String TCG1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/travelComponentGroupingId");
        String TPS1 = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails[1]/travelPlanSegmentId");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/additionalChargeOverridden");
        String date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/date");
        String dayCount = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/pointsValue");

        String responseAdditionalChargeOverridden;
        String responseDate;
        String responseDayCount;
        String responseNetPrice;
        String responseOveridden;
        String responsePointsValue;
        String responseShared;
        TestReporter.logStep("Validate unshared accommodation shared room details rate details");
        for (int i = 1; i <= 2; i++) {
            TestReporter.log("Validating rate details [" + String.valueOf(i) + "]");
            responseAdditionalChargeOverridden = calculate.getUnsharedAccommodationSharedRoomDetailAdditionalChargeOverridden(String.valueOf(i));
            date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails[" + String.valueOf(i) + "]/date");
            responseDate = calculate.getUnsharedAccommodationSharedRoomDetailDate(String.valueOf(i)).split("T")[0];
            responseDate = Randomness.generateCurrentXMLDate(i - 1);
            responseDayCount = calculate.getUnsharedAccommodationSharedRoomDetailDayCount(String.valueOf(i));
            responseNetPrice = calculate.getUnsharedAccommodationSharedRoomDetailNetPrice(String.valueOf(i));
            responseOveridden = calculate.getUnsharedAccommodationSharedRoomDetailOveridden(String.valueOf(i));
            responsePointsValue = calculate.getUnsharedAccommodationSharedRoomDetailPointsValue(String.valueOf(i));
            responseShared = calculate.getUnsharedAccommodationSharedRoomDetailShared(String.valueOf(i));
            dayCount = String.valueOf(i - 1);
            TestReporter.softAssertEquals(addtlChargeOver, responseAdditionalChargeOverridden, "Verify that the unshared accommodation shared room details additional charge overridden [" + responseAdditionalChargeOverridden + "] is that which is expected [" + addtlChargeOver + "].");
            TestReporter.softAssertEquals(date, responseDate, "Verify that the unshared accommodation shared room details date [" + responseDate + "] is that which is expected [" + date + "].");
            TestReporter.softAssertEquals(dayCount, responseDayCount, "Verify that the unshared accommodation shared room details day count [" + responseDayCount + "] is that which is expected [" + dayCount + "].");
            TestReporter.softAssertEquals(netPrice, responseNetPrice, "Verify that the unshared accommodation shared room details net price [" + responseNetPrice + "] is that which is expected [" + netPrice + "].");
            TestReporter.softAssertEquals(overidden, responseOveridden, "Verify that the unshared accommodation shared room details overidden [" + responseOveridden + "] is that which is expected [" + overidden + "].");
            TestReporter.softAssertEquals(pointsValue, responsePointsValue, "Verify that the unshared accommodation shared room details points valus [" + responsePointsValue + "] is that which is expected [" + pointsValue + "].");
            TestReporter.softAssertEquals(shared, responseShared, "Verify that the unshared accommodation shared room details shared [" + responseShared + "] is that which is expected [" + shared + "].");
        }
        TestReporter.logStep("Validate unshared accommodation unshared room details rate details");
        for (int i = 1; i <= 2; i++) {
            TestReporter.log("Validating rate details [" + String.valueOf(i) + "]");
            responseAdditionalChargeOverridden = calculate.getUnsharedAccommodationUnsharedRoomDetailAdditionalChargeOverridden(String.valueOf(i));
            date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails[" + String.valueOf(i) + "]/date");
            responseDate = Randomness.generateCurrentXMLDate(i - 1);
            responseOveridden = calculate.getUnsharedAccommodationUnsharedRoomDetailOveridden(String.valueOf(i));
            responsePointsValue = calculate.getUnsharedAccommodationUnsharedRoomDetailPointsValue(String.valueOf(i));
            responseShared = calculate.getUnsharedAccommodationUnsharedRoomDetailShared(String.valueOf(i));
            TestReporter.softAssertEquals(addtlChargeOver, responseAdditionalChargeOverridden, "Verify that the unshared accommodation shared room details additional charge overridden [" + responseAdditionalChargeOverridden + "] is that which is expected [" + addtlChargeOver + "].");
            TestReporter.softAssertEquals(date, responseDate, "Verify that the unshared accommodation unshared room details date [" + responseDate + "] is that which is expected [" + date + "].");
            TestReporter.softAssertEquals(overidden, responseOveridden, "Verify that the unshared accommodation unshared room details overidden [" + responseOveridden + "] is that which is expected [" + overidden + "].");
            TestReporter.softAssertEquals(pointsValue, responsePointsValue, "Verify that the unshared accommodation unshared room details points valus [" + responsePointsValue + "] is that which is expected [" + pointsValue + "].");
            TestReporter.softAssertEquals(shared, responseShared, "Verify that the unshared accommodation unshared room details shared [" + responseShared + "] is that which is expected [" + shared + "].");
        }

        TestReporter.logStep("Validate resort period dates");
        String responseStartDate = calculate.getUnsharedAccommodationSharedRoomDetailStartDate().split("T")[0];
        String responseEndDate = calculate.getUnsharedAccommodationSharedRoomDetailEndDate().split("T")[0];
        resortPeriodStartDate = Randomness.generateCurrentXMLDate();
        resortPeriodEndDate = Randomness.generateCurrentXMLDate(2);
        TestReporter.softAssertEquals(responseStartDate, resortPeriodStartDate, "Verify that the unshared accommodation shared room details start date [" + responseStartDate + "] is that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(responseEndDate, resortPeriodEndDate, "Verify that the unshared accommodation shared room details end date[" + responseEndDate + "] is that which is expected [" + resortPeriodEndDate + "].");
        responseStartDate = calculate.getUnsharedAccommodationSharedRoomDetailStartDate().split("T")[0];
        responseEndDate = calculate.getUnsharedAccommodationSharedRoomDetailEndDate().split("T")[0];
        TestReporter.softAssertEquals(responseStartDate, resortPeriodStartDate, "Verify that the unshared accommodation unshared room details start date [" + responseStartDate + "] is that which is expected [" + resortPeriodStartDate + "].");
        TestReporter.softAssertEquals(responseEndDate, resortPeriodEndDate, "Verify that the unshared accommodation unshared room details end date[" + responseEndDate + "] is that which is expected [" + resortPeriodEndDate + "].");

        TestReporter.logStep("Validate TPS, TCG, and TC ids");
        String responseTC = calculate.getUnsharedAccommodationSharedRoomDetailTC();
        String responseTCG = calculate.getUnsharedAccommodationSharedRoomDetailTCG();
        TestReporter.softAssertEquals(responseTC, TC1, "Verify that the unshared accommodation shared room details TC ID [" + responseTC + "] is that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(responseTCG, TCG1, "Verify that the unshared accommodation shared room details TCG ID [" + responseTCG + "] is that which is expected [" + TCG1 + "].");
        responseTC = calculate.getUnsharedAccommodationUnsharedRoomDetailTC();
        responseTCG = calculate.getUnsharedAccommodationUnsharedRoomDetailTCG();
        TestReporter.softAssertEquals(responseTC, TC1, "Verify that the unshared accommodation unshared room details TC ID [" + responseTC + "] is that which is expected [" + TC1 + "].");
        TestReporter.softAssertEquals(responseTCG, TCG1, "Verify that the unshared accommodation unshared room details TCG ID [" + responseTCG + "] is that which is expected [" + TCG1 + "].");

        String responseTPS = calculate.getUnsharedAccommodationTPS();
        TestReporter.softAssertEquals(responseTPS, TPS1, "Verify that the unshared accommodation TPS ID [" + responseTPS + "] is that which is expected [" + TPS1 + "].");
        TestReporter.assertAll();
    }

}
