package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResmErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestProcessContainerModifyBusinessEvent_Negative extends AccommodationBaseTest {

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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "processContainerModifyBusinessEvent", "negative" })
    public void TestProcessContainerModifyBusinessEvent_guarAccomm() {
        String fault = "INVALID REQUEST ! : ExternalReferenceDetail cannot be null";

        TestReporter.logScenario("Test - processContainerModifyBusinessEvent - guarAccomm");

        AutoCancel ac = new AutoCancel(environment);
        ac.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        ac.sendRequest();

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);
        process.setTravelPlanSegmentID(getBook().getTravelPlanSegmentId());
        process.setByPassFreeze("true");
        process.setExternalReferenceType("RESERVATION");
        process.setExternalReferenceCode("");

        process.setExternalReferenceNumber(getBook().getTravelComponentGroupingId());
        process.setExternalReferenceSource("DREAMS_TP");
        process.setAttemptAutoReinstate("false");
        process.sendRequest();

        TestReporter.logAPI(!process.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + process.getFaultString() + " ]", process);
        validateApplicationError(process, LiloResmErrorCode.INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "processContainerModifyBusinessEvent", "negative" })
    public void TestProcessContainerModifyBusinessEvent_guarAccomm_invalidTPl() {
        String fault = "INVALID REQUEST! : Option key value not provided";

        TestReporter.logScenario("Test - processContainerModifyBusinessEvent  - guarAccomm_invalidTP");
        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);

        process.setTravelPlanSegmentID(getBook().getTravelPlanSegmentId());
        process.setByPassFreeze("true");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber("123");
        process.setExternalReferenceSource("DREAMS_TP");
        process.sendRequest();
        TestReporter.logAPI(!process.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + process.getFaultString() + " ]", process);
        validateApplicationError(process, AccommodationErrorCode.INVALID_REQUEST);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "processContainerModifyBusinessEvent", "negative" })
    public void TestProcessContainerModifyBusinessEvent_guarAccomm_tpWithoutTps() {
        String fault = "INVALID REQUEST! : Option key value not provided";

        TestReporter.logScenario("Test - processContainerModifyBusinessEvent  - guarAccomm_tpWithoutTps");

        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(environment);
        // process.setTravelPlanSegmentID(value);
        // process.setByPassFreeze(value);
        // process.setExternalReferenceType(value);
        // process.setExternalReferenceCode(value);
        // process.setExternalReferenceNumber(value);
        // process.setExternalReferenceSource("DREAMS_TP");
        // process.sendRequest();

        TestReporter.logAPI(!process.getFaultString().contains(fault), "Validate correct fault string [ " + fault + " ] exists. Found [ " + process.getFaultString() + " ]", process);
        validateApplicationError(process, AccommodationErrorCode.INVALID_REQUEST);
    }
}
