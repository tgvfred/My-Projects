package com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request;

import com.disney.api.restServices.folio.chargeAccountService.chargeAccount.retrieveGuests.request.objects.ExternalReferenceTO;

public class RetrieveGuestsRequest {
	private ExternalReferenceTO externalReferenceTO;
	private String sourceAccountingCenter;

	/**
	* 
	* @return
	* The externalReferenceTO
	*/
	public ExternalReferenceTO getExternalReferenceTO() {		
	return externalReferenceTO;
	 }
	
	
	/**
	* 
	* @param externalReferenceTO
	* The externalReferenceTO
	*/
	public void setExternalReferenceTO(ExternalReferenceTO externalReferenceTO) {	
	this.externalReferenceTO = externalReferenceTO;
	 }
	
	
	/**
	* 
	* @return
	* The sourceAccountingCenter
	*/
	public String getSourceAccountingCenter() {
	return sourceAccountingCenter;
	 }

	/**
	* 
	* @param sourceAccountingCenter
	* The sourceAccountingCenter
	*/
	public void setSourceAccountingCenter(String sourceAccountingCenter) {
		this.sourceAccountingCenter = sourceAccountingCenter;
	 }
}
