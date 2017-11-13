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
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_CANCEL_REASON extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    // accommodation sales request grabs data providers from database
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_CANCEL_REASON(String TC_RSN_TYP_NM, String LGCY_RSN_CD, String TC_RSN_NM) {
        System.out.println(TC_RSN_TYP_NM + " " + LGCY_RSN_CD + " " + TC_RSN_NM);

        GetOptionDetail getOptionDetail = new GetOptionDetail(Environment.getBaseEnvironmentName(environment));
        getOptionDetail.setAccommodationSalesOptionsEnum("CANCEL_REASON");
        getOptionDetail.setOptionKeyVal(LGCY_RSN_CD);

        if (LGCY_RSN_CD.equals(null)) {
            throw new SQLValidationException("No records found for tp ID [ " + tpId + " ]");

        } else {

            getOptionDetail.sendRequest();
            TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "Error in the request. Response status code not 200.", getOptionDetail);
            TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(TC_RSN_NM), "The response Option Value [" + getOptionDetail.getOptionValue() + "] matches the database TC_RSN_NM [" + TC_RSN_NM + "].");
        }
    }

    // grabs the GetOptions operation from the database and sends the key and value pair
    @DataProvider(name = "dp", parallel = true)
    public Object[][] OptionKV() {

        String sql = "SELECT TC_RSN_TYP_NM, LGCY_RSN_CD, TC_RSN_NM"
                + " FROM res_mgmt.prdf_tc_rsn"
                + " WHERE TC_RSN_TYP_NM = 'Cancel'";

        Database db = new OracleDatabase(environment, Database.DREAMS);

        Object[][] rs = db.getResultSet(sql);
        try {

            List<Object[]> l = new ArrayList<Object[]>(Arrays.asList(rs));
            l.remove(0);

            return l.toArray(new Object[][] {});
        } catch (Exception e) {

            throw new SQLValidationException("No charges found for tp ID [ " + tpId + " ]");

        }

    }
}
