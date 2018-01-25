package com.disney.utils.dataFactory.database.sqlStorage;

public class Dreams_AccommodationQueries extends Dreams {

    public static String getChargeItemStatusByTpTpsTcgIds(String tpId, String tpsId, String tcgId) {
        return "select * "
                + "from folio.extnl_ref a, folio.chrg_grp_extnl_ref b, folio.chrg c, folio.chrg_item d "
                + "where a.EXTNL_REF_VAL in ('" + tpId + "','" + tpsId + "','" + tcgId + "') "
                + "and b.EXTNL_REF_ID = a.EXTNL_REF_ID "
                + "and c.CHRG_GRP_ID = b.CHRG_GRP_ID "
                + "and c.chrg_id = d.chrg_id";
    }

    public static String getRoomTypesWithHighRoomCounts() {

        return "select NUMROOMS, ROOM_TYPE, c.SEQ_NM RESORT, ROOM_DESC, c.RSRT_FAC_ID , WRK_LOC_ID "
                + "from( select NUMROOMS, b.RSRC_INVTRY_TYP_CD ROOM_TYPE, FAC_ID, RM_DS ROOM_DESC "
                + "from( select count(a.rsrc_id) NUMROOMS, a.RSRC_INVTRY_TYP_ID  TYPE_ID, a.RSRT_FAC_ID FAC_ID, a.RM_DS "
                + "from rsrc_inv.rm a "
                + "group by a.RSRC_INVTRY_TYP_ID, a.RSRT_FAC_ID, a.RM_DS "
                + "order by count(a.rsrc_id) desc) "
                + "join rsrc_inv.RSRC_INVTRY_TYP b on b.RSRC_INVTRY_TYP_ID = TYPE_ID "
                + "order by NUMROOMS desc) "
                + "join rsrc_inv.rsrt_seq c on c.RSRT_FAC_ID = FAC_ID "
                + "join rsrc_inv.WRK_LOC d on FAC_ID = d.HM_RSRT_FAC_ID "
                + "join accting.FAC_TXN_ACCT_CTR e on TXN_ACCT_CTR_ID = e.DFLT_TXN_ACCT_CTR_ID "
                + "where seq_nm not like ('New%') "
                + "and seq_nm not like ('%20%') "
                + "and NUMROOMS > 100 "
                + "and ROOM_DESC not like '%accessible%' "
                + "and c.SEQ_NM like '% %' AND WRK_LOC_ID NOT IN ( 32100,32301, 66,555,63,597) "
                + "and TXN_ACCT_CTR_ID is not null "
                + "and TXN_ACCT_CTR_ID not in (77803, 10054) "
                + "and WRK_LOC_NM not like ('%HOLA%')"
                + "and HM_RSRT_FAC_ID = e.FAC_ID "
                + "and c.RSRT_FAC_ID not in (select unique(a.DVC_RSRT_FAC_ID) from rsrc_inv.dvc_rm_typ_xref a ) "
                + "group by NUMROOMS, ROOM_TYPE, c.SEQ_NM, ROOM_DESC, c.RSRT_FAC_ID, WRK_LOC_ID "
                + "order by NUMROOMS desc";
    }

