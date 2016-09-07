package com.disney.api.soapServices.activityModule.activityServicePort.operations;

import com.disney.api.soapServices.activityModule.activityServicePort.ActivityService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class NoShow extends ActivityService {
	protected String inventoryBefore;
	protected String inventoryAfter;
	public NoShow(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("noShow")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowActivityComponentRequest/reservationNumber", value);}
	/**
	 * Sets the sales channel in the SOAP request
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowActivityComponentRequest/salesChannel", value);}
	/**
	 * Sets the communication channel in the SOAP request
	 * @param value - communication channel 
	 */
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowActivityComponentRequest/communicationChannel", value);}
	/**
	 * Gets the cancellation number, from updating the reservation to 'No Show', in the response
	 * @return cancellation number
	 */
	public String getCancellationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/noShowActivityComponentResponse/cancellationNumber");}
	
	public void sendRequest(String rrId, String dateTime){
		inventoryBefore = getInventory(rrId, dateTime);
		super.sendRequest();
		Sleeper.sleep(5000);
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