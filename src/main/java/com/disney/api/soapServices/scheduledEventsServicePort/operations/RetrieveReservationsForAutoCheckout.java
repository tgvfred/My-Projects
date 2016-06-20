package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import com.disney.AutomationException;
import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveReservationsForAutoCheckout extends ScheduledEventsServicePort{
	public RetrieveReservationsForAutoCheckout(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveReservationsForAutoCheckout")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setSourceAccountingCenter(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckout/retrieveReservationForAutoCheckoutRequest/srcAccountingCenterId", value);
	}
	
	public void setProcessDateDaysOut(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckout/retrieveReservationForAutoCheckoutRequest/processDate", "fx:getdate; DaysOut:" + value);
	}
	
	public void setDateEqualCondition(String value){
		if(!value.equals("=") && !value.equals("!="))  throw new AutomationException("Expected values for 'DateEqualCondition' are '=' or '!='");
		setRequestNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckout/retrieveReservationForAutoCheckoutRequest/dateEqualCondition", value);
	}
	
	public int getNumberOfReservations(){
		return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReservationsForAutoCheckoutResponse/travelPlanSegmentIds").getLength();		
	}
}