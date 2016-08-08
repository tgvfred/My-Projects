package com.disney.api.soapServices.folioModule.folioServicePort.operations;

import com.disney.api.soapServices.folioModule.folioServicePort.FolioService;
import com.disney.utils.XMLTools;

public class RetrieveAccountingPaymentDetail extends FolioService{	

	public RetrieveAccountingPaymentDetail(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAccountingPaymentDetail")));
		
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));

		generateServiceContext();	
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setPaymentId(String paymentId){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAccountingPaymentDetail'][1]/*[local-name(.)='paymentId'][1]", paymentId);
	}
	
	public void setFolioId(String folioId){
		setRequestNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAccountingPaymentDetail'][1]/*[local-name(.)='folioId'][1]", folioId);
	}	
	
	public String getTerminalId(){
		String terminalId = null;
		try{
			terminalId = getResponseNodeValueByXPath("/*[local-name(.)='Envelope'][1]/*[local-name(.)='Body'][1]/*[local-name(.)='retrieveAccountingPaymentDetailResponse'][1]/*[local-name(.)='returnParameter'][1]/*[local-name(.)='paymentInformationTO'][1]/*[local-name(.)='deviceTerminalId'][1]");
		}catch(Exception e){
			terminalId = "";
		}
		
		return terminalId;
	}
}
