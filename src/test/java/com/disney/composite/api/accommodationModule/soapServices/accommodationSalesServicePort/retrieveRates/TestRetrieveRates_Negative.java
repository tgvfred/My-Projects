package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveRates;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Cancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveRates;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;

public class TestRetrieveRates_Negative extends AccommodationBaseTest {
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

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates" })
    public void TestRetrieveRates_nullTcg() {
        String faultString = "Required parameters are missing : Invalid Travel Component grouping Id#0";

        TestReporter.logScenario("Test Retrieve Rates Null TCG");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieveRates.sendRequest();

        TestReporter.assertTrue(retrieveRates.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieveRates.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieveRates, AccommodationErrorCode.REQ_PARAM_MISSING);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveRates", "" })
    public void TestRetrieveRates_showDiningReservation() {
        String faultString = "Accommodation Component not found : NO ACCOMMODATION FOUND WITH ID#471952101120";
        String tcgId = "471952101120";

        /*
         * String tcgId = getBook().getTravelComponentGroupingId();
         * String faultString = " Accommodation Component not found : NO ACCOMMODATION FOUND WITH ID#" + tcgId;
         */

        TestReporter.logScenario("Dinning reservation rates");
        RetrieveRates retrieveRates = new RetrieveRates(environment, "retrieveRates");
        retrieveRates.setTravelComponentGroupingId(tcgId);
        retrieveRates.sendRequest();

        TestReporter.assertTrue(retrieveRates.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieveRates.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieveRates, AccommodationErrorCode.ACCOMMODATION_COMPONENT_NOT_FOUND);

    }
}
