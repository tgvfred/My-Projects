package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.validateShareRules;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ValidateShareRules;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Sleeper;
import com.disney.utils.TestReporter;

public class TestValidateShareRules_twoSharedRoomDetails_mismatchedPackageCodes extends AccommodationBaseTest {

    private ReplaceAllForTravelPlanSegment book;
    private String tpId;
    private String tpsId;
    private String tcgId;
    private String tcId;
    private String startDate;
    private String endDate;
    private String packageCode;

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
        packageCode = getPackageCode();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentServicePort", "validateShareRules" })
    public void testValidateShareRules_twoSharedRoomDetails_mismatchedPackageCodes() {

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

        String faultString = "Cannot change Block/Resort/Package for an shared Accommodation. : ROOM TYPE, PACKAGE AND BLOCK SHOULD BE SAME FOR SHARE!";

        ValidateShareRules validate = new ValidateShareRules(environment, "TwoRoomDetails");
        validate.setTravelComponentGroupingId(tcgId);
        validate.setTravelComponentId(tcId);
        validate.setTravelComponentGroupingId(book.getTravelComponentGroupingId(), "2");
        validate.setTravelComponentId(book.getTravelComponentId(), "2");
        validate.setStartDate(startDate, "1");
        validate.setEndDate(endDate, "1");
        validate.setStartDate(getArrivalDate(), "2");
        validate.setEndDate(getDepartureDate(), "2");
        validate.setPackageCode(packageCode, "1");
        validate.setPackageCode("Invalid", "2");
        validate.setBlockCode(BaseSoapCommands.REMOVE_NODE.toString(), "1");
        validate.setBlockCode(BaseSoapCommands.REMOVE_NODE.toString(), "2");
        validate.sendRequest();

        TestReporter.assertTrue(validate.getFaultString().contains(faultString), "Verify that the fault string [" + validate.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(validate, AccommodationErrorCode.CANT_CHANGE_BLOCK_RESORT_PACKAGE_FOR_SHARED_ACCOMMODATION);

    }
}
