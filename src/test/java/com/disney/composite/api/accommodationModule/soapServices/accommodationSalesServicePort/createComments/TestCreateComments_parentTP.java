package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.createComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCreateComments_parentTP extends AccommodationBaseTest {
    String commentId = Randomness.randomAlphaNumeric(10);
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
    String parentId = "";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "CreateComments" })
    public void testCreateComments_parentTP() {

        String expectedIsActive = "true";
        String expectedGSR = "false";
        String expectedConfidential = "true";
        String expectedCommentLevel = "TP";
        String expectedCreatedBy = "AutoJUnit.us";
        String TpExternalReferenceNumber = getExternalRefNumber();
        String TpExternalReferenceSource = getExternalRefSource();
        String TpExternalReferenceType = "RESERVATION";
        parentId = getBook().getTravelPlanId();
        CreateComments create = new CreateComments(environment, "Main");
        create.setParentIds(parentId);
        create.setIsActive(expectedIsActive);
        create.setSendToGSR(expectedGSR);
        create.setConfidential(expectedConfidential);
        create.setProfileId("1065");
        create.setProfileCode("RoomReadyN");
        create.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
        create.setCommentText(commentText);
        create.setCommentLevel(expectedCommentLevel);
        create.setTcId(getBook().getTravelComponentId());
        create.setTpsId(getBook().getTravelPlanSegmentId());
        create.setTpId(getBook().getTravelPlanId());
        create.setCreatedBy(expectedCreatedBy);
        create.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
        create.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
        create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create.setRoomExternalReferenceNumber(TpExternalReferenceNumber);
        create.setRoomExternalReferenceSource(TpExternalReferenceSource);
        create.setRoomExternalReferenceType(TpExternalReferenceType);
        create.setRoomExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        create.sendRequest();

        // Validate node response values
        TestReporter.logStep("Validate Response node values.");
        TestReporter.setAssertFailed(false);
        TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", create);
        TestReporter.softAssertTrue(create.getSendToGSR().equals(expectedGSR), "Verify that the sendToGSR node [" + create.getSendToGSR() + "] is what is expected [" + expectedGSR + "]");
        TestReporter.softAssertTrue(create.getConfidential().equals(expectedConfidential), "Verify that the confidential node [" + create.getConfidential() + "] is what is expected [" + expectedConfidential + "]");
        TestReporter.softAssertTrue(create.getCommentText().equals(commentText), "Verify that the commentText node [" + create.getCommentText() + "] is what is expected [" + commentText + "]");
        TestReporter.softAssertTrue(create.getCommentLevel().equals(expectedCommentLevel), "Verify that the commentLevel node [" + create.getCommentLevel() + "] is what is expected [" + expectedCommentLevel + "]");
        TestReporter.softAssertTrue(create.getCreatedBy().equals(expectedCreatedBy), "Verify that the createdBy node [" + create.getCreatedBy() + "] is what is expected [" + expectedCreatedBy + "]");
        TestReporter.softAssertTrue(create.getProfileCode().equals("RoomReadyN"), "Verify that the profile code node [" + create.getCreatedBy() + "] is what is expected [RoomReadyN]");
        TestReporter.softAssertTrue(create.getProfileId().equals("1065"), "Verify that the profile ID  node [" + create.getCreatedBy() + "] is what is expected [1065]");
        TestReporter.assertAll();

        // Validate comment data in RES_MGMT_REQ table
        String GSR_IN = (create.getSendToGSR().equals("true")) ? "Y" : "N";
        String CFDNTL_IN = (create.getConfidential().equals("true")) ? "Y" : "N";

        String RES_MGMT_REQ_VALIDATE_sql = "SELECT * " +
                " FROM RES_MGMT.RES_MGMT_REQ " +
                " WHERE TP_ID = " + parentId + " " +
                " AND GSR_IN = '" + GSR_IN + "' " +
                " AND CFDNTL_IN = '" + CFDNTL_IN + "' " +
                " AND RES_MGMT_REQ_TX = '" + create.getCommentText() + "' ";

        Database RES_MGMT_REQ_VALIDATE_db = new OracleDatabase(environment, Database.DREAMS);
        Recordset RES_MGMT_REQ_VALIDATE_rs = new Recordset(RES_MGMT_REQ_VALIDATE_db.getResultSet(RES_MGMT_REQ_VALIDATE_sql));

        if (RES_MGMT_REQ_VALIDATE_rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for tp ID [ " + getBook().getTravelPlanId() + " ]", RES_MGMT_REQ_VALIDATE_sql);
        }

        TestReporter.logStep("Verify that the comment shows up in the RES_MGMT_REQ_VALIDATE database.");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("TP_ID"), parentId, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("TP_ID") + "] matches the comment data [ " + parentId + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_REQ_TX"), create.getCommentText(), "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_REQ_TX") + "] matches the comment data [ " + create.getCommentText() + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("GSR_IN"), GSR_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("GSR_IN") + "] matches the comment data [ " + GSR_IN + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN"), CFDNTL_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN") + "] matches the comment data [ " + CFDNTL_IN + "]");

        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_PRFL_ID"), "1065", "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_PRFL_ID") + "] matches the comment data [1065]");
        TestReporter.assertAll();

        // GSR WILL NO LONGER BE INVOKED POST COMO. PLEASE SEE TASK TK-635536 FOR DETAILS
        String sql = "select * " +
                " from ext_intf.gsr_rcd a " +
                " join ext_intf.gsr_guest b on a.GSR_GUEST_ID = b.GSR_GUEST_ID " +
                " where a.tps_id = '" + getBook().getTravelPlanSegmentId() + "'" +
                " and a.cmt_tx = '" + create.getCommentText() + "' ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.logStep("Verify that no GSR records are returned");
        TestReporter.softAssertEquals(rs.getRowCount(), 0, "Verify that no GSR records were returned.");

        // Verify that the comment exists in the TPV3 database table
        String TPV3_sql = "select * " +
                " from sales_tp.tp " +
                " where tp_id = '" + getBook().getTravelPlanId() + "' ";

        Database TPV3_db = new OracleDatabase(environment, Database.SALESTP);
        Recordset TPV3_rs = new Recordset(TPV3_db.getResultSet(TPV3_sql));

        if (TPV3_rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for tp ID [ " + getBook().getTravelPlanId() + " ]", TPV3_sql);
        }

        TestReporter.logStep("Verify that the comment shows up in the TPV3 database.");
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertEquals(TPV3_rs.getValue("TP_ID"), getBook().getTravelPlanId(), "Verify that the TPV3 databse tp id [ " + TPV3_rs.getValue("TP_ID") + "] matches the comment data [ " + getBook().getTravelPlanId() + "]");
        TestReporter.assertAll();

    }
}