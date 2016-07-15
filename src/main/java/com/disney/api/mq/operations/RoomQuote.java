package com.disney.api.mq.operations;

import com.disney.api.mq.MqCore;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RoomQuote extends MqCore{
	private String errorMsg = "";
	public RoomQuote( String environment, String location, boolean isARP, String scenario){
		super(environment, location, isARP);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("RoomQuoteRQ")));
		setRequestNodeValueByXPath(getTestScenario( "RoomQuoteRQ", scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public boolean isSuccess(){
		try {
			 errorMsg = getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/ResultsInfo/ResultInfo/@Value");
		}catch(XPathNotFoundException e){
			return true;
		}
		return false;
	}
	
	public String getErrorMessage(){return errorMsg;}
	
	public void setFromDateInDaysOut(String daysOut){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/@FromDate", "fx:getdate; DaysOut:"+daysOut);
	}
	
	public void setToDateInDaysOut(String daysOut){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/@ToDate", "fx:getdate; DaysOut:"+daysOut);
	}
	
	public void setRoomPriceQuoteIdentifier(String value){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/@RoomPriceQuoteIdentifier", value);
	}

	public void setRoomType(String value){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/@RoomType", value);
	}

	public void setResortCode(String value){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/@ResortCode", value);
	}

	public void setRoomTypeHandle(String value){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/RoomInfo/@RoomTypeHandle", value);
	}

	public void setPackageCode(String value){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/@PackageCode", value);
	}

	public void setRoomTypeCode(String value){
		setRequestNodeValueByXPath("/RoomQuoteRQ/RoomQuoteRequestSpec/RoomQuoteDetailsSpec/RoomInfo/@RoomTypeCode", value);
	}
	public String getBalanceDueDate(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@BalanceDueDate");		
	}

	public String getCommissionAmount(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@CommissionAmount");		
	}

	public String getDepositDueDate(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@DepositDueDate");		
	}

	public String getMinimumDepositAmount(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@MinimumDepositAmount");		
	}

	public String getNetAmount(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@NetAmount");		
	}

	public String getTaxAmount(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@TaxAmount");		
	}

	public String getTotalAmount(){ 
		return getResponseNodeValueByXPath("/RoomQuoteRS/RoomQuoteResults/RoomQuoteResultsInfo/QuoteResultInfo/@TotalAmount");		
	}
}
