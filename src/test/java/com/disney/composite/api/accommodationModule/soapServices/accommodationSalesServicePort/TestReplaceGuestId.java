package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ReplaceGuestId;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestReplaceGuestId {
    private String environment = "";
    private Book book = null;

    @BeforeMethod(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(String environment) {
        this.environment = environment;
        book = new Book(environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");
        book.sendRequest();
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        try {
            if (book != null) {
                if (book.getTravelPlanSegmentId() != null) {
                    if (!book.getTravelPlanSegmentId().isEmpty()) {
                        Cancel cancel = new Cancel(environment, "Main");
                        cancel.setCancelDate(Randomness.generateCurrentXMLDate(0));
                        cancel.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
                        cancel.sendRequest();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceGuestId", "example" })
    public void testReplaceGuestId_MainFlow() {
        TestReporter.logScenario("Test Replace Guest ID");
        ReplaceGuestId ReplaceGuestId = new ReplaceGuestId(environment, "Main");
        ReplaceGuestId.setguestId(book.getGuestId());
        ReplaceGuestId.sendRequest();
        TestReporter.logAPI(!book.getResponseStatusCode().equals("200"), "An error occurred replacing guest id", book);
        TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
    }
}