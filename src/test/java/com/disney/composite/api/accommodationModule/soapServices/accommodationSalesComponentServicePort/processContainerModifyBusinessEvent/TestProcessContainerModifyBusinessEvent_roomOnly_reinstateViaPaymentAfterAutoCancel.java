package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.ProcessContainerModifyBusinessEventHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.folioModule.paymentService.operations.PostCardPayment;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestProcessContainerModifyBusinessEvent_roomOnly_reinstateViaPaymentAfterAutoCancel extends AccommodationBaseTest {

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
    public void testProcessContainerModifyBusinessEvent_roomOnly_reinstateViaPaymentAfterAutoCancel() {
        TestReporter.logInfo("testProcessContainerModifyBusinessEvent_roomOnly_reinstateViaPaymentAfterAutoCancel");
        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();
        String tcg = getBook().getTravelComponentGroupingId();

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        ac.sendRequest();
        TestReporter.logAPI(!ac.getResponseStatusCode().equals("200"), "An error occurred in the auto cancel request.", ac);

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(Environment.getBaseEnvironmentName(environment));

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

        PostCardPayment pcp = new PostCardPayment(Environment.getBaseEnvironmentName(environment), "Visa-CreditCard");
        pcp.setTravelPlanSegmentId(tps);
        pcp.setTravelPlanId(tp);
        pcp.setAmount("60");
        pcp.setFolioId("138020007");

        pcp.setBookingReference("DREAMS_TP", tp);
        pcp.setExternalReference("DREAMS_TC", getBook().getTravelComponentId());
        pcp.setRequestNodeValueByXPath("/Envelope/Body/postCardPayment/pmtInfo/cardAuthorizationInfoTO/retrievalReferenceNumber", "1234423434");
        pcp.setLocationId(getLocationId());
        pcp.setPartyId(getBook().getPartyId("1"));
        pcp.sendRequest();

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
