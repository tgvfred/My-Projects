package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.createComments;

import org.testng.annotations.Test;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.CreateComments;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestCreateComments_negative extends AccommodationBaseTest{
String commentId = Randomness.randomAlphaNumeric(10);
String commentText = "This is test comment " + Randomness.randomAlphaNumeric(4);
String parentId = "";
	

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateComments", "negative"})
	public void testCreateComments_nullCommentlevel() {

		
		String expectedIsActive = "true";
		String expectedGSR = "false";
		String expectedConfidential = "true";
		String expectedCreatedBy = "AutoJUnit.us";
		parentId = getBook().getTravelComponentId();
		String faultString = "Create Comments Request Invalid : Comment Level not provided in Comment Info";
		CreateComments create = new CreateComments(environment, "Main");
		create.setParentIds(parentId);
		create.setIsActive(expectedIsActive);
		create.setSendToGSR(expectedGSR);
		create.setConfidential(expectedConfidential);
		create.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
		create.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
		create.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
		create.setCommentText(commentText);
		create.setCommentLevel(BaseSoapCommands.REMOVE_NODE.toString());
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
		
		 TestReporter.assertEquals(create.getFaultString(), faultString, "Verify that the fault string [" + create.getFaultString() + "] is that which is expected [" + faultString + "].");
	     validateApplicationError(create, AccommodationErrorCode.COMMENT_LEVEL_REQUIRED);
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateComments", "negative"})
	public void testCreateComments_nullRequest() {

		String faultString = "Create Comments Request Invalid : CommentRequest should be provided";
		CreateComments create = new CreateComments(environment, "Main");
		create.setRequestNodeValueByXPath("/Envelope/Body/createComments/request", BaseSoapCommands.REMOVE_NODE.toString());
		create.sendRequest();
		
		 TestReporter.assertEquals(create.getFaultString(), faultString, "Verify that the fault string [" + create.getFaultString() + "] is that which is expected [" + faultString + "].");
	     validateApplicationError(create, AccommodationErrorCode.REQUEST_REQUIRED);
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "CreateComments", "negative"})
	public void testCreateComments_nullParentId() {

		String faultString = "Create Comments Request Invalid : CommentRequest should be provided";
		String expectedIsActive = "true";
		String expectedGSR = "false";
		String expectedConfidential = "true";
		String expectedCreatedBy = "AutoJUnit.us";
		parentId = getBook().getTravelComponentId();
		CreateComments create = new CreateComments(environment, "Main");
		create.setParentIds(BaseSoapCommands.REMOVE_NODE.toString());
		create.setIsActive(expectedIsActive);
		create.setSendToGSR(expectedGSR);
		create.setConfidential(expectedConfidential);
		create.setProfileId(BaseSoapCommands.REMOVE_NODE.toString());
		create.setProfileCode(BaseSoapCommands.REMOVE_NODE.toString());
		create.setCommentId(BaseSoapCommands.REMOVE_NODE.toString());
		create.setCommentText(commentText);
		create.setCommentLevel(BaseSoapCommands.REMOVE_NODE.toString());
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
		
		 TestReporter.assertEquals(create.getFaultString(), faultString, "Verify that the fault string [" + create.getFaultString() + "] is that which is expected [" + faultString + "].");
	     validateApplicationError(create, AccommodationErrorCode.REQUEST_REQUIRED);
	}

}
