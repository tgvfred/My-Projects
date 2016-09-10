package com.disney.composite.api.directConnectModule.directConnect.OTA_CancelRQ;

import org.testng.annotations.Test;

import com.disney.api.soapServices.directConnect.operations.DestActivityResRQ;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Randomness;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class TestOTA_CancelRQ {
	// Defining global variables
		private String environment ="Bashful";
		private String uniqueIdentifier = Randomness.randomAlphaNumeric(15);
		private String uniqueID = Randomness.randomNumber(7);
		private String RequestID;
	
	//@Test(groups = {"api", "regression", "directConnect", "OTA_CancelRQ"})
			@Test
			public void testCancel(){
				DestActivityResRQ res = new DestActivityResRQ(environment, "NoComponentsNoAddOns");
				
				res.sendRequest();
				if(res.getResponse().toLowerCase().contains("unique constraint")){
					Sleeper.sleep(Randomness.randomNumberBetween(1, 5) * 1000);
					res.sendRequest();
				}
				Database db = new OracleDatabase("Bashful", Database.DIRECT_CONNECT);
		        Recordset rs = new Recordset(db.getResultSet("select T.RESPONSE_MSG from ecmdc.transactions t where t.conversation_id = '201608221705KJM' and t.trans_status_cd in ('Failed','Success')"));
		        rs.print();
			}
}
