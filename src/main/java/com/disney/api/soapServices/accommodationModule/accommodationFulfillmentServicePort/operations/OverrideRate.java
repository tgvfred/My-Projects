package com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.operations;

import com.disney.api.soapServices.accommodationModule.accommodationFulfillmentServicePort.AccommodationFulfillmentServicePort;
import com.disney.utils.XMLTools;

public class OverrideRate extends AccommodationFulfillmentServicePort{

	public OverrideRate(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("overrideRate")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}

	public void setTpsID(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/travelPlanSegmentId", value);}
	public void setTcgId(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/travelComponentGroupingId", value);}
	public void setAdditionalCharge(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/additionalCharge", value);}
	public void setAdditionalChargeOverridden(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/additionalChargeOverridden", value);}
	public void setBasePrice(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/basePrice", value);}
	public void setDate(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/date", value);}
	public void setDayCount(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/dayCount", value);}
	public void setOverridden(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/overidden", value);}
	public void setShared(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/shared", value);}
	public void setRackRateDate(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/rackRate/date", value);}
	public void setRackRateRate(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/rackRate/rate", value);}
	public void setNetPrice(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/netPrice", value);}
	public void setPointsValue(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/rateDetails/pointsValue", value);}
	public void setOverrideReason(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/overrideReason", value);}
	public void setLocationId(String value){setRequestNodeValueByXPath("/Envelope/Body/overrideRate/request/locationId", value);}
}
