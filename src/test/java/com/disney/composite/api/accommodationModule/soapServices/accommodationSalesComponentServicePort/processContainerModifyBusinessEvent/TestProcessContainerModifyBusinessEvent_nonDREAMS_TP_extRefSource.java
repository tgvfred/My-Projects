package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.AccommodationSalesBatchServiceRest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ProcessContainerModifyBusinessEventHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestProcessContainerModifyBusinessEvent_nonDREAMS_TP_extRefSource extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(30);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent", "negative" })
    public void testProcessContainerModifyBusinessEvent_nonDREAMS_TP_extRefSource() {
        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();
        TestReporter.logScenario("Test - processContainerModifyBusinessEvent - nonDREAMS_TP_extRefSource");

        RestResponse response = AccommodationSalesBatchServiceRest.accommodationSalesBatchService(environment).travelComponentGroupings().autoCancel(getBook().getTravelComponentGroupingId());
        validateResponse(response);
        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);

        process.setTravelPlanSegmentID(tps);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceSource("DPMSProperty");
        process.setExternalReferenceType("RESERVATION");
        process.setExternalReferenceNumber("2357625723562356");

        process.setAttemptAutoReinstate("true");
        process.sendRequest();
        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred in the Process Container Modify Business Event.", process);

        // validations
        String status = "Cancelled";
        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        helper.statusTP_TC(tps, environment);
        helper.tpv3Status(environment, tp);
        helper.reservationHistory(tp, environment);
        helper.chargeGroupStatus(tp, tps, getBook().getTravelComponentGroupingId(), environment, status);
        helper.rimRecordNotConsumed(getBook().getTravelComponentGroupingId(), environment);
        helper.chargeItemsNotActive(getBook().getTravelComponentGroupingId(), environment);
        helper.folioItems(tp, environment);

    }
}
