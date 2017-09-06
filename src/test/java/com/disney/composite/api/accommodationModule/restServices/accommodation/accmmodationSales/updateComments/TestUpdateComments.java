package com.disney.composite.api.accommodationModule.restServices.accommodation.accmmodationSales.updateComments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.BaseRestTest;
import com.disney.api.restServices.Rest;
import com.disney.api.restServices.accommodation.accommodationSales.updateComments.request.UpdateCommentsRequest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestUpdateComments extends BaseRestTest {
    private String environment;
    private String TPId;
    private String TPSId;
    private String TCId;
    private String TCId2;

    /**
     * This will always be used as is. TestNG will pass in the Environment used
     * 
     * @param environment
     *            - Valid environments for active testing are bashful, sleepy and grumpy
     */
    @BeforeClass(alwaysRun = true)
    @Parameters({ "environment" })
    public void setup(@Optional String environment) {
        TestReporter.setDebugLevel(TestReporter.INFO);
        this.environment = environment;

        // generate accommodation booking
        Book book = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");
        book.setPackageCode("I795T");
        book.sendRequest();
        // grab relevant information
        TPId = book.getTravelPlanId();
        TPSId = book.getTravelPlanSegmentId();
        TCId = book.getTravelComponentId();
    }

    /**
     * This is a sample template
     * Expected updates
     * - serviceClusterName - The cluster of services this service falls under (ie. Accommodation, Folio, RIM, GoMaster)
     * - serviceName - name of the service
     * - operationName - name of the operation
     * - OperationName - name of the operation
     * - DataScenario - data scenario used, data sheets can contain multiple scenarios.
     */
    @Test(groups = { "api", "rest", "regression", "accommodation", "accommodationSales", "updateComments", "smoke", "example" })
    public void testupdateComments_SingleTC() throws IOException {

        // set log levels for debugging
        TestReporter.setDebugLevel(TestReporter.INFO);
        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(TCId);
        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel("TC");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");
        RestResponse response = Rest.accommodation(environment).accoomodationSales().updateComments().sendPutRequest(request);
        validateResponse(response);
        TestReporter.assertTrue(response.getResponse().contains("eServiceTestComment"), "The response created the comment and it is present: eServiceTestComment");
    }

    @Test(groups = { "api", "rest", "regression", "accommodation", "accommodationSales", "updateComments", "example" })
    public void testupdateComments_DoubleComment() throws IOException {

        // set log levels for debugging
        TestReporter.setDebugLevel(TestReporter.INFO);
        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(TCId);

        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel("TC");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");
        request.addCommentsInfo();
        request.getCommentsInfo().get(1).setProfileCode("CRNRM");
        request.getCommentsInfo().get(1).setCommentLevel("TC");
        request.getCommentsInfo().get(1).setCommentText("eServiceTestComment2");
        RestResponse response = Rest.accommodation(environment).accoomodationSales().updateComments().sendPutRequest(request);
        validateResponse(response);
        TestReporter.assertTrue(response.getResponse().contains("eServiceTestComment2"), "The response created the comment and it is present: eServiceTestComment");
    }

    @Test(groups = { "api", "rest", "regression", "accommodation", "accommodationSales", "updateComments", "example" })
    public void testupdateComments_DoubleTCAndComment() throws IOException {
        // generate accommodation booking
        Book book2 = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");
        book2.sendRequest();
        // grab relevant information
        TCId2 = book2.getTravelComponentId();

        // set log levels for debugging
        TestReporter.setDebugLevel(TestReporter.INFO);
        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(TCId);
        params.add(TCId2);
        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel("TC");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");
        request.addCommentsInfo();
        request.getCommentsInfo().get(1).setProfileCode("CRNRM");
        request.getCommentsInfo().get(1).setCommentLevel("TC");
        request.getCommentsInfo().get(1).setCommentText("eServiceTestComment2");
        RestResponse response = Rest.accommodation(environment).accoomodationSales().updateComments().sendPutRequest(request);
        validateResponse(response);
        TestReporter.assertTrue(response.getResponse().contains("eServiceTestComment2"), "The response created the comment and it is present: eServiceTestComment");
    }

    @Test(groups = { "api", "rest", "regression", "accommodation", "accommodationSales", "updateComments", "example" })
    public void testupdateComments_DoubleTCAndSingleComment() throws IOException {
        // generate accommodation booking
        Book book2 = new Book(this.environment, "bookRoomOnly2Adults2ChildrenWithoutTickets");
        book2.sendRequest();
        // grab relevant information
        TCId2 = book2.getTravelComponentId();

        // set log levels for debugging
        TestReporter.setDebugLevel(TestReporter.INFO);
        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(TCId);
        params.add(TCId2);
        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel("TC");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");

        RestResponse response = Rest.accommodation(environment).accoomodationSales().updateComments().sendPutRequest(request);
        validateResponse(response);
        TestReporter.assertTrue(response.getResponse().contains("eServiceTestComment"), "The response created the comment and it is present: eServiceTestComment");
    }
}
