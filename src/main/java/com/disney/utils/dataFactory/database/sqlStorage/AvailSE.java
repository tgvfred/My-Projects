package com.disney.utils.dataFactory.database.sqlStorage;

public class AvailSE {
	public static String getResourceAvailibleTimesById(String resourceId){
		return  "select b.ENTRPRS_FAC_ID Facility_ID, a.RSRVBL_RSRC_ID Resource_ID, a.FSELL_INVTRY_ID Inventory_ID, a.FSELL_INVTRY_SRVC_DTS Start_Date " +
				 " from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b " +
				 " where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id " +
				 " and b.RSRVBL_RSRC_ID = '" + resourceId + "' " +
				 " and rownum = 1 " + 
				 " order by fsell_invtry_srvc_dts";
	}
	
	public static String getResourceAvailibleTimesByIdAndStartDate(String resourceId, String startDate){
		return  "select b.ENTRPRS_FAC_ID Facility_ID, a.RSRVBL_RSRC_ID Resource_ID, a.FSELL_INVTRY_ID Inventory_ID, a.FSELL_INVTRY_SRVC_DTS Start_Date " +
				 " from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b " +
				 " where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id " +
				 " and b.RSRVBL_RSRC_ID = '" + resourceId + "' " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') = '" +startDate + "' " +
				 " and rownum = 1 " + 
				 " order by fsell_invtry_srvc_dts";
	}
	
	public static String getResourceAvailibleTimesByIdAndDate(String resourceId, String startDate){
		String date = startDate.contains("T") ? startDate.substring(0, startDate.indexOf("T")) : startDate;
		return  "select Facility_ID, Resource_ID, Inventory_ID, Start_Date " +
				 " FROM ( select b.ENTRPRS_FAC_ID Facility_ID, a.RSRVBL_RSRC_ID Resource_ID, a.FSELL_INVTRY_ID Inventory_ID, a.FSELL_INVTRY_SRVC_DTS Start_Date " +
				 " from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b " +
				 " where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') >= '" +date + "' " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') <  to_Char(sysdate + 30, 'yyyy-mm-dd') " +
				 " and b.RSRVBL_RSRC_ID = '" + resourceId + "' " +
				 " and (AUTH_INVTRY_CN > 0 AND (BK_CN + FREEZE_CN) < AUTH_INVTRY_CN)) " +
				 " where rownum = 1 " + 
				 " order by Start_Date";
	}
	
	public static String getReservableResourceByFacilityAndDate(String facilityId, String date){
		String trimDate = date.contains("T") ? date.substring(0, date.indexOf("T")) : date;
		return "select Resource_ID, Start_Date from "
				+ "( select a.RSRVBL_RSRC_ID Resource_ID, FSELL_INVTRY_SRVC_DTS Start_Date "
				+ "from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b "
				+ "where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id "
				+ "and b.ENTRPRS_FAC_ID = '"+facilityId+"' "
				+ "and to_char(a.FSELL_INVTRY_SRVC_DTS, 'yyyy-mm-dd') = '"+trimDate+"' "
				+ "and (AUTH_INVTRY_CN > 0 AND (BK_CN + FREEZE_CN) < AUTH_INVTRY_CN) "
				+ "order by dbms_random.value ) "
				+ "where rownum = 1";
	}

	public static String getReservableResourceByFacilityAndDateNew(String facilityId, String startDate){
		String date = startDate.contains("T") ? startDate.substring(0, startDate.indexOf("T")) : startDate;
		return  "select Facility_ID, Resource_ID, Inventory_ID, Start_Date " +
				 " FROM ( select b.ENTRPRS_FAC_ID Facility_ID, a.RSRVBL_RSRC_ID Resource_ID, a.FSELL_INVTRY_ID Inventory_ID, a.FSELL_INVTRY_SRVC_DTS Start_Date " +
				 " from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b " +
				 " where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') >= '" +date + "' " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') <  to_Char(sysdate + 60, 'yyyy-mm-dd') " +
				 " and b.ENTRPRS_FAC_ID = '"+facilityId+"' " +
				 "and (AUTH_INVTRY_CN > 0 AND ((BK_CN + FREEZE_CN )< AUTH_INVTRY_CN)) " +
				 " order by  dbms_random.value ) data" + 
				 " WHERE rownum = 1 order by  Start_Date ";
	}
	

	public static String getReservableResourceByFacilityAndSpecificDate(String facilityId, String startDate){
		String date = startDate.contains("T") ? startDate.substring(0, startDate.indexOf("T")) : startDate;
		return  "select Facility_ID, Resource_ID, Inventory_ID, Start_Date " +
				 " FROM ( select b.ENTRPRS_FAC_ID Facility_ID, a.RSRVBL_RSRC_ID Resource_ID, a.FSELL_INVTRY_ID Inventory_ID, a.FSELL_INVTRY_SRVC_DTS Start_Date " +
				 " from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b " +
				 " where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') = '" +date + "' " +
				 " and b.ENTRPRS_FAC_ID = '"+facilityId+"' " +
				 "and (AUTH_INVTRY_CN > 0 AND ((BK_CN + FREEZE_CN )< AUTH_INVTRY_CN)) " +
				 " order by  dbms_random.value ) data" + 
				 " WHERE rownum = 1 order by  Start_Date ";
	}
	public static String getFreezeId(String resourceId, String date){
		return "select RSRVBL_RSRC_ID, FSELL_INVTRY_SRVC_DTS, FREEZE_ID "
			 + "from AVAILSE.fsell_invtry a, "
		     + "AVAILSE.FREEZE_FSELL_INVTRY b "
		     + "WHERE a.FSELL_INVTRY_ID = b.FSELL_INVTRY_ID "
		     + "AND a.RSRVBL_RSRC_ID =  '" + resourceId + "' " 
		     + "and to_char(a.FSELL_INVTRY_SRVC_DTS, 'yyyy-mm-dd') = '"+date+"'  ";
	}
	
	public static String getReservableResourceByProductId(final String productId){
		return "SELECT * FROM AVAILSE.PROD_RSRVBL_RSRC_XREF"
				+ " WHERE PROD_ID = " + productId;
	}
}
