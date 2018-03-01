package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_SALESCHANNELS extends BaseTest {

    // accommodation sales request grabs data providers from the database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_SALESCHANNELS(String SLS_CHAN_ID, String SLS_CHAN_NM) {

        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("SALESCHANNELS");

        getOptionDetail.setOptionKeyVal(SLS_CHAN_ID);
        getOptionDetail.sendRequest();

        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(SLS_CHAN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database SLS_CHAN_NM [" + SLS_CHAN_NM + "].");

    }

    // grabs the GetOptions operation from the database and sends a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = " select SLS_CHAN_ID, SLS_CHAN_NM"
                + " from res_mgmt.SLS_CHAN";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        // Recordset rs;
        return db.getResultSetAsDataProvider(sql);

    }
}
