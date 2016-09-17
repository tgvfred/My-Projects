package com.disney.api.soapServices.profileModule.profileServicePort.operations;

import com.disney.api.soapServices.profileModule.profileServicePort.ProfileServicePort;
import com.disney.utils.XMLTools;

public class GetOptionDetail extends ProfileServicePort{
	public GetOptionDetail(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptionDetail")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	/**
	 * Sets the option enum type in the SOAP request
	 * @param value - option enum type
	 */
	public void setProfileOptionEnumType(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionDetail/profileOptionEnum", value);}

	/**
	 * Sets the option enum type in the SOAP request
	 * @param value - option enum type
	 */
	public void setProfileOptionKey(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionDetail/optionKeyVal", value);}
	
	/**
	 * Retrieves the value from the SOAP response
	 * @return
	 */
	public String getResponseOptionValue(){return getResponseNodeValueByXPath("/Envelope/Body/getOptionDetailResponse/response/optionValue");}
	
	public int getNumberResponseNodes(){ return getNumberOfResponseNodesByXPath("/Envelope/Body/getOptionDetailResponse/response/optionValue");}
}
