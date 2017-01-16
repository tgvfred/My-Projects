package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UpdateComments;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestUpdateComments {
	private String environment = "";
    Book book = null;
    @BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		//System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "updatecomments"})
	public void testUpdateComments_MainFlow(){
		
		UpdateComments UpdateCommets = new UpdateComments(environment, "updatecomments");
		UpdateCommets.setparentIds(book.getTravelComponentId());
		UpdateCommets.setcommentText(Randomness.randomString(5));
		UpdateCommets.settcId(book.getTravelComponentId());
		UpdateCommets.settpId(book.getTravelPlanId());
		UpdateCommets.settpsId(book.getTravelPlanSegmentId());
		UpdateCommets.sendRequest();
		TestReporter.assertEquals(UpdateCommets.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(UpdateCommets.getcommentId(), "The response contains a CommentID");
		TestReporter.assertNotNull(UpdateCommets.getcommentLevel(), "The response contains a Comment Level");
		
	}
}
