package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveRoles extends ScheduledEventsServicePort{
	Map<String, String> roles = new HashMap<String, String>();
	int numRoles = -1;
	
	public RetrieveRoles(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveRoles")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	

	/**
	 * Retrieves all roles
	 * @return - all roles
	 */
	public Map<String, String> getRoles(){
		if(numRoles == -1) getNumberOfRoles();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveRolesResponse/roles");
		for(int i = 0; i < numRoles; i++){
			roles.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return roles;
	}
	/**
	 * Retrieves the number of roles
	 * @return - number of roles
	 */
	public int getNumberOfRoles(){
		numRoles = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveRolesResponse/roles").getLength();
		return numRoles;
	}
}