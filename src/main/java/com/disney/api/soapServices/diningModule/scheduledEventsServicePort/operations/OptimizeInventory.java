package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class OptimizeInventory extends ScheduledEventsServicePort{
	
	private boolean notSetFreezeId = true;
	public OptimizeInventory(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("optimizeInventory")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	
	
	public void setTravelPlanSegmentId(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/travelPlanSegmentId", value);}
	public void setFreezeId(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/freezeId", value);}
	public void setPartySize(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/partySize", value);}
	public void setPartymIxAdultCount(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/partyMixAdultCount", value);}
	public void setPartyMixChildCount(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/partyMixChildCount", value);}
	public void setPartyMixInfantCount(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/partyMixInfantCount", value);}
	public void setInventoryGot(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/inventoryGot", value);}
	public void setInventoryWant(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/inventoryWant", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/communicationChannel", value);}
	
	public boolean isSuccessful(){
		try{return getResponseNodeValueByXPath("/Envelope/Body/optimizeInventoryResponse/status").equals("SUCCESS");}
		catch(XPathNotFoundException e){return false;}
	}
	

	
//	public void setFreezeId(String freezeId){
//		setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/freezeId", freezeId);
//		notSetFreezeId  = false;
//	}
	public void setFreezeId(String rrId, String date){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rs = null;
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");

		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getResourceAvailibleTimesByIdAndDate(rrId, date)));
		rsInventory.print();
		String startdate = date.contains("T") 
				? date.substring(0,date.indexOf("T"))
				: date;
		String startTime = rsInventory.getValue("START_DATE").substring(rsInventory.getValue("START_DATE").indexOf(" ")+1, rsInventory.getValue("START_DATE").length()).replace(".0", "");
//		setReservableResourceId(rsInventory.getValue("Resource_ID"));
		freeze.setReservableResourceId(rrId);
		freeze.setStartDate(startdate);
		freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
		freeze.sendRequest();
//		TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
		int timesTried = 0;
//		while(freeze.getSuccess().equals("failure") && timesTried < 5){
////			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
////			rsInventory.print();
//			startdate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
//			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
////			setReservableResourceId(rsInventory.getValue("Resource_ID"));
//			freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));
//			freeze.setStartDate(startdate);
//			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
//			freeze.sendRequest();
//			if(freeze.getSuccess().equals("failure")) timesTried++;
//		}
//		if(freeze.getSuccess().equals("failure")){
			TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
//		}
		freezeId = freeze.getFreezeID();
//		setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
		setRequestNodeValueByXPath("/Envelope/Body/optimizeInventory/optimizeInventoryRequest/freezeId", freezeId);
		notSetFreezeId = false;
	}
}