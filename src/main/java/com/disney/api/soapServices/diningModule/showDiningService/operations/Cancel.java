package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.test.utils.Sleeper;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Cancel extends ShowDiningService {
	protected String inventoryBefore;
	protected String inventoryAfter;
	public Cancel(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanSegmentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelShowDiningRequest/reservationNumber", value);
	}
	
	public String getCancellationConfirmationNumber(){
		return getResponseNodeValueByXPath("/Envelope/Body/cancelShowDiningResponse/cancellationNumber");
	}
	
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
