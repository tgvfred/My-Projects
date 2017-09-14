package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.StageMassModifyTransactional;
import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_submittedToBooked_massModify extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToBooked_massModify() {

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        StageMassModifyTransactional modify = new StageMassModifyTransactional(Environment.getBaseEnvironmentName(environment), "MainProcLst");

        modify.setProcessName("MASS_MODIFY");
        modify.setMassModifyRoomDetailTcId(getBook().getTravelComponentId());
        modify.setMassModifyRoomDetailTpsId(getBook().getTravelPlanSegmentId());
        modify.setMassModifyRoomDetailTcgID(getBook().getTravelComponentGroupingId());
        modify.setMassModifyRoomDetailPeriodStartDate("2017-07-18");
        modify.setMassModifyRoomDetailPeriodEndDates("2017-07-19");
        modify.setMassModifyRoomDetailPackageCode("I795T");
        modify.setMassModifyRoomDetailResortCode("1S");
        modify.setMassModifyRoomDetailRoomType("SP");
        modify.setMassModifyRoomDetailPrimaryGuestDetailFirstName("Test");
        modify.setMassModifyRoomDetailPrimaryGuestDetailLastName("Test");
        modify.setMassModifyRoomDetailPrimaryGuestDetailPartyId("0");
        modify.setMassModifyRoomDetailPrimaryGuestDetailDoNotMail("true");
        modify.setMassModifyRoomDetailPrimaryGuestDetailDoNotPhone("true");
        modify.setMassModifyRoomDetailPrimaryGuestDetailPreferredLanguage("eng");
        modify.setMassModifyRoomDetailPrimaryGuestDetailGuestId(getBook().getGuestId());
        modify.setMassModifyRoomDetailPrimaryGuestDetailACtive("true");
        modify.setMassModifyRoomDetailConfirmationIndicator("true");
        modify.sendRequest();
        TestReporter.logAPI(!modify.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", modify);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(modify.getResponseProcessId()));
        update.setProcessType("MASS_MODIFY");
        update.setProcessingStatus("BOOKED");
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        helper.validationOverall(helper.retrieveProcRunId(modify.getResponseProcessId()), "BOOKED", Randomness.generateCurrentDatetime().substring(0, 10));
        helper.validationMassModify(helper.retrieveProcRunId(modify.getResponseProcessId()), getBook().getTravelPlanSegmentId(), getBook().getTravelComponentGroupingId(), getBook().getTravelComponentId());
    }
}
