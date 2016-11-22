package com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.operations;

import com.disney.api.soapServices.accommodationModule.accommodationSalesComponentService.AccommodationSalesComponentService;
import com.disney.utils.XMLTools;

public class OverrideAccommodationRatesRequest extends AccommodationSalesComponentService{

	public OverrideAccommodationRatesRequest(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("overrideAccommodationRates")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	public OverrideAccommodationRatesRequest(String environment) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("overrideAccommodationRates")));
		
		generateServiceContext();			
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTpsID(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/travelPlanSegmentId", value);}
	public void setTcgId(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/travelComponentGroupingId", value);}
	public void setAdditionalCharge(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/additionalCharge", value);}
	public void setAdditionalChargeOverridden(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/additionalChargeOverridden", value);}
	public void setBasePrice(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/basePrice", value);}
	public void setDate(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/date", value);}
	public void setDayCount(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/dayCount", value);}
	public void setOverridden(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/overidden", value);}
	public void setShared(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/shared", value);}
	public void setRackRateDate(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/rackRate/date", value);}
	public void setRackRateRate(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/rackRate/rate", value);}
	public void setNetPrice(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/netPrice", value);}
	public void setPointsValue(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/rateDetails/pointsValue", value);}
	public void setOverrideReason(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/overrideReason", value);}
	public void setLocationId(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideAccommodationRates/request/locationId", value);}
}