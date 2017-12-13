package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.calculateSharedRates;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CalculateSharedRates;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestCalculateSharedRates_TwoAccommodationsRO_MultiDayPartial extends AccommodationBaseTest {
    String rateDetailsAccommOne;
    String rateDetailsAccommTwo;
    String rateDetailsAccommOneRD2;
    String rateDetailsAccommTwoRD2;
    String rateDetailsAccommOneRD3;
    String rateDetailsAccommTwoRD3;
    double rateDetails1;
    double rateDetails2;
    double rateDetails1RD2;
    double rateDetails2RD2;
    double rateDetails1RD3;
    double rateDetails2RD3;

    private ReplaceAllForTravelPlanSegment book1;
    private AccommodationBaseTest base;

    @Override
    @Parameters("environment")
    @BeforeMethod(alwaysRun = true)
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(4);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        bookReservation();
        book1 = getBook();

        setDaysOut(3);
        setNights(4);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());

        bookReservation();

        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second accommodation: " + getBook().getFaultString(), getBook());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "calculateSharedRates" })
    public void testCalculateSharedRates_TwoAccommodationRO_MultiDayPartial() {

        CalculateSharedRates calculate = new CalculateSharedRates(environment, "TwoAccommodations");
        calculate.setPeriodSD(book1.getStartDate());
        calculate.setPeriodED(book1.getEndDate());
        calculate.setPeriodSD2(getArrivalDate());
        calculate.setPeriodED2(getDepartureDate());
        calculate.sendRequest();
        TestReporter.logAPI(!calculate.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", calculate);

        rateDetailsAccommOne = calculate.getRateDetailsAccommOne();
        rateDetailsAccommTwo = calculate.getRateDetailsAccommTwo();
        rateDetailsAccommOneRD2 = calculate.getRateDetailsAccommOneRD2();
        rateDetailsAccommTwoRD2 = calculate.getRateDetailsAccommTwoRD2();
        rateDetailsAccommOneRD3 = calculate.getRateDetailsAccommOneRD3();
        rateDetailsAccommTwoRD3 = calculate.getRateDetailsAccommTwoRD3();
        rateDetails1 = Double.parseDouble(rateDetailsAccommOne);
        rateDetails2 = Double.parseDouble(rateDetailsAccommTwo);
        rateDetails1RD2 = Double.parseDouble(rateDetailsAccommOneRD2);
        rateDetails2RD2 = Double.parseDouble(rateDetailsAccommTwoRD2);
        rateDetails1RD3 = Double.parseDouble(rateDetailsAccommOneRD3);
        rateDetails2RD3 = Double.parseDouble(rateDetailsAccommTwoRD3);

        Double totalRateAmount = 0.0;
        Double totalRateAmountRD2 = 0.0;
        Double totalRateAmountRD3 = 0.0;
        totalRateAmount = rateDetails1 + rateDetails2;
        totalRateAmountRD2 = rateDetails1RD2 + rateDetails2RD2;
        totalRateAmountRD3 = rateDetails1RD3 + rateDetails2RD3;

        String totalRateAmountString = String.valueOf(totalRateAmount);
        String totalRateAmountStringRD2 = String.valueOf(totalRateAmountRD2);
        String totalRateAmountStringRD3 = String.valueOf(totalRateAmountRD3);

        TestReporter.softAssertTrue(totalRateAmountString.equals(calculate.getTotalRateAmount()), "The first rate details in the first acommodation is [" + rateDetailsAccommOne + "] and the first rate details in the second accommodation is [" + rateDetailsAccommTwo + "] and is equal to the [" + calculate.getTotalRateAmount() + "]");
        TestReporter.softAssertTrue(totalRateAmountStringRD2.equals(calculate.getTotalRateAmountIndex2()), "The second rate details in the first acommodation is [" + rateDetailsAccommOneRD2 + "] and the second rate details in the second accommodation is [" + rateDetailsAccommTwoRD2 + "] and is equal to the [" + calculate.getTotalRateAmountIndex2() + "]");
        TestReporter.softAssertTrue(totalRateAmountStringRD3.equals(calculate.getTotalRateAmountIndex3()), "The third rate details in the first acommodation is [" + rateDetailsAccommOneRD3 + "] and the third rate details in the second accommodation is [" + rateDetailsAccommTwoRD3 + "] and is equal to the [" + calculate.getTotalRateAmountIndex3() + "]");

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

        TestReporter.softAssertTrue(calculate.getShared(1).equals("true"), "The Shared node in the accommodation node number[" + 1 + "] is set to [" + calculate.getShared(1) + "].");
        TestReporter.softAssertTrue(calculate.getShared(2).equals("true"), "The Shared node in the accommodation node number[" + 2 + "] is set to [" + calculate.getShared(2) + "].");

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
