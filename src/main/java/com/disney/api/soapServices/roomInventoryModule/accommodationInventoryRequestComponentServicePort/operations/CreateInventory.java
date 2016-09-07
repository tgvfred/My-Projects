package com.disney.api.soapServices.roomInventoryModule.accommodationInventoryRequestComponentServicePort.operations;

import com.disney.api.soapServices.roomInventoryModule.accommodationInventoryRequestComponentServicePort.AccommodationInventoryRequestComponentServicePort;
import com.disney.utils.XMLTools;

public class CreateInventory extends AccommodationInventoryRequestComponentServicePort{
	public CreateInventory(String environment, String scenario) {
		super(environment);
		
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createInventory")));
		generateServiceContext();
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
		
}