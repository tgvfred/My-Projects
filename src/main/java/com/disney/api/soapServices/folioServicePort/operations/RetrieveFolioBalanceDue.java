package com.disney.api.soapServices.folioServicePort.operations;

import com.disney.api.soapServices.folioServicePort.FolioService;
import com.disney.utils.XMLTools;

public class RetrieveFolioBalanceDue extends FolioService{	

	public RetrieveFolioBalanceDue(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveFolioBalanceDue")));

		generateServiceContext();	
		//setRequestNodeValueByXPath(getTestScenario("/services/folioConfigurationService/createOrRetreivePaymentDevice/createOrRetrievePaymentDeviceInput.xls", scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setExternalReference(String externalReferenceType, String externalReferenceValue){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioBalanceDue/externalReference/referenceName",externalReferenceType);
		setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioBalanceDue/externalReference/referenceValue",externalReferenceValue);
	}
	
	public void setFolioType(String folioType){
		setRequestNodeValueByXPath("//Envelope/Body/retrieveFolioBalanceDue/folioType", folioType);
	}
	
	public void setLocationId(String locationId){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveFolioBalanceDue/locationID", locationId);
	}
	
	public String getDepositRequired(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveFolioBalanceDueResponse/returnParameter/depRequired/amount");
		//return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveFolioBalanceDueResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='depRequired'][1]/*[local-name(.)='amount'][1]");
	}
	
	public String getPaymentRequired(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveFolioBalanceDueResponse/returnParameter/folioBalance/amount");
		//return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveFolioBalanceDueResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='folioBalance'][1]/*[local-name(.)='amount'][1]");
	}
	
	public String getFolioId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveFolioBalanceDueResponse/returnParameter/folioId");
		//return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveFolioBalanceDueResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='folioId'][1]");
	}
}
