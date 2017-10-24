package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrievePostedCancellationFee;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class RetrievePostedCancellationFeeHelper {

    private String environment;
    private String tcID;
    private String chargeTypeName;
    private String chargeAmount;

    public RetrievePostedCancellationFeeHelper(String environment) {
        this.environment = environment;
    }

    public void getTcIdWithTcg(String tcg) {

        String sql = "SELECT TC_ID "
                + "FROM RES_MGMT.TC "
                + "WHERE TC_GRP_NB = '" + tcg + "' "
                + "and TC.TC_TYP_NM = 'AccommodationComponent'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        tcID = rs.getValue("TC_ID");

    }

    public void getTcIdWithTps(String tps) {

        String sql = "select TC_ID "
                + "from RES_MGMT.TC a "
                + "join RES_MGMT.TC_GRP b on  a.TC_GRP_NB = b.TC_GRP_NB "
                + "where b.TPS_ID = '" + tps + "' "
                + "and a.TC_TYP_NM = 'AccommodationComponent'";
        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        tcID = rs.getValue("TC_ID");
    }

    public void getChargeTypeAndAmount(RetrievePostedCancellationFee ret, Boolean fees) {

        if (fees) {
            String sql = "SELECT CHRG.CHRG_TYP_NM, CHRG.CHRG_AM "
                    + "FROM FOLIO.CHRG "
                    + "JOIN FOLIO.CHRG_EXTNL_REF on CHRG_EXTNL_REF.CHRG_ID = CHRG.CHRG_ID "
                    + "WHERE CHRG_EXTNL_REF_VL = '" + tcID + "'";
            Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));

            chargeTypeName = rs.getValue("CHRG_TYP_NM");
            chargeAmount = rs.getValue("CHRG_AM");

            TestReporter.softAssertEquals(chargeTypeName, "Product Charge", "Verify the charge type in the DB: [" + chargeTypeName + "] "
                    + "comes back with the expected value: [Product Charge] ");
            TestReporter.softAssertEquals(chargeAmount, ret.getProductPrice(), "Verify the product price in the DB: [" + chargeAmount + "] "
                    + "matches the value from the response: [" + ret.getProductPrice() + "] ");
        } else {
            String sql = "SELECT CHRG.CHRG_TYP_NM, CHRG.CHRG_AM "
                    + "FROM FOLIO.CHRG "
                    + "JOIN FOLIO.CHRG_EXTNL_REF on CHRG_EXTNL_REF.CHRG_ID = CHRG.CHRG_ID "
                    + "WHERE CHRG_EXTNL_REF_VL = '" + tcID + "'";
            Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));

            int rowCount = rs.getRowCount();

            TestReporter.softAssertTrue(rowCount <= 0, "Verify the row count is zero as expected");
            TestReporter.softAssertEquals("0.0", ret.getProductPrice(), "Verify the product price [0.0] "
                    + "matches the value from the response: [" + ret.getProductPrice() + "] ");
        }
        TestReporter.assertAll();
    }

}
