package com.disney.utils.dataFactory;

import com.disney.AutomationException;
import com.disney.utils.dataFactory.database.Recordset;

public class FacilityInfo {
private static String mealTable = "QAAUTO_METADATA_MEAL_FACILITIES";
private static String activityTable = "QAAUTO_METADATA_ACTIVITY_FACILITIES";
	
	
	public enum FacilityColumns{
		FACILITY_NAME{ public String toString() {return "FACILITY_NAME";}},
		FACILITY_ID{ public String toString() {return "FACILITY_ID";}}
	}

	private static Recordset getInfo(String table, FacilityColumns using, String searchTest){
		return VirtualTable.compileJSON(table, new VirtualTable().getRows(table, using.toString(), searchTest ));
	}
	
	
	public static String getMealFacilityIDByName(String name){
		return getInfo(mealTable, FacilityColumns.FACILITY_NAME, name).getValue(FacilityColumns.FACILITY_ID.toString());
	}
	
	public static String getMealFacilityNameById(String id){
		return getInfo(mealTable, FacilityColumns.FACILITY_ID, id).getValue(FacilityColumns.FACILITY_NAME.toString());
	}

	public static String getActivityFacilityIDByName(String name){
		return getInfo(activityTable, FacilityColumns.FACILITY_NAME, name).getValue(FacilityColumns.FACILITY_ID.toString());
	}
	
	public static String getActivityFacilityNameById(String id){
		return getInfo(activityTable, FacilityColumns.FACILITY_ID, id).getValue(FacilityColumns.FACILITY_NAME.toString());
	}
}
