package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationSalesServicePort.operations.BookForShare;
import com.disney.utils.TestReporter;

public class TestBookForShare {
	private String environment = "";
	Book book = null;
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment" })
	public void setup(String environment) {
		this.environment = environment;
		book= new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		//System.out.println(book.getResponse());
	}
		
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "bookForShare"})
	public void testBookForShare_MainFlow(){
	    BookForShare BookForShare = new BookForShare(environment, "Main" );
	    BookForShare.sendRequest();
		//System.out.println(BookForShare.getRequest());
		//System.out.println(BookForShare.getResponse());
		TestReporter.assertEquals(BookForShare.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(BookForShare.getlocationId(),  "The response contains a Location Id");
		TestReporter.assertNotNull(BookForShare.getshared(),  "The response contains a Shared");
	}
}