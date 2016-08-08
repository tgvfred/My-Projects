package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class MassCancel extends ScheduledEventsServicePort{
	public MassCancel(String environment) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("massCancel")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	public MassCancel(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("massCancel")));			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/facilityId", value);}
	public void setServiceDate(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/serviceDate", value);}
	public void setServiceWindowStart(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/serviceWindowStart", value);}
	public void setServiceWindowEnd(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/serviceWindowEnd", value);}
	public void setMassCancelReasonId(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/massCancelReasonId", value);}
	public void setProductChannelId(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/productChannelId", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/salesChannel", value);}
	public void setCommunicationsChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/communicationChannel", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/massCancel/massCancelRequest/sourceAccountingCenter", value);}
}
