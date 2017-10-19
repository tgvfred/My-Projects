package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Add;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.AddAccommodationHelper;
import com.disney.api.soapServices.accommodationModule.helpers.ProcessContainerModifyBusinessEventHelper;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestProcessContainerModifyBusinessEvent_guarAccomm_tpWithMultipleAccommTcgs extends AccommodationBaseTest {

    private AddAccommodationHelper accommHelper;
    private Add add;
    String tcg;

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
        // setAddRoom(true);
        isComo.set("false");
        bookReservation();

        String tpId = getBook().getTravelPlanId();
        String tpsId = getBook().getTravelPlanSegmentId();
        tcg = getBook().getTravelComponentGroupingId();
        setSendRequest(false);
        bookReservation();
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(tpsId);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent" })
    public void testProcessContainerModifyBusinessEvent_guarAccomm_tpWithMultipleAccommTcgs() {

        // Add a second accommodation

        String tps = getBook().getTravelPlanSegmentId();
        // String tp = getBook().getTravelPlanId();
        //
        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(tcg);
        ac.sendRequest();

        System.out.println(ac.getRequest());
        System.out.println(ac.getResponse());

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(Environment.getBaseEnvironmentName(environment));
        // process.setTravelPlanSegmentID("472121534976");
        process.setTravelPlanSegmentID(tps);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber(tcg);
        process.setExternalReferenceSource("DREAMS_TCG");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());

        process.setAttemptAutoReinstate("true");
        process.sendRequest();
        System.out.println(process.getRequest());
        System.out.println(process.getResponse());
        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);
        // validations

        ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        String status = "UnEarned";
        // helper.statusTP_TC(tpsNum1, environment);
        /*
         * helper.statusTP_TCWithZeroCanc(tpsNum1, environment);
         * helper.statusTP_TC(tpsNum2, environment);
         * helper.statusTP_TCNoCanc(tpsNum1, environment);
         * helper.statusTP_TCNoCanc(tpsNum2, environment);
         * helper.tpv3Status(environment, tp);
         * helper.reservationHistory(tp, environment);
         * helper.chargeGroupStatus(tp, tpsNum1, tcg, environment, status);
         * helper.chargeGroupStatus(tp, tpsNum2, tcgNum2, environment, status);
         * helper.rimRecordConsumed(tcg, environment);
         * 
         * helper.rimRecordConsumed(tcgNum2, environment);
         * helper.chargeItemsActive(tcg, environment);
         * helper.chargeItemsActive(tcgNum2, environment);
         * helper.folioItems(tp, environment);
         */
    }

}
