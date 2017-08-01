package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.share;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.Share;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestShare extends AccommodationBaseTest {

	/*
	Validation Steps 
	
	Validate reservation history for 'Shared' record
        Check that we can find records matching this tps_id in the db res_mgmt

	Validate SHR_IN in res_mgmt.acm_cmpnt
	    Check that that record has the SHR flag set

	Validate share period is the latest start date and the earliest end date (smallest window)
        Check that start and end states are the min between the two reservations

	Validate assignmentOwnerId changes for 'second' tcg
		

	Validate group guarantee for node charge group of tcg

	Validate old vs new

	 */
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "share", "debug"})
	public void testShare_MainFlow(){
		Share share = new Share(environment );
		share.setTravelComponentGroupingId(getBook().getTravelComponentGroupingId());
		share.setRoomNumber(BaseSoapCommands.REMOVE_NODE.toString());
		share.setLocationId(BaseSoapCommands.REMOVE_NODE.toString());
		share.sendRequest();
	
		TestReporter.logAPI(!share.getResponseStatusCode().equals("200"), "The response code was not 200", share);
		TestReporter.assertNotNull(share.getTravelComponentGroupingId(),  "The response contains a Travel Component Grouping Id");
		TestReporter.assertNotNull(share.getTravelComponentId(),  "The response contains a Travel Component Id");
		TestReporter.assertNotNull(share.getTravelPlanSegmentId(),  "The response contains a Travel Plan Id");

		String tpsID = share.getTravelPlanSegmentId();
		String tcg = share.getTravelComponentGroupingId();
		String tcid = share.getTravelComponentId();
		// Validate Reservation History for Shared Record
		String sql =  "select * " +
				"from res_mgmt.res_hist a " +
				" where a.tps_id = " + tpsID;
		// Debug
		System.out.println("------ TPSID: "+ tpsID);
		System.out.println("------ TCID: "+ tcid);
		System.out.println("------ TCGID: "+ tcg);
		System.out.println("------ SQL: "+sql);

		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		rs.print();
		String reservationHistoryID = rs.getValue("RES_HIST_ID", 1);
		TestReporter.assertNotNull(reservationHistoryID, "No Reservation Hisotry found for this record");


		// Lets look for a Shared Flag
		sql = "select * from res_mgmt.acm_cmpnt a "+ 
				"INNER JOIN res_mgmt.res_hist b on  a.acm_tc_id = b.tc_id "+
				"where tps_id = " + tpsID ;
		rs = new Recordset(db.getResultSet(sql));
		rs.print();
		String shared = rs.getValue("SHR_IN", 1);
		TestReporter.assertEquals(shared, "Y", "Shared flag on record one is not set.  TPSID: "+ tpsID );
		shared = rs.getValue("SHR_IN", 2);
		TestReporter.assertEquals(shared, "Y", "Shared flag on record one is not set.  TPSID: "+ tpsID );

		// Validate Share Period
		// TODO - waiting for info for determining share period
		
		
		//Validate assignmentOwnerId changes for 'second' tcg
		// TODO - 
		
		// Validate group guarantee for node charge group of tcg
		sql = "select d.GUAR_TYP_NM "+
				"from folio.EXTNL_REF a "+
				"left outer join folio.CHRG_GRP_EXTNL_REF b on a.EXTNL_REF_ID = b.EXTNL_REF_ID "+
				"left outer join folio.CHRG_GRP c on b.CHRG_GRP_ID = c.CHRG_GRP_ID "+
				"left outer join folio.NODE_CHRG_GRP d on c.CHRG_GRP_ID = d.NODE_CHRG_GRP_ID "+
				"where a.EXTNL_REF_VAL in ('472092173062')";

		rs = new Recordset(db.getResultSet(sql));
		rs.print();
		String guarantee = rs.getValue("GUAR_TYP_NM", 1);

		TestReporter.assertEquals(guarantee, "GROUP_GUARANTEED", "Group Guaranteed not set for Node Charge of Group: "+ tcg);		
		
		
		// Validate old vs new
		
		
	}
}

