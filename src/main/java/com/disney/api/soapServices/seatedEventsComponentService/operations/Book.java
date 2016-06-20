package com.disney.api.soapServices.seatedEventsComponentService.operations;

import com.disney.api.soapServices.seatedEventsComponentService.SeatedEventsComponentService;
import com.disney.utils.XMLTools;

public class Book extends SeatedEventsComponentService{
	public Book(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/firstName", value);}
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/lastName", value);}
	public void setPrimaryGuestPhoneNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/phoneDetails/number", value);}
	public void setPrimaryGuestAddress1(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/addressDetails/addressLine1", value);}
	public void setPrimaryGuestAddressCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/addressDetails/country", value);}
	public void setPrimaryGuestAddressPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/addressDetails/postalCode", value);}
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/admissionProducts/productId", value);}
	public void setTicketAlphaCode(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/admissionProducts/ticketAlphaCode", value);}
	public void setTicketQuantity(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/admissionProducts/ticketQuantity", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/communicationChannel", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/salesChannel", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/sourceAccountingCenter", value);}
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/admissionProducts/serviceStartDate", value);}
	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/seatedEventBookResponseWSTO/reservationNumber");}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/bookResponse/seatedEventBookResponseWSTO/travelPlanId");}
}
