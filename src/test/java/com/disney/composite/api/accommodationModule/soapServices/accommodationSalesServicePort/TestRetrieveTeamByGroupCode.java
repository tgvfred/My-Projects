package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations.RetrieveTeamsByGroupCode;
//import com.disney.api.soapServices.travelPlanSegmentModule.travelPlanSegmentServicePort.operations.GetBookedTravelPlanSegments.GetBookedTravelPlanSegments;
import com.disney.utils.Randomness;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestRetrieveTeamByGroupCode {
private String environment = "";
	
@BeforeClass(alwaysRun = true)
	@Parameters({  "environment" })
	public void setup(String environment) {
		this.environment = environment;
	
	}

@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode"})
public void testRetrieveTeamsByGroupCodeMissingGroupCode(){
	
	RetrieveTeamsByGroupCode RetrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment, "" );
	RetrieveTeamsByGroupCode.setgroupcode("");
	RetrieveTeamsByGroupCode.sendRequest();
	System.out.println(RetrieveTeamsByGroupCode.getRequest());
	System.out.println(RetrieveTeamsByGroupCode.getResponse());
	TestReporter.assertEquals(RetrieveTeamsByGroupCode.getResponseStatusCode(), "200", "The response code was not 200");
}

	

@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode"})
public void testRetrieveTeamsByGroupCodeMainFlow(){


	String sql = "select a.grp_cd, a.grp_tm_nm " +
			"from res_mgmt.grp_tm a " +
			"where a.grp_cd in ( " +
			"select * " +
			"from (select a.grp_cd " +
			"from res_mgmt.grp_tm a " +
			"group by a.grp_cd " +
			"having count(a.grp_tm_nm) < 2 " +      
			"order by dbms_random.value )  " +
			"where rownum < 2 ) ";

	Database db = new OracleDatabase(environment, Database.DREAMS);
	Recordset rs = new Recordset(db.getResultSet(sql));
	rs.print();

	//rs.getvalue(row, column);

	//TestReporter.assertTrue(true, "Assert Failed - Here is the assert failure");

   String groupCode = rs.getValue(1,1);
   String teamCode = rs.getValue(2,1);

	RetrieveTeamsByGroupCode RetrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment, "" );
		RetrieveTeamsByGroupCode.setgroupcode(groupCode);
		RetrieveTeamsByGroupCode.sendRequest();

		
		System.out.println(RetrieveTeamsByGroupCode.getRequest());
		System.out.println(RetrieveTeamsByGroupCode.getResponse());
		
		String teamCodeRetrieved = RetrieveTeamsByGroupCode.getTeamCode();
		
		//Debug
		System.out.println("teamcode: " + teamCode);
		System.out.println("teamCodeRetr: " + teamCodeRetrieved);
		
		TestReporter.assertEquals(RetrieveTeamsByGroupCode.getResponseStatusCode(), "200", "The response code was not 200");
		
		TestReporter.assertEquals(teamCode,  teamCodeRetrieved, "The team code retrieved: "+ teamCode + " does not match teamCode in db: " + teamCodeRetrieved );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
