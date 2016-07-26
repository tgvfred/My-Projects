package com.disney.utils.dataFactory;

import com.disney.utils.dataFactory.database.Recordset;

public class FacilityInfo {
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_FACILITIES
	 */
	private static String mealTable = "QAAUTO_METADATA_MEAL_FACILITIES";


	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_FACILITIES
	 */
	private static String activityTable = "QAAUTO_METADATA_ACTIVITY_FACILITIES";
	
	
	/**
	 * Common column names between Meal and Activity tables
	 * @author AutoXP
	 *
	 */
	public enum FacilityColumns{
		FACILITY_NAME{ public String toString() {return "FACILITY_NAME";}},
		FACILITY_ID{ public String toString() {return "FACILITY_ID";}}
	}

	/**
	 * Main method that calls Virtual Table code to get info
	 * @param table - references mealTable or activityTable for Virtual Table to look at
	 * @param using - Which column is being queried in the table
	 * @param data - data record to find in desired column
	 * @return Recordset of rows that matched query
	 */
	private static Recordset getInfo(String table, FacilityColumns using, String data){
		return VirtualTable.compileJSON(table, new VirtualTable().getRows(table, using.toString(), data ));
	}
		
	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_FACILITIES
	 * @param name Name of the Facility to find ID for
	 * @return Facility ID for desired Facility Name
	 */
	public static String getMealFacilityIDByName(String name){
		return getInfo(mealTable, FacilityColumns.FACILITY_NAME, name).getValue(FacilityColumns.FACILITY_ID.toString());
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_MEAL_FACILITIES
	 * @param id ID of the Facility to find Name for
	 * @return Facility Name for desired Facility ID
	 */
	public static String getMealFacilityNameById(String id){
		return getInfo(mealTable, FacilityColumns.FACILITY_ID, id).getValue(FacilityColumns.FACILITY_NAME.toString());
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_FACILITIES
	 * @param name Name of the Facility to find ID for
	 * @return Facility ID for desired Facility Name
	 */
	public static String getActivityFacilityIDByName(String name){
		return getInfo(activityTable, FacilityColumns.FACILITY_NAME, name).getValue(FacilityColumns.FACILITY_ID.toString());
	}

	/**
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_FACILITIES
	 * @param id ID of the Facility to find Name for
	 * @return Facility Name for desired Facility ID
	 */
	public static String getActivityFacilityNameById(String id){
		return getInfo(activityTable, FacilityColumns.FACILITY_ID, id).getValue(FacilityColumns.FACILITY_NAME.toString());
	}
}
