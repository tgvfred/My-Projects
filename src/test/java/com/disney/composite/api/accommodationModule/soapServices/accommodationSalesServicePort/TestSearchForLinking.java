package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.SearchForLinking;
import com.disney.utils.TestReporter;

public class TestSearchForLinking {
    private String environment = "";
    Book book = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
        book = new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");
        book.sendRequest();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "SearchForLinking", "example" })
    public void testSearchForLinking_MainFlow() {

        SearchForLinking SearchForLinking = new SearchForLinking(environment, "Main");
        SearchForLinking.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        SearchForLinking.sendRequest();
        /*
         * System.out.println(SearchForLinking.getRequest());
         * System.out.println(SearchForLinking.getResponse());
         */TestReporter.assertEquals(SearchForLinking.getResponseStatusCode(), "200", "The response code was not 200");
    }
}
