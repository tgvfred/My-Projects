package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveCancellationPolicy;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveCancellationPolicy;
import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;

public class TestRetrieveCancellationPolicy_negative extends AccommodationBaseTest {

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_nullRequest() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setRequestNodeValueByXPath("/Envelope/Body/retrieveCancellationPolicy/request", BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_nullTcg() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_negativeTcg() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId("-1");
        retrieve.setTravelComponentGroupingId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_nullTps() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(BaseSoapCommands.REMOVE_NODE.toString());
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_negativeTps() {

        String faultString = "Required parameters are missing : null";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId("-1");
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.REQUIRED_PARAMETERS_MISSING);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_invalidTps() {

        String faultString = "Travel Plan Segment Should not be NULL";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId("5555555");
        retrieve.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.TRVL_PLAN_SGMT_CANNOT_BE_NULL);

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveCancellationPolicy", "negative" })
    public void testRetrieveCancellationPolicy_invalidTcg() {

        String faultString = "Accommodations not found";

        RetrieveCancellationPolicy retrieve = new RetrieveCancellationPolicy(environment, "Main");
        retrieve.setTravelPlanSegmentId(getBook().getTravelPlanSegmentId());
        retrieve.setTravelComponentGroupingId("555555");
        retrieve.sendRequest();

        TestReporter.assertTrue(retrieve.getFaultString().replaceAll("\\s", "").contains(faultString.replaceAll("\\s", "")), "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
        validateApplicationError(retrieve, AccommodationErrorCode.ACCOMMODATIONS_NOT_FOUND);

    }

}
