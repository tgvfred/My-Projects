package com.disney.api.restServices.folio.PaymentV2.folioPaymentV2.establishSettlementMethod.request.objects;

public class SettlementMethodRequest {
	private String activeIndicator = "true";
	private String deviceTerminalId = "00RJ";
	private String expressCheckout = "false";
	private String paymentMethod = "Visa";
	private String paymentMethodType = "CreditCard";
	private String stratusTerminalNumber = "3549";
	private String useForIncidentals = "false";
	private MerchantInfo merchantInfo;
	private CreditCardInfo creditCardInfo;
	private FolioIdentifierInfo folioIdentifierInfo;
	
	public SettlementMethodRequest(){
		merchantInfo = new MerchantInfo();
		creditCardInfo = new CreditCardInfo();
		folioIdentifierInfo = new FolioIdentifierInfo();
	}
	/**
	* 
	* @return
	* The activeIndicator
	*/
	public String getActiveIndicator() {
	return activeIndicator;
	 }

	/**
	* 
	* @param activeIndicator
	* The activeIndicator
	*/
	public void setActiveIndicator(String activeIndicator) {
	this.activeIndicator = activeIndicator;
	 }

	/**
	* 
	* @return
	* The deviceTerminalId
	*/
	public String getDeviceTerminalId() {
	return deviceTerminalId;
	 }

	/**
	* 
	* @param deviceTerminalId
	* The deviceTerminalId
	*/
	public void setDeviceTerminalId(String deviceTerminalId) {
	this.deviceTerminalId = deviceTerminalId;
	 }

	/**
	* 
	* @return
	* The expressCheckout
	*/
	public String getExpressCheckout() {
	return expressCheckout;
	 }

	/**
	* 
	* @param expressCheckout
	* The expressCheckout
	*/
	public void setExpressCheckout(String expressCheckout) {
	this.expressCheckout = expressCheckout;
	 }

	/**
	* 
	* @return
	* The paymentMethod
	*/
	public String getPaymentMethod() {
	return paymentMethod;
	 }

	/**
	* 
	* @param paymentMethod
	* The paymentMethod
	*/
	public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
	 }

	/**
	* 
	* @return
	* The paymentMethodType
	*/
	public String getPaymentMethodType() {
	return paymentMethodType;
	 }

	/**
	* 
	* @param paymentMethodType
	* The paymentMethodType
	*/
	public void setPaymentMethodType(String paymentMethodType) {
	this.paymentMethodType = paymentMethodType;
	 }

	/**
	* 
	* @return
	* The stratusTerminalNumber
	*/
	public String getStratusTerminalNumber() {
	return stratusTerminalNumber;
	 }

	/**
	* 
	* @param stratusTerminalNumber
	* The stratusTerminalNumber
	*/
	public void setStratusTerminalNumber(String stratusTerminalNumber) {
	this.stratusTerminalNumber = stratusTerminalNumber;
	 }

	/**
	* 
	* @return
	* The useForIncidentals
	*/
	public String getUseForIncidentals() {
	return useForIncidentals;
	 }

	/**
	* 
	* @param useForIncidentals
	* The useForIncidentals
	*/
	public void setUseForIncidentals(String useForIncidentals) {
	this.useForIncidentals = useForIncidentals;
	 }

	/**
	* 
	* @return
	* The merchantInfo
	*/
	public MerchantInfo getMerchantInfo() {
	return merchantInfo;
	 }

	/**
	* 
	* @param merchantInfo
	* The merchantInfo
	*/
	public void setMerchantInfo(MerchantInfo merchantInfo) {
	this.merchantInfo = merchantInfo;
	 }

	/**
	* 
	* @return
	* The creditCardInfo
	*/
	public CreditCardInfo getCreditCardInfo() {
	return creditCardInfo;
	 }

	/**
	* 
	* @param creditCardInfo
	* The creditCardInfo
	*/
	public void setCreditCardInfo(CreditCardInfo creditCardInfo) {
	this.creditCardInfo = creditCardInfo;
	 }

	/**
	* 
	* @return
	* The folioIdentifierInfo
	*/
	public FolioIdentifierInfo getFolioIdentifierInfo() {
	return folioIdentifierInfo;
	 }

	/**
	* 
	* @param folioIdentifierInfo
	* The folioIdentifierInfo
	*/
	public void setFolioIdentifierInfo(FolioIdentifierInfo folioIdentifierInfo) {
	this.folioIdentifierInfo = folioIdentifierInfo;
	 }
}
