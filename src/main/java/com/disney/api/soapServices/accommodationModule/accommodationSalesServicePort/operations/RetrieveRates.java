package com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;

public class RetrieveRates extends AccommodationSalesServicePort{
	
	public RetrieveRates(String environment) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveRates")));
	    removeComments() ;
		removeWhiteSpace();
		generateServiceContext();
	}
	public RetrieveRates(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveRates")));
        generateServiceContext();			
	    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	    removeComments() ;
		removeWhiteSpace(); 
	}
	//Setters
	public void setTravelComponentGroupingId(String tcg_id){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveRates/travelComponentGroupingId", tcg_id);
	    }
	
	//Getters
	    public String getroomTypeCode(){
	    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/roomTypeCode");
	    }
	    public String getAdditionalChargeOverridden(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/additionalChargeOverridden");
	    }
	    public String getBillCode(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/billCode");
	    }
	    public String getPackageName(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/packageName");
	    }
	    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails") + 1;
	    
	    public String getRateDetails(String index){
	    	return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails["+index+"]");
	    }
	    public Integer  getRateDetails(){
	    	return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails");
	    }
	    public String getAdditionalCharge(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/additionalCharge");
	    }
	    public String getBasePrice(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/basePrice");
	    }
	    public String getRateDate(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/date");
	    }
	    public String getDayCount(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/dayCount");
	    }
	    
	    public String getRateDetailsOveridden(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/overidden");
	    }
	    public String getShared(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/shared");
	    }
	    int index1 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate") + 1;
	    public String getRackRateDetails(String index){
	    	return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate["+index+"]");
	    }
	    public Integer  getRackRateDetails(){
	    	return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate");
	    }
	    public String getRackRateDate(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/date");
	    }
	    public String getRackRate(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/additionalChargeOverridden");
	    }
	    public String getNetPrice(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/netPrice");
	    }
	    public String getPointsValue(){
		    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/pointsValue");
	    }
	    
	    
}	    
	    
	    
	    
	    //Malika's Commented Code
	    /*public String getRate(){
	    return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/rate");
	    }
	    
	    int index3 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate") + 1;
	    
	    public void setRequestTravelComponentGroupingIdIndex(String index3, String tcgId){
			setRequestNodeValueByXPath("/Envelope/Body/retrieveRates/travelComponentGroupingId["+index3+"]", tcgId);
	    }
	  
	
	    int index = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail") + 1;
		   public String getAccommodationRateDetails(String index) {return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail["+index+"]");}
		   public Integer getAccommodationRateDetails(){return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail");}
	    
	   int index1 = getNumberOfRequestNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails") + 1;
	   public String getRateDetails(String index) {return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails["+index+"]");}
	   public Integer getRateDetails(){return getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails");}
	  // public String getRate(){ return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/rate");}
	   //public String getRateDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/date");}
	   public String getPackageName(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/packageName");}
	   public String getBillCode() {return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/billCode");}
	   
	   public int addRateDetailsNode(){
		   int currentNodes = 3;
		   try{currentNodes = XMLTools.getNodeList(getRequestDocument(), "/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate").getLength();}
		   catch(XPathNotFoundException e){}
		   getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails");
		   getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/["+String.valueOf(currentNodes+1)+"]");
		   getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/["+String.valueOf(currentNodes+1)+"]");
		   currentNodes++;
		   return currentNodes;
	   }
	   //public String getRate(){ return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/rate");}
	  // public String getRateDate(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveRatesResponse/accomodationRatesDetail/ratedetails/rackRate/date");}
}*/