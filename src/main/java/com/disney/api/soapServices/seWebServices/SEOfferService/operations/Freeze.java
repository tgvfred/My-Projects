package com.disney.api.soapServices.seWebServices.SEOfferService.operations;

import com.disney.api.soapServices.seWebServices.SEOfferService.SEOfferService;
import com.disney.utils.Randomness;
import com.disney.utils.XMLTools;

public class Freeze extends SEOfferService{
	String firstReservableResourceId;
	
	public Freeze(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("freeze")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/@conversationId", Randomness.generateConversationId());
	}

	public void setServiceContextRequestorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestor/@id", value);
	}

	public void setServiceContextRequestorSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestor/@salesChannel", value);
	}

	public void setServiceContextRequestorCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestor/@communicationsChannel", value);
	}

	public void setServiceContextReceiverId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/receiver/@id", value);
	}

	public void setServiceContextRequestorAuthenticationRole(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestorAuthentication/@role", value);
	}

	public void setServiceContextRequestorAuthenticationIdentity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestorAuthentication/@identity", value);
	}

	public void setServiceContextRequestorAuthenticationUsername(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestorAuthentication/@username", value);
	}

	public void setServiceContextRequestorAuthenticationUri(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/requestorAuthentication/@uri", value);
	}

	public void setServiceContextUserAuthenticationRole(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/userAuthentication/@role", value);
	}

	public void setServiceContextUserAuthenticationIdentity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/serviceContext/userAuthentication/@identity", value);
	}

	public void setGuestValue(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/OfferToFreeze/GuestValue", value);
	}

	public void setStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/OfferToFreeze/RequestedStartDate", value);
	}

	public void setStartTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/OfferToFreeze/RequestedStartTime", value);
	}

	public void setReservableResourceId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/OfferToFreeze/ReservableResources/ReservableResourceID", value);
	}

	public void setResourceQuantity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/OfferToFreeze/ReservableResources/Quantity", value);
	}

	public void setUnitCount(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/OfferToFreeze/UnitCount", value);
	}

	public void setCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/freezeRequest/CommunicationChannel", value);
	}
	
	public String getSuccess(){
		return getResponseNodeValueByXPath("/Envelope/Body/freezeResponse/Success");
	}
	
	public String getFreezeID(){
		return getResponseNodeValueByXPath("/Envelope/Body/freezeResponse/FreezeID");
	}
	
	public String getTimeToLive(){
		return getResponseNodeValueByXPath("/Envelope/Body/freezeResponse/TimeToLive");
	}
	
	public String getFreezeExpiryDateTime(){
		return getResponseNodeValueByXPath("/Envelope/Body/freezeResponse/FreezeExpiryDateTime");
	}
	
	public String getReservableResourceID(){
		return getResponseNodeValueByXPath("/Envelope/Body/freezeResponse/ReservableResourceID");
	}
	
	public String getQuantity(){
		return getResponseNodeValueByXPath("/Envelope/Body/freezeResponse/Quantity");
	}
}
