package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ProcessContainerModifyBusinessEventHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.folioModule.paymentService.helpers.CardPaymentHelper;
import com.disney.utils.TestReporter;
import com.disney.utils.tdm.pCard.CardTypes;

public class TestProcessContainerModifyBusinessEvent_roomOnly_reinstateViaPayment extends AccommodationBaseTest {

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
        getAddTravelAgency();
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent" })
    public void testProcessContainerModifyBusinessEvent_roomOnly_reinstateViaPayment() {

        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();
        String tcg = getBook().getTravelComponentGroupingId();

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);
        process.setTravelPlanSegmentID(tps);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber(tp);
        process.setExternalReferenceSource("DREAMS_TP");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
        process.setAttemptAutoReinstate("true");
        process.sendRequest();

        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);

        // validations

        CardPaymentHelper payment = new CardPaymentHelper(getBook(), getHouseHold());
        payment.makePaymentForIndividual(CardTypes.VISA);

        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        String status = "UnEarned";
        helper.statusTP_TCNoCanc(tps, environment);
        helper.tpv3Status(environment, tp);
        helper.reservationHistory(tp, environment);
        helper.chargeGroupStatus(tp, tps, tcg, environment, status);
        helper.rimRecordConsumed(tcg, environment);
        helper.chargeItemsActive(tcg, environment);
        helper.folioItems(tp, environment);
        helper.rimGuaranteeStatus(environment, getBook().getTravelComponentId());
        helper.folioNodeChargeGroupST(environment, tps, tcg);
    }
}
