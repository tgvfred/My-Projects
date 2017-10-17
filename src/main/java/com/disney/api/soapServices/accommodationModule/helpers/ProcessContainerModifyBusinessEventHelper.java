package com.disney.api.soapServices.accommodationModule.helpers;

public class ProcessContainerModifyBusinessEventHelper {

    public void statusTP_TC(String tps, String env) {
        String sql = "   select a.TRVL_STS_NM TP_STATUS, a.TPS_CNCL_NB CANCEL_NUMBER, c.TRVL_STS_NM TC_STATUS"
                + " from res_mgmt.tps a"
                + " join res_mgmt.tc_grp b on a.tps_id = b.tps_id"
                + " join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb"
                + " where a.tps_id ='" + tps + "'";

    }

    public void reservationHistry(String tp, String env) {
        String sql = "select a. RES_HIST_PROC_DS EVENT"
                + " from res_mgmt.res_hist a"
                + " where a.tp_id='" + tp + "'";
    }

    public void rimRecord(String tcg, String env) {
        String sql = "select*"
                + " from res_mgmt.tc a"
                + " join rsrc_inv.RSRC_ASGN_OWNR b"
                + " on a.ASGN_OWN_ID=b.ASGN_OWNR_ID"
                + " join rsrc_inv.RSRC_ASGN_REQ c on b.ASGN_OWNR_ID=c.ASGN_OWNR_ID"
                + " where a.tc_grp_nb='" + tcg + "'";
    }

    public void chargeGroupStatuss(String tp, String tps, String tcg) {
        String sql = "select c.CHRG_GRP_STS_NM"
                + " from"
                + " folio.EXTNL_REF a"
                + " left outer"
                + "  join folio."
                + " CHRG_GRP_EXTNL_REF b"
                + " on a.EXTNL_REF_ID="
                + " b.EXTNL_REF_ID left"
                + " outer join"
                + "  folio.CHRG_GRP c"
                + "  on b.CHRG_GRP_ID="
                + "  c.CHRG_GRP_ID"
                + " where a.EXTNL_REF_VAL in ('" + tp + "','" + tps + "', '" + tcg + "')";
    }

    public void chargeItems(String tcg, String env) {
        String sql = "select d.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " where a.EXTNL_REF_VAL in ('" + tcg + "')";

    }

    public void folioItems(String tp, String env) {
        String sql = "select h.*"
                + " from folio.EXTNL_REF a"
                + " left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID"
                + " left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID"
                + " left outer join folio.CHRG d on c.CHRG_GRP_ID = d.CHRG_GRP_ID"
                + " left outer join folio.CHRG_ITEM e on d.CHRG_ID = e.CHRG_ID"
                + " left outer join folio.CHRG_GRP_FOLIO f on c.CHRG_GRP_ID = f.ROOT_CHRG_GRP_ID"
                + " left outer join folio.FOLIO g on f.CHRG_GRP_FOLIO_ID = g.FOLIO_ID"
                + " left outer join folio.FOLIO_ITEM h on g.FOLIO_ID = h.FOLIO_ID"
                + "where a.EXTNL_REF_VAL in ('" + tp + "')";
    }

    public void tpv3Status(String env) {

        String sql = "select b.SLS_ORD_ITEM_STS_NM TPV3_STATUS"
                + " from sales_tp.sls_ord a"
                + " join sales_tp.sls_ord_item b on a.sls_ord = b.sls_ord";

    }

    public void tcReason(String tcg, String env) {
        String sql = "select b.TC_RSN_TYP_NM,b.TC_RSN_TX, b.TC_RSN_CNTCT_NM"
                + " from res_mgmt.tc a"
                + " join res_mgmt."
                + " tc_rsn b"
                + "  on a.tc_id="
                + "  b.tc_id"
                + " where a.tc_grp_nb='" + tcg + "'";

    }
}
