package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrievePostedCancellationFee;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.CheckInHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.date.DateTimeConversion;

public class TestRetrievePostedCancellationFee_TPS_multiTCG_cancellationFees_Positive extends AccommodationBaseTest {

    private CheckInHelper helper;
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

        Cancel cancel = new Cancel(environment, "Main_WithFee");
        cancel.setCancelDate(DateTimeConversion.ConvertToDateYYYYMMDD("0"));
        cancel.setTravelComponentGroupingId(tcgId);

        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request/overridden", BaseSoapCommands.REMOVE_NODE.toString());
        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request/waived", BaseSoapCommands.REMOVE_NODE.toString());
        cancel.setRequestNodeValueByXPath("/Envelope/Body/cancel/request/overriddenCancelFee", BaseSoapCommands.REMOVE_NODE.toString());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.equals("200"), "Verify that no error occurred canceling a reservation: " + cancel.getFaultString(), cancel);

        cancel.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        cancel.sendRequest();
        TestReporter.logAPI(!cancel.equals("200"), "Verify that no error occurred canceling a reservation: " + cancel.getFaultString(), cancel);

        RetrievePostedCancellationFee retrieve = new RetrievePostedCancellationFee(environment, "TpsTwoTcg");
        retrieve.setid(getBook().getTravelPlanSegmentId());
        retrieve.setid(tcgId, "2");
        retrieve.setid(getBook().getTravelComponentGroupingId(), "3");
        retrieve.sendRequest();

        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred retrieving posted cancellation fee", retrieve);

    }
}
