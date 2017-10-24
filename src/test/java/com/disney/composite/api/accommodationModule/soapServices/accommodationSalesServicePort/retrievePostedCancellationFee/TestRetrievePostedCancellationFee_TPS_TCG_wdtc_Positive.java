package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.RetrievePostedCancellationFeeHelper;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestRetrievePostedCancellationFee_TPS_TCG_wdtc_Positive extends AccommodationBaseTest {
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
        setIsWdtcBooking(true);
        bookReservation();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_TPS_TCG_wdtc_Positive() {
        Cancel cancel = new Cancel(environment, "Main_WithFeeWaived");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "TpsANDTcg");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.setid(getBook().getTravelComponentGroupingId(), "2");
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fee", retrieve);

        RetrievePostedCancellationFeeHelper helper = new RetrievePostedCancellationFeeHelper(environment);
        helper.getTcIdWithTcg(getBook().getTravelComponentGroupingId());
        helper.getChargeTypeAndAmount(retrieve, false);
    }
}