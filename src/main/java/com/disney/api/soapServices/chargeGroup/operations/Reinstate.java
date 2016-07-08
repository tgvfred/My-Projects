package com.disney.api.soapServices.chargeGroup.operations;

import com.disney.api.soapServices.chargeGroup.ChargeGroup;
import com.disney.utils.XMLTools;

public class Reinstate extends ChargeGroup{
	public Reinstate(String environment) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reinstate")));
	
		generateServiceContext();
//		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets the charge account reference name in the SOAP request
	 * @param value - charge account reference name
	 */
	public void setReferenceName(String value){setRequestNodeValueByXPath("/Envelope/Body/reinstate/chGrpExtRef/referenceName", value);}
	/**
	 * Sets the charge account reference value in the SOAP request
	 * @param value - charge account reference value
	 */
	public void setReferenceValue(String value){setRequestNodeValueByXPath("/Envelope/Body/reinstate/chGrpExtRef/referenceValue", value);}	
}