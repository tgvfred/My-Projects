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

public class TestProcessContainerModifyBusinessEvent_guarAccomm_tpWithMultipleAccommTcgs extends AccommodationBaseTest {
    String tcg;
    String tpsNum2;
    String tcgNum2;
    String tpsId;
    String tpId;

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

        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcg = getBook().getTravelComponentGroupingId();
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);
        getBook().sendRequest();

        tcgNum2 = getBook().getTravelComponentGroupingId();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second TPS: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent" })
    public void testProcessContainerModifyBusinessEvent_guarAccomm_tpWithMultipleAccommTcgs() {

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(tcg);
        ac.sendRequest();
        TestReporter.logAPI(!ac.getResponseStatusCode().equals("200"), "An error occurred in the auto cancel.", ac);

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(Environment.getBaseEnvironmentName(environment));
        process.setTravelPlanSegmentID(tpsId);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber(tpId);
        process.setExternalReferenceSource("DREAMS_TP");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());

        process.setAttemptAutoReinstate("true");
        process.sendRequest();

        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);
        // validations

        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        String status = "UnEarned";
        helper.statusTP_TCNoCanc(tpsId, environment);

        helper.reservationHistory(tpId, environment);
        helper.chargeGroupStatus(tpId, tpsId, tcg, environment, status);
        helper.chargeGroupStatus(tpId, tpsId, tcgNum2, environment, status);
        helper.rimRecordNotConsumed(tcg, environment);

        helper.rimRecordConsumed(tcgNum2, environment);
        helper.chargeItemsNotActive(tcg, environment);
        helper.chargeItemsActive(tcgNum2, environment);
        helper.folioItems(tpId, environment);

    }

}
