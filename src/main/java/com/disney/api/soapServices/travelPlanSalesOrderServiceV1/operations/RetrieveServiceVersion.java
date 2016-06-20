package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class RetrieveServiceVersion extends TravelPlanSalesOrderService{

	public RetrieveServiceVersion(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveServiceVersion")));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
}
