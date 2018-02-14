package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionDetail;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_REGION extends BaseTest {
    // accommodation sales request grabs data providers from party service response
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_REGION(String key, String value) {
        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("REGION");

        getOptionDetail.setOptionKeyVal(key.split(",")[0]);

        getOptionDetail.sendRequest();
        TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getOptionDetail.getFaultString(), getOptionDetail);
        TestReporter.assertTrue(getOptionDetail.getOptionKey().equals(key.split(",")[0]), "The response Option KEY [" + getOptionDetail.getOptionKey() + "] matches the PartyService getOptions key [" + key.split(",")[0] + "].");
        TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(value), "The response Option VALUE [" + getOptionDetail.getOptionValue() + "] matches the PartyService getOptions value [" + value + "].");
        TestReporter.assertAll();
    }

    // grabs the GetOptions operation from the Party Service Port and sends a request to get a key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {
        String sql = "select rgn_cd, PRMY_SUB_DIV_NM "
                + "from guest.rgn "
                + "where rgn_cd in ( "
                + "        select rgn_cd "
                + "        from( "
                + "                select count(rgn_cd ) rgnCount, rgn_cd "
                + "                from guest.rgn "
                + "                group by rgn_cd "
                + "                order by count(rgn_cd ) desc "
                + "        ) "
                + "        where  rgnCount < 2 "
                + ")";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        return db.getResultSetAsDataProvider(sql);

    }
}
