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
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_RATE_OVERRIDE_REASON extends AccommodationBaseTest {

    Map<String, String> allPairs = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    // accommodation sales request grabs data providers from the database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_INVENTORY_OVERRIDE_OVERRIDE_REASON(String TC_RSN_TYP_NM, String LGCY_RSN_CD, String TC_RSN_NM) {
        System.out.println(TC_RSN_TYP_NM + " " + LGCY_RSN_CD + " " + TC_RSN_NM);

        GetOptionDetail getOptionDetail = new GetOptionDetail(Environment.getBaseEnvironmentName(environment));
        getOptionDetail.setAccommodationSalesOptionsEnum("RATE_OVERRIDE_REASON");

        getOptionDetail.setOptionKeyVal(LGCY_RSN_CD);

        if (LGCY_RSN_CD.equals(null)) {
            throw new SQLValidationException("No records found for tp ID [ " + tpId + " ]");

        } else {

            getOptionDetail.sendRequest();
            // System.out.println(getOptionDetail.getResponse());
            // System.out.println(getOptionDetail.getRequest());
            TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", getOptionDetail);
            TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(TC_RSN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database TC_RSN_NM [" + TC_RSN_NM + "].");
        }
    }

    // grabs the GetOptions operation from the databse and sends a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = " select TC_RSN_TYP_NM, LGCY_RSN_CD, TC_RSN_NM"
                + " from res_mgmt.prdf_tc_rsn"
                + " where TC_RSN_TYP_NM = 'Rate Override'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        // Recordset rs;
        Object[][] rs = db.getResultSet(sql);

        try {
            List<Object[]> l = new ArrayList<Object[]>(Arrays.asList(rs));
            l.remove(0);

            return l.toArray(new Object[][] {});
        } catch (Exception e) {

            throw new SQLValidationException("No records found for tp ID [ " + tpId + " ]");

        }
    }

}
