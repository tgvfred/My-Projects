package com.disney.composite.api.partyV3.searchGuestIDByExternalReference;

import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.partyV3.operations.SearchGuestIDByExternalReference;
import com.disney.composite.BaseTest;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.Dreams;
import com.disney.utils.dataFactory.guestFactory.Guest;

public class TestSearchGuestIDByExternalReference  extends BaseTest{
	private Guest guest = new Guest();
	
	@BeforeTest(alwaysRun = true)
	@Parameters({ "environment" })
	public void setup(@Optional String environment){
		this.environment = environment;
		guest.sendToApi(this.environment, true);
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByODS(){
		TestReporter.logScenario("Search By External Reference Type and Value");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", guest.getOdsId());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(Regex.match("[0-9]+", search.getPartyId()), "The Party ID ["+search.getPartyId()+"] is not numeric as expected.");
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByIataNumber(){
		String type = "IATANUMBER";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDBySwid(){
		String type = "SWID";
		TestReporter.logScenario("Search By External Reference Type " + type);
	
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByGuid(){
		String type = "GUID";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByPassportID(){
		String type = "PASSPORT";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}

	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByXbmsID(){
		String type = "XBMS";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}

	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByOriginalTxnLGuestID(){
		String type = "ORIGINAL_TXN_GUEST_ID";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}
	

	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByFolioID(){
		String type = "FOLIOID";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}
	

	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByPlid(){
		String type = "PLID";
		TestReporter.logScenario("Search By External Reference Type " + type);
		
		// Find existing data in environment to use
		Database db = new OracleDatabase(environment, Database.DREAMS);
		Recordset rs = new Recordset(db.getResultSet(Dreams.getGuestExternalReferenceInfoByType(type)));
		
		// If no data found, then skip test without failing
		if(rs.getRowCount() == 0) throw new SkipException("No pre-existing external references with type [" + type +"] ");
		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(type, rs.getValue("TXN_PTY_EXTNL_REF_VAL"));
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getPartyId().equals(rs.getValue("TXN_PTY_ID")), "The Party ID ["+search.getPartyId()+"] is not ["+rs.getValue("TXN_PTY_ID")+"] as expected.");
	}
	
	
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Type_NoResults(){
		TestReporter.logScenario("Search By External Reference Type, No Results");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("?", guest.getOdsId());
		search.sendRequest();		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_NoTypeNode(){
		TestReporter.logScenario("Search By External Reference Type Node, No Results");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference(BaseSoapCommands.REMOVE_NODE.toString(), guest.getOdsId());
		search.sendRequest();
		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_Values_NoResults(){
		TestReporter.logScenario("Search By External Reference Value, No Results");
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", "-1");
		search.sendRequest();		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 0, "Party IDs were found when none were expected.");
	}
	
	@Test(groups = {"api", "regression", "party", "partyV3"})
	public void testSearchGuestIDByExternalReference_MultipleValues(){
		TestReporter.logScenario("Search By External Reference Type and Multiple Values");
		Guest guest2 = new Guest();
		guest2.sendToApi(environment, true);		
		SearchGuestIDByExternalReference search = new SearchGuestIDByExternalReference(this.environment);
		search.setGuestExternalReference("ODS", guest.getOdsId());
		search.addExternalReferenceValue(guest2.getOdsId());
		search.sendRequest();		
		TestReporter.logAPI(!search.getResponseStatusCode().contains("200"), search.getFaultString(), search);
		TestReporter.assertTrue(search.getNumberOfResponsePartyIds() == 2, "Party IDs were found when none were expected.");
	}
}