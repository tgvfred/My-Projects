package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
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
    public void testProcessContainerModifyBusinessEvent_nonDREAMS_TP_extRefSource() {
        // String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();
        TestReporter.logScenario("Test - processContainerModifyBusinessEvent - nonDREAMS_TP_extRefSource");

        AutoCancel ac = new AutoCancel(environment);
        ac.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        ac.sendRequest();

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);
        process.setTravelPlanSegmentID("472121534976");
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceSource("DPMSProperty");
        process.setExternalReferenceType("RESERVATION");
        process.setExternalReferenceNumber("2357625723562356");

        process.setAttemptAutoReinstate("true");
        process.sendRequest();
        System.out.println(process.getRequest());
        System.out.println(process.getResponse());
        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);

        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);
        // validations
        String status = "UnEarned";
        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        helper.statusTP_TC("472121534976", environment);
        // helper.tpv3Status(environment);
        helper.reservationHistory("472121534976", environment);
        helper.chargeGroupStatus(tp, "472121534976", getBook().getTravelComponentGroupingId(), environment, status);
        helper.rimRecordConsumed(getBook().getTravelComponentGroupingId(), environment);
        helper.chargeItemsActive(getBook().getTravelComponentGroupingId(), environment);
        helper.folioItems(tp, environment);

    }
}
