package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateGuaranteeStatus;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateGuaranteeStatus;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class Test_UpdateGuaranteeStatus_shared_Negative extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);

        setIsShared(true);
        bookReservation();

        Share share = new Share(environment, "oneTcgOnly");
        share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        share.sendRequest();
        TestReporter.assertTrue(share.getResponseStatusCode().equals("200"), "Verify that no error occurred sharing a reservation: " + share.getFaultString());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateGuaranteeStatus", "negative" })
    public void testUpdateGuaranteeStatus_shared() {

        String faultString = " Guarantee status can not be changed  : guarantee status can not be changed on shared room ";

        UpdateGuaranteeStatus update = new UpdateGuaranteeStatus(environment);
        update.setRequestTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        update.setRequestguaranteedByEnum("CREDIT CARD");
        update.sendRequest();

        TestReporter.assertEquals(faultString, update.getFaultString(), "Verify that the fault string [" + update.getFaultString() + "] is that which is expected.[" + faultString + "]");
        validateApplicationError(update, AccommodationErrorCode.GUARANTEE_STATUS_CANNOT_CHANGE);

    }
}
