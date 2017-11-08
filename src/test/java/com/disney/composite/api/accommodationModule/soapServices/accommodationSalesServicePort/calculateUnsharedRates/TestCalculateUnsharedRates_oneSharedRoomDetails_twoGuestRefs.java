package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateUnsharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateUnsharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateUnsharedRates_oneSharedRoomDetails_twoGuestRefs extends AccommodationBaseTest {

    CalculateUnsharedRates calculate;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateUnsharedRates" })
    public void Test_CalculateUnsharedRates_OneSharedRoomDetails_twoGuestRefs() {

        calculate = new CalculateUnsharedRates(environment, "Main_TwoGuestRefs");
        calculate.setUnsharedChainSharedRoomDetailTCGId("0");
        calculate.setUnsharedChainSharedRoomDetailTCId("0");
        calculate.setUnsharedChainShareRoomDetailsTPSId("0");
        calculate.setUnsharedChainUnsharedRoomDetailTCGId("0");
        calculate.setUnsharedChainUnsharedRoomDetailTCId("0");
        calculate.setUnsharedAccommodationSharedRoomDetailsTCGId("0");
        calculate.setUnsharedAccommodationSharedRoomDetailsTCId("0");
        calculate.setUnsharedAccommodationUnSharedRoomDetailsTCGId("0");
        calculate.setUnsharedAccommodationUnSharedRoomDetailsTCId("0");
        calculate.setUnsharedAccommodationTPSId("0");

        calculate.addUnsharedChainUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainUnsharedRoomDetailGuestReferenceDetails();

        calculate.addUnsharedChainSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedChainSharedRoomDetailGuestReferenceDetails();

        calculate.addUnsharedAccommUnsharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedAccommUnsharedRoomDetailGuestReferenceDetails();

        calculate.addUnsharedAccommSharedRoomDetailGuestReferenceDetails();
        calculate.setUnsharedAccommSharedRoomDetailGuestReferenceDetails();

        // calculate.setRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/exchangeFee", BaseSoapCommands.REMOVE_NODE.toString());
        calculate.sendRequest();
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred calculating unshared rates: " + calculate.getFaultString(), calculate);

        validateUnsharedRoomRateDetailsResponse();
        validateSharedRoomRateDetailsResponse();
        validateSharedRoomRateDetailsResponseDiffers();
        validateUnSharedRoomRateDetailsResponseDiffers();
        validateNumberShareChainUnsharedRoomRateDetailsResponseNodes();
        validateNumberShareChainSharedRoomRateDetailsResponseNodes();
        validateUnSharedAccommNumberUnSharedRoomRateDetailsResponseNodes();
        validateUnSharedAccommNumberSharedRoomRateDetailsResponseNodes();

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

    public void validateUnsharedRoomRateDetailsResponse() {

        TestReporter.logStep("Validate Unshared room rate details in the response node.");
        String addtlCharge = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/basePrice");
        String date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/date");
        String dayCount = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/unSharedRoomDetail/rateDetails/pointsValue");

        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalCharge(), addtlCharge, "Verify that the response returns the additional charge [" + calculate.getUnsharedRoomDetailsAdditionalCharge() + "] that which is expected [" + addtlCharge + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsAdditionalChargeOveridden(), addtlChargeOver, "Verify that the response returns the additional charge overidden [" + calculate.getUnsharedRoomDetailsAdditionalChargeOveridden() + "] that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsBasePrice(), basePrice, "Verify that the response returns the tcId [" + calculate.getUnsharedRoomDetailsBasePrice() + "] that which is expected [" + basePrice + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDate(), date, "Verify that the date [" + calculate.getUnsharedRoomDetailsDate() + "] that which is expected [" + date + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsDayCount(), dayCount, "Verify that the response returns the day count [" + calculate.getUnsharedRoomDetailsDayCount() + "] that which is expected [" + dayCount + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsOveridden(), overidden, "Verify that the response returns the overidden status [" + calculate.getUnsharedRoomDetailsOveridden() + "] that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsShared(), shared, "Verify that the response returns the shared status [" + calculate.getUnsharedRoomDetailsShared() + "] that which is expected [" + shared + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsNetPrice(), netPrice, "Verify that the response returns the net price [" + calculate.getUnsharedRoomDetailsNetPrice() + "] that which is expected [" + netPrice + "].");
        TestReporter.softAssertEquals(calculate.getUnsharedRoomDetailsPointsValue(), pointsValue, "Verify that the response returns the points value [" + calculate.getUnsharedRoomDetailsPointsValue() + "] that which is expected [" + pointsValue + "].");
        TestReporter.assertAll();

    }

    public void validateSharedRoomRateDetailsResponse() {

        TestReporter.logStep("Validate shared room rate details in the response node.");
        String addtlCharge = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/basePrice");
        String date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/date");
        String dayCount = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/sharedRoomDetail/rateDetails/pointsValue");

        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsAdditionalCharge(), addtlCharge, "Verify that the response returns the additional charge [" + calculate.getSharedRoomDetailsAdditionalCharge() + "] that which is expected [" + addtlCharge + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsAdditionalChargeOveridden(), addtlChargeOver, "Verify that the response returns the additional overriden charge [" + calculate.getSharedRoomDetailsAdditionalChargeOveridden() + "] that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsBasePrice(), basePrice, "Verify that the response returns the base price [" + calculate.getSharedRoomDetailsBasePrice() + "] that which is expected [" + basePrice + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsDate(), date, "Verify that the date [" + calculate.getSharedRoomDetailsDate() + "] that which is expected [" + date + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsDayCount(), dayCount, "Verify that the response returns the day count [" + calculate.getSharedRoomDetailsDayCount() + "] that which is expected [" + dayCount + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsOveridden(), overidden, "Verify that the response returns the overidden details [" + calculate.getSharedRoomDetailsOveridden() + "] that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsShared(), shared, "Verify that the response returns the details shared [" + calculate.getSharedRoomDetailsShared() + "] that which is expected [" + shared + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsNetPrice(), netPrice, "Verify that the response returns the net price [" + calculate.getSharedRoomDetailsNetPrice() + "] that which is expected [" + netPrice + "].");
        TestReporter.softAssertEquals(calculate.getSharedRoomDetailsPointsValue(), pointsValue, "Verify that the response returns the points value [" + calculate.getSharedRoomDetailsPointsValue() + "] that which is expected [" + pointsValue + "].");
        TestReporter.assertAll();

    }

    public void validateSharedRoomRateDetailsResponseDiffers() {

        TestReporter.logStep("Validate shared room rate details differ from what is in the response node.");
        String addtlCharge = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/basePrice");
        String date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/date");
        String dayCount = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unSharedChain/shareRoomDetails/sharedRoomDetail/rateDetails/pointsValue");

        TestReporter.softAssertTrue(calculate.getShareChainSharedRoomDetailsAdditionalCharge() != addtlCharge, "Verify that the response does not returns the additional charge [" + calculate.getShareChainSharedRoomDetailsAdditionalCharge() + "] that was passed into the request[" + addtlCharge + "].");
        TestReporter.softAssertEquals(calculate.getShareChainSharedRoomDetailsAdditionalChargeOveridden(), addtlChargeOver, "Verify that the response returns the additional charge overriden [" + calculate.getShareChainSharedRoomDetailsAdditionalChargeOveridden() + "] that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertTrue(calculate.getShareChainSharedRoomDetailsBasePrice() != basePrice, "Verify that the response does not return the base price [" + calculate.getShareChainSharedRoomDetailsBasePrice() + "] that which was passed into the request [" + basePrice + "].");
        TestReporter.softAssertTrue(calculate.getShareChainSharedRoomDetailsDate() != date, "Verify that the response does not return the date [" + calculate.getShareChainSharedRoomDetailsDate() + "] that was passed into the request [" + date + "].");
        TestReporter.softAssertTrue(calculate.getShareChainSharedRoomDetailsDayCount() != dayCount, "Verify that the response does not return the day count [" + calculate.getShareChainSharedRoomDetailsDayCount() + "] that was passed into the request [" + dayCount + "].");
        TestReporter.softAssertEquals(calculate.getShareChainSharedRoomDetailsOveridden(), overidden, "Verify that the response returns the overridden field [" + calculate.getUnsharedRoomDetailsOveridden() + "] that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(calculate.getShareChainSharedRoomDetailsShared(), shared, "Verify that the response returns the shared field [" + calculate.getShareChainSharedRoomDetailsShared() + "] that which is expected [" + shared + "].");
        TestReporter.softAssertTrue(calculate.getShareChainSharedRoomDetailsNetPrice() != netPrice, "Verify that the response does not return the net price [" + calculate.getShareChainSharedRoomDetailsNetPrice() + "] that was passed into the request [" + netPrice + "].");
        TestReporter.softAssertTrue(calculate.getShareChainSharedRoomDetailsPointsValue() != pointsValue, "Verify that the response does not return the points value [" + calculate.getShareChainSharedRoomDetailsPointsValue() + "] that was passed into the request [" + pointsValue + "].");
        TestReporter.assertAll();

    }

    public void validateUnSharedRoomRateDetailsResponseDiffers() {

        TestReporter.logStep("Validate unshared room rate details differs from what is in the response node.");
        String addtlCharge = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/additionalCharge");
        String addtlChargeOver = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/additionalChargeOverridden");
        String basePrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/basePrice");
        String date = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/date");
        String dayCount = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/dayCount");
        String overidden = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/overidden");
        String shared = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/shared");
        String netPrice = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/netPrice");
        String pointsValue = calculate.getRequestNodeValueByXPath("/Envelope/Body/calculateUnsharedRates/request/unsharedAccomadation/unSharedRoomDetail/rateDetails/pointsValue");

        TestReporter.softAssertTrue(calculate.getUnSharedRoomDetailsAdditionalCharge() != addtlCharge, "Verify that the response does not returns the additional charge [" + calculate.getUnsharedRoomDetailsAdditionalCharge() + "] that was passed into the request[" + addtlCharge + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedRoomDetailsAdditionalChargeOveridden(), addtlChargeOver, "Verify that the response returns the additional charge overriden [" + calculate.getUnsharedRoomDetailsAdditionalChargeOveridden() + "] that which is expected [" + addtlChargeOver + "].");
        TestReporter.softAssertTrue(calculate.getUnSharedRoomDetailsBasePrice() != basePrice, "Verify that the response does not return the base price [" + calculate.getUnsharedRoomDetailsBasePrice() + "] that which was passed into the request [" + basePrice + "].");
        TestReporter.softAssertTrue(calculate.getUnSharedRoomDetailsDate() != date, "Verify that the response does not return the date [" + calculate.getUnsharedRoomDetailsDate() + "] that was passed into the request [" + date + "].");
        TestReporter.softAssertTrue(calculate.getUnSharedRoomDetailsDayCount() != dayCount, "Verify that the response does not returns the day count [" + calculate.getUnsharedRoomDetailsDayCount() + "] that was passed into the request [" + dayCount + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedRoomDetailsOveridden(), overidden, "Verify that the response returns the overridden field [" + calculate.getUnsharedRoomDetailsOveridden() + "] that which is expected [" + overidden + "].");
        TestReporter.softAssertEquals(calculate.getUnSharedRoomDetailsShared(), shared, "Verify that the response returns the shared field [" + calculate.getUnsharedRoomDetailsShared() + "] that which is expected [" + shared + "].");
        TestReporter.softAssertTrue(calculate.getUnSharedRoomDetailsNetPrice() != netPrice, "Verify that the response does not return the net price [" + calculate.getUnsharedRoomDetailsNetPrice() + "] that was passed into the request [" + netPrice + "].");
        TestReporter.softAssertTrue(calculate.getUnSharedRoomDetailsPointsValue() != pointsValue, "Verify that the response does not return the points value [" + calculate.getUnSharedRoomDetailsPointsValue() + "] that was passed into the request [" + pointsValue + "].");
        TestReporter.assertAll();

    }

    public void validateNumberShareChainUnsharedRoomRateDetailsResponseNodes() {
        TestReporter.logStep("Validate the number of guest response nodes");
        // Verify that 2 response nodes were returned for the guest node.
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails"), 2, "Verify that there were two response nodes returned for the ShareChain/UnSharedRoom detail guest node.");
        TestReporter.assertAll();
    }

    public void validateNumberShareChainSharedRoomRateDetailsResponseNodes() {
        TestReporter.logStep("Validate the number of guest response nodes");
        // Verify that 2 response nodes were returned for the guest node.
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/shareChains/shareRoomDetails/sharedRoomDetail/roomReservationDetail/guestReferenceDetails"), 2, "Verify that there were two response nodes returned for the ShareChain/SharedRoom detail guest node.");
        TestReporter.assertAll();
    }

    public void validateUnSharedAccommNumberUnSharedRoomRateDetailsResponseNodes() {
        TestReporter.logStep("Validate the number of guest response nodes");
        // Verify that 2 response nodes were returned for the guest node.
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/unSharedRoomDetail/roomReservationDetail/guestReferenceDetails"), 2, "Verify that there were two response nodes returned for the UnSharedAccommodation/UnSharedRoom detail guest node.");
        TestReporter.assertAll();
    }

    public void validateUnSharedAccommNumberSharedRoomRateDetailsResponseNodes() {
        TestReporter.logStep("Validate the number of guest response nodes");
        // Verify that 2 response nodes were returned for the guest node.
        TestReporter.softAssertEquals(calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateUnsharedRatesResponse/splitRateWithTotalTO/unsharedAccommodation/sharedRoomDetail/roomReservationDetail/guestReferenceDetails"), 2, "Verify that there were two response nodes returned for the UnSharedAccommodation/SharedRoom detail guest node.");
        TestReporter.assertAll();
    }
}
