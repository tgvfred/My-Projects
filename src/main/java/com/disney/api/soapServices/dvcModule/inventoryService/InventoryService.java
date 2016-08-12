package com.disney.api.soapServices.dvcModule.inventoryService;

import com.disney.api.soapServices.core.BaseSoapService;

public class InventoryService extends BaseSoapService{
	
	public InventoryService() {
		// TODO Auto-generated constructor stub
	}
	
	public InventoryService(String environment) {	
		setEnvironmentServiceURL("InventoryService", environment);	
	}

}
