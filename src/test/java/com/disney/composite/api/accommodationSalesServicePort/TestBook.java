package com.disney.composite.api.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

public class TestBook {
	private String environment = "";
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookWithoutTickets(){
		Book book = new Book(environment, "bookWithoutTickets" );
		System.out.println(book.getRequest());
		book.sendRequest();
		System.out.println(book.getResponse());
		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	} 

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book", "debug"})
	public void testBook_BookDVCCash(){
		Book book = new Book(environment, "bookDVCCash" );
		book.sendRequest();
		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_Book2AdultsWithoutTickets(){
		Book book = new Book(environment, "book2AdultsWithoutTickets" );
		book.sendRequest();

		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookGroupPackageWithoutTickets(){
		Book book = new Book(environment, "bookGroupPackageWithoutTickets" );
		book.sendRequest();
		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookWDTC2Adults2ChildrenWithoutTickets(){
		Book book = new Book(environment, "bookWDTC2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookRoomOnly2Adults2ChildrenWithoutTicketss(){
		Book book = new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" );
		book.sendRequest();
		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookWholesale2AdultsWithoutTickets(){
		Book book = new Book(environment, "bookWholesale2AdultsWithoutTickets" );
		book.sendRequest();
		TestReporter.assertEquals(book.getResponseStatusCode(), "200", "The response code was not 200");
		TestReporter.assertNotNull(book.getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}
	
	
	
}
