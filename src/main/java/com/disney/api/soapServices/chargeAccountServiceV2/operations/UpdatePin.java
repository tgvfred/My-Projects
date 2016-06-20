package com.disney.api.soapServices.chargeAccountServiceV2.operations;

import com.disney.api.soapServices.chargeAccountServiceV2.ChargeAccountServiceV2;
import com.disney.utils.XMLTools;

public class UpdatePin extends ChargeAccountServiceV2{
	public UpdatePin(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updatePin")));
	
		generateServiceContext();
		System.out.println(getRequest());
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();

	}
	
	public void setChargeAccountId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='updatePin'][1]/*[local-name(.)='chargeAccountIdentifiers'][1]/*[local-name(.)='chargeAccountId'][1]", value);
	}
	
	public void setPinNumber(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='updatePin'][1]/*[local-name(.)='pinNumber'][1]", value);
	}
	
	public String getPinNumber(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='updatePin'][1]/*[local-name(.)='pinNumber'][1]");
	}
}
