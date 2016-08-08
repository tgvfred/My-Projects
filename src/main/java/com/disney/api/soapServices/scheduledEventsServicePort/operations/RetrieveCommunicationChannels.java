package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveCommunicationChannels extends ScheduledEventsServicePort{
	Map<String, String> communicationChannels = new HashMap<String, String>();
	int numCommunicationChannels = -1;
	
	public RetrieveCommunicationChannels(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveCommunicationChannels")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all communication channels
	 * @return - all communication channels
	 */
	public Map<String, String> getCommunicationChannels(){
		if(numCommunicationChannels == -1) getNumberOfCommunicationChannels();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCommunicationChannelsResponse/communicationChannels");
		for(int i = 0; i < numCommunicationChannels; i++){
			communicationChannels.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return communicationChannels;
	}
	/**
	 * Retrieves the number of communication channels
	 * @return - number of communication channels
	 */
	public int getNumberOfCommunicationChannels(){
		numCommunicationChannels = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveCommunicationChannelsResponse/communicationChannels").getLength();
		return numCommunicationChannels;
	}
}