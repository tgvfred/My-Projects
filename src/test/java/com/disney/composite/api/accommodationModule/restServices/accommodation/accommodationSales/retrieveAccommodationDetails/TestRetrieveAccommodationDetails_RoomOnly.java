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
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveAccommodationDetails_RoomOnly extends AccommodationBaseTest {

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

    }

    @Test(groups = { "api", "rest", "regression", "accommodation", "accommodationSales", "retrieveAccommodationDetails" })
    public void testRetrieveAccommodationDetails_RoomOnly() {

        RetrieveAccommodationDetailsRequest request = new RetrieveAccommodationDetailsRequest();

        String tp = getBook().getTravelPlanId().toString();
        long tpIdLong = Long.parseLong(tp);
        request.setTravelPlanIds(Arrays.asList(tpIdLong));

        RestResponse response = AccommodationSalesRest.accommodationSales(Environment.getBaseEnvironmentName(environment)).retrieveAccommodationDetails().sendPostRequest(request);
        System.out.println(tp);
        validateResponse(response);
        RetrieveAccommodationDetailsResponse rr = response.mapJSONToObject(RetrieveAccommodationDetailsResponse.class);

        String sql = "select a.tp_id, a.TRVL_STS_NM, a.TPS_ARVL_DT, a.TPS_DPRT_DT, c.FAC_ID"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tp_id = '" + getBook().getTravelPlanId() + "'"
                + " and c.tc_typ_nm = 'AccommodationComponent'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        TestReporter.softAssertTrue(tpId.equals(rr.getTravelPlanId()), "The travel plan id in the request [" + tpId + "] matches the travel plan in the response [" + rr.getTravelPlanId() + "]");
        // for (int i = 0; i <=rr.toString().length(); i++) {

        TestReporter.softAssertTrue(!"".equals(rr.getAccommodationDetails().get(1).getPlanTypeName()), "The plan type name is [" + rr.getAccommodationDetails().get(1).getPlanTypeName() + "]");
        TestReporter.softAssertTrue(rs.getValue("TPS_DPRT_DT").equals(rr.getAccommodationDetails().get(1).getEndDate()), "The end date is [" + rr.getAccommodationDetails().get(1).getEndDate() + "] vs the query [" + rs.getValue("TPS_DPRT_DT") + "]");
        TestReporter.softAssertTrue(rs.getValue("TPS_ARVL_DT").equals(rr.getAccommodationDetails().get(1).getStartDate()), "The start date is [" + rr.getAccommodationDetails().get(1).getStartDate() + "] vs the query [" + rs.getValue("TPS_ARVL_DT") + "]");
        TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals(rr.getAccommodationDetails().get(1).getFacilityId()), "The facility id is [" + rr.getAccommodationDetails().get(1).getFacilityId() + "] vs the query [" + rs.getValue("FAC_ID") + "]");
        TestReporter.softAssertTrue(rs.getValue("TRVL_STS_NM").equals(rr.getAccommodationDetails().get(1).getStatus()), "The status is [" + rr.getAccommodationDetails().get(1).getStatus() + "] vs the query [" + rs.getValue("TRVL_STS_NM") + "]");

        // }

    }
}