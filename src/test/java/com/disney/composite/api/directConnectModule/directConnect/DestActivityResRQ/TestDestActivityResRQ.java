package com.disney.composite.api.directConnectModule.directConnect.DestActivityResRQ;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.disney.api.soapServices.directConnect.operations.DestActivityResRQ;
import com.disney.composite.BaseTest;
import com.disney.test.utils.Sleeper;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.Randomness;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestDestActivityResRQ extends BaseTest {
	// Defining global variables
	private String environment ="Bashful";
	private String uniqueIdentifier = Randomness.randomAlphaNumeric(15);
	private String uniqueID = Randomness.randomNumber(7);
	private String RequestID;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void  setup(@Optional String environment) {
		this.environment = "Bashful"; //temp solution
		
	}

	
	@Test(groups={ "regression", "smoke", "dirctconnect", "DestActivityResRQ", "api"})
	public void TestDestActivityResRQ_SuccessfulDTIBooking(){
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		DestActivityResRQ destactivityresrq = new DestActivityResRQ(environment , "Positive");
		destactivityresrq.setPayloadRequestId(uniqueIdentifier);
		destactivityresrq.setEchoToken(uniqueIdentifier);
		destactivityresrq.setUniqueId(uniqueID);
		destactivityresrq.sendRequest();
		TestReporter.logAPI(!destactivityresrq.getResponseStatusCode().contains("200"), destactivityresrq.getFaultString() ,destactivityresrq);
		RequestID = destactivityresrq.getRequestId();
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z]+",destactivityresrq.getRequestId()) , "The uniqueIdentifier is ["+RequestID+"]");
		//DB call
		Database db = new OracleDatabase("Bashful", Database.DIRECT_CONNECT);
		Recordset rs = new Recordset(db.getResultSet("select T.RESPONSE_MSG from ecmdc.transactions t where t.conversation_id = '"+uniqueIdentifier+"' and t.trans_status_cd in ('Failed','Success')"));
		//Write the results to the report
		rs.print(); 
		//Convert the Recordset to a string
		String responseMessage = rs.printString();
		//Grab the section where the error messages are located
		Object testvalue = responseMessage.substring(4564, 4582);
		//Convert to a string
		String reservationNum = testvalue.toString();
		//Print out the error code and validate
		System.out.println(reservationNum);
		TestReporter.assertTrue(reservationNum.contains("Error Code=\"DC7505"),"The message contains the proper error code of: Error Code=\"DC7505\" ShortText=\"Product Not Permitted\" Type=\"unknown\">Product Not Permitted</Error ");
	}
}
