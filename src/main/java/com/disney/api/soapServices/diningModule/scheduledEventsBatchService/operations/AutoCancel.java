package com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;

public class AutoCancel extends ScheduledEventsBatchService{

	//Define a base query with values that indicate areas that need to be modified at runtime
	private String query = "select TC_GRP_NB as TCG from res_mgmt.tc_grp a where a.tps_id = '{TPS_ID}'";
	private String database = "DREAMS";
	private String environment;
	
	
	public AutoCancel(String environment, String scenario) {
		super(environment);
		this.environment = environment;

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("autoCancel")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTravelComponentGroupingId(String value){setRequestNodeValueByXPath("/Envelope/Body/autoCancel/travelComponentGroupingId", value);}
	
	public String getTravelPlanSegmentId(){return getResponseNodeValueByXPath("/Envelope/Body/autoCancelResponse/return/travelPlanSegmentId");}
	public String getTravelStatus(){return getResponseNodeValueByXPath("/Envelope/Body/autoCancelResponse/return/travelStatus");}

	/**
	 * 
	 * @param environment - Dreams Log environment to query
	 * @param endTime - The maximum date-time stamp with which to search for records (i.e. the events should have occurred prior to this time)
	 * @param startTime - The minimum date-time stamp with which to search for records (i.e. the events should have occurred before this time)
	 * @param conversationId - conversation ID as indicated by the application
	 * @param eventType - expected type of event for which to search
	 * @param reservationNumber - expected reservation number expected to be linked to NGE events
	 * @param expectedNumberOfEvents - number of events expected from this query
	 */
	public String getTravelComponentGroupIdUsingTPS(String tps_id) {
		query = query.replace("{TPS_ID}", tps_id);
		OracleDatabase odb = new OracleDatabase(environment, database);
		Recordset resultSet;
		resultSet = new Recordset(odb.getResultSet(query));

		resultSet.print();
		TestReporter.assertTrue(resultSet.getRowCount() > 0, "No Travel Component Group ID was found to be associated with TPS_ID ["+tps_id+"] in the ["+environment+"] environment.");
		int column = resultSet.getColumnIndex("TCG");
		return resultSet.getValue(column, 1);
	}
}
