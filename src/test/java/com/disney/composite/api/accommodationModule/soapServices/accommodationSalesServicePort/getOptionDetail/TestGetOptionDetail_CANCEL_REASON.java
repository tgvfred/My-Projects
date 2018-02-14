package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_CANCEL_REASON extends BaseTest {

    // accommodation sales request grabs data providers from database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_CANCEL_REASON(String TC_RSN_TYP_NM, String LGCY_RSN_CD, String TC_RSN_NM) {

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("CANCEL_REASON");
        getOptionDetail.setOptionKeyVal(LGCY_RSN_CD);
        getOptionDetail.sendRequest();
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(TC_RSN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database TC_RSN_NM [" + TC_RSN_NM + "].");

    }

    // grabs the GetOptions operation from the database and sends the key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = "SELECT TC_RSN_TYP_NM, LGCY_RSN_CD, TC_RSN_NM"
                + " FROM res_mgmt.prdf_tc_rsn"
                + " WHERE TC_RSN_TYP_NM = 'Cancel'";

        Database db = new OracleDatabase(environment, Database.DREAMS);

        return db.getResultSetAsDataProvider(sql);
    }
}
