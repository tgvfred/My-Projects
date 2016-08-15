package com.disney.api.soapServices.folioModule.folioServicePort.operations;

import com.disney.api.soapServices.folioModule.folioServicePort.FolioService;
import com.disney.utils.XMLTools;

public class ModifyResponsiblePartyAccount extends FolioService{	
	
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
	public ModifyResponsiblePartyAccount(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modifyResponsiblePartyAccount")));
		System.out.println(getRequest());
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	/**
	* @Summary:			sets the charge group ref name - if its coming from dreams would be DREAMS_TP
	* @Author:			Jessica Marshall
	* @Version:			12/15/2014
	* @Return:			N/A
	* @param
	*/
	public void setChargeGroupRefName(String chargeGroupRefName){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/chargeGroupReference/referenceName",chargeGroupRefName);
	}
	
	/**
	* @Summary:			sets the charge group ref value - if its coming from dreams would be the travel plan ID
	* @Author:			Jessica Marshall
	* @Version:			12/15/2014
	* @Return:			N/A
	* @param
	*/
	public void setChargeGroupRefValue(String chargeGroupRefValue){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/chargeGroupReference/referenceValue",chargeGroupRefValue);
	}
	
	/**
	* @Summary:			sets the party account reference name - usuall ODS
	* @Author:			Jessica Marshall
	* @Version:			12/15/2014
	* @Return:			N/A
	* @param
	*/
	public void setPartyReqRefName(String refName){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/partyRequest/externalReferences/referenceName",refName);
	}
	
	/**
	* @Summary:			sets the party account reference value - if using ODS reference value, then can get it from 
	* 					guest table in dreams database by passing in the guest ID from the retrieve response from 
	* 					accommodation sales.  Query is located in the reservationInfo class in data factory
	* @Author:			Jessica Marshall
	* @Version:			12/15/2014
	* @Return:			N/A
	* @param
	*/
	public void setPartyReqRefValue(String refValue){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/partyRequest/externalReferences/referenceValue",refValue);
	}
	
	

	
	/**
	* @Summary:			The primary guest first name must match the guest first name from the reservation.  This operation won't let 
	* 					you update the guest names to something different
	* @Author:			Jessica Marshall
	* @Version:			12/5/2014
	* @Return:			N/A
	* @param
	*/
	public void setPrimaryGuestFirstName(String firstName){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/partyRequest/firstName", firstName);
	}
	
	/**
	* @Summary:			The primary guest last name must match the guest first name from the reservation.  This operation won't let 
	* 					you update the guest names to something different
	* @Author:			Jessica Marshall
	* @Version:			12/5/2014
	* @Return:			N/A
	* @param
	*/
	public void setPrimaryGuestLastName(String lastName){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/partyRequest/lastName", lastName);
	}
	
	public void setFolioType(String folioType){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/folioType", folioType);
	}
	
	public void setFolioID(String folioID){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/folioID", folioID);
	}
	
	public void setCreditCardNum(String creditCardNum){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/settlementMethod/card/creditCardNumber", creditCardNum);
	}
	
	public void setCreditCardType(String creditCardType){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/settlementMethod/card/creditCardType", creditCardType);
	}
	
	public void setCCExpMonth (String expMonth){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/settlementMethod/card/cardAuthorizationInfo/expirationMonth", expMonth);
	}
	
	public void setCCExpYear (String expYear){
		setRequestNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccount/responsiblePartyAccountRequest/settlementMethod/card/cardAuthorizationInfo/expirationYear", expYear);
	}
	
	public String getFolioIDResponse(){
		return getResponseNodeValueByXPath("/Envelope/Body/modifyResponsiblePartyAccountResponse/returnParameter/folioID");
	}
	
}
