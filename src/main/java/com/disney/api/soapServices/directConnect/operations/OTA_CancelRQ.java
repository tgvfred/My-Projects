/**
 * 
 */
package com.disney.api.soapServices.directConnect.operations;

import com.disney.api.soapServices.directConnect.DirectConnect;
import com.disney.utils.XMLTools;

/**
 * @author mccak082
 *
 */
public class OTA_CancelRQ extends DirectConnect{
	public OTA_CancelRQ (String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("OTA_CancelRQ.java")));
		
	}
}	


