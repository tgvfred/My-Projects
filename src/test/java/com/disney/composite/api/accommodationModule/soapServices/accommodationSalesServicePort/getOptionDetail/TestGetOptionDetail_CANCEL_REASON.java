package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class TestGetOptionDetail_CANCEL_REASON extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_CANCEL_REASON(String TC_RSN_TYP_NM, String LGCY_RSN_CD, String TC_RSN_NM) {
        System.out.println(TC_RSN_TYP_NM + " " + LGCY_RSN_CD + " " + TC_RSN_NM);

        GetOptionDetail getOptionDetail = new GetOptionDetail(Environment.getBaseEnvironmentName(environment));
        getOptionDetail.setAccommodationSalesOptionsEnum("CANCEL_REASON");

        getOptionDetail.setOptionKeyVal(LGCY_RSN_CD);
        getOptionDetail.sendRequest();
        System.out.println(getOptionDetail.getResponse());
        System.out.println(getOptionDetail.getRequest());
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error with request", getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(TC_RSN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database TC_RSN_NM [" + TC_RSN_NM + "].");
        // TestReporter.assertTrue(getOptionDetail.getOptionKey().equals(key.split(",")[0]), "The response Option KEY [" + getOptionDetail.getOptionKey() + "] matches the PartyService getOptions key [" + key.split(",")[0] + "].");

    }

    // grabs the GetOptions operation from the Party Service Port and sends a request to get a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = "SELECT TC_RSN_TYP_NM, LGCY_RSN_CD, TC_RSN_NM"
                + " FROM res_mgmt.prdf_tc_rsn"
                + " WHERE TC_RSN_TYP_NM = 'Cancel'";

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
