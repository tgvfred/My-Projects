package com.disney.api.soapServices.activityModule.activityServicePort.operations;

import com.disney.api.soapServices.activityModule.activityServicePort.ActivityService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Arrived extends ActivityService {
	private String inventoryBefore;
	private String inventoryAfter;
	public Arrived(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/reservationNumber", value);}
	/**
	 * Sets the sales channel in the SOAP request
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/salesChannel", value);}
	/**
	 * Sets the communications channel in the SOAP request
	 * @param value - communications channel
	 */
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedActivityComponentRequest/communicationChannel", value);}
	/**
	 * Gets the status from updating the reservation to 'Arrived'
	 * @return - status from updating the reservation to 'Arrived'
	 */
	public String getArrivalStatus(){return getResponseNodeValueByXPath("/Envelope/Body/arrivedActivityComponentResponse/status");}
	
	public void sendRequest(String rrId, String dateTime){
		inventoryBefore = getInventory(rrId, dateTime);
		super.sendRequest();
		inventoryAfter = getInventory(rrId, dateTime);		
	}
	private String getInventory(String rrId, String dateTime){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getAvailableResourceCount(rrId, dateTime)));
		rsInventory.print();
		return rsInventory.getValue("BK_CN");
	}
	public String getInventoryCountBefore(){return inventoryBefore;}
	public String getInventoryCountAfter(){return inventoryAfter;}
}