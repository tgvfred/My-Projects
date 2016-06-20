package com.disney.api.soapServices.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.tableServiceDiningServicePort.TableServiceDiningServicePort;
import com.disney.utils.XMLTools;

public class Cancel extends TableServiceDiningServicePort {
	public Cancel(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("cancel")));
		
		generateServiceContext();				
        setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/reservationNumber", value);}
	public void setCancelReasonId(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/cancelReasonId", value);}
	public void setCancelFeeWaived(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/isCancelFeeWaived", value);}
	public void setCancelFeeOverridden(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/isCancelFeeOverridden", value);}
	public void setOverriddenCancelFee(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/overriddenCancelFee", value);}
	public void setAuthorizationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/authorizationNumber", value);}
	public void ssetSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/communicationChannel", value);}
	public void setBypassLogToDemandTracking(String value){setRequestNodeValueByXPath("/Envelope/Body/cancel/cancelTableServiceRequest/bypassLogToDemandTracking", value);}

	public String getCancellationConfirmationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/cancelTableServiceResponse/cancellationNumber");}
}
