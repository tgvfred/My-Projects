package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.getOptionsByFilter;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.BaseTest;
import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.GetOptionsByFilter;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestGetOptionsByFilter_Positive extends BaseTest {
    private static OracleDatabase db;

    @BeforeSuite
    @Parameters("environment")
    public static void beforeSuite(String environment) {
        db = new OracleDatabase(environment, Database.DREAMS);
    }

    @DataProvider(name = "countryCodes")
    public static Object[][] dpCountryCodes() {
        Object[][] rs = db.getResultSet("SELECT DISTINCT CNTRY_CD FROM GUEST.RGN");
        List<Object[]> ls = new ArrayList<Object[]>();
        for (int i = 1; i < rs.length; i++) {
            if (rs[i][0].equals("MNE") || rs[i][0].equals("SRB")) {
                continue;
            }
            ls.add(rs[i]);
        }
        return ls.toArray(new Object[ls.size()][]);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_COUNTRY() {
        TestReporter.logScenario("Test - Get Options By Filter - COUNTRY");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("COUNTRY");

        String sql = "SELECT CNTRY_CD, CNTRY_SHORT_NM FROM GUEST.CNTRY";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_ROLE() {
        TestReporter.logScenario("Test - Get Options By Filter - ROLE");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("ROLE");

        String sql = "SELECT PTY_RL_NM FROM GUEST.PTY_RL";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_LANGUAGE() {
        TestReporter.logScenario("Test - Get Options By Filter - LANGUAGE");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("LANGUAGE");

        String sql = "SELECT LANG_CD, LANG_NM FROM GUEST.LANG";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_DEVICE_TYPE() {
        TestReporter.logScenario("Test - Get Options By Filter - DEVICE_TYPE");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("DEVICE_TYPE");

        String sql = "SELECT PHN_DEV_TYP_NM FROM GUEST.PHN_DEV_TYP";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_LOCATOR_USE_TYPE() {
        TestReporter.logScenario("Test - Get Options By Filter - LOCATOR_USE_TYPE");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("LOCATOR_USE_TYPE");

        String sql = "SELECT LCTR_USG_TYP_NM FROM GUEST.LCTR_USG_TYP";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_SUFFIX() {
        TestReporter.logScenario("Test - Get Options By Filter - SUFFIX");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("SUFFIX");

        String sql = "SELECT SFX_CD, SFX_NM FROM GUEST.SFX";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_SALUTATION() {
        TestReporter.logScenario("Test - Get Options By Filter - SALUTATION");
        GetOptionsByFilter getOptionsByFilter = createAndSendFilterlessRequestAndValidateResponse("SALUTATION");

        String sql = "SELECT SALUT_CD, SALUT_NM FROM GUEST.SALUT";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    @Test(enabled = false, dataProvider = "countryCodes", groups = { "api", "regression", "accommodation", "accommodationSalesService", "getOptionsByFilter" })
    public void testGetOptionsByFilter_REGION(String countryCode) {
        TestReporter.logScenario("Test - Get Options By Filter - REGION");

        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setAccommodationSalesOptionsEnum("REGION");
        getOptionsByFilter.addOptionFilters("COUNTRY_CODE_FILTER_ON_REGION", countryCode);
        getOptionsByFilter.sendRequest();

        TestReporter.logAPI(!getOptionsByFilter.getResponseStatusCode().equals("200"), getOptionsByFilter.getFaultString(), getOptionsByFilter);

        String sql = "SELECT RGN_CD, PRMY_SUB_DIV_NM FROM GUEST.RGN WHERE CNTRY_CD = '" + countryCode + "'";
        setComparisonValidation(getOptionsByFilter.getResponseOptionKeyValuePairs(), getDatabaseKeyValuePairs(sql));
        validateSpecialEnvironment(getOptionsByFilter);
    }

    /*
     * Utility Functions
     */
    private GetOptionsByFilter createAndSendFilterlessRequestAndValidateResponse(String option) {
        GetOptionsByFilter getOptionsByFilter = new GetOptionsByFilter(environment);
        getOptionsByFilter.setAccommodationSalesOptionsEnum(option);
        getOptionsByFilter.sendRequest();

        TestReporter.logAPI(!getOptionsByFilter.getResponseStatusCode().equals("200"), getOptionsByFilter.getFaultString(), getOptionsByFilter);

        return getOptionsByFilter;
    }

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

    private void validateSpecialEnvironment(BaseSoapService service) {
        if (Environment.isSpecialEnvironment(environment) || isComo.equals("true")) {
            BaseSoapService serviceBaseLine = (BaseSoapService) service.clone();
            serviceBaseLine.setEnvironment(Environment.getBaseEnvironmentName(environment));
            serviceBaseLine.sendRequest();
            TestReporter.assertTrue(service.validateResponseNodeQuantity(serviceBaseLine, true), "Response Node Validation Result");
        }
    }
}
