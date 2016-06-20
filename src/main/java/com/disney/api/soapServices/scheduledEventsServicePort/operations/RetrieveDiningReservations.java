package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveDiningReservations extends ScheduledEventsServicePort{
	public RetrieveDiningReservations(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveDiningReservations")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDiningReservations/retrieveDiningReservationsRequest/facilityIds", value);}
	public void setReservationDate(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDiningReservations/retrieveDiningReservationsRequest/reservationDate", value);}
	public void setReservationWindowEndTime(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDiningReservations/retrieveDiningReservationsRequest/reservationWindowEndTime", value);}
	public void setReservationWindowStartTime(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDiningReservations/retrieveDiningReservationsRequest/reservationWindowStartTime", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/retrieveDiningReservations/retrieveDiningReservationsRequest/sourceAccountingCenter", value);}
	
	public String getErrorCode(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/errorCode");}
	public String getErrorMessage(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/errorMessage");}
	public String getPartyMixAdultCount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/partyMixAdultCount");}
	public String getPartyMixChildCount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/partyMixChildCount");}
	public String getPartyMixInfantCount(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/partyMixInfantCount");}
	public String getPartySize(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/partySize");}
	public String getRequestStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/requestStatus");}
	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/reservationNumber");}
	public String getVip(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/vip");}
	public String getExtraCareRequired(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/extraCareRequired");}
	public String getGuaranteed(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/guaranteed");}
	public String getPaidInFull(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/paidInFull");}
	public String getDepositPaid(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveDiningReservationsResponse/diningReservations/depositPaid");}
	public NodeList getDiningReservations(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveDiningReservationsResponse/diningReservations");}
}