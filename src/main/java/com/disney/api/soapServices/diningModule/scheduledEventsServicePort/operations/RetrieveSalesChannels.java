package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveSalesChannels extends ScheduledEventsServicePort{
	Map<String, String> salesChannels = new HashMap<String, String>();
	int numSalesChannels = -1;
	
	public RetrieveSalesChannels(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSalesChannels")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all sales channels
	 * @return - all sales channels
	 */
	public Map<String, String> getSalesChannels(){
		if(numSalesChannels == -1) getNumberOfSalesChannels();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSalesChannelsResponse/salesChannels");
		for(int i = 0; i < numSalesChannels; i++){
			salesChannels.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return salesChannels;
	}
	/**
	 * Retrieves the number of sales channels
	 * @return - number of sales channels
	 */
	public int getNumberOfSalesChannels(){
		numSalesChannels = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveSalesChannelsResponse/salesChannels").getLength();
		return numSalesChannels;
	}
}