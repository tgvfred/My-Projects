package com.disney.api.soapServices.profileModule.profileServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.profileModule.profileServicePort.ProfileServicePort;
import com.disney.utils.XMLTools;

public class GetOptionsByFilter extends ProfileServicePort{
	public GetOptionsByFilter(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("getOptionsByFilter")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	/**
	 * Sets the option enum type in the SOAP request
	 * @param value - option enum type - Only ITEM is valid value here
	 */
	public void setProfileOptionEnumType(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/profileOptionEnum", value);}

	/**
	 * Sets the option enum type in the SOAP request
	 * @param value - option key - Only SERVICE_TYPE is valid here
	 */
	public void setProfileOptionKey(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/optionFilters/key", value);}
	/**
	 * Sets the option enum type in the SOAP request
	 * @param value - option value
	 */
	public void setProfileOptionValue(String value){setRequestNodeValueByXPath("/Envelope/Body/getOptionsByFilter/optionFilters/value", value);}
	
	/**
	 * Retrieves a node list of key-value pairs from the SOAP response
	 * @return
	 */
	public NodeList getReponseOptions(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/getOptionsByFilterResponse/response");}
	/**
	 * Gathers all key-value pairs for each option from the response
	 * @return - all key-value pairs for each option
	 */
	public Map<String, String> getOptionsKeyValuePairs(){
		Map<String, String> pairs = new HashMap<String, String>();
		try{
			NodeList options = getReponseOptions();
			for(int i = 0; i < options.getLength(); i++){
				pairs.put(XMLTools.getNodeList(options.item(i), "optionKey").item(0).getTextContent(), 
						XMLTools.getNodeList(options.item(i), "optionValue").item(0).getTextContent());
			}
		}catch(XPathNotFoundException throwAway){}
		
	
		return pairs;
	}
}
