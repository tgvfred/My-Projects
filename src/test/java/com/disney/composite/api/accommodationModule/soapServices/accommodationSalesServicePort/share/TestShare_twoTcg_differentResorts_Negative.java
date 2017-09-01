package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestShare_twoTcg_differentResorts_Negative extends AccommodationBaseTest {

    private Share share;
    String firstTcg;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        Environment.getBaseEnvironmentName(environment);
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setArrivalDate(getDaysOut());
        setNights(2);
        setDepartureDate(getNights());
        setValues();
        bookReservation();

        firstTcg = getBook().getTravelComponentGroupingId();
        setDaysOut(1);
        setArrivalDate(getDaysOut());
        setNights(2);
        setDepartureDate(getNights());

        String previousResort = getResortCode();
        String previousRoomTypeCode = getRoomTypeCode();
        // Loop until
        // 1. The resort is the same
        // 2. The room type has changed for the original resort
        do {
            setValues();
        } while (!getResortCode().equals(previousResort) && getRoomTypeCode().equals(previousRoomTypeCode));
        bookReservation();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_twoTcg_differentResorts_Negative() {

        if (Environment.isSpecialEnvironment(environment)) {
            if (true) {
                throw new SkipException("Folio Fix in Progress, for now operation not supported.");
            }
        }
        share = new Share(environment, "Main_twoTcg");
        share.setTravelComponentGroupingId(firstTcg);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = "Cannot change Block/Resort/Package for an shared Accommodation. : ROOM TYPE, PACKAGE AND BLOCK SHOULD BE SAME FOR SHARE!";

        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(share, AccommodationErrorCode.CANNOT_CHANGE_PACKAGE_ACCOM);
    }

}