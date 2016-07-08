package com.disney.composite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.AutomationException;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.LogItems;
import com.disney.utils.dataFactory.database.LogItems.LogItem;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class BaseTest {
	
	protected String environment;
	protected HouseHold hh = null;
	//private List<LogItems> logItems = new ArrayList<LogItems>();
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = "Sleepy";
		hh = new HouseHold(1);
	}
	
	protected void validateLogs(BaseSoapService soap, LogItems logItems){
		validate(true, soap, logItems);
	}
	
	
	protected void validateNotInLogs(BaseSoapService soap, LogItems logItems){
		validate(false,soap,logItems);
	}
	
	private void validate(boolean shouldBeInLogs, BaseSoapService soap, LogItems logItems){
		boolean isValid = false;
		boolean containsRequest = false;
		boolean containsResponse = false;
		if(!environment.equalsIgnoreCase("Grumpy") && !environment.equalsIgnoreCase("Development")){
				
			Recordset rs = getLogs(environment, soap.getConversationID());
			
			for(LogItem item : logItems.getItems()){
				isValid = false;
				containsRequest = false;
				containsResponse = false;
				
				System.out.println(item.getServiceClass() + "#" + item.getServiceOperation());
				
				for(rs.moveFirst() ; rs.hasNext() ; rs.moveNext()){
					
					//Ensures Request for desired operation exists
					if(!containsRequest){
						containsRequest = (rs.getValue("SVC_CLASS").contains(item.getServiceClass()) && 
											rs.getValue("SVC_OPERATION").equals(item.getServiceOperation()) &&
											rs.getValue("BP_STEP").contains("Request")) ? true : false;
					}
					
					//Ensure Response for desired Operation exists
					if(!containsResponse){
						containsResponse = (rs.getValue("SVC_CLASS").contains(item.getServiceClass()) &&
											rs.getValue("SVC_OPERATION").equals(item.getServiceOperation()) && 
											(rs.getValue("BP_STEP").contains("Exception") || rs.getValue("BP_STEP").contains("Response"))) ? true : false;
					}
					
					if(shouldBeInLogs){
						//Ensure Response does not have an Error Code if an error is not expected
						if(rs.getValue("LOG_LVL").equalsIgnoreCase("Exception") || !rs.getValue("ERROR_CODE").equalsIgnoreCase("null")){
							if((rs.getValue("SVC_CLASS").contains(item.getServiceClass()) || 
									rs.getValue("SVC_OPERATION").equals(item.getServiceOperation()))){
								if( item.isErrorExpected() == false){
									System.out.println(rs.getValue("ERROR_CODE") + " : " +rs.getValue("LOG_MSG_TXT") );
									throw new AutomationException("Exception found in [" + item.getServiceClass() +"#"+ item.getServiceOperation() + "]\n" 
									+ rs.getValue("ERROR_CODE") + " : " +rs.getValue("LOG_MSG_TXT"));							
								}
							}
						}
					}
					
					//Break out of loop once Request and Response is validated
					if (containsRequest && containsResponse) {
						isValid = true;
						break;
					}					
				}
	
				// Throw exceptions if Request or Response is not found 
				if(!shouldBeInLogs && containsRequest) throw new AutomationException("Unexpected request found for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "]. Convo ID: " + soap.getConversationID());
				if(!shouldBeInLogs && containsResponse) throw new AutomationException("Unexpected response found for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "]. Convo ID: " + soap.getConversationID());
				if(shouldBeInLogs && !containsRequest) throw new AutomationException("Expected request not found for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "]. Convo ID: " + soap.getConversationID());
				if(shouldBeInLogs && !containsResponse) throw new AutomationException("Expected response not found for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "]. Convo ID: " + soap.getConversationID());
			}
		}else{
			TestReporter.log("Skipping log validation for Grumpy and Development");
		}
	}
	
	private Recordset getLogs(String environment, String convoId){
		Sleeper.sleep(3000); //Adding delay to ensure all logs are in DB 
		
		String sql = "SELECT LOG_ID, LOG_LVL, LOG_MSG_TXT, ERROR_CODE, APP_NAME, BP_STEP, SVC_CLASS, SVC_OPERATION"
				+ "	FROM DRMSLOG.LOG_MSG "
				+ " WHERE CNVRSTN_ID = '"+ convoId+"' "
						+ "AND SVC_CLASS IS Not Null "
					//	+ "AND BP_STEP like 'Outbound%' "
						+ "ORDER BY SVC_CLASS ASC";
		
		System.out.println(sql);
		Database db = new OracleDatabase(environment, Database.DREAMS_LOG);
		Recordset rs = new Recordset(db.getResultSet(sql));
		rs.print();
		
		return rs;
	}
}