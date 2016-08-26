package com.disney.api.soapServices.activityModule.activityServicePort.operations;

import com.disney.api.soapServices.activityModule.activityServicePort.ActivityService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Cancel extends ActivityService {
	protected String inventoryBefore;
	protected String inventoryAfter;
	public Cancel(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
		generateServiceContext();	
		
        generateServiceContext();			
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelActivityComponentRequest/reservationNumber", value);}	
	/**
	 * Gets the cancellation number from the SOAP response
	 * @return - cancellation number
	 */
	public String getCancellationConfirmationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/cancelActivityComponentResponse/cancellationNumber");}
	
	public void sendRequest(String rrId, String dateTime){
		inventoryBefore = getInventory(rrId, dateTime);
		super.sendRequest();
		int maxTries = 10;
		int tries = 0;
		do{
			Sleeper.sleep(1000);
			tries++;
			inventoryAfter = getInventory(rrId, dateTime);
		}while(tries <= maxTries && !inventoryAfter.equals(inventoryBefore));		
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