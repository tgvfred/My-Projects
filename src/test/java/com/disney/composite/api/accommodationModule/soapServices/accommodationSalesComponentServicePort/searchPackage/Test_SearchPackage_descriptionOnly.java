package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.searchPackage;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentServicePort.operations.SearchPackage;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class Test_SearchPackage_descriptionOnly extends AccommodationBaseTest {

    private String pkg;
    private String desc;
    private String pkgCode = "H557K";

    @Test(groups = { "api", "regression", "accommodation", "accommodationComponentSalesService", "SearchPackage" })
    public void testSearchPackage_descriptionOnly() {

        SearchPackage search = new SearchPackage(environment, "Main");
        search.setPackageDescription("Basic Package");
        search.setBookingDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.setPackageCode(BaseSoapCommands.REMOVE_NODE.toString());
        search.setSalesChannelIDs(BaseSoapCommands.REMOVE_NODE.toString());
        search.setResortArrivalDate(BaseSoapCommands.REMOVE_NODE.toString());
        search.sendRequest();
        TestReporter.logAPI(!search.getResponseStatusCode().equals("200"), "An error occurred retrieving the summary for the travel component grouping [" + getBook().getTravelComponentGroupingId() + "]: " + search.getFaultString(), search);

        packageCheck(search.getPackageDescriptionByPackageCode(pkgCode), pkgCode);

        // Old vs New Validation
        // if (Environment.isSpecialEnvironment(environment)) {
        // SearchPackage clone = (SearchPackage) search.clone();
        // clone.setEnvironment(Environment.getBaseEnvironmentName(environment));
        // clone.sendRequest();
        // if (!clone.getResponseStatusCode().equals("200")) {
        // TestReporter.logAPI(!clone.getResponseStatusCode().equals("200"), "Error was returned", clone);
        // }
        // clone.addExcludedBaselineXpathValidations("/Envelope/Header");
        // TestReporter.assertTrue(clone.validateResponseNodeQuantity(search, true), "Validating Response Comparison");
        // }
    }

    public void packageCheck(String pkgDesc, String pkgCde) {

        String sql = "select a.pkg_cd, a.BKNG_STRT_DT, a.BKNG_END_DT, a.TRVL_STRT_DT, a.TRVL_END_DT, a.PKG_GST_FACING_DESC, a.SALES_CHANNEL_ID "
                + "FROM pma_wdw.pkg a "
                + "WHERE to_Char(a.BKNG_END_DT, 'yyyy-mm-dd HH24:MI:SS') > '2017-05-23' "
                + "and to_Char(a.BKNG_STRT_DT, 'yyyy-mm-dd HH24:MI:SS') < '2017-05-23' "
                + "and to_Char(a.TRVL_END_DT, 'yyyy-mm-dd HH24:MI:SS') > '2017-05-23' "
                + "and to_Char(a.TRVL_STRT_DT, 'yyyy-mm-dd HH24:MI:SS') < '2017-05-23' "
                + "and a.sys_of_rec not in ('PACKAGER') "
                + "and a.sales_channel not in ('SPORTS') "
                + "and a.pkgr_pkg_cd is not null "
                + "and a.pkg_group_name is not null "
                + "and a.PKG_GST_FACING_DESC is not null "
                + "and a.SALES_CHANNEL_ID is not null "
                + "and a.expired != 'Y' "
                + "and a.complete != 'N' "
                + "and a.pkg_cd = '" + pkgCde + "' "
                + "and a.PKG_GST_FACING_DESC = '" + pkgDesc + "' "
                + "order by dbms_random.value";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.RECOMMENDER);
        Recordset rs = new Recordset(db.getResultSet(sql));

        pkg = rs.getValue("PKG_CD");
        desc = rs.getValue("PKG_GST_FACING_DESC");

        TestReporter.assertEquals(pkg, pkgCode, "Verify the Package Code [" + pkgCode + "] matches the Package Code found"
                + " in the DB [" + pkg + "]");
        TestReporter.assertEquals(desc, pkgDesc, "Verify the Package Description [" + pkgDesc + "] matches the Package Description found"
                + " in the DB [" + desc + "]");
    }
}
