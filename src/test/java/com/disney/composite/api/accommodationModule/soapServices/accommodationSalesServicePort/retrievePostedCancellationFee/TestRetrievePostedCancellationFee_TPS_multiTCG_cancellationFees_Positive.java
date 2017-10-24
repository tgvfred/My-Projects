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

public class TestRetrievePostedCancellationFee_TPS_multiTCG_cancellationFees_Positive extends AccommodationBaseTest {
    private String tpId;
    private String tpsId;
    private String tcgId;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        isComo.set("true");
        bookReservation();

        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrievePostedCancellationFee" })
    public void testRetrievePostedCancellationFee_TPS_multiTCG_cancellationFees_Positive() {
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().sendRequest();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a reservation: " + getBook().getFaultString(), getBook());

        Cancel cancel = new Cancel(environment, "Main_WithFeeWaived");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        // int tries = 0;
        // int maxTries = 20;
        // boolean success = false;
        // do {
        // Sleeper.sleep(1000);
        // cancel.sendRequest();
        // tries++;
        //
        // } while (tries < maxTries && !success);
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);

        cancel.setTravelComponentGroupingId(tcgId);
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred cancelling the reservation: " + cancel.getFaultString(), cancel);

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "TpsTwoTcg");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.setid(tcgId, "2");
        retrieve.setid(getBook().getTravelComponentGroupingId(), "3");
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fee", retrieve);

        RetrievePostedCancellationFeeHelper helper = new RetrievePostedCancellationFeeHelper(environment);
        helper.getTcIdWithTcg(getBook().getTravelComponentGroupingId());
        helper.getChargeTypeAndAmount(retrieve, true);
    }
}