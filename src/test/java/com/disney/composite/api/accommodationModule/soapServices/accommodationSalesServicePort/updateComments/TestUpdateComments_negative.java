package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.updateComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateComments;
import com.disney.api.soapServices.accommodationModule.applicationError.LiloResm;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestUpdateComments_negative extends AccommodationBaseTest {
    String commentId = Randomness.randomAlphaNumeric(10);
    String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
    String parentId = "";

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "updateComments", "negative" })
    public void testCreateComments_nullRequest() {

        String faultString = "Create Comments Request Invalid : CommentRequest should be provided";
        UpdateComments update = new UpdateComments(environment, "Main");
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request", BaseSoapCommands.REMOVE_NODE.toString());
        update.sendRequest();

        TestReporter.assertEquals(update.getFaultString(), faultString, "Verify that the fault string [" + update.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(update, LiloResm.REQUEST_REQUIRED);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "updateComments", "negative" })
    public void testCreateComments_nullCommentsInfo() {

        String faultString = "Create Comments Request Invalid : CommentRequest should be provided";
        parentId = getBook().getTravelComponentId();
        UpdateComments update = new UpdateComments(environment, "Main");
        update.setparentIds(parentId);
        update.setRequestNodeValueByXPath("/Envelope/Body/updateComments/request/commentsInfo", BaseSoapCommands.REMOVE_NODE.toString());
        update.sendRequest();

        TestReporter.assertEquals(update.getFaultString(), faultString, "Verify that the fault string [" + update.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(update, LiloResm.REQUEST_REQUIRED);
    }

}
