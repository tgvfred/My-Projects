package com.disney.api.soapServices.diningModule.eventDiningService.operations;

import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class NoShow extends EventDiningService {
	protected String inventoryBefore;
	protected String inventoryAfter;
	public NoShow(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("noShow")));
//		System.out.println(getRequest());	
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowEventDining/reservationNumber", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowEventDining/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/noShow/noShowEventDining/communicationChannel", value);}
	public String getCancellationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/noShowEventDiningResponse/cancellationNumber");}
	
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
