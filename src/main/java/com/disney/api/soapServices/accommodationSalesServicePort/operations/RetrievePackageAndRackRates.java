package com.disney.api.soapServices.accommodationSalesServicePort.operations;


import com.disney.api.soapServices.accommodationSalesServicePort.AccommodationSalesServicePort;
import com.disney.utils.XMLTools;


	public class RetrievePackageAndRackRates extends AccommodationSalesServicePort{
	    public RetrievePackageAndRackRates(String environment, String scenario) {
			super(environment);
			
			//Generate a request from a project xml file
			setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrievePackageAndRackRates")));
			generateServiceContext();
		    setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	     removeComments() ;
			removeWhiteSpace(); 
	     }
	    public void setaccomComponentId(String tc_id){
		   setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/accomComponentId", tc_id);
	     }
	   public void setTravelPlanSegementId(String tps_id){
		  setRequestNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRates/request/travelPlanSegmentId", tps_id);
	   }
	   public String getdate(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRatesResponse/response/packageRackRates/date");
	}
	   public String getpackagePrice(){
			return getResponseNodeValueByXPath("/Envelope/Body/retrievePackageAndRackRatesResponse/response/packageRackRates/packagePrice");
	}
	}
