package com.disney.api.restServices.folio.folioService.folio.retrieveSettlementMethods.request.objects;

public class FolioIdentifierTO {
	private ExternalReferenceTO externalReferenceTO;
	private String folioType;

	public FolioIdentifierTO(){
		externalReferenceTO = new ExternalReferenceTO();
	}
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
	* The folioType
	*/
	public String getFolioType() {
	return folioType;
	 }

	/**
	* 
	* @param folioType
	* The folioType
	*/
	public void setFolioType(String folioType) {
	this.folioType = folioType;
	 }
}
