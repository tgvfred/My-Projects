package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestShare_cancelThenShare_Negative extends AccommodationBaseTest {

    private Share share;
    private String firstTCG;

    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        // TestReporter.setDebugLevel(TestReporter.INFO); //Uncomment this line
        // to invoke lower levels of reporting
        setEnvironment(environment);
        isComo.set("false");
        daysOut.set(0);
        nights.set(1);
        arrivalDate.set(Randomness.generateCurrentXMLDate(getDaysOut()));
        departureDate.set(Randomness.generateCurrentXMLDate(getDaysOut() + getNights()));
        setValues();
        bookReservation();

        firstTCG = getBook().getTravelComponentGroupingId();
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "share", "negative" })
    public void Test_Share_cancelThenShare_Negative() {

        Cancel cancel = new Cancel(environment, "MainCancel");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "Verify that no error occurred while sharing a room " + cancel.getFaultString(), cancel);

        share = new Share(environment, "Main_oneTcg");
        share.setTravelComponentGroupingId(firstTCG);
        share.addSharedComponent();
        share.setSecondTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();

        String faultString = " Accommodation should be in Booked status to be Shared : Accommodation not in Booked Status";

        validateApplicationError(share, AccommodationErrorCode.ACCOMMODATION_NOT_IN_BOOKED_STATUS_CANNOT_BE_SHARED);
        TestReporter.assertEquals(share.getFaultString(), faultString, "Verify that the fault string [" + share.getFaultString() + "] is that which is expected [" + faultString + "].");

    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            cancel(firstTCG);
            cancel(getBook().getTravelComponentGroupingId());
        } catch (Exception e) {
        }
    }

}