package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveComments;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveComments;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;

public class TestRetrieveComments_nullRequest extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "RetriveComments", "negative" })
    public void TestRetrieveComments_nullRequest_negative() {

        // Validate comment with a call to retrieveComments
        RetrieveComments retrieve = new RetrieveComments(environment, "Main");
        retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveComments/request", "fx:removenode");
        retrieve.sendRequest();
        TestReporter.logAPI(retrieve.getResponseStatusCode().equals("200"), "An error occurred getting comment as expected", retrieve);

        String errorXPath = "/Envelope/Body/Fault/";

        TestReporter.softAssertEquals(true, retrieve.getResponseNodeValueByXPath(errorXPath + "faultstring").contains("Unable to Retrieve Comments! : Invalid request for retrieveComments on TC"), "Validate that" + " error contains Unable to Retrieve Comments! : Invalid request for retrieveComments on TC!");

    }

}
