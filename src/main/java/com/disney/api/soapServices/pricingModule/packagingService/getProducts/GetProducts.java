package com.disney.api.soapServices.pricingModule.packagingService.getProducts;

import com.disney.api.soapServices.pricingModule.packagingService.PackagingService;
import com.disney.utils.XMLTools;

public class GetProducts extends PackagingService{
	public GetProducts(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getProducts")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setIncludeChannels(String value){
		setRequestNodeValueByXPath("/Envelope/Body/getProducts/request/@includeChannels", value);
	}
	
	public void setIncludeMarketingMessages(String value){
		setRequestNodeValueByXPath("/Envelope/Body/getProducts/request/@includeMarketingMessages", value);
	}
	
	public void setIncludePolicies(String value){
		setRequestNodeValueByXPath("/Envelope/Body/getProducts/request/@includePolicies", value);
	}
	
	public void setProductID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/getProducts/request/@productID", value);
	}
	
	public String getProductDescription(){
		return getResponseNodeValueByXPath("/Envelope/Body/getProductsResponse/CollectionOfProductVO/products/@description");
	}
}
