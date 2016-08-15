package com.disney.api.soapServices.diningModule.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.AutomationException;
import com.disney.api.soapServices.diningModule.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveReservationsForAutoCheckout extends ScheduledEventsServicePort{
	Integer numberOfReservations = null;
	Map<String, String> reservationNumbers = new HashMap<String, String>();
	
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
	
	public void setProcessDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckout/retrieveReservationForAutoCheckoutRequest/processDate", value);
	}
	
	public void setDateEqualCondition(String value){
		if(!value.equals("=") && !value.equals("!="))  throw new AutomationException("Expected values for 'DateEqualCondition' are '=' or '!='");
		setRequestNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckout/retrieveReservationForAutoCheckoutRequest/dateEqualCondition", value);
	}
	
	public void setDateEqualCondition_Negative(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckout/retrieveReservationForAutoCheckoutRequest/dateEqualCondition", value);
	}
	
	public int getNumberOfReservations(){
		try{numberOfReservations = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveReservationsForAutoCheckoutResponse/travelPlanSegmentIds").getLength();}
		catch(Exception e){numberOfReservations = 0;}
		return numberOfReservations;		
	}
	
	public Map<String, String> getAllReservationNumbers(){
		if(numberOfReservations == null) numberOfReservations = getNumberOfReservations();
		for(int i = 1; i < numberOfReservations; i++){
			reservationNumbers.put(String.valueOf(i), getResponseNodeValueByXPath("/Envelope/Body/retrieveReservationsForAutoCheckoutResponse/travelPlanSegmentIds["+i+"]"));
		}
		return reservationNumbers;
	}
}