    public static String getRoomTypesWithHighRoomCounts(String workLocationIds) {

        return "select NUMROOMS, ROOM_TYPE, c.SEQ_NM RESORT, ROOM_DESC, c.RSRT_FAC_ID , WRK_LOC_ID "
                + "from( select NUMROOMS, b.RSRC_INVTRY_TYP_CD ROOM_TYPE, FAC_ID, RM_DS ROOM_DESC "
                + "from( select count(a.rsrc_id) NUMROOMS, a.RSRC_INVTRY_TYP_ID  TYPE_ID, a.RSRT_FAC_ID FAC_ID, a.RM_DS "
                + "from rsrc_inv.rm a "
                + "group by a.RSRC_INVTRY_TYP_ID, a.RSRT_FAC_ID, a.RM_DS "
                + "order by count(a.rsrc_id) desc) "
                + "join rsrc_inv.RSRC_INVTRY_TYP b on b.RSRC_INVTRY_TYP_ID = TYPE_ID "
                + "order by NUMROOMS desc) "
                + "join rsrc_inv.rsrt_seq c on c.RSRT_FAC_ID = FAC_ID "
                + "join rsrc_inv.WRK_LOC d on FAC_ID = d.HM_RSRT_FAC_ID "
                + "join accting.FAC_TXN_ACCT_CTR e on TXN_ACCT_CTR_ID = e.DFLT_TXN_ACCT_CTR_ID "
                + "where seq_nm not like ('New%') "
                + "and seq_nm not like ('%20%') "
                + "and NUMROOMS > 100 "
                + "and ROOM_DESC not like '%accessible%' "
                + "and c.SEQ_NM like '% %' AND WRK_LOC_ID IN ( " + workLocationIds + ") "
                + "and TXN_ACCT_CTR_ID is not null "
                + "and TXN_ACCT_CTR_ID not in (77803, 10054) "
                + "and WRK_LOC_NM not like ('%HOLA%')"
                + "and HM_RSRT_FAC_ID = e.FAC_ID "
                + "and c.RSRT_FAC_ID not in (select unique(a.DVC_RSRT_FAC_ID) from rsrc_inv.dvc_rm_typ_xref a ) "
                + "group by NUMROOMS, ROOM_TYPE, c.SEQ_NM, ROOM_DESC, c.RSRT_FAC_ID, WRK_LOC_ID "
                + "order by NUMROOMS desc";
    }

    public static String getTpPartyGuestInfoByTpId(String tpId) {
        return "select b.IDVL_FST_NM, b.IDVL_MID_NM, b.IDVL_LST_NM, b.DVC_MBR_TYP_NM, c.MBRSHP_ID, "
                + "e.ADDR_LN_1_TX, e.CITY_NM, e.RGN_CD, e.PSTL_CD, f.TXN_PTY_EML_ADDR_TX, g.PHN_NB "
                + "from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_IDVL_PTY_MBSHP c, guest.TXN_PTY_LCTR d "
                + ", guest.TXN_PTY_ADDR_LCTR e, guest.TXN_PTY_EML_LCTR f, guest.TXN_PTY_PHN_LCTR g "
                + "where a.tp_id = " + tpId + " "
                + "and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "and b.TXN_IDVL_PTY_ID = c.TXN_IDVL_PTY_ID "
                + "and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "and e.TXN_PTY_ADDR_LCTR_ID = ( "
                + "        select TXN_PTY_ADDR_LCTR_ID "
                + "        from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_IDVL_PTY_MBSHP c, guest.TXN_PTY_LCTR d, guest.TXN_PTY_ADDR_LCTR e "
                + "        where a.tp_id = " + tpId + " "
                + "        and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "        and b.TXN_IDVL_PTY_ID = c.TXN_IDVL_PTY_ID "
                + "        and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "        and d.TXN_PTY_LCTR_ID = e.TXN_PTY_ADDR_LCTR_ID "
                + ") "
                + "and f.TXN_PTY_EML_LCTR_ID = ( "
                + "        select TXN_PTY_EML_LCTR_ID "
                + "        from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_IDVL_PTY_MBSHP c, guest.TXN_PTY_LCTR d, guest.TXN_PTY_EML_LCTR f "
                + "        where a.tp_id = " + tpId + " "
                + "        and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "        and b.TXN_IDVL_PTY_ID = c.TXN_IDVL_PTY_ID "
                + "        and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "        and d.TXN_PTY_LCTR_ID = f.TXN_PTY_EML_LCTR_ID "
                + ") and g.TXN_PTY_PHN_LCTR_ID = ( "
                + "        select TXN_PTY_PHN_LCTR_ID "
                + "        from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_IDVL_PTY_MBSHP c, guest.TXN_PTY_LCTR d, guest.TXN_PTY_PHN_LCTR g "
                + "        where a.tp_id = " + tpId + " "
                + "        and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "        and b.TXN_IDVL_PTY_ID = c.TXN_IDVL_PTY_ID "
                + "        and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "        and d.TXN_PTY_LCTR_ID = g.TXN_PTY_PHN_LCTR_ID )";
    }

