package com.disney.composite.api.accommodationModule.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestBook {
	private String environment = "";
	private ThreadLocal<Book> book = new ThreadLocal<Book>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"environment"})
	public void setup(String environment) {this.environment = environment;}
	
	@AfterMethod(alwaysRun=true)
	public void teardown(){
		try{
			if(book != null){
				if(book.get().getTravelPlanSegmentId() != null){
					if(!book.get().getTravelPlanSegmentId().isEmpty()){
						Cancel cancel = new Cancel(environment, "Main");
						cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
						cancel.setTravelComponentGroupingId(book.get().getTravelComponentGroupingId());
						cancel.sendRequest();
					}
				}
			}
		}catch(Exception e){}
	}
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookWithoutTickets(){
		TestReporter.logScenario("Test Book Without Tickets");
		book.set(new Book(environment, "bookWithoutTickets" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a room only reservation without tickets", book.get());
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	} 

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book", "debug"})
	public void testBook_BookDVCCash(){
		TestReporter.logScenario("Test Book DVC Cash");
		book.set(new Book(environment, "bookDVCCash" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a DVC Cash reservation", book.get());	
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_Book2AdultsWithoutTickets(){
		TestReporter.logScenario("Test Book 2 Adults Without Tickets");
		book.set(new Book(environment, "book2AdultsWithoutTickets" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a room only reservation for 2 adults without tickets", book.get());
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookGroupPackageWithoutTickets(){
		TestReporter.logScenario("Test Book Group Package Without Tickets");
		book.set(new Book(environment, "bookGroupPackageWithoutTickets" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a group package reservation without tickets", book.get());
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookWDTC2Adults2ChildrenWithoutTickets(){
		TestReporter.logScenario("Test Book WDTC 2 Adults 2 Children Without Tickets");
		book.set(new Book(environment, "bookWDTC2Adults2ChildrenWithoutTickets" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a WDTC reservation for 2 adults and 2 children without tickets", book.get());
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookRoomOnly2Adults2ChildrenWithoutTickets(){
		TestReporter.logScenario("Test Book Room Only 2 Adults 2 Children Without Tickets");
		book.set(new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a room only reservation for 2 adults and 2 children without tickets", book.get());
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "book"})
	public void testBook_BookWholesale2AdultsWithoutTickets(){
		TestReporter.logScenario("Test Book Wholesale 2 Adults Without Tickets");
		book.set(new Book(environment, "bookWholesale2AdultsWithoutTickets" ));
		book.get().sendRequest();
		TestReporter.logAPI(!book.get().getResponseStatusCode().equals("200"), "An error occurred booking a wholesale reservation for 2 adults without tickets", book.get());
	    TestReporter.log("Travel Plan ID: " + book.get().getTravelPlanId());
		TestReporter.assertNotNull(book.get().getTravelPlanId(), "The response contains a Travel Plan ID");
		TestReporter.assertNotNull(book.get().getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
	}	
}