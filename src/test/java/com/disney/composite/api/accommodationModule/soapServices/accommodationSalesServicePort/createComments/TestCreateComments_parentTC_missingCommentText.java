package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.createComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.ServiceConstants;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestCreateComments_parentTC_missingCommentText extends AccommodationBaseTest {
    String commentId = BaseSoapCommands.REMOVE_NODE.toString();
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
    String parentId = "";
    CreateComments create;
    RetrieveComments retrieve;

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "CreateComments" })
    public void testCreateComments_parentTC_missingCommentText() {

        String expectedIsActive = "true";
        String expectedConfidential = "true";
        String expectedGSR = "true";
        String expectedCommentLevel = "TC";
        String expectedCreatedBy = "AutoJUnit.us";
        parentId = getBook().getTravelComponentId();
        create = new CreateComments(environment, "Main");
        create.setParentIds(parentId);
        create.setIsActive(expectedIsActive);
        create.setSendToGSR(expectedGSR);
        create.setConfidential(expectedConfidential);
        create.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        create.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
        create.setCommentId(commentId);
        create.setCommentText(commentText);
        create.setCommentLevel(expectedCommentLevel);
        create.setTcId(getBook().getTravelComponentId());
        create.setTpsId(getBook().getTravelPlanSegmentId());
        create.setTpId(getBook().getTravelPlanId());
        create.setCreatedBy(expectedCreatedBy);
        create.setCreatedDate(Randomness.generateCurrentXMLDate());
        create.setUpdatedDate(Randomness.generateCurrentXMLDate());
        create.setUpdatedBy(expectedCreatedBy);
        create.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
        create.setRoomExternalReferenceNumber(getBook().getTravelComponentId());
        create.setRoomExternalReferenceSource(ServiceConstants.FolioExternalReference.DREAMS_TC);
        create.setTpsExternalReferenceNumber(getBook().getTravelPlanSegmentId());
        create.setTpsExternalReferenceSource(ServiceConstants.FolioExternalReference.DREAMS_TPS);
        create.setCommentType("TravelComponentComment");
        create.sendRequest();
        TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", create);
        commentId = create.getCommentId();

        // Validate node response values
        TestReporter.logStep("Validate Response node values.");
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertTrue(create.getConfidential().equals(expectedConfidential), "Verify that the confidential node [" + create.getConfidential() + "] is what is expected [" + expectedConfidential + "]");
        TestReporter.softAssertTrue(create.getCommentLevel().equals(expectedCommentLevel), "Verify that the commentLevel node [" + create.getCommentLevel() + "] is what is expected [" + expectedCommentLevel + "]");
        TestReporter.softAssertTrue(create.getCreatedBy().equals(expectedCreatedBy), "Verify that the createdBy node [" + create.getCreatedBy() + "] is what is expected [" + expectedCreatedBy + "]");
        TestReporter.assertAll();

        // Validate comment with a call to retrieveComments
        retrieve = new RetrieveComments(environment, "Main");
        retrieve.setParentIds(parentId);
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);
        validate(create, retrieve);

        // Validate comment data in RES_MGMT_REQ table
        String CFDNTL_IN = (create.getConfidential().equals("true")) ? "Y" : "N";
        String GSR_IN = (create.getSendToGSR().equals("true")) ? "Y" : "N";

        String RES_MGMT_REQ_VALIDATE_sql = "SELECT * " +
                " FROM RES_MGMT.RES_MGMT_REQ " +
                " WHERE TC_ID = " + parentId + " " +
                " AND CFDNTL_IN = '" + CFDNTL_IN + "' " +
                " AND GSR_IN = '" + GSR_IN + "' ";

        Database RES_MGMT_REQ_VALIDATE_db = new OracleDatabase(environment, Database.DREAMS);
        Recordset RES_MGMT_REQ_VALIDATE_rs = new Recordset(RES_MGMT_REQ_VALIDATE_db.getResultSet(RES_MGMT_REQ_VALIDATE_sql));
        System.out.println(parentId);

        TestReporter.logStep("Verify that the comment shows up in the RES_MGMT_REQ_VALIDATE database.");
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID"), parentId, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID") + "] matches the comment data [ " + parentId + "]");
        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN"), CFDNTL_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN") + "] matches the comment data [ " + CFDNTL_IN + "]");
        TestReporter.assertAll();

        // Verify that the comment exists in the TPV3 database table
        String TPV3_sql = "select * " +
                " from sales_tp.tp " +
                " where tp_id = '" + getBook().getTravelPlanId() + "' ";

        Database TPV3_db = new OracleDatabase(environment, Database.SALESTP);
        Recordset TPV3_rs = new Recordset(TPV3_db.getResultSet(TPV3_sql));

        TestReporter.logStep("Verify that the comment shows up in the TPV3 database.");
        TestReporter.setAssertFailed(false);
        TestReporter.softAssertEquals(TPV3_rs.getValue("TP_ID"), getBook().getTravelPlanId(), "Verify that the TPV3 databse tp id [ " + TPV3_rs.getValue("TP_ID") + "] matches the comment data [ " + getBook().getTravelPlanId() + "]");
        TestReporter.assertAll();
    }

    private void validate(CreateComments create, RetrieveComments retrieve) {

        TestReporter.logStep("Validate comment with a call to retreiveComments service.");
        TestReporter.setAssertFailed(false);
        int numNodes = retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo");
        for (int i = 1; i <= numNodes; i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
            if (create.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {
                TestReporter.softAssertEquals(create.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create.getIsActive() + "]");
                TestReporter.softAssertEquals(create.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create.getConfidential() + "]");
                TestReporter.softAssertEquals(create.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create.getCommentId() + "]");
                TestReporter.softAssertEquals(create.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create.getCommentLevel() + "]");
                TestReporter.softAssertEquals(create.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create.getCreatedBy() + "]");
                String[] createdDate = create.getCreatedDate().split("T");
                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
                TestReporter.assertAll();
                break;
            }
        }
    }
}