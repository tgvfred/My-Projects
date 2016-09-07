package com.disney.api.soapServices.diningModule.seatedEventsComponentService.operations;

import com.disney.api.soapServices.diningModule.seatedEventsComponentService.SeatedEventsComponentService;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;

public class Book extends SeatedEventsComponentService{
	private String inventoryBefore;
	private String inventoryAfter;
	private String reservableResourceId;
	private String dateTime;
	private String startTime;
	private String startDate;
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
	/**
	 * Sets the Nexus Reservation Code
	 * @param value - Nexus Reservation Code
	 */
	public void setNexusReservationCode(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/nexusReservationCode", value);}
	/**
	 * Sets the title of the primary guest
	 * @param value - title of the primary guest
	 */
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/title", value);}
	/**
	 * Sets the primary guest country
	 * @param value - primary guest country
	 */
	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/primaryGuest/addressDetails/country", value);}
	/**
	 * Sets the travel plan ID
	 * @param value - travel plan ID
	 */
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookCirqueRequest/travelPlanId", value);}
	
	
	
	
	
	
	
	
	
	
	
//	@Override
//	public void sendRequest(){
//		if(notSetFreezeId) 	setFreezeId();
//		boolean failure = false;
//		try{setInventoryCountBefore(getInventory());}
//		catch(Exception e){failure = true;}
//		super.sendRequest();
//		if(!failure) setInventoryCountAfter(getInventory());
//		if(!invokeRimError){
//			if(getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
//					getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
//				setFreezeId();
//				setInventoryCountBefore(getInventory());
//				super.sendRequest();	
//				setInventoryCountAfter(getInventory());
//			}
//		}
//	}
//	private void setInventoryCountBefore(String before){inventoryBefore = before;}
//	public String getInventoryCountBefore(){return inventoryBefore;}
//	private void setInventoryCountAfter(String after){inventoryAfter = after;}
//	public String getInventoryCountAfter(){return inventoryAfter;}
//	public String getReservableResourceId(){return reservableResourceId;}
//	public String getDateTime(){return dateTime;}
//	public String getStartTime(){return startTime;}
//	public String getStartDate(){return startDate;}
//	private String getInventory(){
//		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
//		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getAvailableResourceCount(reservableResourceId, dateTime)));
////		rsInventory.print();
//		return rsInventory.getValue("BK_CN");
//	}
//	
//	public void setFreezeId(){
//		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
//		String freezeId = "";
//		Freeze freeze = new Freeze(getEnvironment(), "Main");
//
//		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
//		
//		startDate = rsInventory.getValue("START_DATE").contains(" ") 
//						   ? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
//					       : rsInventory.getValue("START_DATE");
//						   
//		startTime = rsInventory.getValue("START_DATE").replace(".0", "");
//		setReservableResourceId(rsInventory.getValue("Resource_ID"));
//		freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));			
//		reservableResourceId = rsInventory.getValue("Resource_ID");
//		dateTime = rsInventory.getValue("START_DATE").replace(".0", "");
//		freeze.setStartDate(startDate);	
//		freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
//		freeze.sendRequest();
//		int timesTried = 0;
//		while(freeze.getSuccess().equals("failure") && timesTried < 5){	
//			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
//			
//			startDate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
//			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
//			setReservableResourceId(rsInventory.getValue("Resource_ID"));
//			freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
//			freeze.setStartDate(startDate);	
//			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
//			freeze.sendRequest();	
//			if(freeze.getSuccess().equals("failure"))timesTried++;
//		}
//		TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
//		freezeId = freeze.getFreezeID();
//		setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
//		
//		if(!invokeRimError) setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/freezeId", freezeId);
//		notSetFreezeId = false;
//	}
//	public void setFreezeId(String freezeId){
//		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/freezeId", freezeId);
//		notSetFreezeId = false;
//	}
//	public void setFreezeIdForError(String freezeId){
//		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/freezeId", freezeId);
//		invokeRimError = true;
//	}
}
