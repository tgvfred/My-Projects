package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesComponentServicePort.processContainerModifyBusinessEvent;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.AutoCancel;
import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations.ProcessContainerModifyBusinessEvent;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.Environment;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestProcessContainerModifyBusinessEvent_guarAccomm_negExtRefNumber extends AccommodationBaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    @Parameters("environment")
    public void setup(String environment) {
        setEnvironment(environment);
        isComo.set("false");
        setDaysOut(0);
        setNights(1);
        setArrivalDate(getDaysOut());
        setDepartureDate(getDaysOut() + getNights());
        setValues(getEnvironment());
        setIsWdtcBooking(true);
        setAddNewGuest(true);
        isComo.set("false");
        bookReservation();

    }

    @Test(groups = { "api", "regression", "accommodation", "accommodationSalesComponentService", "processContainerModifyBusinessEvent" })
    public void testProcessContainerModifyBusinessEvent_guarAccomm_negExtRefNumber() {

        String tps = getBook().getTravelPlanSegmentId();
        String tp = getBook().getTravelPlanId();

        AutoCancel ac = new AutoCancel(Environment.getBaseEnvironmentName(environment));
        ac.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());

        ac.sendRequest();
        System.out.println(ac.getRequest());
        System.out.println(ac.getResponse());
        ProcessContainerModifyBusinessEvent process = new ProcessContainerModifyBusinessEvent(Environment.getBaseEnvironmentName(environment));
        // process.setTravelPlanSegmentID("472121534976");
        process.setTravelPlanSegmentID(tps);
        process.setByPassFreeze("true");
        process.setExternalReferenceCode(BaseSoapCommands.REMOVE_NODE.toString());
        process.setExternalReferenceNumber("-" + getBook().getTravelComponentGroupingId());
        process.setExternalReferenceSource("DREAMS_TP");
        process.setExternalReferenceType(BaseSoapCommands.REMOVE_NODE.toString());

        process.setAttemptAutoReinstate("true");
        process.sendRequest();
        System.out.println(process.getRequest());
        System.out.println(process.getResponse());
        TestReporter.logAPI(!process.getResponseStatusCode().equals("200"), "An error occurred process container modify business event the reservation.", process);
        // validations

        String sql = "select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tps_id = '" + tps + "'";
        // + " where a.tps_id = '" + "472121534976" + "'";

        String tpv3Status = "select b.SLS_ORD_ITEM_STS_NM TPV3_STATUS"
                + " from sales_tp.sls_ord a"
                + " join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord";
        String rsHistorysql = "select a.RES_HIST_PROC_DS EVENT"
                + " from res_mgmt.res_hist a"
                + " where a.tp_id = '" + tp + "'";
        // +" where a.tp_id = '472131649008'";
        String folioItemsSql = "select h.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID"
                + " left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID"
                + " left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID"
                + " where a.EXTNL_REF_VAL in ('" + tp + "')";

        String chargeGroupStatusSql = "select c.CHRG_GRP_STS_NM"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " where a.EXTNL_REF_VAL in ('" + tps + "','" + tp + "', '" + getBook().getTravelComponentGroupingId() + "')";

        String rimSql = "select *"
                + " from res_mgmt.tc a"
                + " join rsrc_inv.RSRC_ASGN_OWNR b on a.ASGN_OWN_ID = b.ASGN_OWNR_ID"
                + " join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID = c.ASGN_OWNR_ID"
                + " where a.tc_grp_nb = -472121562656";

        Database db = new OracleDatabase(environment, Database.DREAMS);
        // Database db2 = new OracleDatabase(environment, Database.SALESTP);

        Recordset rs = new Recordset(db.getResultSet(sql));
        // Recordset rs2 = new Recordset(db2.getResultSet(tpv3Status));
        Recordset rs3 = new Recordset(db.getResultSet(rsHistorysql));
        Recordset rs4 = new Recordset(db.getResultSet(chargeGroupStatusSql));
        Recordset rs5 = new Recordset(db.getResultSet(folioItemsSql));
        Recordset rs6 = new Recordset(db.getResultSet(rimSql));
        rs.print();
        // rs2.print();
        rs3.print();
        rs4.print();
        rs5.print();
        rs6.print();
        TestReporter.assertTrue(rs.getValue("TRVL_STS_NM TP_STATUS", 1) != "REINSTATE", "The reservation is [" + rs.getValue("TRVL_STS_NM TP_STATUS", 1) + "]");

        // TestReporter.assertTrue(rs2.getValue("SLS_ORD_ITEM_STS_NM TPV3_STATUS") != "", "The TPV3 status is [" + rs.getValue("SLS_ORD_ITEM_STS_NM TPV3_STATUS") + "]");

        for (int i = 0; i <= rs3.getRowCount(); i++) {
            if (rs3.getValue("TPS_CNCL_NB CANCEL_NUMBER", i) == "Auto Cancelled") {

                TestReporter.assertTrue(rs3.getValue("TPS_CNCL_NB CANCEL_NUMBER", i) == "Auto Cancelled", "The cancellation number is [" + rs.getValue("TPS_CNCL_NB CANCEL_NUMBER", i) + "]");

            }

        }

        TestReporter.assertTrue(rs3.getValue("TPS_CNCL_NB CANCEL_NUMBER", 1) != "0", "The cancellation number is [" + rs.getValue("TPS_CNCL_NB CANCEL_NUMBER", 1) + "]");

        TestReporter.assertTrue(rs3.getValue("RES_HIST_PROC_DS EVENT") != "REINSTATE", "The reservation history is [" + rs.getValue("RES_HIST_PROC_DS EVENT") + "]");
        TestReporter.assertTrue(rs4.getValue("CHRG_GRP_STS_NM") != "CANCELLED", "The charge group is [" + rs.getValue("RES_HIST_PROC_DS EVENT") + "]");

    }
}