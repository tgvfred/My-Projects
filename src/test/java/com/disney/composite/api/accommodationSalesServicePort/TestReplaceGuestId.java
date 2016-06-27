package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.ReplaceGuestId;
import com.disney.utils.TestReporter;

public class TestReplaceGuestId {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "replaceGuestId"})
	public void testReplaceGuestId_MainFlow(){
		ReplaceGuestId ReplaceGuestId= new ReplaceGuestId(environment, "Main" );
	    ReplaceGuestId.setguestId(book.getGuestId());
		ReplaceGuestId.sendRequest();
	    System.out.println(ReplaceGuestId.getRequest());
	    System.out.println(ReplaceGuestId.getResponse());
		TestReporter.assertEquals(ReplaceGuestId.getResponseStatusCode(), "200", "The response code was not 200");
	}
}