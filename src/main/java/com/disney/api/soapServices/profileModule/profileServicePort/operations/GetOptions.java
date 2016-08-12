package com.disney.api.soapServices.profileModule.profileServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.profileModule.profileServicePort.ProfileServicePort;
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
	/**
	 * Gathers all key-value pairs for each option from the response
	 * @return - all key-value pairs for each option
	 */
	public Map<String, String> getOptionsKeyValuePairs(){
		Map<String, String> pairs = new HashMap<String, String>();
		NodeList options = getReponseOptions();
		for(int i = 0; i < options.getLength(); i++){
			pairs.put(XMLTools.getNodeList(options.item(i), "optionKey").item(0).getTextContent(), 
					XMLTools.getNodeList(options.item(i), "optionValue").item(0).getTextContent());
		}
		return pairs;
	}
}
