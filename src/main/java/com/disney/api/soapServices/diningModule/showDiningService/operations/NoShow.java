package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class NoShow extends ShowDiningService {
	private String inventoryBefore;
	private String inventoryAfter;
	public NoShow(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("noShow")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowShowDiningRequest/reservationNumber", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowShowDiningRequest/communicationChannel", value);
	}
	
	public String getCancellationConfirmationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/noShowShowDiningResponse/cancellationNumber");
	}
	
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