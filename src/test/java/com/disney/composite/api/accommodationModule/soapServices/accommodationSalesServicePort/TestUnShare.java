package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.UnShare;
import com.disney.utils.TestReporter;

public class TestUnShare {
    private String environment = "";
    Book book = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
        book = new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");

        book.sendRequest();
        // System.out.println(book.getRequest());
        // System.out.println(book.getResponse());
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "unShare", "example" })
    public void testUnShare_MainFlow() {

        UnShare UnShare = new UnShare(environment, "Main");
        UnShare.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        UnShare.sendRequest();
        // System.out.println(UnShare.getRequest());
        // System.out.println(UnShare.getResponse());
        TestReporter.assertEquals(UnShare.getResponseStatusCode(), "200", "The response code was not 200");
        TestReporter.assertNotNull(UnShare.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment ID");
        TestReporter.assertNotNull(UnShare.getTravelComponentId(), "The response contains a Travel Plan Component ID");
    }
}
