package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.ModifyWithPrice;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestModifyWithPrice {
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "modifyWithPrice", "example" })
    public void ModifyWithPrice_MainFlow() {
        TestReporter.logScenario("Test Modify with Price");
        ModifyWithPrice ModifyWithPrice = new ModifyWithPrice(environment, "Main");
        ModifyWithPrice.setTravelPlanSegementId(book.getTravelPlanSegmentId());
        ModifyWithPrice.setTravelComponentId(book.getTravelComponentId());
        ModifyWithPrice.setTravelComponentGroupingId(book.getTravelComponentGroupingId());
        ModifyWithPrice.setTravelPlanId(book.getTravelPlanId());
        ModifyWithPrice.sendRequest();
        TestReporter.logAPI(!ModifyWithPrice.getResponseStatusCode().equals("200"), "An error occurred modifying with price", ModifyWithPrice);
        TestReporter.log("Travel Plan ID: " + book.getTravelPlanId());
        TestReporter.assertNotNull(ModifyWithPrice.getTravelComponentGroupingId(), "The response contains a Travel Component Grouping ID");
        TestReporter.assertNotNull(ModifyWithPrice.getTravelComponentId(), "The response contains a Travel Component ID");
    }
}