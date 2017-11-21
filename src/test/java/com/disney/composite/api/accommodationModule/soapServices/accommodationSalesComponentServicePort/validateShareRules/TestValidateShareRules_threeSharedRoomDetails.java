package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_threeSharedRoomDetails extends AccommodationBaseTest {

    private ReplaceAllForTravelPlanSegment book;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;
    private String tcgIdTwo;
    private String tcIdTwo;
    private String tcgIdThree;
    private String tcIdThree;
    private String startDate;
    private String endDate;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();

        book = getBook();

        tpId = book.getTravelPlanId();
        tpsId = book.getTravelPlanSegmentId();
        tcgId = book.getTravelComponentGroupingId();
        tcId = book.getTravelComponentId();
        startDate = getArrivalDate();
        endDate = getDepartureDate();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules" })
    public void testValidateShareRules_threeSharedRoomDetails() {

        bookTwoMore();

        ValidateShareRules validate = new ValidateShareRules(environment, "ThreeRoomDetails");
        validate.setTravelComponentGroupingId(tcgId);
        validate.setTravelComponentId(tcId);
        validate.setTravelComponentGroupingId(tcgIdTwo, "2");
        validate.setTravelComponentId(tcIdTwo, "2");
        validate.setTravelComponentGroupingId(tcgIdThree, "3");
        validate.setTravelComponentId(tcIdThree, "3");
        validate.setStartDate(startDate, "1");
        validate.setEndDate(endDate, "1");
        validate.setStartDate(getArrivalDate(), "2");
        validate.setEndDate(getDepartureDate(), "2");
        validate.setStartDate(getArrivalDate(), "3");
        validate.setEndDate(getDepartureDate(), "3");
        validate.sendRequest();
        TestReporter.logAPI(!validate.getResponseStatusCode().equals("200"), "An error occurred validating share rules", validate);

        TestReporter.assertTrue(validate.getReturn().contains("true"), "Validate the return value is set to true as expected: [" + validate.getReturn() + "]");

    }

    public void bookTwoMore() {

        setSendRequest(false);
        setDaysOut(1);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        bookReservation();
        book.setTravelPlanId(tpId);
        book.setTravelPlanSegementId(tpsId);

        int triesOne = 0;
        int maxTriesOne = 30;
        boolean successOne = false;
        do {
            Sleeper.sleep(1000);
            book.sendRequest();
            triesOne++;
            if (book.getResponseStatusCode().equals("200")) {
                successOne = true;
            }
        } while (triesOne < maxTriesOne && !successOne);
        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + book.getFaultString(), book);

        tcgIdTwo = book.getTravelComponentGroupingId();
        tcIdTwo = book.getTravelComponentId();

        setSendRequest(false);
        setDaysOut(1);
        setNights(2);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        bookReservation();
        book.setTravelPlanId(tpId);
        book.setTravelPlanSegementId(tpsId);

        int triesTwo = 0;
        int maxTriesTwo = 30;
        boolean successTwo = false;
        do {
            Sleeper.sleep(1000);
            book.sendRequest();
            triesTwo++;
            if (book.getResponseStatusCode().equals("200")) {
                successTwo = true;
            }
        } while (triesTwo < maxTriesTwo && !successTwo);
        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + book.getFaultString(), book);

        tcgIdThree = book.getTravelComponentGroupingId();
        tcIdThree = book.getTravelComponentId();
    }
}
