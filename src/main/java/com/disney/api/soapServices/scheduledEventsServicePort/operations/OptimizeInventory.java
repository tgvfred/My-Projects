package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class OptimizeInventory extends ScheduledEventsServicePort{
	
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
}