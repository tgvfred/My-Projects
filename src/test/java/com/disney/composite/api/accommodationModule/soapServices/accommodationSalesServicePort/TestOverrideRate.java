package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.OverrideRate;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestOverrideRate {
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "overrideRate", "example" })
    public void testOverrideRate_MainFlow() {
        TestReporter.logScenario("Test Override Rate");
        OverrideRate OverrideRate = new OverrideRate(environment, "Main");
        OverrideRate.setTravelPlanSegementId(book.getTravelPlanSegmentId());
        OverrideRate.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        OverrideRate.sendRequest();
        TestReporter.logAPI(!OverrideRate.getResponseStatusCode().equals("200"), "An error occurred overriding rate", OverrideRate);
        TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
        TestReporter.assertNotNull(OverrideRate.gettravelComponentId(), "The response contains a Travel Component Id");
        TestReporter.assertNotNull(OverrideRate.gettravelComponentGroupingId(), "The response contains a TravelComponent GroupingI d");
    }
}