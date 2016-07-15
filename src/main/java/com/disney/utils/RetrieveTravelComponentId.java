package com.disney.utils;

import org.apache.commons.lang3.StringUtils;

import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;

public class RetrieveTravelComponentId {
	private String environment;
	private String travelPlanSegment;
	
	public RetrieveTravelComponentId(String environment, String tps){
		this.environment = environment;
		this.travelPlanSegment = tps;
	}
	
	public String searchForReservationInformationByTravelPlanSegment(){
		Database db = new OracleDatabase(environment, OracleDatabase.DREAMS);
		Recordset rs;
		
		if(StringUtils.isNumeric(travelPlanSegment)){rs = new Recordset(db.getResultSet(Dreams.getReservationInfoByTpsId(travelPlanSegment)));}
		else{rs = new Recordset(db.getResultSet(Dreams.getTpsIDFromExternalReference(travelPlanSegment)));}			
		return rs.getValue("TC_ID");
	}
}