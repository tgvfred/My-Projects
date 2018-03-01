package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_INVENTORY_OVERRIDE_OVERRIDE_REASON extends BaseTest {

    Map<String, String> allPairs = new HashMap<String, String>();

    // accommodation sales request grabs data providers from the database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_INVENTORY_OVERRIDE_OVERRIDE_REASON(String TC_RSN_TYP_NM, String LGCY_RSN_CD, String TC_RSN_NM) {

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("INVENTORY_OVERRIDE_OVERRIDE_REASON");

        getOptionDetail.setOptionKeyVal(LGCY_RSN_CD);

        getOptionDetail.sendRequest();
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(TC_RSN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database TC_RSN_NM [" + TC_RSN_NM + "].");

    }

    // grabs the GetOptions operation from the the database and sends a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = " select TC_RSN_TYP_NM, LGCY_RSN_CD, TC_RSN_NM"
                + " from res_mgmt.prdf_tc_rsn"
                + " where TC_RSN_TYP_NM = 'Inventory Override'";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        return db.getResultSetAsDataProvider(sql);
    }
}