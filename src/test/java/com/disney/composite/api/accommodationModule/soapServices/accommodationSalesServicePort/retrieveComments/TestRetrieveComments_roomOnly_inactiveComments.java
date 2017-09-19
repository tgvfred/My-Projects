package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RemoveComments;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveComments_roomOnly_inactiveComments extends AccommodationBaseTest {
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

        CreateComments create2 = new CreateComments(environment, "Main");
        create2.setParentIds(parentId);
        create2.setIsActive(expectedIsActive);
        create2.setSendToGSR(expectedGSR);
        create2.setConfidential(expectedConfidential);
        create2.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        create2.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
        create2.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
        create2.setCommentText(commentText + " Second Comment");
        create2.setCommentLevel(expectedCommentLevel);
        create2.setTcId(getBook().getTravelComponentId());
        create2.setTpsId(getBook().getTravelPlanSegmentId());
        create2.setTpId(getBook().getTravelPlanId());
        create2.setCreatedBy(expectedCreatedBy);
        create2.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create2.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create2.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
        create2.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
        create2.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create2.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create2.sendRequest();

        CreateComments create3 = new CreateComments(environment, "Main");
        create3.setParentIds(parentId);
        create3.setIsActive(expectedIsActive);
        create3.setSendToGSR(expectedGSR);
        create3.setConfidential(expectedConfidential);
        create3.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        create3.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
        create3.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
        create3.setCommentText(commentText + " Second Comment");
        create3.setCommentLevel(expectedCommentLevel);
        create3.setTcId(getBook().getTravelComponentId());
        create3.setTpsId(getBook().getTravelPlanSegmentId());
        create3.setTpId(getBook().getTravelPlanId());
        create3.setCreatedBy(expectedCreatedBy);
        create3.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create3.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create3.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
        create3.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
        create3.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create3.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create3.sendRequest();

        CreateComments create4 = new CreateComments(environment, "Main");
        create4.setParentIds(parentId);
        create4.setIsActive(expectedIsActive);
        create4.setSendToGSR(expectedGSR);
        create4.setConfidential(expectedConfidential);
        create4.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        create4.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
        create4.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
        create4.setCommentText(commentText + " Second Comment");
        create4.setCommentLevel(expectedCommentLevel);
        create4.setTcId(getBook().getTravelComponentId());
        create4.setTpsId(getBook().getTravelPlanSegmentId());
        create4.setTpId(getBook().getTravelPlanId());
        create4.setCreatedBy(expectedCreatedBy);
        create4.setCreatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create4.setUpdatedDate(BaseSoapCommands.REMOVE_NODE.toString());
        create4.setUpdatedBy("Thomas " + Randomness.randomAlphaNumeric(4));
        create4.setStatus(BaseSoapCommands.REMOVE_NODE.toString());
        create4.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create4.setRequestNodeValueByXPath("/Envelope/Body/createComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        create4.sendRequest();

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

        TestReporter.logAPI(!create2.getResponseStatusCode().equals("200"), "An error occurred in the create comment request", create);
        TestReporter.logAPI(!create3.getResponseStatusCode().equals("200"), "An error occurred in the create comment request", create);
        TestReporter.logAPI(!create4.getResponseStatusCode().equals("200"), "An error occurred in the create comment request", create);

        // Remove third and fourth comments
        RemoveComments remove3 = new RemoveComments(environment, "Main");
        remove3.setparentIds(getBook().getTravelComponentId());
        remove3.setcommentId(create3.getCommentId());
        remove3.sendRequest();
        TestReporter.logAPI(!remove3.getResponseStatusCode().equals("200"), "An error occurred with the request to remove the third comment", remove3);

        RemoveComments remove4 = new RemoveComments(environment, "Main");
        remove4.setparentIds(getBook().getTravelComponentId());
        remove4.setcommentId(create4.getCommentId());
        remove4.sendRequest();
        TestReporter.logAPI(!remove4.getResponseStatusCode().equals("200"), "An error occurred with the request to remove the third comment", remove4);

        // Validate comment with a call to retrieveComments
        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
        retrieve.setParentIds(parentId);
        retrieve.sendRequest();
        TestReporter.logAPI(!retrieve.getResponseStatusCode().equals("200"), "An error occurred getting options by filter", retrieve);

        // validates that the first inactive comes before the second
        validateOrder(create3, create4, retrieve);

        // validates that active ones come before inactive
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

    private void validateOrder(CreateComments create, CreateComments create2, RetrieveComments retrieve) {

        int firstEntryIndex = 0;
        int secondEntryIndex = 0;
        TestReporter.logStep("Validate comment with a call to retreiveComments service.");
        TestReporter.setAssertFailed(false);

        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
            if (create.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {

                TestReporter.softAssertEquals("false", retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create.getIsActive() + "]");
                TestReporter.softAssertEquals(create.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create.getSendToGSR() + "]");
                TestReporter.softAssertEquals(create.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create.getConfidential() + "]");
                TestReporter.softAssertEquals(create.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create.getCommentId() + "]");
                TestReporter.softAssertEquals(create.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create.getCommentText() + "]");
                TestReporter.softAssertEquals(create.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create.getCommentLevel() + "]");
                TestReporter.softAssertEquals(create.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create.getCreatedBy() + "]");
                String[] createdDate = create.getCreatedDate().split("T");
                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
                TestReporter.assertAll();
                firstEntryIndex = i;
                break;
            }
        }

        TestReporter.setAssertFailed(false);

        for (int i = 1; i <= retrieve.getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveCommentsResponse/response/commentsInfo"); i++) {
            String commentXPath = "/Envelope/Body/retrieveCommentsResponse/response/commentsInfo[" + i + "]/";
            if (create2.getCommentId().equals(retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"))) {

                TestReporter.softAssertEquals("false", retrieve.getResponseNodeValueByXPath(commentXPath + "isActive"), "Verify that the retrieved isActive node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "isActive") + "] matches the expected [" + create2.getIsActive() + "]");
                TestReporter.softAssertEquals(create2.getSendToGSR(), retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR"), "Verify that the retrieved sendToGSR node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "sendToGSR") + "] matches the expected [" + create2.getSendToGSR() + "]");
                TestReporter.softAssertEquals(create2.getConfidential(), retrieve.getResponseNodeValueByXPath(commentXPath + "confidential"), "Verify that the retrieved confidential node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "confidential") + "] matches the expected [" + create2.getConfidential() + "]");
                TestReporter.softAssertEquals(create2.getCommentId(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentId"), "Verify that the retrieved commentId node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentId") + "] matches the expected [" + create2.getCommentId() + "]");
                TestReporter.softAssertEquals(create2.getCommentText(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentText"), "Verify that the retrieved commentText node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentText") + "] matches the expected [" + create2.getCommentText() + "]");
                TestReporter.softAssertEquals(create2.getCommentLevel(), retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel"), "Verify that the retrieved commentLevel node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "commentLevel") + "] matches the expected [" + create2.getCommentLevel() + "]");
                TestReporter.softAssertEquals(create2.getCreatedBy(), retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy"), "Verify that the retrieved createdBy node [" + retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdBy") + "] matches the expected [" + create2.getCreatedBy() + "]");
                String[] createdDate = create2.getCreatedDate().split("T");
                String[] retrievedDate = retrieve.getResponseNodeValueByXPath(commentXPath + "auditDetail/createdDate").split("T");
                TestReporter.softAssertEquals(createdDate[0], retrievedDate[0], "Verify that the retrieved createdDate node [" + retrievedDate[0] + "] matches the expected [" + createdDate[0] + "]");
                TestReporter.assertAll();
                secondEntryIndex = i;
                break;
            }
        }

        TestReporter.logStep("Validate Comment Order");
        TestReporter.assertTrue(secondEntryIndex > firstEntryIndex, "Validate that the second comment entry appears after the first");
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
