package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptions;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptions;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptions_Positive extends BaseTest {
    private static OracleDatabase db;

    @BeforeSuite
    @Parameters("environment")
    public static void beforeSuite(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testGetOptions_COUNTRY() {
        validateSpecialEnvironment("COUNTRY", "getOptions_Country");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testGetOptions_LANGUAGE() {
        validateSpecialEnvironment("LANGUAGE", "getOptions_Language");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testGetOptions_DEVICE_TYPE() {
        validateSpecialEnvironment("DEVICE_TYPE", "getOptions_Device");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testGetOptions_LOCATOR_USE_TYPE() {
        validateSpecialEnvironment("LOCATOR_USE_TYPE", "getOptions_Locator");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_SUFFIX() {
        validateSpecialEnvironment("SUFFIX", "getOptions_Suffix");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_SALUTATION() {
        validateSpecialEnvironment("SALUTATION", "getOptions_Salutation");
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_REGION() {
        // Only exists in Latest, so Response Node Validation is skipped
        validateSpecialEnvironment("REGION", "getOptions_Region");

        TestReporter.logScenario("Test - Get Options - REGION");

        GetOptions getOptions = new GetOptions(environment);
        getOptions.setOptionEnum("REGION");

        getOptions.sendRequest();
        TestReporter.logAPI(!getOptions.getResponseStatusCode().equals("200"), getOptions.getFaultString(), getOptions);
        // String sql = "SELECT RGN_CD, PRMY_SUB_DIV_NM FROM GUEST.RGN";
        // setComparisonValidation(getOptions.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_COMMUNICATIONCHANNELS() {

        TestReporter.logScenario("Test - Get Options - COMMUNICATIONCHANNELS");
        GetOptions getOptions = new GetOptions(environment);

        getOptions.setOptionEnum("COMMUNICATIONCHANNELS");

        getOptions.sendRequest();

        String sql = "SELECT COMNCTN_CHAN_ID, COMNCTN_CHAN_NM FROM RES_MGMT.COMNCTN_CHAN";
        setComparisonValidation(getOptions.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_SALESCHANNELS() {

        TestReporter.logScenario("Test - Get Options - SALESCHANNELS");
        GetOptions getOptions = new GetOptions(environment);

        getOptions.setOptionEnum("SALESCHANNELS");

        getOptions.sendRequest();

        String sql = "SELECT SLS_CHAN_ID, SLS_CHAN_NM FROM RES_MGMT.SLS_CHAN";
        setComparisonValidation(getOptions.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_CANCEL_REASON() {

        TestReporter.logScenario("Test - Get Options - CANCEL_REASON");
        GetOptions getOptions = new GetOptions(environment);

        getOptions.setOptionEnum("CANCEL_REASON");

        getOptions.sendRequest();

        TestReporter.logAPI(!getOptions.getResponseStatusCode().equals("200"), getOptions.getFaultString(), getOptions);
        String sql = "SELECT LGCY_RSN_CD, TC_RSN_NM FROM RES_MGMT.PRDF_TC_RSN";
        setComparisonValidation(getOptions.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptions" })
    public void testgetOptions_ALTERNATE_COUNTRY_CODE() {
        validateSpecialEnvironment("ALTERNATE_COUNTRY_CODE", "getOptions_AltCountryCode");
    }

    /*
     * Utility Functions
     */

    private Set<Entry<String, String>> getDatabaseKeyValuePairs(String sql) {
        Set<Entry<String, String>> toreturn = new HashSet<>();
        Object[][] rs = new Recordset(db.getResultSet(sql)).getArray();
        for (int i = 1; i < rs.length; i++) {
            String key = (String) rs[i][0];
            String value = rs[i].length > 1 ? (String) rs[i][1] : key;
            toreturn.add(new SimpleImmutableEntry<String, String>(key, value));
        }
        return toreturn;
    }

    private void setComparisonValidation(Set<Entry<String, String>> response, Set<Entry<String, String>> database) {
        if (response.equals(database)) {
            TestReporter.assertTrue(true, "The response contains all of the expected key value pairs found in the database.");
        } else {
            Set<Entry<String, String>> dbexclusive = new HashSet<>(database);
            dbexclusive.removeAll(response);
            Set<Entry<String, String>> rsexclusive = new HashSet<>(response);
            rsexclusive.removeAll(database);

            StringBuilder sb = new StringBuilder();
            if (dbexclusive.size() > 0) {
                sb.append("The response is missing the following key value pairs found in the database: ");
                for (Entry<String, String> entry : dbexclusive) {
                    sb.append("[").append(entry.getKey()).append(", ").append(entry.getValue()).append("], ");
                }
                sb.replace(sb.length() - 2, sb.length(), "");
            }
            if (rsexclusive.size() > 0) {
                sb.append(sb.length() > 0 ? "\n" : "").append("The database is missing the following key value pairs found in the response: ");
                for (Entry<String, String> entry : rsexclusive) {
                    sb.append("[").append(entry.getKey()).append(", ").append(entry.getValue()).append("], ");
                }
                sb.replace(sb.length() - 2, sb.length(), "");
            }
            TestReporter.logFailure(sb.toString());
            Assert.fail();
        }
    }

    private void validateSpecialEnvironment(String parameter, String scenario) {
        TestReporter.logScenario("Test - Get Options - " + parameter);
        GetOptions service = new GetOptions(environment);
        service.setOptionEnum(parameter);
        service.sendRequest();
        TestReporter.logAPI(!service.getResponseStatusCode().equals("200"), "An error occurred in the get " + parameter + " options request", service);

        service.validateResponseNodeQuantity(scenario, true);

        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            BaseSoapService serviceBaseLine = (BaseSoapService) service.clone();
            serviceBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            serviceBaseLine.sendRequest();
            TestReporter.assertTrue(service.validateResponseNodeQuantity(serviceBaseLine, true), "Response Node Validation Result");
        }
    }
}
