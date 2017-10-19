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

public class TestProcessContainerModifyBusinessEvent_guarAccomm_noAutoReinstate extends AccommodationBaseTest {

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
    public void testProcessContainerModifyBusinessEvent_guarAccomm_noAutoReinstate() {

        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(tps);

        ac.sendRequest();
        TestReporter.logAPI(!ac.getResponseStatusCode().equals("200"), "An error occurred in auto cancel request.", ac);

        System.out.println(ac.getRequest());
        System.out.println(ac.getResponse());
        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(Environment.getBaseEnvironmentName(environment));

        // process.setTravelPlanSegmentID("472121534976");
        process.setTravelPlanSegmentID(BaseSoapCommands.REMOVE_NODE.toString());
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber("472121589516");
        process.setExternalReferenceSource("DREAMS_TP");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());

        process.setAttemptAutoReinstate("false");
        process.sendRequest();

        System.out.println(process.getRequest());
        System.out.println(process.getResponse());

        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);
        // validations
        String status = "Cancelled";
        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        helper.statusTP_TC(tps, environment);
        helper.tpv3Status(environment, tp);
        helper.reservationHistory(tp, environment);
        helper.chargeGroupStatus(tp, tps, "472121589516", environment, status);
        helper.rimRecordNotConsumed("472121589516", environment);
        helper.chargeItemsNotActive("472121589516", environment);
        helper.folioItems(tp, environment);

    }
}
