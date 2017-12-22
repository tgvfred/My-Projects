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
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveAccommodationDetails_RoomOnly extends AccommodationBaseTest {
    String tp;

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

        tp = getBook().getTravelPlanId().toString();
        long tpIdLong = Long.parseLong(tp);
        request.setTravelPlanIds(Arrays.asList(tpIdLong));

        RestResponse response = AccommodationSalesRest.accommodationSales(environment).retrieveAccommodationDetails().sendPostRequest(request);
        System.out.println(tp);
        validateResponse(response);
        RetrieveAccommodationDetailsResponse[] rr = response.mapJSONToObject(RetrieveAccommodationDetailsResponse[].class);

        String sql = "select a.tp_id, a.TRVL_STS_NM, a.TPS_ARVL_DT, a.TPS_DPRT_DT, c.FAC_ID"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tp_id = '" + tp + "'"
                + " and c.tc_typ_nm = 'AccommodationComponent'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));
        rs.print();
        for (int i = 0; i <= rr.length - 1; i++) {
            TestReporter.softAssertTrue(tp.equals(rr[i].getTravelPlanId().toString()), "The travel plan id in the request [" + tp + "] matches the travel plan in the response [" + rr[i].getTravelPlanId() + "]");

            TestReporter.softAssertTrue(!"".equals(rr[i].getAccommodationDetails().get(0).getPackagePlanType()), "The package plan type name is [" + rr[i].getAccommodationDetails().get(0).getPackagePlanType() + "]");
            TestReporter.softAssertTrue(rs.getValue("TPS_DPRT_DT").substring(0, 9).equalsIgnoreCase(rr[i].getAccommodationDetails().get(0).getDepartureDate().substring(0, 9)), "The end date is [" + rr[i].getAccommodationDetails().get(0).getDepartureDate() + "] vs the query [" + rs.getValue("TPS_DPRT_DT") + "]");
            TestReporter.softAssertTrue(rs.getValue("TPS_ARVL_DT").substring(0, 9).equals(rr[i].getAccommodationDetails().get(0).getArrivalDate().substring(0, 9)), "The start date is [" + rr[i].getAccommodationDetails().get(0).getArrivalDate() + "] vs the query [" + rs.getValue("TPS_ARVL_DT") + "]");
            TestReporter.softAssertTrue(rs.getValue("FAC_ID").equals(rr[i].getAccommodationDetails().get(0).getFacilityId().toString()), "The facility id is [" + rr[i].getAccommodationDetails().get(0).getFacilityId() + "] vs the query [" + rs.getValue("FAC_ID") + "]");
            TestReporter.softAssertTrue(rs.getValue("TRVL_STS_NM").equals(rr[i].getAccommodationDetails().get(0).getStatus()), "The status is [" + rr[i].getAccommodationDetails().get(0).getStatus() + "] vs the query [" + rs.getValue("TRVL_STS_NM") + "]");

        }

    }
}