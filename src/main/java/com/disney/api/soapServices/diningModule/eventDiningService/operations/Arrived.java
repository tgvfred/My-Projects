package com.disney.api.soapServices.diningModule.eventDiningService.operations;

import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Arrived extends EventDiningService {
	protected String inventoryBefore;
	protected String inventoryAfter;
	public Arrived(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedEventDiningRequest/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedEventDiningRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedEventDiningRequest/communicationChannel", value);}
	public String getArrivalStatus(){return getResponseNodeValueByXPath("/Envelope/Body/arrivedEventDiningResponse/status");}
	
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
