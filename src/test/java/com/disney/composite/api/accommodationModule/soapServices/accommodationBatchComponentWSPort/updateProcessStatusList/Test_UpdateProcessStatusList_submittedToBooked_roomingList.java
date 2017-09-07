package com.disney.composite.api.accommodationModule.soapServices.accommodationBatchComponentWSPort.updateProcessStatusList;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationBatchComponentWSPort.operation.UpdateProcessStatusList;
import com.disney.api.soapServices.accommodationModule.accommodationBatchServicePort.operation.StageRoomingListReservationData;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.accommodationModule.helpers.UpdateProcessStatusListHelper;
import com.disney.api.soapServices.accommodationModule.roomingListServicePort.operation.SaveRoomingListTemplate;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_UpdateProcessStatusList_submittedToBooked_roomingList extends AccommodationBaseTest {

    private String extRefVal;
    private String tcg;
    private String name;

    @Test(groups = { "api", "regression", "accommodation", "accommodationBatchComponentWS", "UpdateProcessStatusList" })
    public void testUpdateProcessStatusList_submittedToBooked_roomingList() {

        // Sets up a Rooming List Template for use later on
        SaveRoomingListTemplate save = new SaveRoomingListTemplate(Environment.getBaseEnvironmentName(environment));

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

        // Remove unneeded nodes
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateId", BaseSoapCommands.REMOVE_NODE.toString());
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/defaultNoOfRows", BaseSoapCommands.REMOVE_NODE.toString());
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/styleClass", BaseSoapCommands.REMOVE_NODE.toString());
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/defaultValue", BaseSoapCommands.REMOVE_NODE.toString());
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/fieldSequenceCharacter", BaseSoapCommands.REMOVE_NODE.toString());
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList/styleClass", BaseSoapCommands.REMOVE_NODE.toString());

        // Add additional Nodes Required
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("templateFieldList"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]", BaseSoapCommands.ADD_NODE.commandAppend("orderNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]", BaseSoapCommands.ADD_NODE.commandAppend("fieldSequenceNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]", BaseSoapCommands.ADD_NODE.commandAppend("fieldTO"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldId"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("isRepeatable"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("defaultFieldIndicator"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldName"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldDataType"));

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/orderNumber", "2");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldSequenceNumber", "2");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO/fieldId", "2");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO/isRepeatable", "false");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO/defaultFieldIndicator", "true");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO/fieldName", "DepartureDate");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[2]/fieldTO/fieldDataType", "Date");

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("templateFieldList"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]", BaseSoapCommands.ADD_NODE.commandAppend("defaultValue"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]", BaseSoapCommands.ADD_NODE.commandAppend("orderNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]", BaseSoapCommands.ADD_NODE.commandAppend("fieldSequenceNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]", BaseSoapCommands.ADD_NODE.commandAppend("fieldTO"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldId"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("isRepeatable"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("defaultFieldIndicator"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldName"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldDataType"));

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/defaultValue", "FirstName");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/orderNumber", "3");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldSequenceNumber", "3");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO/fieldId", "3");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO/isRepeatable", "false");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO/defaultFieldIndicator", "true");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO/fieldName", "FirstName");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[3]/fieldTO/fieldDataType", "Date");

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("templateFieldList"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]", BaseSoapCommands.ADD_NODE.commandAppend("defaultValue"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]", BaseSoapCommands.ADD_NODE.commandAppend("orderNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]", BaseSoapCommands.ADD_NODE.commandAppend("fieldSequenceNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]", BaseSoapCommands.ADD_NODE.commandAppend("fieldTO"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldId"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("isRepeatable"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("defaultFieldIndicator"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldName"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldDataType"));

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/defaultValue", "LastName");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/orderNumber", "4");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldSequenceNumber", "4");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO/fieldId", "4");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO/isRepeatable", "false");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO/defaultFieldIndicator", "true");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO/fieldName", "LastName");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[4]/fieldTO/fieldDataType", "Date");

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO", BaseSoapCommands.ADD_NODE.commandAppend("templateFieldList"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]", BaseSoapCommands.ADD_NODE.commandAppend("defaultValue"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]", BaseSoapCommands.ADD_NODE.commandAppend("orderNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]", BaseSoapCommands.ADD_NODE.commandAppend("fieldSequenceNumber"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]", BaseSoapCommands.ADD_NODE.commandAppend("fieldTO"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldId"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("isRepeatable"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("defaultFieldIndicator"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldName"));
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO", BaseSoapCommands.ADD_NODE.commandAppend("fieldDataType"));

        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/defaultValue", "1");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/orderNumber", "6");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldSequenceNumber", "5");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO/fieldId", "6");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO/isRepeatable", "false");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO/defaultFieldIndicator", "true");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO/fieldName", "RoomQuantity");
        save.setRequestNodeValueByXPath("/Envelope/Body/saveRoomingListTemplate/request/templateTO/templateFieldList[5]/fieldTO/fieldDataType", "Numeric");

        save.sendRequest();
        TestReporter.logAPI(!save.getResponseStatusCode().equals("200"), "Something got messed up, but here is the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", save);

        // Stages the data for a Room List Reservation
        StageRoomingListReservationData stage = new StageRoomingListReservationData(Environment.getBaseEnvironmentName(environment));

        stage.setProcessName("ROOMINGLIST");
        stage.setTemplateId(tempId());
        stage.setReservationNumber(getBook().getTravelPlanSegmentId());
        stage.setProcessingDate(Randomness.generateCurrentDatetime().substring(0, 10));
        stage.setRowNumber("1");
        stage.setCheckShared("true");
        stage.setRenderFlag("true");
        stage.setCheckReservation("true");
        stage.setChecked("true");

        // Remove unneeded nodes
        stage.setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/processId", BaseSoapCommands.REMOVE_NODE.toString());
        stage.setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/inventoryOverrideReasonCode", BaseSoapCommands.REMOVE_NODE.toString());
        stage.setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/inventoryOverrideContactName", BaseSoapCommands.REMOVE_NODE.toString());
        stage.setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/reservationDataList", BaseSoapCommands.REMOVE_NODE.toString());
        stage.setRequestNodeValueByXPath("/Envelope/Body/stageRoomingListReservationData/request/roomingListReservationTOList/errorTOList", BaseSoapCommands.REMOVE_NODE.toString());

        stage.sendRequest();
        TestReporter.logAPI(!stage.getResponseStatusCode().equals("200"), "Something got messed up, but here is the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", stage);

        // Update Process Status List call
        UpdateProcessStatusListHelper helper = new UpdateProcessStatusListHelper(environment);

        UpdateProcessStatusList update = new UpdateProcessStatusList(environment, "Main");

        update.setProcessDataIdList(helper.retrieveProcRunId(stage.getProcessId()));
        update.setProcessType("ROOMINGLIST");
        update.setProcessingStatus("BOOKED");
        update.setTPSId(getBook().getTravelPlanSegmentId());
        update.sendRequest();
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]", update);

        // Validations
        helper.validationOverall(helper.retrieveProcRunId(stage.getProcessId()), "SUBMITTED", Randomness.generateCurrentDatetime().substring(0, 10));
        helper.validationRoomList(helper.retrieveProcRunId(stage.getProcessId()), getBook().getTravelPlanSegmentId());
    }

    public String tempId() {

        String sql = "select RM_LIST_TMPL_ID "
                + "from res_mgmt.rm_list_tmpl a "
                + "where a.RM_LIST_TMPL_NM = 'SomeUniqueName'";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        extRefVal = rs.getValue("RM_LIST_TMPL_ID");

        this.tcg = extRefVal;

        return extRefVal;
    }
}
