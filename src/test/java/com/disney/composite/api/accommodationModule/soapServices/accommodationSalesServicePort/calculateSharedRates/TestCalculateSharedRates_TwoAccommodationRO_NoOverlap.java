package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateSharedRates;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateSharedRates_TwoAccommodationRO_NoOverlap extends AccommodationBaseTest {

    String rateDetailsAccommOne;
    String rateDetailsAccommTwo;
    double rateDetails1;
    double rateDetails2;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates" })
    public void testCalculateSharedRates_TwoAccommodationRO_NoOverlap() {

        // System.out.println(twoDaysFromToday);
        CalculateSharedRates calculate = new CalculateSharedRates(environment, "TwoAccommodations");
        calculate.setBookingDate("2017-10-25T00:00:00");
        calculate.setPeriodSD("2017-10-25T00:00:00");
        calculate.setPeriodED("2017-10-26T00:00:00");

        calculate.setBookingDate2("2017-10-27T00:00:00");
        calculate.setPeriodSD2("2017-10-27T00:00:00");
        calculate.setPeriodED2("2017-10-28T00:00:00");
        calculate.sendRequest();
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred Calculating Shared Rates: " + calculate.getFaultString(), calculate);

        TestReporter.softAssertTrue(calculate.getBookingDate().equals(calculate.getBookingDateRQ()), "The booking date in the request [" + calculate.getBookingDateRQ() + "] matches the booking date in the response [" + calculate.getBookingDate() + "].");
        TestReporter.softAssertTrue(calculate.getInventoryStatus().equals(calculate.getInventoryStatusRQ()), "The inventory status in the request [" + calculate.getInventoryStatusRQ() + "] matches the inventory status in the response [" + calculate.getInventoryStatus() + "].");
        TestReporter.softAssertTrue(calculate.getOverideFreeze().equals(calculate.getOverideFreezeRQ()), "The overide freeze in the request [" + calculate.getOverideFreezeRQ() + "] matches the overide freeze in the response [" + calculate.getOverideFreeze() + "].");
        TestReporter.softAssertTrue(calculate.getResortCode().equals(calculate.getResortCodeRQ()), "The resort code in the request [" + calculate.getResortCodeRQ() + "] matches the resort code in the response [" + calculate.getResortCode() + "].");
        TestReporter.softAssertTrue(calculate.getResortPeriodSD().equals(calculate.getResortPeriodSDRQ()), "The period start date in the request [" + calculate.getResortPeriodSDRQ() + "] matches the period start date in the response [" + calculate.getResortPeriodSD() + "].");
        TestReporter.softAssertTrue(calculate.getResortPeriodED().equals(calculate.getResortPeriodEDRQ()), "The period end date in the request [" + calculate.getResortPeriodEDRQ() + "] matches the period end date in the response [" + calculate.getResortPeriodED() + "].");
        TestReporter.softAssertTrue(calculate.getFirstName().equals(calculate.getFirstNameRQ()), "The first name in the request [" + calculate.getFirstNameRQ() + "] matches the first name in the response [" + calculate.getFirstName() + "].");
        TestReporter.softAssertTrue(calculate.getLastName().equals(calculate.getLastNameRQ()), "The last name in the request [" + calculate.getFirstNameRQ() + "] matches the last name in the response [" + calculate.getFirstName() + "].");
        TestReporter.softAssertTrue(calculate.getEmailAddress().equals(calculate.getEmailAddressRQ()), "The email address in the request [" + calculate.getEmailAddressRQ() + "] matches the email address in the response [" + calculate.getEmailAddress() + "].");
        TestReporter.softAssertTrue(calculate.getPhoneDetailsNumber().equals(calculate.getPhoneDetailsNumberRQ()), "The phone number in the request [" + calculate.getPhoneDetailsNumberRQ() + "] matches the phone number in the response [" + calculate.getPhoneDetailsNumber() + "].");
        TestReporter.softAssertTrue(calculate.getAddressLine1().equals(calculate.getAddressLine1RQ()), "The address in the request [" + calculate.getAddressLine1RQ() + "] matches the address in the response [" + calculate.getEmailAddress() + "].");
        TestReporter.softAssertTrue(calculate.getDoNotMailIndicator().equals(calculate.getDoNotMailIndicatorRQ()), "The Do Not Mail Indicator in the request [" + calculate.getDoNotMailIndicatorRQ() + "] matches the Do Not Mail Indicator in the response [" + calculate.getDoNotMailIndicator() + "].");
        TestReporter.softAssertTrue(calculate.getDoNotPhoneIndicator().equals(calculate.getDoNotPhoneIndicatorRQ()), "The Do Not Phone Indicator in the request [" + calculate.getDoNotPhoneIndicatorRQ() + "] matches the Do Not Phone Indicator in the response [" + calculate.getDoNotPhoneIndicator() + "].");
        TestReporter.softAssertTrue(calculate.getTravelStatus().equals(calculate.getTravelStatusRQ()), "The travel status in the request [" + calculate.getTravelStatusRQ() + "] matches the travel status in the response [" + calculate.getTravelStatus() + "].");

        TestReporter.softAssertTrue(calculate.getRateDetailsAccommOneNoOverlap(1).equals(calculate.getTotalRateAmountIndex()), "The rate details in the first acommodation is [" + calculate.getRateDetailsAccommOneNoOverlap(1) + "] and is equal to the first total rate amount [" + calculate.getTotalRateAmountIndex() + "]");
        TestReporter.softAssertTrue("0.0".equals(calculate.getTotalRateAmountIndex2().toString()), "The second total rates [" + calculate.getTotalRateAmountIndex2() + "] will equal 0.0");
        TestReporter.softAssertTrue(calculate.getRateDetailsAccommTwoNoOverlap(1).equals(calculate.getTotalRateAmountIndex3()), "The rate details in the second accommodation is [" + calculate.getRateDetailsAccommTwoNoOverlap(1) + "] and is equal to the [" + calculate.getTotalRateAmountIndex3() + "]");

        for (int j = 1; j <= calculate.getNumberOfResponseNodesByXPath("/Envelope/Body/calculateSharedRatesResponse/splitRateWithTotalTO/accommodations/rateDetails/shared"); j++) {
            TestReporter.softAssertTrue(calculate.getShared(j).equals("false"), "The Shared node in the accommodation node number[" + j + "] is set to [" + calculate.getShared(j) + "].");
        }
        if (Environment.isSpecialEnvironment(environment)) {
            CalculateSharedRates clone = (CalculateSharedRates) calculate.clone();
            clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned: " + clone.getFaultString(), clone);
            }
            clone.addExcludedBaselineAttributeValidations("@xsi:nil");
            clone.addExcludedBaselineAttributeValidations("@xsi:type");
            clone.addExcludedBaselineXpathValidations("/Envelope/Header");
            TestReporter.assertTrue(clone.validateResponseNodeQuantity(calculate, true), "Validating Response Comparison");

        }
    }
}
