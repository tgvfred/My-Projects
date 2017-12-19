package com.disney.composite.api.accommodationModule.restServices.accommodation.accommodationSales.retrieveAccommodationDetails;

import java.util.Arrays;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.restServices.AccommodationSalesRest;
import com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.request.RetrieveAccommodationDetailsRequest;
import com.disney.api.restServices.accommodationSales.retrieveAccommodationDetails.response.RetrieveAccommodationDetailsResponse;
import com.disney.api.restServices.core.RestResponse;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;

public class TestRetrieveAccommodationDetails_multipleBookings extends AccommodationBaseTest {
    String tpId;
    String tpId2;
    String tcg;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(environment);
        bookReservation();

        tpId = getBook().getTravelPlanId();
        tcg = getBook().getTravelComponentGroupingId();
        setSendRequest(false);
        bookReservation();

        getBook().setTravelComponentGroupingId(tcg);
        getBook().sendRequest();
        tpId2 = getBook().getTravelPlanId();
        TestReporter.logAPI(!getBook().getResponseStatusCode().equals("200"), "Verify that no error occurred booking a second TPS: " + getBook().getFaultString(), getBook());

    }

    @Test(groups = { "api", "rest", "regression", "accommodation", "accommodationSales", "retrieveAccommodationDetails" })
    public void testRetrieveAccommodationDetails_multipleBookings() {

        RetrieveAccommodationDetailsRequest request = new RetrieveAccommodationDetailsRequest();

        long tpIdLong = Long.parseLong(tpId);
        long tpIdLong2 = Long.parseLong(tpId2);
        request.setTravelPlanIds(Arrays.asList(tpIdLong, tpIdLong2));

        // request.setTravelPlanIds(Arrays.asList(travelPlanInt, travelPlanInt2));

        RestResponse response = AccommodationSalesRest.accommodationSales(Environment.getBaseEnvironmentName(getEnvironment())).retrieveAccommodationDetails().sendPostRequest(request);
        validateResponse(response);
        RetrieveAccommodationDetailsResponse rr = response.mapJSONToObject(RetrieveAccommodationDetailsResponse.class);
        TestReporter.softAssertTrue(tpId.equals(rr.getTravelPlanId()), "The travel plan id in the request [" + tpId + "] matches the travel plan in the response [" + rr.getTravelPlanId() + "]");
        TestReporter.softAssertTrue(tpId2.equals(rr.getTravelPlanId()), "The travel plan id in the request [" + tpId2 + "] matches the travel plan in the response [" + rr.getTravelPlanId() + "]");

        // for (int i = 0; i <=rr.toString().length(); i++) {

        TestReporter.softAssertTrue(!"".equals(rr.getAccommodationDetails().get(1).getPlanTypeName()), "The plan type name is [" + rr.getAccommodationDetails().get(1).getPlanTypeName() + "]");
        TestReporter.softAssertTrue(!"".equals(rr.getAccommodationDetails().get(1).getEndDate()), "The end date is [" + rr.getAccommodationDetails().get(1).getEndDate() + "]");
        TestReporter.softAssertTrue(!"".equals(rr.getAccommodationDetails().get(1).getStartDate()), "The start date is [" + rr.getAccommodationDetails().get(1).getStartDate() + "]");
        TestReporter.softAssertTrue(!"".equals(rr.getAccommodationDetails().get(1).getFacilityId()), "The facility id is [" + rr.getAccommodationDetails().get(1).getFacilityId() + "]");
        TestReporter.softAssertTrue(!(rr.getAccommodationDetails().get(1).getStatus()).equals(""), "The status is [" + rr.getAccommodationDetails().get(1).getStatus() + "]");

    }
}
