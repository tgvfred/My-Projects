package com.disney.api.soapServices.profileServicePort.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.profileServicePort.ProfileServicePort;
import com.disney.utils.XMLTools;

public class GetOptions extends ProfileServicePort{
	public GetOptions(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptions")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	/**
	 * Sets the option enum type in the SOAP request
	 * @param value - option enum type
	 */
	public void setProfileOptionEnumType(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptions/profileOptionEnum", value);}
	/**
	 * Retrieves a node list of key-value pairs from the SOAP response
	 * @return
	 */
	public NodeList getReponseOptions(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/getOptionsResponse/response");}
}
