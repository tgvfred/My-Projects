package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.RemoveComments;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.UpdateComments;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRemoveCommets {
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
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RemoveComments", "debug"})
	public void testRemoveComments_MainFlow(){
		
		RemoveComments RemoveComments = new RemoveComments(environment, "Main");
		RemoveComments.setparentIds(book.getTravelComponentId());
		RemoveComments.setcommentText(Randomness.randomString(5));
		RemoveComments.sendRequest();
		//System.out.println(RemoveComments.getRequest());
		//System.out.println(RemoveComments.getResponse());
		TestReporter.assertEquals(RemoveComments.getResponseStatusCode(), "200", "The response code was not 200");
		
	}
}