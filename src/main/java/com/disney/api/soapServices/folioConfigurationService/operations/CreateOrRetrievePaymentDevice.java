package com.disney.api.soapServices.folioConfigurationService.operations;

import com.disney.api.soapServices.folioConfigurationService.FolioConfigurationService;
import com.disney.utils.XMLTools;

public class CreateOrRetrievePaymentDevice extends FolioConfigurationService{	

	public CreateOrRetrievePaymentDevice(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("createOrRetrievePaymentDevice")));

		generateServiceContext();	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setCampusId(String campus){
		setRequestNodeValueByXPath("//paymentDeviceTO/campusId", campus);
	}

	public void setDeviceTypeName(String value){
		setRequestNodeValueByXPath("//paymentDeviceTO/deviceTypeName", value);
	}
	
	public void setDeviceName(String value){
		setRequestNodeValueByXPath("//paymentDeviceTO/deviceName", value);
	}
	
	public String getDeviceTerminalId(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='createOrRetrievePaymentDeviceResponse'][1]/*[local-name(.)='parameter1'][1]/*[local-name(.)='deviceTerminalId'][1]");
	}
}