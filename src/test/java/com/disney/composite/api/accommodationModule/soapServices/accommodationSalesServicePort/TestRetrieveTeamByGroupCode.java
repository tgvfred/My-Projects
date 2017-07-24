package com.disney.composite.api.accommodationModule.soapServices.accommodationSalesServicePort;

import java.util.List;

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
		// Skip setting group codes. 
		RetrieveTeamsByGroupCode.sendRequest();
		System.out.println(RetrieveTeamsByGroupCode.getRequest());
		System.out.println(RetrieveTeamsByGroupCode.getResponse());
		TestReporter.assertEquals(RetrieveTeamsByGroupCode.getResponseStatusCode(), "200", "The response code was not 200");
		try {
			TestReporter.assertNull( RetrieveTeamsByGroupCode.getTeamCode(), "The team code retrieved: "+  RetrieveTeamsByGroupCode.getTeamCode() + ".   Expected null" );
		} catch (Exception e) {
			// continue
		}finally {
			// clean up
		}
	}

	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode"})
	public void testRetrieveTeamsByGroupCodeSingle(){

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

		String groupCode = rs.getValue(1,1);
		String teamCode = rs.getValue(2,1);

		RetrieveTeamsByGroupCode RetrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment, "" );
		RetrieveTeamsByGroupCode.setgroupcode(groupCode);
		RetrieveTeamsByGroupCode.sendRequest();

		String teamCodeRetrieved = RetrieveTeamsByGroupCode.getTeamCode();

		//Debug
		System.out.println("teamcode: " + teamCode);
		System.out.println("teamCodeRetr: " + teamCodeRetrieved);

		TestReporter.assertEquals(RetrieveTeamsByGroupCode.getResponseStatusCode(), "200", "The response code was not 200");

		TestReporter.assertEquals(teamCode,  teamCodeRetrieved, "The team code retrieved: "+ teamCode + " does not match teamCode in db: " + teamCodeRetrieved );
	}	


	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode"})
	public void testRetrieveTeamsByGroupCodeMulitple(){

		String sql = "select a.grp_cd, a.grp_tm_nm " +
				"from res_mgmt.grp_tm a " +
				"where a.grp_cd in ( " +
				"select * " +
				"from (select a.grp_cd " +
				"from res_mgmt.grp_tm a " +
				"group by a.grp_cd " +
				"having count(a.grp_tm_nm) >= 2 " +      
				"order by dbms_random.value )  " +
				"where rownum < 2 ) ";

		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		rs.print();

		String groupCode = rs.getValue(1,1);
		String teamCode;

		RetrieveTeamsByGroupCode retrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment, "" );
		retrieveTeamsByGroupCode.setgroupcode(groupCode);
		retrieveTeamsByGroupCode.sendRequest();

		TestReporter.logAPI(!retrieveTeamsByGroupCode.getResponseStatusCode().equals("200"), "The response code was not 200", retrieveTeamsByGroupCode);

		System.out.println(retrieveTeamsByGroupCode.getCountOfTeamCode());
		List<String> teams = retrieveTeamsByGroupCode.getTeamNames();

		System.out.println();
		//Debug
		//System.out.println(RetrieveTeamsByGroupCode.getRequest());
		//System.out.println(RetrieveTeamsByGroupCode.getResponse());

		String teamCodeRetrieved = retrieveTeamsByGroupCode.getTeamCode();

		// Compare Each Row
		int rows = rs.getRowCount();
		boolean found = false;
		for (rs.moveFirst(); rs.hasNext(); rs.moveNext()) {
			for(String name : teams){
				if(name.equalsIgnoreCase(rs.getValue("grp_tm_nm"))){
					found = true;
					break;
				}	
			}
			if(found){
				TestReporter.softAssertTrue(true, "Team name [ " + rs.getValue("grp_tm_nm") + " ] was found as expected");
			}else{
				TestReporter.softAssertTrue(false, "Team name [ " + rs.getValue("grp_tm_nm") + " ] was not found as expected");
			}
			found = false;
		}

		TestReporter.assertAll();
	}


	@Test(groups={"api", "regression", "accommodation", "accommodationSalesService", "RetrieveTeamsByGroupCode"})
	public void testRetrieveTeamsByGroupCodeNoTeamNames(){

		String sql = "select a.grp_cd, b.grp_tm_nm " +
				"from group_mgmt.grp a " +
				"left outer join res_mgmt.grp_tm b on a.grp_cd = b.grp_cd " +
				"where b.grp_tm_nm is null " +
				"and rownum <= 5 " +
				"order by dbms_random.value" ;

		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(sql));
		rs.print();

		String groupCode = rs.getValue(1,1);
		String teamCode;

		RetrieveTeamsByGroupCode retrieveTeamsByGroupCode = new RetrieveTeamsByGroupCode(environment, "" );
		retrieveTeamsByGroupCode.setgroupcode(groupCode);
		retrieveTeamsByGroupCode.sendRequest();

		TestReporter.logAPI(!retrieveTeamsByGroupCode.getResponseStatusCode().equals("200"), "The response code was not 200", retrieveTeamsByGroupCode);

		System.out.println(retrieveTeamsByGroupCode.getCountOfTeamCode());
		//List<String> teams = retrieveTeamsByGroupCode.getTeamNames();
		try	{
			String teamCodeRetrieved = retrieveTeamsByGroupCode.getTeamCode();

			TestReporter.softAssertNull(teamCodeRetrieved, "Team Names unexpectedly found. Should be nil ");

		}
		catch (Exception e) {
			// no catch
		} finally {
			TestReporter.assertAll();	
			System.out.println("End Finally on No Team Names");
		}	
	}
}





