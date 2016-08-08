package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class CancelBundle extends TravelPlanSalesOrderService{

	public CancelBundle(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancelBundle")));
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/cancelBundleRequests/@travelPlanId", value);}
	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/cancelBundleRequests/@salesOrderId", value);}
	public void setBundleId(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/cancelBundleRequests/@bundleId", value);}
	public void setWaiveCancelFee(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/cancelBundleRequests/@waiveCancelFee", value);}
	public void setCancelContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/cancelBundleRequests/@cancelContactName", value);}
	public void setCancelReason(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/cancelBundleRequests/@cancelReason", value);}
	public void setBusinessContextCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/businessContext/@communicationsChannel", value);}
	public void setBusinessContextDestination(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/businessContext/@destination", value);}
	public void setBusinessContextPointOfOrigin(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/businessContext/@pointOfOrigin", value);}
	public void setBusinessContext(String value){setRequestNodeValueByXPath("/Envelope/Body/cancelBundle/businessContext/@salesChannel", value);}
	
	public String getResponseSalesOrderId(){return getResponseNodeValueByXPath("/Envelope/Body/cancelBundleResponse/canceBundleResponse/@salesOrderId");}
	public String getResponseTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/cancelBundleResponse/canceBundleResponse/@travelPlanId");}
}