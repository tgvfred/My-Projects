package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestUpdateComments_parentTC_updateIsActive extends AccommodationBaseTest {
    String commentId = Randomness.randomNumber(10);
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
    String parentId = "";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateComments" })
    public void testUpdateComments_parentTC_updateIsActive() {

        parentId = getBook().getTravelComponentId();
        String isActive = "true";
        String sendToGSR = "false";
        String confidential = "true";
        String commentLevel = "TC";
        String createdBy = "AutoJUnit.us";
        UpdateComments update = new UpdateComments(environment, "Main");
        update.setparentIds(parentId);
        update.setIsActive(isActive);
        update.setSendToGSR(sendToGSR);
        update.setConfidential(confidential);
        update.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        update.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
        update.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
        update.setcommentText(commentText);
        update.setCommentLevel(commentLevel);
        update.settcId(getBook().getTravelComponentId());
        update.settpsId(getBook().getTravelPlanSegmentId());
        update.settpId(getBook().getTravelPlanId());
        update.setcreatedby(createdBy);
        update.setcreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        update.setupdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        update.setupdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
        update.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        update.sendRequest();

        // Validate node response values
        TestReporter.logStep("Validate Response node values.");
        TestReporter.setAssertFailed(false);
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", update);
        TestReporter.softAssertTrue(update.getSendToGSR().equals(sendToGSR), "Verify that the sendToGSR node [" + update.getSendToGSR() + "] is what is expected [" + sendToGSR + "]");
        TestReporter.softAssertTrue(update.getConfidential().equals(confidential), "Verify that the confidential node [" + update.getConfidential() + "] is what is expected [" + confidential + "]");
        TestReporter.softAssertTrue(update.getCommentText().equals(commentText), "Verify that the commentText node [" + update.getCommentText() + "] is what is expected [" + commentText + "]");
        TestReporter.softAssertTrue(update.getCommentLevel().equals(commentLevel), "Verify that the commentLevel node [" + update.getCommentLevel() + "] is what is expected [" + commentLevel + "]");
        TestReporter.softAssertTrue(update.getcreatedBy().equals(createdBy), "Verify that the createdBy node [" + update.getcreatedBy() + "] is what is expected [" + createdBy + "]");
        TestReporter.assertAll();
        validate(update);

        // Update comment text
        String oldCommentId = update.getCommentId();
        update.setcommentText(commentText + " update");
        update.setIsActive("false");
        update.sendRequest();

        // verify changes and comment id update
        TestReporter.logStep("Verify changes and comment id update");
        TestReporter.setAssertFailed(false);
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", update);
        TestReporter.softAssertTrue(update.getCommentText().equals(commentText + " update"), "Verify that the commentText node [" + update.getCommentText() + "] is what is expected [" + commentText + " update" + "]");
        TestReporter.softAssertFalse(update.getCommentId().equals(oldCommentId), "Verify that the old comment id [" + oldCommentId + "] is updated [" + update.getCommentId() + "]");
        TestReporter.softAssertTrue(update.getIsActive().equals("true"), "Verify that isActive is still set to true [" + update.getIsActive() + "]");
        TestReporter.assertAll();

        // validate new comment text changes
        validate(update);
    }

    private void validate(UpdateComments update) {
        // Validate comment with a call to retrieveComments
        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
        retrieve.setParentIds(parentId);
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);
        validate(update, retrieve);

        // Validate comment data in RES_MGMT_REQ table
        String GSR_IN = (update.getSendToGSR().equals("true")) ? "Y" : "N";
        String CFDNTL_IN = (update.getConfidential().equals("true")) ? "Y" : "N";

        String RES_MGMT_REQ_VALIDATE_sql = "SELECT * " +
                " FROM RES_MGMT.RES_MGMT_REQ " +
                " WHERE TC_ID = " + parentId + " " +
                " AND GSR_IN = '" + GSR_IN + "' " +
                " AND CFDNTL_IN = '" + CFDNTL_IN + "' " +
                " AND RES_MGMT_REQ_TX = '" + update.getCommentText() + "' ";

        Database RES_MGMT_REQ_VALIDATE_db = new OracleDatabase(environment, Database.DREAMS);
        Recordset RES_MGMT_REQ_VALIDATE_rs = new Recordset(RES_MGMT_REQ_VALIDATE_db.getResultSet(RES_MGMT_REQ_VALIDATE_sql));

        if (RES_MGMT_REQ_VALIDATE_rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found in recordset for tp ID [ " + getBook().getTravelPlanId() + " ]", RES_MGMT_REQ_VALIDATE_sql);
        }

        TestReporter.logStep("Verify that the comment shows up in the RES_MGMT_REQ_VALIDATE database.");
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID"), parentId, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID") + "] matches the comment data [ " + parentId + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_REQ_TX"), update.getCommentText(), "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_REQ_TX") + "] matches the comment data [ " + update.getCommentText() + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("GSR_IN"), GSR_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("GSR_IN") + "] matches the comment data [ " + GSR_IN + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN"), CFDNTL_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN") + "] matches the comment data [ " + CFDNTL_IN + "]");
        TestReporter.assertAll();

        // GSR WILL NO LONGER BE INVOKED POST COMO. PLEASE SEE TASK TK-635536 FOR DETAILS
        String sql = "select * " +
                " from ext_intf.gsr_rcd a " +
                " join ext_intf.gsr_guest b on a.GSR_GUEST_ID = b.GSR_GUEST_ID " +
                " where a.tps_id = '" + getBook().getTravelPlanSegmentId() + "'" +
                " and a.cmt_tx = '" + update.getCommentText() + "' ";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.logStep("Verify that no GSR records are returned");
        TestReporter.softAssertEquals(rs.getRowCount(), 0, "Verify that no GSR records were returned.");

        // Verify that the comment exists in the TPV3 database table
        String TPV3_sql = "select * " +
                " from sales_tp.tp " +
                " where tp_id = '" + update.getTpId() + "' ";

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

    private void validate(UpdateComments update, RetrieveComments retrieve) {

        TestReporter.logStep("Validate comment with a call to retreiveComments service.");
        TestReporter.setAssertFailed(false);

        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
            if (update.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {
                TestReporter.softAssertEquals(update.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + update.getIsActive() + "]");
                TestReporter.softAssertEquals(update.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + update.getSendToGSR() + "]");
                TestReporter.softAssertEquals(update.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + update.getConfidential() + "]");
                TestReporter.softAssertEquals(update.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + update.getCommentId() + "]");
                TestReporter.softAssertEquals(update.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + update.getCommentText() + "]");
                TestReporter.softAssertEquals(update.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + update.getCommentLevel() + "]");
                TestReporter.softAssertEquals(update.getcreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + update.getcreatedBy() + "]");
                String[] createdDate = update.getcreatedDate().split("T");
                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
                TestReporter.assertAll();
                break;
            }
        }
    }
}