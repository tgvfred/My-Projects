package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveServiceOptions extends TravelPlanSalesOrderService{

	public RetrieveServiceOptions(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveServiceOptions")));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setServiceOption(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveServiceOptions/retrieveServiceOptionsRequest/@serviceOption", value);}
	public String getServiceOption(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveServiceOptionsResponse/retrieveServiceOptionsResponse/@serviceOption");}
}
