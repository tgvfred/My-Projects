package com.disney.api.soapServices.tdodWorkflow.operations;

import com.disney.api.soapServices.tdodWorkflow.TdodWorkflow;
import com.disney.utils.XMLTools;


public class StartNewWorkflow extends TdodWorkflow{

	public StartNewWorkflow() {

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("startNewWorkflow")));
		//System.out.println(getRequest());
		
		removeComments() ;
		removeWhiteSpace();
		System.out.println(getRequest());
	}
	
	public String getFacilityId(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='addAuthorizedAccessGroup'][1]/*[local-name(.)='authAccsGroup'][1]/*[local-name(.)='authorizedAccesses'][1]/*[local-name(.)='resourceAccessDetail'][1]/*[local-name(.)='facilityId'][1]");	
	}
	
	public String getGroupName(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='addAuthorizedAccessGroup'][1]/*[local-name(.)='authAccsGroup'][1]/*[local-name(.)='authAcsGrpName'][1]");	
	}
	
	public String getResourceId(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='addAuthorizedAccessGroup'][1]/*[local-name(.)='authAccsGroup'][1]/*[local-name(.)='authorizedAccesses'][1]/*[local-name(.)='resourceAccessDetail'][1]/*[local-name(.)='resourceNumber'][1]");
	}
	
	public String getResourceName(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='addAuthorizedAccessGroup'][1]/*[local-name(.)='authAccsGroup'][1]/*[local-name(.)='authorizedAccesses'][1]/*[local-name(.)='resourceAccessDetail'][1]/*[local-name(.)='resourceName'][1]");
	}
	
	public String getGroupStartDate(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='addAuthorizedAccessGroup'][1]/*[local-name(.)='authAccsGroup'][1]/@*[local-name(.)='validFrom'][1]");
	}
	
	public String getGroupEndDate(){
		return getRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='addAuthorizedAccessGroup'][1]/*[local-name(.)='authAccsGroup'][1]/@*[local-name(.)='validTo'][1]");
	}
			
}
