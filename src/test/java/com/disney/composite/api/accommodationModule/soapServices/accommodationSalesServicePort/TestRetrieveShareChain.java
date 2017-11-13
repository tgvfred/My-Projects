// package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;
//
// import org.testng.annotations.Test;
//
// import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveShareChain;
// import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
// import com.disney.utils.TestReporter;
//
// public class TestRetrieveShareChain extends AccommodationBaseTest {
//
// @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "retrieveShareChain", "example" })
// public void testRetrieveShareChain_MainFlow() {
// RetrieveShareChain RetrieveShareChain = new RetrieveShareChain(environment);
// RetrieveShareChain.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
// RetrieveShareChain.sendRequest();
// TestReporter.assertEquals(RetrieveShareChain.getResponseStatusCode(), "200", "The response code was not 200");
// TestReporter.assertNotNull(RetrieveShareChain.getTravelComponentId(), "The response contains a Travel Component Id");
// TestReporter.assertNotNull(RetrieveShareChain.getTravelPlanSegmentId(), "The response contains a Travel Plan Segment Id");
// }
// }