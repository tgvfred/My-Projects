package com.disney.api.soapServices.accommodationModule.helpers;

import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.SQLValidationException;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class UpdateGuaranteeStatusHelper {

    private ThreadLocal<String> environment = new ThreadLocal<String>();
    private String name1;
    private String ind1;
    private String name2;
    private String ind2;

    public UpdateGuaranteeStatusHelper(String environment) {
        this.environment.set(environment);
    }

    public String getEnvironment() {
        return this.environment.get();
    }

    public void validation(String tpid, String gname, String gname2, String gind) {

        String sql = "select d.GUAR_TYP_NM, h.GUAR_IN "
                + "from folio.EXTNL_REF a "
                + "left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "
                + "left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "
                + "left outer join folio.NODE_CHRG_GRP d on b.CHRG_GRP_ID = d.ROOT_CHRG_GRP_ID "
                + "left outer join res_mgmt.tps e on a.extnl_ref_val = e.tp_id "
                + "left outer join res_mgmt.tc_grp f on e.tps_id = f.tps_id "
                + "left outer join res_mgmt.tc g on f.tc_grp_nb = g.tc_grp_nb "
                + "left outer join rsrc_inv.rsrc_asgn_ownr h on g.ASGN_OWN_ID = h.ASGN_OWNR_ID "
                + "where a.EXTNL_REF_VAL in ('" + tpid + "') "
                + "and d.NODE_CHRG_GRP_ID is not null "
                + "and h.guar_in is not null";

        Database db = new OracleDatabase(Environment.getBaseEnvironmentName(environment.get()), Database.DREAMS);
        Recordset rs = new Recordset(db.getResultSet(sql));

        if (rs.getRowCount() == 0) {
            throw new SQLValidationException("Nothing found for tp ID [ " + tpid + " ]", sql);
        }

        name1 = rs.getValue("GUAR_TYP_NM", 1);
        ind1 = rs.getValue("GUAR_IN", 1);

        name2 = rs.getValue("GUAR_TYP_NM", 2);
        ind2 = rs.getValue("GUAR_IN", 2);

        TestReporter.assertEquals(name1, gname, "Verify the Guarantee Type Name [" + gname + "] matches the Guarantee Type Name found"
                + " in the DB [" + name1 + "]");

        TestReporter.assertEquals(name2, gname2, "Verify the Guarantee Type Name [" + gname2 + "] matches the Guarantee Type Name found"
                + " in the DB [" + name2 + "]");

        TestReporter.assertEquals(ind1, gind, "Verify the Guarantee Indicator [" + gind + "] matches the Guarantee Indicator found"
                + " in the DB [" + ind1 + "]");

        TestReporter.assertEquals(ind2, gind, "Verify the Guarantee Indicator [" + gind + "] matches the Guarantee Indicator found"
                + " in the DB [" + ind2 + "]");

    }
}
