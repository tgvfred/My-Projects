package com.disney.composite.api.directConnectModule.directConnect.DestActivityResRQ;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.disney.api.soapServices.directConnect.operations.DestActivityResRQ;
import com.disney.test.utils.Randomness;
import com.disney.test.utils.dataFactory.database.Recordset;
import com.disney.utils.Regex;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestDestActivityResRQ_Negative {
		private String environment ="Bashful";
		private String uniqueIdentifier = Randomness.randomAlphaNumeric(15);
		private String uniqueID = Randomness.randomNumber(7);
		private String RequestID;
		
		@BeforeClass(alwaysRun = true)
		@Parameters({ "environment" })
		public void  setup(@Optional String environment) {
			this.environment = "Bashful"; //temp solution
			
		}

		@Test(groups={ "regression", "smoke", "dirctconnect","digitalticketing","negative", "DestActivityResRQ", "api"})
		public void TestDestActivityResRQ_Negative_InvalidProduct(){
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			DestActivityResRQ destactivityresrq = new DestActivityResRQ(environment , "Main");
			destactivityresrq.setPayloadRequestId(uniqueIdentifier);
			destactivityresrq.setEchoToken(uniqueIdentifier);
			destactivityresrq.setUniqueId(uniqueID);
			destactivityresrq.sendRequest();
			TestReporter.logAPI(!destactivityresrq.getResponseStatusCode().contains("200"), destactivityresrq.getFaultString() ,destactivityresrq);
			RequestID = destactivityresrq.getRequestId();
			TestReporter.assertTrue(Regex.match("[0-9A-Za-z]+",destactivityresrq.getRequestId()) , "The uniqueIdentifier is ["+RequestID+"]");
			//DB call to grab DC message
			Database db = new OracleDatabase("Bashful", Database.DIRECT_CONNECT);
			Recordset rs = new Recordset(db.getResultSet("select T.RESPONSE_MSG from ecmdc.transactions t where t.conversation_id = '"+uniqueIdentifier+"' and t.trans_status_cd in ('Failed','Success')"));
			//Write the results to the report
			rs.print(); 
			//Convert the Recordset to a string
			String responseMessage = rs.printString();
			//Grab the section where the error messages are located
			Object testvalue = responseMessage.substring(4564, 4582);
			//Convert to a string
			String errorCode = testvalue.toString();
			//Print out the error code and validate
			System.out.println(errorCode);
			TestReporter.assertTrue(errorCode.contains("Error Code=\"DC7505"),"The message contains the proper error code of: Error Code=\"DC7505\" ShortText=\"Product Not Permitted\" Type=\"unknown\">Product Not Permitted</Error ");
		}
		@Test(groups={ "regression", "smoke", "dirctconnect","digitalticketing","negative", "DestActivityResRQ", "api"})
		public void TestDestActivityResRQ_Negative_41Tickets(){
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			DestActivityResRQ destactivityresrq = new DestActivityResRQ(environment , "MaxTickets");
			destactivityresrq.setPayloadRequestId(uniqueIdentifier);
			destactivityresrq.setEchoToken(uniqueIdentifier);
			destactivityresrq.setUniqueId(uniqueID);
			destactivityresrq.sendRequest();
			TestReporter.logAPI(!destactivityresrq.getResponseStatusCode().contains("200"), destactivityresrq.getFaultString() ,destactivityresrq);
			RequestID = destactivityresrq.getRequestId();
			TestReporter.assertTrue(Regex.match("[0-9A-Za-z]+",destactivityresrq.getRequestId()) , "The uniqueIdentifier is ["+RequestID+"]");
			//DB call to grab DC message
			Database db = new OracleDatabase("Bashful", Database.DIRECT_CONNECT);
			Recordset rs = new Recordset(db.getResultSet("select T.RESPONSE_MSG from ecmdc.transactions t where t.conversation_id = '"+uniqueIdentifier+"' and t.trans_status_cd in ('Failed','Success')"));
			//Write the results to the report
			rs.print(); 
			//Convert the Recordset to a string
			String responseMessage = rs.printString();
			//Grab the section where the error messages are located
			Object testvalue = responseMessage.substring(4486, 4504);
			//Convert to a string
			String errorCode = testvalue.toString();
			//Print out the error code and validate
			System.out.println(errorCode);
			TestReporter.assertTrue(errorCode.contains("Error Code=\"DC0462"),"The message contains the proper error code of: Error Code=\"DC0462\"ShortText=\"Ticket quantity exceeded.\" Type=\"unknown\">Ticket quantity exceeded.");
		}
		@Test(groups={ "regression", "smoke", "dirctconnect","digitalticketing","negative", "DestActivityResRQ", "api"})
		public void TestDestActivityResRQ_Negative_CannotConnectToSystemOfRecord(){
			TestReporter.setDebugLevel(TestReporter.DEBUG);
			DestActivityResRQ destactivityresrq = new DestActivityResRQ(environment , "Main");
			destactivityresrq.setPayloadRequestId(uniqueIdentifier);
			destactivityresrq.setEchoToken(uniqueIdentifier);
			destactivityresrq.setUniqueId(uniqueID);
			destactivityresrq.sendRequest();
			TestReporter.logAPI(!destactivityresrq.getResponseStatusCode().contains("200"), destactivityresrq.getFaultString() ,destactivityresrq);
			RequestID = destactivityresrq.getRequestId();
			TestReporter.assertTrue(Regex.match("[0-9A-Za-z]+",destactivityresrq.getRequestId()) , "The uniqueIdentifier is ["+RequestID+"]");
			//DB call to grab DC message
			Database db = new OracleDatabase("Bashful", Database.DIRECT_CONNECT);
			Recordset rs = new Recordset(db.getResultSet("select T.RESPONSE_MSG from ecmdc.transactions t where t.conversation_id = '"+uniqueIdentifier+"' and t.trans_status_cd in ('Failed','Success')"));
			//Write the results to the report
			rs.print(); 
			//Convert the Recordset to a string
			String responseMessage = rs.printString();
			//Grab the section where the error messages are located
			Object testvalue = responseMessage.substring(4564, 4582);
			//Convert to a string
			String errorCode = testvalue.toString();
			//Print out the error code and validate
			System.out.println(errorCode);
			TestReporter.assertTrue(errorCode.contains("Error Code=\"DC0022"),"The message contains the proper error code of: Error Code=\"DC0022\"ShortText=\"Could not connect to System of Record.\" Type=\"unknown\">Could not connect to System of Record.");
		}
}
