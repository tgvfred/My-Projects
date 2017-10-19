package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;

public class TestRetrievePostedCancellationFee_TPS_TCG_libgo_Positive extends AccommodationBaseTest {

    private CheckInHelper helper;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        setIsLibgoBooking(true);
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_TPS_TCG_libgo_Positive() {

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "TpsANDTcg");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.setid(getBook().getTravelComponentGroupingId(), "2");
        retrieve.sendRequest();

    }
}
