package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ProcessContainerModifyBusinessEventHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestProcessContainerModifyBusinessEvent_guarAccomm_tpWithMultipleAccommTps extends AccommodationBaseTest {
    String tcg;
    String tpsNum1;
    String tpsNum2;
    String tcgNum2;

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
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();
        String tpId = getBook().getTravelPlanId();
        tcg = getBook().getTravelComponentGroupingId();
        tpsNum1 = getBook().getTravelPlanSegmentId();
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);

        getBook().sendRequest();
        tpsNum2 = getBook().getTravelPlanSegmentId();
        tcgNum2 = getBook().getTravelComponentGroupingId();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second TPS: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent" })
    public void testProcessContainerModifyBusinessEvent_guarAccomm_tpWithMultipleAccommTps() {

        String tp = getBook().getTravelPlanId();

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(tcg);
        ac.sendRequest();
        TestReporter.logAPI(!ac.getResponseStatusCode().equals("200"), "An error occurred in auto cancel.", ac);

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);
        process.setTravelPlanSegmentID(tpsNum1);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber(tp);
        process.setExternalReferenceSource("DREAMS_TP");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());

        process.setAttemptAutoReinstate("true");
        process.sendRequest();

        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);
        // validations

        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        String status = "UnEarned";

        helper.statusTP_TCNoCanc(tpsNum1, environment);
        helper.statusTP_TCNoCanc(tpsNum2, environment);
        helper.tpv3Status(environment, tp);
        helper.reservationHistory(tp, environment);
        helper.chargeGroupStatus(tp, tpsNum1, tcg, environment, status);
        helper.chargeGroupStatus(tp, tpsNum2, tcgNum2, environment, status);
        helper.rimRecordConsumed(tcg, environment);

        helper.rimRecordConsumed(tcgNum2, environment);
        helper.chargeItemsActive(tcg, environment);
        helper.chargeItemsActive(tcgNum2, environment);
        helper.folioItems(tp, environment);

    }
}
