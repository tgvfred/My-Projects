package com.disney.api.soapServices.folioServicePort.operations;

import com.disney.api.soapServices.folioServicePort.FolioService;
import com.disney.utils.XMLTools;

public class RetrieveResponsiblePartyDetails extends FolioService {

	/**
	* @throws 			NA
	* @Summary:			Builds the request for the modifyResponsiblePartyAccount operation.  Contains setters to
	* 					set the xpath nodes and getters to get xpath values from the response
	* 					There are serveral dynamic xpath nodes you must set, like the travel plan ID, folio ID, etc
	* @Precondition:
	* @Author:			Jessica Marshall
	* @Version:			12/15/2014
	* @Return:			N/A
	*/
	public RetrieveResponsiblePartyDetails(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveResponsiblePartyDetails")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTravelPlanId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetails'][1]/*[local-name(.)='responsiblePartyDetailReq'][1]/*[local-name(.)='travelPlanReferfence'][1]/*[local-name(.)='referenceValue'][1]", value);
	}
	
	public void setSourceAccountingCenterId(String value){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetails'][1]/*[local-name(.)='responsiblePartyDetailReq'][1]/*[local-name(.)='sourceAccountingCenterID'][1]", value);
	}
	
	public String getTerminalId(){
		String terminalId = "";
		try{
			terminalId = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][1]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]"); 
		}catch(Exception e){
			terminalId = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][2]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
		}
		return terminalId;
	}
	
	public String getTerminalId(String index) throws RuntimeException{
//		String[] ids = new String[2];
//		try{
//			ids[0] = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][1]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
//			ids[1] = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][2]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
//		}catch(RuntimeException e){
//			try{
//				ids[0] = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][2]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
//				ids[1] = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][3]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
//			}catch(RuntimeException rte){
//				ids[0] = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][1]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
//				ids[1] = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods'][3]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
//			}
//		}
		return getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveResponsiblePartyDetailsResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='responsiblePartyDetails'][1]/*[local-name(.)='settlementMethods']["+index+"]/*[local-name(.)='card'][1]/*[local-name(.)='deviceTerminalId'][1]");
	}
	
	public String getTerminalId(int index) throws RuntimeException{
		return getTerminalId(String.valueOf(index));
	}
}
