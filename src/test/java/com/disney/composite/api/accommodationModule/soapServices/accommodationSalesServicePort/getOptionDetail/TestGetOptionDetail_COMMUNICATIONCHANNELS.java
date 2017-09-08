package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_COMMUNICATIONCHANNELS extends AccommodationBaseTest {

    Map<String, String> allPairs = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_COMMUNICATIONCHANNELS(String COMNCTN_CHAN_ID, String COMNCTN_CHAN_NM) {
        System.out.println(COMNCTN_CHAN_ID + " " + COMNCTN_CHAN_NM);

        GetOptionDetail getOptionDetail = new GetOptionDetail(Environment.getBaseEnvironmentName(environment));
        getOptionDetail.setAccommodationSalesOptionsEnum("COMMUNICATIONCHANNELS");

        getOptionDetail.setOptionKeyVal(COMNCTN_CHAN_ID);
        getOptionDetail.sendRequest();
        System.out.println(getOptionDetail.getResponse());
        System.out.println(getOptionDetail.getRequest());
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error with request", getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(COMNCTN_CHAN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database COMNCTN_CHAN_NM [" + COMNCTN_CHAN_NM + "].");

    }

    // grabs the GetOptions operation from the Party Service Port and sends a request to get a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = " select COMNCTN_CHAN_ID, COMNCTN_CHAN_NM"
                + " from res_mgmt.comnctn_chan";

        // String sql = "select LGCY_RSN_CD"
        // + "from res_mgmt.prdf_tc_rsn"
        // + "where TC_RSN_TYP_NM = 'Cancel'";
        Database db = new OracleDatabase(environment, Database.DREAMS);
        // Recordset rs;
        Object[][] rs = db.getResultSet(sql);

        // TestReporter.softAssertEquals(RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID"), parentId, "Verify that the RES_MGMT_VAIDATE data [ " + RES_MGMT_REQ_VALIDATE_rs.getValue("TC_ID") + "] matches the comment data [ " + parentId + "]");

        // System.out.println(rs);

        // Object[][] objKeyValue = rs.getValue("LGCY_RSN_CD")[];

        List<Object[]> l = new ArrayList<Object[]>(Arrays.asList(rs));
        l.remove(0);

        return l.toArray(new Object[][] {});

    }
}
