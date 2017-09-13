package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;


import org.testng.annotations.Test;


import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveComments_roomOnly_multCommentsOnMultLevels extends AccommodationBaseTest {
    String commentId = Randomness.randomAlphaNumeric(10);
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
    String parentId = "";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "CreateComments" })
    public void testCreateComments_parentTC() {

        String expectedIsActive = "true";
        String expectedGSR = "false";
        String expectedConfidential = "true";
        String expectedCommentLevel = "TC";
        String expectedCreatedBy = "AutoJUnit.us";
        parentId = getBook().getTravelComponentId();
        com.disney.api.soapServices.travelPlanModule.travelPlanService.operations.CreateComment createTP = new com.disney.api.soapServices.travelPlanModule.travelPlanService.operations.CreateComment(environment, "MinimalInfo");
        createTP.setParentIds(getBook().getTravelPlanId());
        createTP.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        createTP.setCommentText(commentText);
        createTP.setTpsId(getBook().getTravelPlanSegmentId());
        createTP.setTpId(getBook().getTravelPlanId());
        createTP.sendRequest();
        
        
        TestReporter.logAPI(!createTP.getResponseStatusCode().equals("200"), "An error occurred submitting TP comment", createTP);
        
        
        com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.CreateComment createTPS = new com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.CreateComment(environment, "Main");
        createTPS.setParentIds(getBook().getTravelPlanSegmentId());            
        createTPS.setCommentText(commentText);                
        createTPS.sendRequest();
        
        
        TestReporter.logAPI(!createTPS.getResponseStatusCode().equals("200"), "An error occurred submitting TPS comment", createTPS);
        
      
        CreateComments create = new CreateComments(environment, "Main");
        create.setParentIds(parentId);
        create.setIsActive(expectedIsActive);
        create.setSendToGSR(expectedGSR);
        create.setConfidential(expectedConfidential);
        create.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        create.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
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
        create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
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
        TestReporter.assertAll();

        // Validate comment with a call to retrieveComments
        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
        retrieve.setParentIds(parentId);
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);
        validate(create, retrieve);
        validateActiveOrder(retrieve);

        // Validate comment data in RES_MGMT_REQ table
        
        

        String RES_MGMT_REQ_VALIDATE_sql = "SELECT * " +
                " FROM RES_MGMT.RES_MGMT_REQ " +
                " WHERE tp_id = " + create.getTpId() + " " +
                " AND RES_MGMT_REQ_TYP_NM = 'TravelComponentComment' ";              
        

        Database RES_MGMT_REQ_VALIDATE_db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(RES_MGMT_REQ_VALIDATE_db.getResultSet(RES_MGMT_REQ_VALIDATE_sql));

 
        
        // 
        
        TestReporter.assertTrue(rs.getRowCount() == retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"), "Validate number of records returned matches SOAP response");
        
        TestReporter.logStep("Verify that the comments shows up in the RES_MGMT_REQ_VALIDATE database.");
        TestReporter.setAssertFailed(false);
        
        
        for (int i = 1; i <= rs.getRowCount(); i++) {

            boolean found = false;

            for (int j = 1; j <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); j++) {

                String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + j + "]/";
                if (rs.getValue("RES_MGMT_REQ_ID", i).equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {
                     TestReporter.softAssertEquals(rs.getValue("RES_MGMT_REQ_ID", i), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + rs.getValue("RES_MGMT_REQ_ID", i) + "]");
                     TestReporter.softAssertEquals(rs.getValue("TC_ID", i), retrieve.getResponseNodeValueByXPath(commentXPath + "/commentOwnerDetail/tcId"), "Verify that the retrieved tcID node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "/commentOwnerDetail/tcId") + "] matches the expected [" + rs.getValue("TC_ID", i) + "]");
                     TestReporter.softAssertEquals(rs.getValue("RES_MGMT_REQ_TX", i), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + rs.getValue("RES_MGMT_REQ_TX", i) + "]");
                     found=true;
                    break;                
                }

            }
            TestReporter.softAssertTrue(found, "Validate response records match database records");
        }
        TestReporter.assertAll();

        
        
//        
//        
//        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID"), parentId, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID") + "] matches the comment data [ " + parentId + "]");
//        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_REQ_TX"), create.getCommentText(), "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("RES_MGMT_REQ_TX") + "] matches the comment data [ " + create.getCommentText() + "]");
//        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("GSR_IN"), GSR_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("GSR_IN") + "] matches the comment data [ " + GSR_IN + "]");
//        TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN"), CFDNTL_IN, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("CFDNTL_IN") + "] matches the comment data [ " + CFDNTL_IN + "]");
//        TestReporter.assertAll();

       
        
        
        if (Environment.isSpecialEnvironment(environment)) {

            RetrieveComments clone = (RetrieveComments) retrieve.clone();
            clone.setParentIds(parentId);
            clone.sendRequest();
            if (!clone.getResponseStatusCode().equals("200")) {
                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned for cloned request", clone);
            }

            TestReporter.assertTrue(retrieve.validateResponseNodeQuantity(clone, true), "Validating Response Comparison");
        }

    }

    private void validate(CreateComments create, RetrieveComments retrieve) {

        TestReporter.logStep("Validate comment with a call to retreiveComments service.");
        TestReporter.setAssertFailed(false);

        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
            TestReporter.softAssertEquals("TC", retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [TC]");
            if (create.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {
                TestReporter.softAssertEquals(create.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create.getIsActive() + "]");
                TestReporter.softAssertEquals(create.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create.getSendToGSR() + "]");
                TestReporter.softAssertEquals(create.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create.getConfidential() + "]");
                TestReporter.softAssertEquals(create.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create.getCommentId() + "]");
                TestReporter.softAssertEquals(create.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create.getCommentText() + "]");               
                TestReporter.softAssertEquals(create.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create.getCreatedBy() + "]");
                String[] createdDate = create.getCreatedDate().split("T");
                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
                TestReporter.assertAll();
                break;                
            }
        }
    }
    
    private void validateActiveOrder(RetrieveComments retrieve) {
        String cache = "";
        TestReporter.logStep("Validate Active Comments Come First");
        TestReporter.setAssertFailed(false);

        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";            
                if (cache == "false") {                    
                    TestReporter.softAssertEquals("false", retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected false");                    
                }                         
                cache = retrieve.getResponseNodeValueByXPath(commentXPath + "isActive");
                TestReporter.assertAll();
                break;                
            }
        }
}



