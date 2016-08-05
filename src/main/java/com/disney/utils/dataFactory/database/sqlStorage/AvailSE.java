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
	
	public static String getResourceAvailibleTimesByIdAndDate(String resourceId, String startDate){
		return  "select b.ENTRPRS_FAC_ID Facility_ID, a.RSRVBL_RSRC_ID Resource_ID, a.FSELL_INVTRY_ID Inventory_ID, a.FSELL_INVTRY_SRVC_DTS Start_Date " +
				 " from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b " +
				 " where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id " +
				 " and to_Char(a.fsell_invtry_srvc_dts, 'yyyy-mm-dd') >= '" +startDate.substring(0, startDate.indexOf("T")) + "' " +
				 " and b.RSRVBL_RSRC_ID = '" + resourceId + "' " +
				 " and rownum = 1 " + 
				 " order by fsell_invtry_srvc_dts";
	}
	
	public static String getReservableResourceByFacilityAndDate(String facilityId, String date){
		return "select Resource_ID from "
				+ "( select a.RSRVBL_RSRC_ID Resource_ID "
				+ "from AVAILSE.fsell_invtry a, AVAILSE.rsrvbl_rsrc b "
				+ "where a.rsrvbl_rsrc_id = b.rsrvbl_rsrc_id "
				+ "and b.ENTRPRS_FAC_ID = '"+facilityId+"' "
				+ "and to_char(a.FSELL_INVTRY_SRVC_DTS, 'yyyy-mm-dd') = '"+date+"' "
				+ "order by dbms_random.value ) "
				+ "where rownum = 1";
	}
}
