package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveComments_roomOnly_commentWithProfileId extends AccommodationBaseTest {
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
        CreateComments create = new CreateComments(environment, "Main");
        create.setParentIds(parentId);
        create.setIsActive(expectedIsActive);
        create.setSendToGSR(expectedGSR);
        create.setConfidential(expectedConfidential);
        create.setProfileId("1065");
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
        TestReporter.logAPI(!create.getResponseStatusCode().equals("200"), "An error occurred in the create comment request", create);
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
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred in the create comment request", retrieve);

        // Validates response nodes
        validate(create, retrieve);

        // Validates that active orders come before inactive ones
        validateActiveOrder(retrieve);

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
            if (create.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {

                TestReporter.softAssertEquals(create.getIsActive(), retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create.getIsActive() + "]");
                TestReporter.softAssertEquals(create.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create.getSendToGSR() + "]");
                TestReporter.softAssertEquals(create.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create.getConfidential() + "]");
                TestReporter.softAssertEquals(create.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create.getCommentId() + "]");
                TestReporter.softAssertEquals(create.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create.getCommentText() + "]");
                TestReporter.softAssertEquals(create.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create.getCommentLevel() + "]");
                TestReporter.softAssertEquals(create.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create.getCreatedBy() + "]");
                TestReporter.softAssertEquals(create.getProfileId(), retrieve.getResponseNodeValueByXPath(commentXPath + "profileId"), "Verify that the retrieved profileID node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "profileId") + "] matches the expected [" + create.getProfileId() + "]");
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
            if (cache.equals("false")) {
                TestReporter.softAssertEquals("false", retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected false");
            }
            cache = retrieve.getResponseNodeValueByXPath(commentXPath + "isActive");

        }
        TestReporter.assertAll();
    }

}
