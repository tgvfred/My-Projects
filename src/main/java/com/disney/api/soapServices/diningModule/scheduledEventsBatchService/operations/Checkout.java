package com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations;

import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Checkout extends ScheduledEventsBatchService{
	protected String inventoryBefore;
	protected String inventoryAfter;
	public Checkout(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("checkout")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	public void setTravelPlanSegmentId(String tpsId){
		setRequestNodeValueByXPath("/Envelope/Body/checkout/checkoutRequest/travelPlanSegmentId", tpsId);
	}
	
	public boolean isSuccessfullyCheckedOut(){
		return getResponseNodeValueByXPath("/Envelope/Body/checkoutResponse/checkoutResponseDetail/isSuccess").equals("true");		
	}

	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/checkoutResponse/checkoutResponseDetail/travelPLanSegmentId");}
	
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