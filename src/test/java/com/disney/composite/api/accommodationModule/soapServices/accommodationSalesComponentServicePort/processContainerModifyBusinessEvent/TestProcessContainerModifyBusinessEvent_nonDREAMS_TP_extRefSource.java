package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent", "negative" })
    public void testProcessContainerModifyBusinessEvent_nonDREAMS_TP_extRefSource() {
        String fault = "No Travel Plan found for Travel Plan Id :";
        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();
        TestReporter.logScenario("Test - processContainerModifyBusinessEvent - nonDREAMS_TP_extRefSource");

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        ac.sendRequest();
        TestReporter.logAPI(!ac.getResponseStatusCode().equals("200"), "An error occurred in the auto cancel.", ac);

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);

        process.setTravelPlanSegmentID(tps);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceSource("DPMSProperty");
        process.setExternalReferenceType("RESERVATION");
        process.setExternalReferenceNumber("2357625723562356");

        process.setAttemptAutoReinstate("true");
        process.sendRequest();
        TestReporter.logAPI(!process.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + process.getFaultString() + " ]", process);
        validateApplicationError(process, AccommodationErrorCode.TRAVEL_PLAN_NOT_FOUND);

        // // validations
        // String status = "Cancelled";
        // ProcessContainerModifyBusinessEventHelper helper = new ProcessContainerModifyBusinessEventHelper();
        // helper.statusTP_TC(tps, environment);
        // helper.tpv3Status(environment, tp);
        // helper.reservationHistory(tp, environment);
        // helper.chargeGroupStatus(tp, tps, getBook().getTravelComponentGroupingId(), environment, status);
        // helper.rimRecordNotConsumed(getBook().getTravelComponentGroupingId(), environment);
        // helper.chargeItemsNotActive(getBook().getTravelComponentGroupingId(), environment);
        // helper.folioItems(tp, environment);
        //
        // }
    }
}