package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.replaceAllForTravelPlanSegment;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestReplaceAllForTravelPlanSegment_Negative_TPSNotUnderTP extends AccommodationBaseTest {
    private String tpPtyId = null;
    private String tpId = null;
    private String tpsId = null;
    private String tcgId = null;
    private String tcId = null;

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getNights());
        setValues(getEnvironment());
        bookReservation();
        tpId = getBook().getTravelPlanId();
        tpsId = getBook().getTravelPlanSegmentId();
        tcgId = getBook().getTravelComponentGroupingId();
        tcId = getBook().getTravelComponentId();
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "replaceAllForTravelPlanSegment" })
    public void testReplaceAllForTravelPlanSegment_Negative_TPSNotUnderTP() {
        String faultString = "Travel Plan does not match!";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        String sql = "select * from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c, res_mgmt.tc_gst d where a.tps_id = b.tps_id and b.tc_grp_nb = c.tc_grp_nb and TC_TYP_NM = 'AccommodationComponent' and rownum = 1";
        Recordset rs = new Recordset(db.getResultSet(sql));
        setSendRequest(false);
        getBook().setTravelPlanId(tpId);
        getBook().setTravelPlanSegementId(rs.getValue("TPS_ID"));
        getBook().setTravelComponentGroupingId(rs.getValue("TC_GRP_NB"));
        getBook().setTravelComponentId(rs.getValue("TC_ID"));
        getBook().setReplaceAll("true");

        getBook().sendRequest();
        validateApplicationError(getBook(), AccommodationErrorCode.INVALID_REQUEST);
        TestReporter.assertTrue(getBook().getFaultString().trim().contains(faultString.trim()), "Verify that the faultstring [" + getBook().getFaultString() + "] is that which is expected [" + faultString + "].");

    }
}
