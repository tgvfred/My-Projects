package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageCancelData;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceAllForTravelPlanSegment;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_multipleProcessDataIdList_submitOne extends AccommodationBaseTest {

    private ReplaceAllForTravelPlanSegment book;
    private ReplaceAllForTravelPlanSegment book1;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);
        isComo.set("false");
        bookReservation();

        book = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(environment), "RoomOnlyNoTickets");
        book.sendRequest();

        book1 = new ReplaceAllForTravelPlanSegment(Environment.getBaseEnvironmentName(environment), "RoomOnlyNoTickets");
        book1.sendRequest();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_multipleProcessDataIdList_submitOne() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        StageCancelData cancel = new StageCancelData(Environment.getBaseEnvironmentName(environment), "MainUpPrLst");
        cancel.setProcessName("MASS_CANCEL");
        cancel.setCancelContactName("Cancel Name");
        cancel.setCancelDate("2017-17-07");
        cancel.setCancelReasonCode("AIR");
        cancel.setIsOverridden("false");
        cancel.setIsWaived("false");
        cancel.setOVerridenCancelFEe("0");
        cancel.setTCg(book.getTravelComponentGroupingId());

        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/cancelContactName", "Cancel Name");
        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/cancelDate", "2017-17-07");
        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/cancelReasonCode", "AIR");
        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/isOverridden", "false");
        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/isWaived", "false");
        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/overriddenCancelFee", "0");
        cancel.setRequestNodeValueByXPath("//massCancelAccommodationRequestDetails[2]/travelComponentGroupingId", book1.getTravelComponentGroupingId());

        cancel.sendRequest();
        TestReporter.logAPI(!cancel.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + book.getTravelComponentGroupingId() + "]", cancel);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunIdMulti1(cancel.getResponseProcessId()));
        update.setProcessType("MASS_CANCEL");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + book.getTravelComponentGroupingId() + "]", update);

        // Validations
        TestReporter.logStep("Multiple Proc Run ID's found in the DB: first Proc Run ID [" + helper.retrieveProcRunIdMulti1(cancel.getResponseProcessId()) + "] & second Proc Run ID [" + helper.retrieveProcRunIdMulti2(cancel.getResponseProcessId()) + "]");
        TestReporter.logStep("Verify Proc Run ID that was passed into the RQ [" + helper.retrieveProcRunIdMulti1(cancel.getResponseProcessId()) + "] has an updated status:");
        // Sleeper.sleep(10000);
        helper.validationOverall(helper.retrieveProcRunIdMulti1(cancel.getResponseProcessId()), "BOOKED", Randomness.generateCurrentDatetime().substring(0, 10));

        TestReporter.logStep("Verify Proc Run ID that was not passed into the RQ [" + helper.retrieveProcRunIdMulti2(cancel.getResponseProcessId()) + "] doesn't have an updated status:");
        // Sleeper.sleep(10000);
        helper.validationOverall_MASSCancel(helper.retrieveProcRunIdMulti2(cancel.getResponseProcessId()), "BOOKED", Randomness.generateCurrentDatetime().substring(0, 10));
    }
}
