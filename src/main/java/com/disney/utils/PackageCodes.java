package com.disney.utils;

import java.util.Calendar;

import org.testng.Assert;

import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.date.DateTimeConversion;

@SuppressWarnings("unused")
public class PackageCodes {
    private Datatable datatable = new Datatable();
    private String addSql;
    private String visibility = "SELLABLE";
    private Boolean useBookingDates = false;

    public Boolean getUseBookingDates() {
        return useBookingDates;
    }

    public void setUseBookingDates(Boolean useBookingDates) {
        this.useBookingDates = useBookingDates;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String retrievePackageCode(String environment, String arrivalDate,
            String resortLocation, String packageType, String packageBillCode,
            String resortCode, String roomCode, String packageDescription) {
        // ****************************************************************
        // Define the method name
        // ****************************************************************
        class Local {
        }
        ;
        String className = Local.class.getName().split("$")[0];
        String functionName = className + "." + Local.class.getEnclosingMethod().getName();
        if (TestReporter.getDebugLevel() == TestReporter.INFO || TestReporter.getDebugLevel() == TestReporter.DEBUG) {
            TestReporter.log("Start method: " + functionName);
        }
        String sql = null;          // the sql that will be executed
        String bookingDate = null;  // the date the reservation was booked
        String packageCode = "";

        // Ensure the date format is correct
        bookingDate = DateTimeConversion.formatDate("yyyy-MM-dd", arrivalDate);

        // Determine Resort Location
        if (!resortLocation.equalsIgnoreCase("dlr")) {
            resortLocation = "WDW";
        }

        // ***Justin - Updated so SQL pulls both 3 and 5 character packages
        sql = "SELECT DISTINCT(PKG.PKG_CD)" +
                " FROM PMA_" + resortLocation + ".PKG " +
                " JOIN PMA_" + resortLocation + ".ACMPROD_PKG On PKG.PKG_CD = ACMPROD_PKG.PKG_CD " +
                " JOIN REC_" + resortLocation + ".FAC On ACMPROD_PKG.FAC_CD = FAC.FAC_CD " +
                " JOIN PMA_" + resortLocation + ".ACM_PROD On ACMPROD_PKG.ACM_PROD_CD = ACM_PROD.ACM_PROD_CD " +
                " WHERE PKG.SALES_CHANNEL = '" + packageType + "' " +
                " AND PKG.BKNG_STRT_DT <=  to_date('" + bookingDate + "','YYYY-MM-DD:HH24:MI:SS') " +
                " AND PKG.BKNG_END_DT >=  to_date('" + bookingDate + "','YYYY-MM-DD:HH24:MI:SS') " +
                " AND PKG.TRVL_STRT_DT <=  to_date('" + bookingDate + "','YYYY-MM-DD:HH24:MI:SS') " +
                " AND PKG.TRVL_END_DT >=  to_date('" + bookingDate + "','YYYY-MM-DD:HH24:MI:SS') " +
                " AND ACMPROD_PKG.VISIBILITY = '" + visibility + "' " +
                " AND (LENGTH(PKG.PKG_CD) = 5 Or LENGTH(PKG.PKG_CD) = 3) " +
                " AND PKG.PKG_CD NOT LIKE 'PKGR %' ";
        if (!useBookingDates) {
            sql = sql.replace("AND PKG.BKNG_STRT_DT <=  to_date('" + bookingDate + "','YYYY-MM-DD:HH24:MI:SS')", "");
            sql = sql.replace("AND PKG.BKNG_END_DT >=  to_date('" + bookingDate + "','YYYY-MM-DD:HH24:MI:SS')", "");
        }
        // System.out.println(sql);
        // Add optional SQL
        if (!packageType.equalsIgnoreCase("HI-RM ONLY")) {
            sql = sql + " AND PKG.PKG_GROUP_NAME = 'Annual Packages' ";
        } else {
            sql = sql + " AND PKG.PKG_GROUP_NAME = 'Annual Rack Rate' ";
        }

        if (!resortCode.equalsIgnoreCase("")) {
            sql = sql + " AND FAC.FAC_CD = '" + resortCode.trim() + "' ";
        }

        if (!roomCode.equalsIgnoreCase("")) {
            sql = sql + " AND ACM_PROD.ACM_PROD_CD = '" + roomCode.trim() + "' ";
        }

        if (!packageBillCode.equalsIgnoreCase("")) {
            sql = sql + " AND PKG.BILL_TRAP_CD= '" + packageBillCode.trim() + "' ";
        }

        if (!packageDescription.equalsIgnoreCase("")) {
            sql = sql + " AND TRIM(PKG.PKG_GST_FACING_DESC) = '" + packageDescription.trim() + "' ";
        }

        Calendar cal = Calendar.getInstance();
        int intBookingDate = Integer.parseInt(bookingDate.split("-")[0]);
        int calendarYear = cal.get(Calendar.YEAR);
        if (intBookingDate == calendarYear) {
            sql = sql + " and product_yr = '" + cal.get(Calendar.YEAR) + "'";
        } else {
            int difference = Math.abs(calendarYear - intBookingDate);
            sql = sql + " and product_yr = '" + String.valueOf(cal.get(Calendar.YEAR) + difference) + "'";
        }

        if (addSql != null) {
            sql += addSql;
        }

        // Report SQL
        // System.out.println(sql);
        if (TestReporter.getDebugLevel() == TestReporter.INFO || TestReporter.getDebugLevel() == TestReporter.DEBUG) {
            TestReporter.logNoHtmlTrim("SQL: " + sql);
        }

        // Retrieve info
        OracleDatabase odb = new OracleDatabase(environment, "RECOMMENDER");

        Object[][] resultSet = odb.getResultSet(sql);

        Assert.assertNotEquals(resultSet.length, 1, "The package code query did not return any records.");

        TestReporter.log("End method: " + functionName);
        TestReporter.log("Package Code: " + resultSet[1][0].toString());
        return resultSet[1][0].toString();
    }

    // @Test
    // public void testPackageCodes(){
    // String pkgCode = retrievePackageCode("Grumpy", "0", "WDW", "HI-WDTCPKG",
    // "*WDTC", "", "", "Aulani Basic Package");
    // System.out.println(pkgCode);
    // }

    public void takePackageCode(String scenario) {

        datatable.setVirtualtablePage("ackageInformation");
        datatable.setVirtualtableScenario(scenario);

        String packageDeascription = datatable.getDataParameter("PackageDeascription");
        String packageType = datatable.getDataParameter("PackageType");
    }

    public void addPackageCodeSql(String addSql) {
        this.addSql = addSql;
    }
}
