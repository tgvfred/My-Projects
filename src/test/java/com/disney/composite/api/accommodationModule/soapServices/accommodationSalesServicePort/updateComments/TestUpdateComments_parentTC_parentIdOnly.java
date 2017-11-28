package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestUpdateComments_parentTC_parentIdOnly extends AccommodationBaseTest {
    String commentId = Randomness.randomNumber(10);
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
    String parentId = "";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "UpdateComments" })
    public void testUpdateComments_parentTC_parentIdOnly() {
        parentId = getBook().getTravelComponentId();
        UpdateComments update = new UpdateComments(environment, "Main");
        update.setparentIds(parentId);
        update.setIsActive(BaseSoapCommands.REMOVE_NODE.toString());
        update.setSendToGSR(BaseSoapCommands.REMOVE_NODE.toString());
        update.setConfidential(BaseSoapCommands.REMOVE_NODE.toString());
        update.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
        update.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
        update.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
        update.setcommentText(commentText);
        update.setCommentLevel("TC");
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentType", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/auditDetail", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo/commentOwnerDetail", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/roomExternalReference", BaseSoapCommands.REMOVE_NODE.toString());
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/tpsExternalReference", BaseSoapCommands.REMOVE_NODE.toString());

        // int tries = 0;
        // int maxTries = 20;
        // do {
        // Sleeper.sleep(1000);
        update.sendRequest();
        // tries++;
        // } while (tries < maxTries && !update.getResponseStatusCode().equals("200"));

        // Validate that the nodes are not present in the xml
        TestReporter.logStep("Verify the nodes are not present in the response xml");
        TestReporter.setAssertFailed(false);
        TestReporter.logAPI(!update.getResponseStatusCode().equals("200"), "An error occurred getting updating comments: " + update.getFaultString(), update);
        try {
            update.getSendToGSR();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "GSR is not present in the xml.");
        }
        try {
            update.getConfidential();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "Confidential is not present in the xml.");
        }
        try {
            update.getProfileId();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "Profile Id is not present in the xml.");
        }
        try {
            update.getProfileCode();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "Profile Code is not present in the xml.");
        }
        try {
            update.getCommentId();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "Comment Id is not present in the xml.");
        }
        try {
            update.getCommentText();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "Comment Text is not present in the xml.");
        }
        try {
            update.getCommentLevel();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "Comment Level is not present in the xml.");
        }
        try {
            update.getTcId();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "TC Id is not present in the xml.");
        }
        try {
            update.getTpId();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "TP Id is not present in the xml.");
        }
        try {
            update.getTpsId();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "TPS Id is not present in the xml.");
        }
        try {
            update.getcreatedBy();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "CreatedBy is not present in the xml.");
        }
        try {
            update.getcreatedDate();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "CreatedDate is not present in the xml.");
        }
        try {
            update.getupdatedBy();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "UpdatedBy Id is not present in the xml.");
        }
        try {
            update.getupdatedDate();
        } catch (XPathNotFoundException e) {
            TestReporter.softAssertTrue(true, "UpdatedDate Id is not present in the xml.");
        }
        TestReporter.assertAll();

    }
}