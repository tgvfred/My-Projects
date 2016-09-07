package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Arrived extends ShowDiningService {
	private String inventoryBefore;
	private String inventoryAfter;
	public Arrived(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("arrived")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedShowDiningRequest/reservationNumber", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/arrived/arrivedShowDiningRequest/communicationChannel", value);
	}

	public String getResponseStatus(){
		return getResponseNodeValueByXPath("/Envelope/Body/arrivedShowDiningResponse/status");
	}
	
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