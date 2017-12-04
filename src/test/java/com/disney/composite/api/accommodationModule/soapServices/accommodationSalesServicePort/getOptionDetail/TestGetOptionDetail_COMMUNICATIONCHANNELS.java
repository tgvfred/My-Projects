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

public class TestGetOptionDetail_COMMUNICATIONCHANNELS extends AccommodationBaseTest {

    Map<String, String> allPairs = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    // accommodation sales request grabs data providers from database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_COMMUNICATIONCHANNELS(String COMNCTN_CHAN_ID, String COMNCTN_CHAN_NM) {

        GetOptionDetail getOptionDetail = new GetOptionDetail(Environment.getBaseEnvironmentName(environment));
        getOptionDetail.setAccommodationSalesOptionsEnum("COMMUNICATIONCHANNELS");

        getOptionDetail.setOptionKeyVal(COMNCTN_CHAN_ID);
        if (COMNCTN_CHAN_ID.equals(null)) {
            throw new SQLValidationException("No records found for tp ID [ " + tpId + " ]");

        } else {

            getOptionDetail.sendRequest();

            TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", getOptionDetail);
            TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(COMNCTN_CHAN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database COMNCTN_CHAN_NM [" + COMNCTN_CHAN_NM + "].");

        }
    }

    // grabs the GetOptions operation from the database and sends the key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = " select COMNCTN_CHAN_ID, COMNCTN_CHAN_NM"
                + " from res_mgmt.comnctn_chan";

        Database db = new OracleDatabase(environment, Database.DREAMS);

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
