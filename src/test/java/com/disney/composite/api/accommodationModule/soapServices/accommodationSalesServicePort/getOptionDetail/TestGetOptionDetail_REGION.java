package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionDetail;

import java.util.HashMap;
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
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionDetail_REGION extends AccommodationBaseTest {
    Map<String, String> allPairs = new HashMap<String, String>();

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);

        isComo.set("false");

    }

    // accommodation sales request grabs data providers from party service response
    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionDetail" }, dataProvider = "dp")
    public void testGetOptionDetail_REGION(String key, String value) {
        GetOptionDetail getOptionDetail = new GetOptionDetail(environment);
        getOptionDetail.setAccommodationSalesOptionsEnum("REGION");

        getOptionDetail.setOptionKeyVal(key.split(",")[0]);

        if (key.split(",")[0].equals(null)) {
            throw new SQLValidationException("No records found for tp ID [ " + tpId + " ]");

        } else {

            getOptionDetail.sendRequest();
            TestReporter.logAPI(!getOptionDetail.getResponseStatusCode().equals("200"), "An error occurred getting option details: " + getOptionDetail.getFaultString(), getOptionDetail);
            TestReporter.assertTrue(getOptionDetail.getOptionKey().equals(key.split(",")[0]), "The response Option KEY [" + getOptionDetail.getOptionKey() + "] matches the PartyService getOptions key [" + key.split(",")[0] + "].");
            TestReporter.assertTrue(getOptionDetail.getOptionValue().equals(value), "The response Option VALUE [" + getOptionDetail.getOptionValue() + "] matches the PartyService getOptions value [" + value + "].");
            TestReporter.assertAll();
        }
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
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("No charges found for tp ID [ " + tpId + " ]", sql);
        } else {

            Object[][] objKeyValue = new Object[rs.getRowCount()][2];
            int i = 0;
            do {
                objKeyValue[i][0] = rs.getValue("RGN_CD");
                objKeyValue[i][1] = rs.getValue("PRMY_SUB_DIV_NM");
                rs.moveNext();
                i++;
            } while (rs.hasNext());

            return objKeyValue;
        }
    }
}
