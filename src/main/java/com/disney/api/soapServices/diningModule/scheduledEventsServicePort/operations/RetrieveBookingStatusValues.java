package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveBookingStatusValues extends ScheduledEventsServicePort{
	Map<String, String> bookingStatusValues = new HashMap<String, String>();
	int numBookingStatusValues = -1;
	public RetrieveBookingStatusValues(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveBookingStatusValues")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Retrieves all booking status values
	 * @return - all booking status values
	 */
	public Map<String, String> getBookingStatusValues(){
		if(numBookingStatusValues == -1) getNumberOfBookingStatusValues();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveBookingStatusValuesResponse/bookingStatusValues");
		for(int i = 0; i < numBookingStatusValues; i++){
			bookingStatusValues.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return bookingStatusValues;
	}
	/**
	 * Retrieves the number of booking status values
	 * @return - number of booking status values
	 */
	public int getNumberOfBookingStatusValues(){
		numBookingStatusValues = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveBookingStatusValuesResponse/bookingStatusValues").getLength();
		return numBookingStatusValues;
	}
}