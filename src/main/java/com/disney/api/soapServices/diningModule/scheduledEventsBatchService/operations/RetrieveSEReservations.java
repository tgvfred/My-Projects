package com.disney.api.soapServices.diningModule.scheduledEventsBatchService.operations;

import java.util.HashMap;
import java.util.Map;

import com.disney.AutomationException;
import com.disney.api.soapServices.diningModule.scheduledEventsBatchService.ScheduledEventsBatchService;
import com.disney.utils.XMLTools;

public class RetrieveSEReservations extends ScheduledEventsBatchService{
	Integer numberOfReservations = null;
	Map<String, String> reservationNumbers = new HashMap<String, String>();
	
	public RetrieveSEReservations(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveSEReservations")));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setProcessDateDaysOut(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveSEReservations/retrieveSEReservationsRequest/processDate", "fx:getdate; DaysOut:" + value);
	}
	
	public void setProcessDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieveSEReservations/retrieveSEReservationsRequest/processDate", value);
	}
	
	public void setDateEqualCondition(String value){
		if(!value.equals("=") && !value.equals("!="))  throw new AutomationException("Expected values for 'DateEqualCondition' are '=' or '!='");
		setRequestNodeValueByXPath("/Envelope/Body/retrieveSEReservations/retrieveSEReservationsRequest/dateEqualCondition", value);
	}
	
	
	public int getNumberOfReservations(){
		try{numberOfReservations =getNumberOfResponseNodesByXPath("/Envelope/Body/retrieveSEReservationsResponse/setravelPlanSegmentIds");}
		catch(Exception e){numberOfReservations = 0;}
		return numberOfReservations;		
	}
	
	public Map<String, String> getAllReservationNumbers(){
		numberOfReservations = getNumberOfReservations();
		for(int i = 1; i < numberOfReservations; i++){
			reservationNumbers.put(String.valueOf(i), getResponseNodeValueByXPath("/Envelope/Body/retrieveSEReservationsResponse/setravelPlanSegmentIds["+i+"]"));
		}
		return reservationNumbers;
	}
}