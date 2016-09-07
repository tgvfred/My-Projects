package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request;

import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.objects.FolioIdentifierTO;
import com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.objects.ExternalReferenceTO;

public class RetrieveSettlementMethodsRequest {
	private FolioIdentifierTO folioIdentifierTO;

	/**
	* 
	* @return
	* The folioIdentifierTO
	*/
	public FolioIdentifierTO getFolioIdentifierTO() {
	return folioIdentifierTO;
	 }

	/**
	* 
	* @param folioIdentifierTO
	* The folioIdentifierTO
	*/
	public void setFolioIdentifierTO(FolioIdentifierTO folioIdentifierTO) {
	this.folioIdentifierTO = folioIdentifierTO;
	 }
}
