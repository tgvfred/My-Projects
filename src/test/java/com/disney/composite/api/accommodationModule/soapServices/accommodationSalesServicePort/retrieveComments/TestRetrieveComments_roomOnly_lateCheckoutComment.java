package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;


import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
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

public class TestRetrieveComments_roomOnly_lateCheckoutComment extends AccommodationBaseTest {
    
    private static Database db;


    @DataProvider(name = "TCID")
    public Object[][] data() {
        db = new OracleDatabase(environment, Database.DREAMS);        
        String sql = "select TC_ID from "
                +"( "
                +"select * "
                +"from ( "
                +"select a.tc_id, count(a.TC_ID) tc_count "
                +"from res_mgmt.tc a "
                +"join res_mgmt.res_mgmt_req b on a.tc_id = b.tc_id "
                +"where a.tc_typ_nm ='AccommodationComponent' " 
                +"and CMT_REQ_TYP_NM is not null "
                +"and b.CMT_REQ_TYP_NM = 'LateCheckOut' "
                +"group by a.TC_ID "
                +") "
                +"having tc_count > 1 "
                +") "
                +"where ROWNUM < 2";       
        return db.getResultSetAsDataProvider(sql);
    }

    
    @Test(dataProvider = "TCID", groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetriveComments" })
    public void testCreateComments_parentTC(String tcID) {
    }
    
    
//    String commentId = Randomness.randomAlphaNumeric(10);
//    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
//    String parentId = "";
//
//    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "CreateComments" })
//    public void testCreateComments_parentTC() {
//
//        String expectedIsActive = "true";
//        String expectedGSR = "false";
//        String expectedConfidential = "true";
//        String expectedCommentLevel = "TC";
//        String expectedCreatedBy = "AutoJUnit.us";
//        parentId = getBook().getTravelComponentId();
//        CreateComments create = new CreateComments(environment, "Main");
//        create.setParentIds(parentId);
//        create.setIsActive(expectedIsActive);
//        create.setSendToGSR(expectedGSR);
//        create.setConfidential(expectedConfidential);
//        create.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
//        create.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
//        create.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
//        create.setCommentText(commentText);
//        create.setCommentLevel(expectedCommentLevel);
//        create.setTcId(getBook().getTravelComponentId());
//        create.setTpsId(getBook().getTravelPlanSegmentId());
//        create.setTpId(getBook().getTravelPlanId());
//        create.setCreatedBy(expectedCreatedBy);
//        create.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
//        create.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
//        create.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
//        create.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
//        create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
//        create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
//        create.sendRequest();
//        
//        CreateComments create2 = new CreateComments(environment, "Main");
//        create2.setParentIds(parentId);
//        create2.setIsActive(expectedIsActive);
//        create2.setSendToGSR(expectedGSR);
//        create2.setConfidential(expectedConfidential);
//        create2.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setCommentText(commentText + " Second Comment");
//        create2.setCommentLevel(expectedCommentLevel);
//        create2.setTcId(getBook().getTravelComponentId());
//        create2.setTpsId(getBook().getTravelPlanSegmentId());
//        create2.setTpId(getBook().getTravelPlanId());
//        create2.setCreatedBy(expectedCreatedBy);
//        create2.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
//        create2.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
//        create2.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
//        create2.sendRequest();
//
//        // Validate node response values
//        TestReporter.logStep("Validate Response node values.");
//        TestReporter.setAssertFailed(false);
//        TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", create);
//        TestReporter.softAssertTrue(create.getSendToGSR().equals(expectedGSR), "Verify that the sendToGSR node [" + create.getSendToGSR() + "] is what is expected [" + expectedGSR + "]");
//        TestReporter.softAssertTrue(create.getConfidential().equals(expectedConfidential), "Verify that the confidential node [" + create.getConfidential() + "] is what is expected [" + expectedConfidential + "]");
//        TestReporter.softAssertTrue(create.getCommentText().equals(commentText), "Verify that the commentText node [" + create.getCommentText() + "] is what is expected [" + commentText + "]");
//        TestReporter.softAssertTrue(create.getCommentLevel().equals(expectedCommentLevel), "Verify that the commentLevel node [" + create.getCommentLevel() + "] is what is expected [" + expectedCommentLevel + "]");
//        TestReporter.softAssertTrue(create.getCreatedBy().equals(expectedCreatedBy), "Verify that the createdBy node [" + create.getCreatedBy() + "] is what is expected [" + expectedCreatedBy + "]");
//        TestReporter.assertAll();
//        
//        TestReporter.logAPI(!create2.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", create);
//
//        // Validate comment with a call to retrieveComments
//        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
//        retrieve.setParentIds(parentId);
//        retrieve.sendRequest();
//        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);
//        
//        // Validates that the first active order comes before the second
//        validate(create, create2, retrieve);
//        
//        // Validates that active ones come before inactive
//        validateActiveOrder(retrieve);
//
//     
//        
//        if (Environment.isSpecialEnvironment(environment)) {
//
//            RetrieveComments clone = (RetrieveComments) retrieve.clone();
//            clone.setParentIds(parentId);
//            clone.sendRequest();
//            if (!clone.getResponseStatusCode().equals("200")) {
//                TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned for cloned request", clone);
//            }
//
//            TestReporter.assertTrue(retrieve.validateResponseNodeQuantity(clone, true), "Validating Response Comparison");
//        }
//
//    }
//
//    private void validate(CreateComments create,CreateComments create2, RetrieveComments retrieve) {
//
//        int firstEntryIndex = 0;
//        int secondEntryIndex = 0;
//        TestReporter.logStep("Validate comment with a call to retreiveComments service.");
//        TestReporter.setAssertFailed(false);
//        
//
//        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
//            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
//            if (create.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {
//                                
//                TestReporter.softAssertEquals(create.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create.getIsActive() + "]");
//                TestReporter.softAssertEquals(create.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create.getSendToGSR() + "]");
//                TestReporter.softAssertEquals(create.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create.getConfidential() + "]");
//                TestReporter.softAssertEquals(create.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create.getCommentId() + "]");
//                TestReporter.softAssertEquals(create.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create.getCommentText() + "]");
//                TestReporter.softAssertEquals(create.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create.getCommentLevel() + "]");
//                TestReporter.softAssertEquals(create.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create.getCreatedBy() + "]");                
//                String[] createdDate = create.getCreatedDate().split("T");
//                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
//                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
//                TestReporter.assertAll();
//                firstEntryIndex = i;
//                break;                
//            }
//        }
//        
//        TestReporter.setAssertFailed(false);
//
//        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
//            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
//            if (create2.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {
//                                
//                TestReporter.softAssertEquals(create2.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create2.getIsActive() + "]");
//                TestReporter.softAssertEquals(create2.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create2.getSendToGSR() + "]");
//                TestReporter.softAssertEquals(create2.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create2.getConfidential() + "]");
//                TestReporter.softAssertEquals(create2.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create2.getCommentId() + "]");
//                TestReporter.softAssertEquals(create2.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create2.getCommentText() + "]");
//                TestReporter.softAssertEquals(create2.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create2.getCommentLevel() + "]");
//                TestReporter.softAssertEquals(create2.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create2.getCreatedBy() + "]");                
//                String[] createdDate = create2.getCreatedDate().split("T");
//                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
//                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
//                TestReporter.assertAll();
//                secondEntryIndex = i;
//                break;                
//            }
//        }
//        
//        TestReporter.logStep("Validate Comment Order");
//        TestReporter.assertTrue(secondEntryIndex > firstEntryIndex, "Validate that the second comment entry appears after the first");
//    }
//    
//    private void validateActiveOrder(RetrieveComments retrieve) {
//        String cache = "";
//        TestReporter.logStep("Validate Active Comments Come First");
//        TestReporter.setAssertFailed(false);
//
//        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
//            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";            
//                if (cache == "false") {                    
//                    TestReporter.softAssertEquals("false", retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected false");                    
//                }                         
//                cache = retrieve.getResponseNodeValueByXPath(commentXPath + "isActive");
//                TestReporter.assertAll();
//                break;                
//            }
//        }
}



