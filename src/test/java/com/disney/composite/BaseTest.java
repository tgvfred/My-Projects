package com.disney.composite;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.disney.AutomationException;
import com.disney.api.soapServices.applicationError.ApplicationErrorCode;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.Environment;
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
	protected int logTimeout = 3000;
	protected int defaultTimeout = 3000;
	@BeforeSuite
	public void preSuite(){
		try{
			TestReporter.setDebugLevel(Integer.parseInt(System.getenv("debugLevel")));
		}catch(Exception e){}		
	}
	
	@BeforeMethod(alwaysRun = true)
	@Parameters("environment")
	public void setup(@Optional String environment){
		this.environment = environment;
		hh = new HouseHold(1);
	}
	
	protected void validateLogs(BaseSoapService soap, LogItems logItems){
		validate(true, soap, logItems);
	}
	
	protected void validateLogs(BaseSoapService soap, LogItems logItems, int logTimeout){
		this.logTimeout = logTimeout;
		validate(true, soap, logItems);
	}
	
	protected void validateLogs(BaseSoapService soap, LogItems logItems, String logTimeout){
		this.logTimeout = Integer.parseInt(logTimeout);
		validate(true, soap, logItems);
	}
	
	
	protected void validateNotInLogs(BaseSoapService soap, LogItems logItems){
		logTimeout = defaultTimeout;
		validate(false,soap,logItems);
	}
	
	
	protected void validateNotInLogs(BaseSoapService soap, LogItems logItems, int logTimeout){
		this.logTimeout = logTimeout;
		validate(false,soap,logItems);
	}
	
	protected void validateApplicationError(BaseSoapService soap, ApplicationErrorCode error){
		TestReporter.logDebug("Entering BaseTest#validateApplicationError");
		TestReporter.logStep("Validate Application Error<br/></font>Expected Error Details" + error.toString().replace("\n", "<br/>"));
		
		TestReporter.logDebug("Validating Application Error Description");
		if(soap.getServiceExceptionApplicationFaultMessage().contains(error.getDesciption())){
			TestReporter.logDebug("Validated Application Error Description");
			TestReporter.assertTrue(true, "Exception Message was  [" + error.getDesciption() + "] as expected.");
		}else{
			TestReporter.logDebug("Validation of Application Error Description failed");
			TestReporter.logAPI(true, "Exception Message was not [" + error.getDesciption() + "] as expected. Instead found [" +soap.getServiceExceptionApplicationFaultMessage() + "]",soap);
		}

		TestReporter.logDebug("Validating Application Error Code");
		if(soap.getServiceExceptionApplicationFaultCode().contains(error.getErrorCode())){
			TestReporter.assertTrue(true, "Exception Code was [" + error.getErrorCode() + "] as expected.");
			TestReporter.logDebug("Validated Application Error Code");
		}else{
			TestReporter.logDebug("Validation of Application Error Code failed");
			TestReporter.logAPI(true, "Exception Code was not [" + error.getErrorCode() + "] as expected. Instead found [" +soap.getServiceExceptionApplicationFaultCode() + "]",soap);
		}

		TestReporter.logDebug("Validating Application Module Name ");
		if(soap.getServiceExceptionApplicationFaultModule().contains(error.getModuleName())){
			TestReporter.logDebug("Validated Application Module Name ");
			TestReporter.assertTrue(true, "Exception Module was  [" + error.getModuleName() + "] as expected.");
		}else{
			TestReporter.logDebug("Validation of Application Module Name failed");
			TestReporter.logAPI(true, "Exception Module was not [" + error.getModuleName() + "] as expected. Instead found [" +soap.getServiceExceptionApplicationFaultModule() + "]" ,soap);
		}
		TestReporter.logDebug("Exiting BaseTest#validateApplicationError");
	}
	
	private void validate(boolean shouldBeInLogs, BaseSoapService soap, LogItems logItems){
		TestReporter.logDebug("Entering BaseTest#validate");

		boolean isValid = false;
		boolean containsRequest = false;
		boolean containsResponse = false;
		if(!Environment.getEnvironmentName(environment).equalsIgnoreCase("Grumpy") && !environment.equalsIgnoreCase("Development") && !environment.contains("_CM")){
				
			Recordset rs = getLogs(environment, soap.getConversationID());
			
			for(LogItem item : logItems.getItems()){
				isValid = false;
				containsRequest = false;
				containsResponse = false;
				
				// Uncomment the below line to view the service#operation under validation
//				System.out.println(item.getServiceClass() + "#" + item.getServiceOperation());

				TestReporter.logInfo("Searching for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "] in logs");
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
						//TestReporter.logDebug("Searching for Exceptions in log for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "]");
						//Ensure Response does not have an Error Code if an error is not expected
						if(rs.getValue("LOG_LVL").equalsIgnoreCase("Exception") || !rs.getValue("ERROR_CODE").equalsIgnoreCase("null")){
							if((rs.getValue("SVC_CLASS").contains(item.getServiceClass()) || 
									rs.getValue("SVC_OPERATION").equals(item.getServiceOperation()))){
								TestReporter.logDebug("Exception found for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "] in logs");
								if( item.isErrorExpected() == false){
									TestReporter.logDebug("Exception not expected in log for [" + item.getServiceClass() + "#" + item.getServiceOperation() + "] in logs");
									TestReporter.logDebug("Exception found [" + rs.getValue("ERROR_CODE") + " : " +rs.getValue("LOG_MSG_TXT")  + "] ");
									throw new AutomationException("Exception found in [" + item.getServiceClass() +"#"+ item.getServiceOperation() + "]\n" 
									+ rs.getValue("ERROR_CODE") + " : " +rs.getValue("LOG_MSG_TXT"));							
								}
							}
						}/*else if(item.isErrorExpected()){
							throw new AutomationException("Exception was not found in [" + item.getServiceClass() +"#"+ item.getServiceOperation() + "] but was expected");
						}*/
					}
					
					//Break out of loop once Request and Response is validated
					if (containsRequest && containsResponse) {
						TestReporter.logInfo("Found [" + item.getServiceClass() + "#" + item.getServiceOperation() + "] in logs");
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
			TestReporter.log("Skipping log validation for [" + environment + "] environment");
		}
		TestReporter.logDebug("Exiting BaseTest#validate");
	}
	
	private Recordset getLogs(String environment, String convoId){

		TestReporter.logDebug("Entering BaseTest#getLogs");
		TestReporter.logDebug("Waiting for [" + String.valueOf(logTimeout/1000) + "] seconds to ensure logs are pushed to DB");
		Sleeper.sleep(logTimeout); //Adding delay to ensure all logs are in DB 
		TestReporter.logDebug("Done waiting for logs");
		String sql = "SELECT LOG_ID, LOG_LVL, LOG_MSG_TXT, ERROR_CODE, APP_NAME, BP_STEP, SVC_CLASS, SVC_OPERATION"
				+ "	FROM DRMSLOG.LOG_MSG "
				+ " WHERE CNVRSTN_ID = '"+ convoId+"' "
						+ "AND SVC_CLASS IS Not Null "
					//	+ "AND BP_STEP like 'Outbound%' "
						+ "ORDER BY SVC_CLASS ASC";
		
		// Uncomment the below line to output the SQL to the console
//		System.out.println(sql);
		TestReporter.logInfo("SQL to search for log \n "+ sql);
		Database db = new OracleDatabase(environment, Database.DREAMS_LOG);
		Recordset rs = new Recordset(db.getResultSet(sql));

		// Uncomment the below line to output the recordset to the console
//		rs.print();
		TestReporter.logInfo("Records found \n" + rs.printString());

		TestReporter.logDebug("Exiting BaseTest#getLogs");
		return rs;
	}
}
