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
    private Boolean onlyTps;

    public RetrievePostedCancellationFeeHelper(String environment) {
        this.environment = environment;
    }

    public void setOnlyTps(Boolean flag) {
        this.onlyTps = flag;
    }

    public void checkFeeOrNoFee(RetrievePostedCancellationFee ret, String tcgID, boolean fee) {

        if (fee) {

            String sql = "SELECT * "
                    + "FROM FOLIO.CHRG "
                    + "JOIN FOLIO.CHRG_GRP_EXTNL_REF on CHRG_GRP_EXTNL_REF.CHRG_GRP_ID = CHRG.CHRG_GRP_ID "
                    + "JOIN FOLIO.EXTNL_REF on EXTNL_REF.EXTNL_REF_ID = CHRG_GRP_EXTNL_REF.EXTNL_REF_ID "
                    + "WHERE EXTNL_REF.EXTNL_REF_VAL = '" + tcgID + "' "
                    + "AND CHRG.CHRG_TYP_NM = 'Fee Charge'";
            Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));

            chargeAmount = rs.getValue("CHRG_AM");

            TestReporter.assertTrue(rs.getRowCount() > 0, "Validate a fee charge row is returned in the charge db as expected");
            if (!onlyTps) {
                TestReporter.assertEquals(chargeAmount, ret.getProductPrice(), "Verify the product price in the DB: [" + chargeAmount + "] "
                        + "matches the value from the response: [" + ret.getProductPrice() + "] ");
            }
        } else {

            String sql = "SELECT * "
                    + "FROM FOLIO.CHRG "
                    + "JOIN FOLIO.CHRG_GRP_EXTNL_REF on CHRG_GRP_EXTNL_REF.CHRG_GRP_ID = CHRG.CHRG_GRP_ID "
                    + "JOIN FOLIO.EXTNL_REF on EXTNL_REF.EXTNL_REF_ID = CHRG_GRP_EXTNL_REF.EXTNL_REF_ID "
                    + "WHERE EXTNL_REF.EXTNL_REF_VAL = '" + tcgID + "' "
                    + "AND CHRG.CHRG_TYP_NM = 'Fee Charge'";
            Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment), Database.DREAMS);
            Recordset rs = new Recordset(db.getResultSet(sql));

            TestReporter.assertEquals(rs.getRowCount(), 0, "Validate a fee charge row is not returned in the charge db as expected");
        }

    }

}
