package com.disney.composite.api.directConnectModule.directConnect.OTA_CancelRQ;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.disney.api.soapServices.directConnect.operations.DestActivityResRQ;
import com.disney.api.soapServices.directConnect.operations.OTA_CancelRQ;
import com.disney.test.utils.Randomness;
import com.disney.test.utils.dataFactory.database.Recordset;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOTA_CancelRQ_Negative {
	private String environment ="Bashful";
	private String uniqueIdentifier = Randomness.randomAlphaNumeric(15);
	private String uniqueID = Randomness.randomNumber(7);
	private String RequestID;
	
	@BeforeClass(alwaysRun = true)
	@Parameters({ "environment" })
	public void  setup(@Optional String environment) {
		this.environment = "Bashful"; //temp solution
		
	}
	@Test(groups={ "regression", "smoke", "dirctconnect","digitalticketing","negative", "OTA_CancelRQ", "api"})
	public void TestOTA_CancelRQ_Negative_InvalidProduct(){
		TestReporter.setDebugLevel(TestReporter.DEBUG);
		OTA_CancelRQ otacancelrq = new OTA_CancelRQ(environment , "Main_Negative");
		otacancelrq.setPayloadRequestId(uniqueIdentifier);
		otacancelrq.setOTACancelEchoToken(uniqueIdentifier);
		//otacancelrq.setUniqueId(uniqueID);
		otacancelrq.sendRequest();
		TestReporter.logAPI(!otacancelrq.getResponseStatusCode().contains("200"), otacancelrq.getFaultString() ,otacancelrq);
		RequestID = otacancelrq.getRequestId();
		TestReporter.assertTrue(Regex.match("[0-9A-Za-z]+",otacancelrq.getRequestId()) , "The uniqueIdentifier is ["+RequestID+"]");
		//DB call to grab DC message
		Database db = new OracleDatabase("Bashful", Database.DIRECT_CONNECT);
		Recordset rs = new Recordset(db.getResultSet("select T.RESPONSE_MSG from ecmdc.transactions t where t.conversation_id = '"+uniqueIdentifier+"' and t.trans_status_cd in ('Failed','Success')"));
		//Write the results to the report
		rs.print(); 
		//Convert the Recordset to a string
		String responseMessage = rs.printString();
		//Grab the section where the error messages are located
		Object testvalue = responseMessage.substring(4541, 4558);
		//Convert to a string
		String errorCode = testvalue.toString();
		//Print out the error code and validate
		System.out.println(errorCode);
		TestReporter.assertTrue(errorCode.contains("Error Code=\"DC0100"),"The message contains the proper error code of:Error Code=\"DC0100\"ShortText=\"GENERAL APPLICATION EXCEPTION\" Type=\"unknown\">Error during cancelOrder(orderIdentifier, cancellationContext, externalReservationReference, partyIdentifier, applicationContext)");
	}
}