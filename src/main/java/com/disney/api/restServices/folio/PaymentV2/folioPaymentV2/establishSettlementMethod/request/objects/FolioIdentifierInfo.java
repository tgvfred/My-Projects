package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects;

public class FolioIdentifierInfo {
	private String externalReferenceSource = "DREAMS_TP";
	private String externalReferenceValue;
	private String folioType = "INDIVIDUAL";
	private String partyLastName;

	/**
	* 
	* @return
	* The externalReferenceSource
	*/
	public String getExternalReferenceSource() {
	return externalReferenceSource;
	 }

	/**
	* 
	* @param externalReferenceSource
	* The externalReferenceSource
	*/
	public void setExternalReferenceSource(String externalReferenceSource) {
	this.externalReferenceSource = externalReferenceSource;
	 }

	/**
	* 
	* @return
	* The externalReferenceValue
	*/
	public String getExternalReferenceValue() {
	return externalReferenceValue;
	 }

	/**
	* 
	* @param externalReferenceValue
	* The externalReferenceValue
	*/
	public void setExternalReferenceValue(String externalReferenceValue) {
	this.externalReferenceValue = externalReferenceValue;
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

	/**
	* 
	* @return
	* The partyLastName
	*/
	public String getPartyLastName() {
	return partyLastName;
	 }

	/**
	* 
	* @param partyLastName
	* The partyLastName
	*/
	public void setPartyLastName(String partyLastName) {
	this.partyLastName = partyLastName;
	 }
}
