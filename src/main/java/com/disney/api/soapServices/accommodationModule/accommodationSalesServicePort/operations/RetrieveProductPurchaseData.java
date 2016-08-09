package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;

public class RetrieveProductPurchaseData extends AccommodationSalesServicePort{
    public RetrieveProductPurchaseData(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveProductPurchaseData")));
		//generateServiceContext();setRequestNodeValueByXPath(getTestScenario("/services/accommodationSalesServicePort/retrieveProductPurchaseData/retrieveProductPurchaseDataInputs.xls", scenario));
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
        removeComments() ;
		removeWhiteSpace(); 
    }
    public String getproductId(){
	   return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/productDetail/productId");
    }
    public String getproductTypeName(){
	   return getResponseNodeValueByXPath("/Envelope/Body/retrieveProductPurchaseDataResponse/response/productDetail/productTypeName");
    }
}