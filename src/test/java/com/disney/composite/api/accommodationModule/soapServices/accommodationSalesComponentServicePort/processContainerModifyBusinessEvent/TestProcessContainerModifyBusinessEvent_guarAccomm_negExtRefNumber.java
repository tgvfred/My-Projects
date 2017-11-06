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

public class TestProcessContainerModifyBusinessEvent_guarAccomm_negExtRefNumber extends AccommodationBaseTest {

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

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent" })
    public void testProcessContainerModifyBusinessEvent_guarAccomm_negExtRefNumber() {

        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        ac.sendRequest();
        TestReporter.logAPI(!ac.getResponseStatusCode().equals("200"), "An error occurred in the auto cancel request.", ac);

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);
        process.setTravelPlanSegmentID(tps);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber("-" + tp);
        process.setExternalReferenceSource("DREAMS_TP");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
        process.setAttemptAutoReinstate("true");
        process.sendRequest();

        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);

        // validations
        String status = "Cancelled";
        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        helper.statusTP_TC(tps, environment);
        helper.tpv3Status(environment, tp);
        helper.noReservationHistory(tps, environment);
        helper.chargeGroupStatus(tp, tps, getBook().getTravelComponentGroupingId(), environment, status);
        helper.rimRecordNotConsumed(getBook().getTravelComponentGroupingId(), environment);
        helper.chargeItemsNotActive(getBook().getTravelComponentGroupingId(), environment);
        helper.folioItems(tp, environment);

    }
}