    public static String getTpPartyGuestInfoByTpId_NoMembership(String tpId) {
        return "select distinct(b.IDVL_FST_NM), b.IDVL_MID_NM, b.IDVL_LST_NM, b.DVC_MBR_TYP_NM,e.ADDR_LN_1_TX, e.CITY_NM, e.RGN_CD, e.PSTL_CD, f.TXN_PTY_EML_ADDR_TX, g.PHN_NB "
                + "from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_IDVL_PTY_MBSHP c, guest.TXN_PTY_LCTR d, guest.TXN_PTY_ADDR_LCTR e, guest.TXN_PTY_EML_LCTR f, guest.TXN_PTY_PHN_LCTR g "
                + "where a.tp_id = " + tpId + " "
                + "and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "and e.TXN_PTY_ADDR_LCTR_ID = ( "
                + "        select TXN_PTY_ADDR_LCTR_ID "
                + "        from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_PTY_LCTR d, guest.TXN_PTY_ADDR_LCTR e "
                + "        where a.tp_id = " + tpId + " "
                + "        and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "        and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "        and d.TXN_PTY_LCTR_ID = e.TXN_PTY_ADDR_LCTR_ID ) "
                + "and f.TXN_PTY_EML_LCTR_ID = ( "
                + "        select TXN_PTY_EML_LCTR_ID "
                + "        from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_PTY_LCTR d, guest.TXN_PTY_EML_LCTR f "
                + "        where a.tp_id = " + tpId + " "
                + "        and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "        and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "        and d.TXN_PTY_LCTR_ID = f.TXN_PTY_EML_LCTR_ID ) "
                + "and g.TXN_PTY_PHN_LCTR_ID = ( "
                + "        select TXN_PTY_PHN_LCTR_ID "
                + "        from res_mgmt.tp_pty a, guest.txn_idvl_pty b, guest.TXN_PTY_LCTR d, guest.TXN_PTY_PHN_LCTR g "
                + "        where a.tp_id = " + tpId + " "
                + "        and b.TXN_IDVL_PTY_ID = a.TXN_PTY_ID "
                + "        and a.TXN_PTY_ID = d.TXN_PTY_ID "
                + "        and d.TXN_PTY_LCTR_ID = g.TXN_PTY_PHN_LCTR_ID )";
    }

    public static String getTcStatusByTcg(String tcg) {
        return "select * "
                + "from res_mgmt.tc_grp a, res_mgmt.tc b "
                + "where a.tc_grp_nb = b.tc_grp_nb "
                + "and a.tc_grp_nb = '" + tcg + "'";
    }

    public static String getReservationHistoryByTpsId(String tpsId) {
        return "select * "
                + "from res_mgmt.res_hist a , res_mgmt.tps b "
                + "where b.tps_id = '" + tpsId + "' "
                + "and a.tp_id = b.tp_id";
    }

    public static String getReservationHistoryByTpId(String tpId) {
        return "select * "
                + "from res_mgmt.res_hist a , res_mgmt.tps b "
                + "where b.tp_id = '" + tpId + "' "
                + "and a.tp_id = b.tp_id";
    }

    public static String getUniqueNodeChargeGroups(String tpId) {
        return "select unique(a.NODE_CHRG_GRP_ID) "
                + "from folio.chrg_grp_gst a, folio.node_chrg_grp b, folio.root_chrg_grp c, folio.chrg_grp_folio d "
                + "where a.TXN_IDVL_PTY_ID in ( "
                + "select TXN_PTY_ID "
                + "from res_mgmt.tp_pty f "
                + "where f.tp_id = '" + tpId + "') "
                + "and a.NODE_CHRG_GRP_ID = b.NODE_CHRG_GRP_ID "
                + "and b.ROOT_CHRG_GRP_ID = c.ROOT_CHRG_GRP_ID "
                + "and c.ROOT_CHRG_GRP_ID = d.ROOT_CHRG_GRP_ID";
    }

    public static String getUniqueRootChargeGroups(String tpId) {
        return "select unique(b.ROOT_CHRG_GRP_ID) "
                + "from folio.chrg_grp_gst a, folio.node_chrg_grp b, folio.root_chrg_grp c, folio.chrg_grp_folio d "
                + "where a.TXN_IDVL_PTY_ID in ( "
                + "select TXN_PTY_ID "
                + "from res_mgmt.tp_pty f "
                + "where f.tp_id = '" + tpId + "') "
                + "and a.NODE_CHRG_GRP_ID = b.NODE_CHRG_GRP_ID "
                + "and b.ROOT_CHRG_GRP_ID = c.ROOT_CHRG_GRP_ID "
                + "and c.ROOT_CHRG_GRP_ID = d.ROOT_CHRG_GRP_ID";
    }

    public static String getResourceAssignmentOwnerDetailsByAssignmentOwner(String id) {
        return "select * "
                + "from rsrc_inv.rsrc_asgn_ownr a "
                + "WHERE a.ASGN_OWNR_ID = '" + id + "'";
    }

    public static String getAccommodationComponentAssignemtnOwnerIDByTpId(String tpId) {
        return "select c.ASGN_OWN_ID "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + tpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.TC_TYP_NM = 'AccommodationComponent'";
    }

    public static String getAssignmentOwnerIdByTcg(String tcg) {
        return "select c.ASGN_OWN_ID "
                + "from res_mgmt.tps a "
                + "left join res_mgmt.tc_grp b on a.tps_id = b.tps_id "
                + "left join res_mgmt.tc c on b.tc_grp_nb = c.tc_grp_nb "
                + "where c.tc_grp_nb = " + tcg + " "
                + "and c.ASGN_OWN_ID is not null "
                + "and c.ASGN_OWN_ID != 0";
    }

    public static String getChargeInformationByTp(String tpId) {
        return " select e.* " +
                " from FOLIO.CHRG_GRP_FOLIO a  " +
                " left outer join FOLIO.FOLIO b on b.FOLIO_ID= a.CHRG_GRP_FOLIO_ID " +
                " left outer join FOLIO.FOLIO_ITEM c on c.FOLIO_ID= b.FOLIO_ID " +
                " left outer join FOLIO.CHRG_ITEM d on d.CHRG_ITEM_ID = c.FOLIO_ITEM_ID " +
                " left outer join FOLIO.CHRG e on e.CHRG_ID = d.CHRG_ID " +
                " left outer join FOLIO.PMT f on f.FOLIO_ITEM_ID = c.FOLIO_ITEM_ID " +
                " left outer join FOLIO.CHRG_GRP g on g.CHRG_GRP_ID = a.ROOT_CHRG_GRP_ID " +
                " left outer join FOLIO.CHRG_GRP_EXTNL_REF h on h.CHRG_GRP_ID=g.CHRG_GRP_ID " +
                " left outer join FOLIO.EXTNL_REF i on i.EXTNL_REF_ID=h.EXTNL_REF_ID " +
                " left outer join FOLIO.prod_chrg t on t.chrg_id=d.chrg_id " +
                " left outer join FOLIO.chrg_mkt_pkg u on u.chrg_mkt_pkg_id=t.chrg_mkt_pkg_Id  " +
                " where i.EXTNL_REF_VAL ='" + tpId + "' " +
                " AND e.CHRG_ID is not null";
    }

    public static String getLocationIdByTpId(String tpId) {
        return "select d.WRK_LOC_ID "
                + "from rsrc_inv.wrk_loc d "
                + "where d.HM_RSRT_FAC_ID in (select c.fac_id FAC_ID "
                + "from res_mgmt.tps a, res_mgmt.tc_grp b, res_mgmt.tc c "
                + "where a.tp_id = '" + tpId + "' "
                + "and a.tps_id = b.tps_id "
                + "and b.tc_grp_nb = c.tc_grp_nb "
                + "and c.fac_id is not null )";

    }

    public static String getUnusedTpId() {
        return "SELECT tp_id + 1 id "
                + "FROM res_mgmt.tp tp1 "
                + "WHERE NOT EXISTS( "
                + "SELECT null "
                + "FROM res_mgmt.tp tp2 "
                + "WHERE tp2.tp_id = tp1.tp_id+1 ) "
                + "AND ROWNUM = 1";
    }

    public static String getProfileInformationById(String id) {
        return "select a.PRFL_TYP_NM PROFILE_TYPE, a.PRFL_VAL_CD PROFILE_CODE, a.PRFL_VAL_DS PROFILE_DESCRIPTION, b.PRFL_RTE_TYP_NM PROFILE_ROUTINGS_NAME, c.SLCT_IN PROFILE_SELECTABLE "
                + "from prfl_mgmt.prfl a "
                + "left outer join prfl_mgmt.prfl_crtra c on c.prfl_id = a.prfl_id "
                + "left outer join prfl_mgmt.prfl_rte b on a.prfl_id = b.PRFL_ID "
                + "where a.PRFL_ID = " + id;
    }
}
