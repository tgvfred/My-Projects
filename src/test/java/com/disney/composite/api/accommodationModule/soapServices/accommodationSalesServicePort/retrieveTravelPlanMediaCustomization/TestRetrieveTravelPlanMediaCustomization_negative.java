package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort.retrieveTravelPlanMediaCustomization;

import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.applicationError.AccommodationErrorCode;
import com.disney.api.soapServices.accommodationModule.helpers.AccommodationBaseTest;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.dvcModule.dvcSalesService.accommodationSales.operations.RetrieveTravelPlanMediaCustomization;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveTravelPlanMediaCustomization_negative extends AccommodationBaseTest {
	
	
	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "retrieveTravelPlanMediaCustomization"})
	public void testRetrieveTravelPlanMediaCustomization_emptyEntFacilityId(){
		String travelPlanId = getBook().getTravelPlanId();
		String facilityId = getFacilityId();
		String resortCode = getResortCode();
		String locationId = getLocationId();
		String faultString = "Enterprice Facility ID is Required : No Enterprice Facility sent.";
		
		String sql = "select a.TXN_PTY_ID " +
					" from res_mgmt.tp_pty a " +
					" where a.tp_id = " + travelPlanId + " ";
		
		try {Thread.sleep(15000);} catch (InterruptedException e) {e.printStackTrace();}
		Database db = new OracleDatabase(environment, Database.DREAMS);
    	Recordset rs = new Recordset(db.getResultSet(sql));
		
		RetrieveTravelPlanMediaCustomization retrieve = new RetrieveTravelPlanMediaCustomization(environment, "main");
		retrieve.setEnterpriseFacilityId(BaseSoapCommands.REMOVE_NODE.toString());
		retrieve.setTravelPlanId(rs.getValue("TXN_PTY_ID"));
		retrieve.setResortCode(resortCode);
		retrieve.setLocationId(locationId);
		retrieve.sendRequest();
		
		//validate that response xml is true
		 TestReporter.assertEquals(retrieve.getFaultString(), faultString, "Verify that the fault string [" + retrieve.getFaultString() + "] is that which is expected [" + faultString + "].");
	     validateApplicationError(retrieve, AccommodationErrorCode.FACILITY_ID_REQUIRED);
	}
}
	