package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.api.soapServices.accommodationModule.roomingListServicePort.operation.SaveRoomingListTemplate;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class Test_UpdateProcessStatusList_submittedToBooked_roomingList extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToBooked_roomingList() {

        SaveRoomingListTemplate save = new SaveRoomingListTemplate(environment);

        save.setTemplateName("SomeUniqueName");
        save.setCreatedUpdatedBy("AutoJUnit.user");
        save.setLastUsedDate(Randomness.generateCurrentDatetime().substring(0, 10));
        save.setOrderNumber("1");
        save.setFieldSequenceNumber("1");
        save.setFieldId("1");
        save.setIsRepeatable("false");
        save.setDefaultFieldIndicator("true");
        save.setFieldName("ArrivalDate");
        save.setFieldDataType("Date");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/orderNumber", "2");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldSequenceNumber", "2");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldId", "2");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/isRepeatable", "false");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/defaultFieldIndicator", "true");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldTO/fieldDataType", "Date");
        save.sendRequest();
        TestReporter.logAPI(!save.getResponseStatusCode().equals("200"), "Something got messed up, but here is the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", save);

        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        // update.setProcessDataIdList(helper.retrieveProcRunId(remove.getResponseProcessId()));
        update.setProcessDataIdList("");
        update.setProcessType("ROOMINGLIST");
        update.setProcessingStatus("BOOKED");
        update.setTPSId(getBook().getTravelPlanSegmentId());
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        // helper.validationOverall(helper.retrieveProcRunId(remove.getResponseProcessId()), "BOOKED", Randomness.generateCurrentDatetime().substring(0, 10));
        // helper.validationRoomList(helper.retrieveProcRunId(remove.getResponseProcessId()));
    }
}
