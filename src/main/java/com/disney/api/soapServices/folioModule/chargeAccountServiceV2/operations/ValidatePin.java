package com.disney.api.soapServices.folioModule.chargeAccountServiceV2.operations;

import com.disney.api.soapServices.folioModule.chargeAccountServiceV2.ChargeAccountServiceV2;
import com.disney.utils.XMLTools;

public class ValidatePin extends ChargeAccountServiceV2{
	public ValidatePin(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("validatePin")));
	
		generateServiceContext();
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setChargeAccountId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='validatePin'][1]/*[local-name(.)='chargeAccountIdentifier'][1]/*[local-name(.)='chargeAccountId'][1]", value);
	}
	
	public void setPinNumber(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='validatePin'][1]/*[local-name(.)='pinNumber'][1]", value);
	}
	
	public String getValidationResponse(){
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='validatePinResponse'][1]/*[local-name(.)='parameter1'][1]");
	}
}
