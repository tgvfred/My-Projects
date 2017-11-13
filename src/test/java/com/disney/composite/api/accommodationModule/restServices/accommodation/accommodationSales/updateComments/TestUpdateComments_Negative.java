package com.disney.composite.api.accommodationModule.restServices.accommodation.accommodationSales.updateComments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.AccommodationSalesRest;
import com.disney.api.restServices.BaseRestTest;
import com.disney.api.restServices.accommodationSales.updateComments.request.UpdateCommentsRequest;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Book;
import com.disney.utils.TestReporter;

@SuppressWarnings("unused")
public class TestUpdateComments_Negative extends BaseRestTest {
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
    @Test(groups = { "api", "rest", "regression", "negative", "accommodation", "accommodationSales", "updateComments", "example" })
    public void testupdateComments_Negative_NoAuthorization() throws IOException {

        // set log levels for debugging
        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(TCId);
        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel("TC");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");
        RestResponse response = AccommodationSalesRest.accommodationSales(environment).updateComments().sendPutRequestWithMissingAuthToken(request);
        validateResponse(response, "401");
    }

    @Test(groups = { "api", "rest", "regression", "negative", "accommodation", "accommodationSales", "updateComments", "example" })
    public void testupdateComments_Negative_NoTC() throws IOException {

        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(" ");
        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel("TC");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");
        RestResponse response = AccommodationSalesRest.accommodationSales(environment).updateComments().sendPutRequest(request);
        validateResponse(response, "500");
        TestReporter.assertTrue(response.getResponse().contains("The given id must not be null!"), "The response received the proper error of The given id must not be null!");
    }

    @Test(groups = { "api", "rest", "regression", "negative", "accommodation", "accommodationSales", "updateComments", "example" })
    public void testupdateComments_Negative_NoCommentLevel() throws IOException {

        // Create new request
        UpdateCommentsRequest request = new UpdateCommentsRequest();

        // Populate new Request
        List<String> params = new ArrayList<String>();
        params.add(TCId);
        request.setParentIds(params);
        request.getCommentsInfo().get(0).setProfileCode("FL02");
        request.getCommentsInfo().get(0).setCommentLevel(" ");
        request.getCommentsInfo().get(0).setCommentText("eServiceTestComment");
        RestResponse response = AccommodationSalesRest.accommodationSales(environment).updateComments().sendPutRequest(request);
        validateResponse(response, "500");
        TestReporter.assertTrue(response.getResponse().contains("Can not construct instance of com.wdw.dreams.booking.transferobject.enums.CommentLevelEnum"), "The response received the proper error of Can not construct instance of com.wdw.dreams.booking.transferobject.enums.CommentLevelEnum");
    }

}
