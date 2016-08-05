package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveTaxExemptTypes extends ScheduledEventsServicePort{
	Map<String, String> taxExemptTypes = new HashMap<String, String>();
	int numTaxExemptTypes = -1;
	
	public RetrieveTaxExemptTypes(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveTaxExemptTypes")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}

	/**
	 * Retrieves all tax exempt types
	 * @return - all tax exempt types
	 */
	public Map<String, String> getTaxExemptTypes(){
		if(numTaxExemptTypes == -1) getNumberOfTaxExemptTypes();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveTaxExemptTypesResponse/taxExemptTypes");
		for(int i = 0; i < numTaxExemptTypes; i++){
			taxExemptTypes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return taxExemptTypes;
	}
	/**
	 * Retrieves the number of tax exempt types
	 * @return - number of tax exempt types
	 */
	public int getNumberOfTaxExemptTypes(){
		numTaxExemptTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveTaxExemptTypesResponse/taxExemptTypes").getLength();
		return numTaxExemptTypes;
	}
}