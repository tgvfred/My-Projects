package com.disney.api.soapServices.activityModule.activityServicePort.operations;

import com.disney.AutomationException;
import com.disney.api.soapServices.activityModule.activityServicePort.ActivityService;
import com.disney.api.soapServices.availSEModule.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.ComponentPriceBuilder;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
import com.disney.test.utils.Sleeper;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.FacilityInfo;
import com.disney.utils.dataFactory.ProductInfo;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Pricing;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

/**
 * @author AutoXP
 *
 */
public class Book extends ActivityService{	
	private boolean notSetFreezeId = true;
	private boolean rrIdSetInAddDetails = false;
	private boolean invokeRimError = false;
	protected String reservableResourceId;
	protected String dateTime;
	protected String inventoryBefore;
	protected String inventoryAfter;
	protected String startDate;
	protected String startTime;
	private HouseHold party;
	public Book(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	/**
	 * Sets the travel plan ID in the SOAP Request
	 * @param value - travel plan ID
	 */
	public void setTravelPlanId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelPlanId", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest", "fx:AddNode;Node:travelPlanId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelPlanId", value);
		}
	}
	/**
	 * Sets the sales channel in the in the SOAP Request
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/salesChannel", value);}
	/**
	 * Sets the communications channel in the SOAP Request
	 * @param value - communications channel
	 */
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/communicationChannel", value);}
	/**
	 * Sets the unit price dateTime(s) in the SOAP Request
	 * @param value - unit price dateTime
	 * @param index - index of the node to update
	 */
	public void setUnitPriceDateTime(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/unitPrices/date", value);}
	/**
	 * Sets the component unit price dateTime(s) in the SOAP Request
	 * @param value - component unit price dateTime
	 */
	public void setComponentUnitPriceDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices/unitPrices/date", value);}	
//	/**
//	 * Sets the service start dateTime in the SOAP Request
//	 * @param value - service start dateTime
//	 */
//	public void setServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/serviceStartDate", value);}	
	/**
	 * Sets the addON component unit price dateTime in the SOAP Request
	 * @param value - addOn component unit price dateTime
	 */
	public void setAddOnComponentUnitPriceDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/unitPrices/date", value);}	
	/**
	 * Sets the addOn service start dateTime in the SOAP Request
	 * @param value - addOn service start dateTime
	 */
	public void setAddOnServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/serviceStartDate", value);}	
	/**
	 * Sets the source accounting center in the SOAP Request
	 * @param value = source accounting center
	 */
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/sourceAccountingCenter", value);}	
	/**
	 * Gets the sales channel in the SOAP Request
	 * @return sales channel
	 */
	public String getSalesChannel(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/salesChannel");}	
	/**
	 * Gets the communications channel in the SOAP Request
	 * @return communications channel
	 */
	public String getCommunicationsChannel(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/communicationChannel");}	
//	/**
//	 * Gets the primary guest first name in the SOAP Request
//	 * @return primary guest first name
//	 */
//	public String getPrimaryGuestFirstName(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/firstName");}	
//	/**
//	 * Gets the primary guest last name in the SOAP Request
//	 * @return
//	 */
//	public String getPrimaryGuestLastName(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/lastName");}
	/**
	 * Gets the travel plan ID in the SOAP Response
	 * @return travel plan ID
	 */
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/bookActivityComponentResponse/bookResponse/travelPlanId");}	
	/**
	 * Gets the travel plan segment ID in the SOAP Response
	 * @return travel plan segment ID
	 */
	public String getTravelPlanSegmentId(){return getResponseNodeValueByXPath("/Envelope/Body/bookActivityComponentResponse/bookResponse/confirmationNumber");}	
	/**
	 * Sets the component price component type in the SOAP Request
	 * @param value - component price component type
	 * @param index - index of node to set
	 */
	public void setComponentPriceComponentType(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentType", value);}	
	/**
	 * Sets the component price component id in the SOAP Request
	 * @param value - component price component id
	 * @param index - index of node to set
	 */
	public void setComponentPriceComponentId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentId", value);}	
	/**
	 * Sets the component price revenue classification ID in the SOAP Request
	 * @param value - component price revenue classification ID
	 * @param index - index of node to set
	 */
	public void setComponentPriceRevenueClassificationId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/revenueClassification/id", value);}	
	/**
	 * Sets the component price revenue classification name in the SOAP Request
	 * @param value - component price revenue classification name
	 * @param index - index of node to set
	 */
	public void setComponentPriceRevenueClassificationName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/revenueClassification/name", value);}	
//	/**
//	 * Sets the facility ID in the SOAP Request
//	 * @param value - facility ID
//	 */
//	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityId", value);}	
	/**
	 * Sets the facility name in the SOAP Request
	 * @param value - facility name
	 */
	public void setFacilityName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityName", value);
		}catch(XPathNotFoundException xpne){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity", "fx:AddNode;facilityName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityName", value);			
		}
	}
	/**
	 * Sets the facility ID in the SOAP Request by using a known Facility Name
	 * @param name - facility name to find Id for and insert into request
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITIES_FACILITIES
	 */
	public void setFacilityIdByFacilityName(String name){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityId", FacilityInfo.getActivityFacilityIDByName(name));}
	/**
	 * Sets the facility name in the SOAP Request by using known Facility ID
	 * @param id - facility id to find facility name for
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_FACILITIES
	 */
	public void setFacilityNameByFacilityId(String id){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityName", FacilityInfo.getActivityFacilityNameById(id));}
	/**
	 * Sets the product id in the SOAP Request by using known Product Name
	 * @param value - facility id to find facility name for
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 */
	public void setProductIdByProductName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productId", ProductInfo.getActivityProductIDByName(value));}
//	/**
//	 * Sets the product ID in the SOAP Request
//	 * @param value - product ID
//	 */
//	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productId", value);}	
	/**
	 * Sets the product type in the SOAP Request
	 * @param value - product type
	 */
	public void setProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productType", value);}	
//	/**
//	 * Sets the service period ID in the SOAP Request
//	 * @param value - service period ID
//	 */
//	public void setServicePeriosId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/servicePeriodId", value);}	
	/**
	 * Sets the sign-in location in the SOAP Request
	 * @param value - sign-in location
	 */
	public void setSignInLocation(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/signInLocation", value);}	
	/**
	 * Sets the addOn component type in the SOAP Request
	 * @param value addOn component type
	 */
	public void setAddOnComponentType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/componentType", value);}	
	/**
	 * Sets the addON component ID in the SOAP Request
	 * @param value - addOn component ID
	 */
	public void setAddOnComponentId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/componentId", value);}	
	/**
	 * Sets the addOn Revenue Classification ID in the SOAP Request
	 * @param value - addOn revenue classification ID
	 */
	public void setAddOnRevenueClassificationId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/revenueClassification/id", value);}	
	/**
	 * Sets the addOn revenue classification name in the SOAP Request
	 * @param value - addOn revenue classification name
	 */
	public void setAddOnRevenueClassificaitonName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/componentPrices/revenueClassification/name", value);}	
	/**
	 * Sets the addOn product ID in the SOAP Request
	 * @param value - addOn product ID
	 */
	public void setAddOnProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/productId", value);}	
	/**
	 * Sets the addOn product type in the SOAP Request
	 * @param value - addOn product type
	 */
	public void setAddOnProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/addOnComponents/productType", value);}	
	/**
	 * Sets the primary guest address line 1 in the SOAP Request
	 * @param value - primary guest address line 1
	 */
	public void setPrimaryGuestAddress1(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/addressLine1", value);}	
//	/**
//	 * Sets the primary guest city in the SOAP Request
//	 * @param value - primary guest city
//	 */
//	public void setPrimaryGuestCity(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/city", value);}	
//	/**
//	 * Sets the primary guest country in the SOAP Request
//	 * @param value - primary guest country
//	 */
//	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/country", value);}	
//	/**
//	 * Sets the primary guest postal code in the SOAP Request
//	 * @param value - primary guest postal code
//	 */
//	public void setPrimaryGuestPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/postalCode", value);}	
//	/**
//	 * Sets the primary guest state in the SOAP Request
//	 * @param value - primary guest postal code
//	 */
//	public void setPrimaryGuestState(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/state", value);}		
	/**
	 * Sets the primary guest city in the SOAP Request
	 * @param value - primary guest city
	 */
	public void setPrimaryGuestCity(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/city", value);}	
	/**
	 * Sets the primary guest country in the SOAP Request
	 * @param value - primary guest country
	 */
	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/country", value);}	
	/**
	 * Sets the primary guest postal code in the SOAP Request
	 * @param value - primary guest postal code
	 */
	public void setPrimaryGuestPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/postalCode", value);}	
	/**
	 * Sets the primary guest state in the SOAP Request
	 * @param value - primary guest state
	 */
	public void setPrimaryGuestState(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/state", value);}	
	/**
	 * Gets the primary guest party ID in the SOAP Response
	 * @return - primary guest party ID
	 */
	public String getPartyId(){return getResponseNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/partyId");}
	/**
	 * Gets the number of component IDs in the SOAP Response
	 * @return - number of component IDs
	 */
	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices/componentId");}
	/**
	 * Gets the number of nodes for a given xpath in the SOAP Request
	 * @param xpath - xpath for which to search
	 * @return - number of node for a given xpath
	 */
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	/**
	 * Sets component ID and type in the SOAP request
	 * @param index -  index of the node to set
	 * @param id - component id to set
	 * @param type - component type to set
	 */
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices["+index+"]/componentType", type);
	}
	/**
	 * Sets component ID and type in the SOAP request
	 * @param index -  index of the node to set
	 * @param id - component id to set
	 * @param type - component type to set
	 */
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	/**
	 * Removes component prices from the SOAP request
	 */
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/componentPrices" + index, BaseSoapCommands.REMOVE_NODE.toString());
		}
	}	
	/**
	 * Gets the facility ID from the SOAP response
	 * @return facility ID from the SOAP response
	 */
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityId");}
	/**
	 * Gets the service start dateTime from the SOAP response
	 * @return service start dateTime
	 */
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/serviceStartDate");}
	/**
	 * Gets the service period ID from the SOAP response
	 * @return service period ID
	 */
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/servicePeriodId");}
	/**
	 * Gets the product ID from the SOAP response
	 * @return product ID
	 */
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productId");}
	/**
	 * Gets the product type from the SOAP response
	 * @return product type
	 */
	public String getRequestProductType(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productType");}
	/**
	 * Sets the facility ID in the SOAP Request
	 * @param value - facility ID
	 */
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/facilityId", value);}

	/**
	 * Sets the product ID in the SOAP request
	 * @param value product ID
	 */
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/productId", value);}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/servicePeriodId", value);}
	/**
	 * Sets the service start dateTime in the SOAP request
	 * @param value service start dateTime
	 */
	public void setServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/serviceStartDate", value);}
	/**
	 * Sets the reservable resource ID in the SOAP request
	 * @param value reservable resource ID
	 */
	public void setReservableResourceId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/inventoryDetails/reservableResourceId", value);
		reservableResourceId = getRequestReservableResourceId();
	}
	public void setReservableResourceIdForError(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/inventoryDetails/reservableResourceId", value);
		rrIdSetInAddDetails = true;
	}
	/**
	 * Retrieves and set a random reservable resource ID in the SOAP request based on Facility ID
	 */
	public void setReservableResourceId(){
		ReservableResourceByFacilityID resource = new ReservableResourceByFacilityID(getEnvironment(), "Main");
		resource.setFacilityId(getRequestFacilityId());
		resource.sendRequest();
		TestReporter.logAPI(!resource.getResponseStatusCode().equals("200"), "Failed to get Reservable Resource ID", resource);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/inventoryDetails/reservableResourceId", resource.getRandomReservableResourceId());		
	}
	
	public void setVipLevel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/vipLevel", value);}
	public void setInventoryOverrideReasonId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/inventoryOverideReasonId", value);}
	
	public String getRequestReservableResourceId(){ return getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/inventoryDetails/reservableResourceId");	}
	
//	@Override
//	public void sendRequest(){
//		if(notSetFreezeId) 	setFreezeId();
//		super.sendRequest();
//		if(getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY") ||	
//				getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
//				getResponse().contains("Inconsitent Data : getBookingDate : CampusId is Not found for Location")){
//			if(notSetFreezeId) 	setFreezeId();
//			super.sendRequest();	
//		}
//	}
	
	@Override
	public void sendRequest(){
		if(notSetFreezeId) 	setFreezeId();
		boolean failure = false;
		try{setInventoryCountBefore(getInventory());}
		catch(Exception e){failure = true;}
		super.sendRequest();
		if(!failure) setInventoryCountAfter(getInventory());
		if(!invokeRimError){
			if(getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
					getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
				setFreezeId();
				setInventoryCountBefore(getInventory());
				super.sendRequest();
				int maxTries = 10;
				int tries = 0;
				do{
					Sleeper.sleep(1000);
					tries++;
					setInventoryCountAfter(getInventory());
				}while(tries <= maxTries && getInventoryCountBefore() == getInventoryCountAfter());
			}
		}
	}
	private void setInventoryCountBefore(String before){inventoryBefore = before;}
	public String getInventoryCountBefore(){return inventoryBefore;}
	private void setInventoryCountAfter(String after){inventoryAfter = after;}
	public String getInventoryCountAfter(){return inventoryAfter;}
	public String getReservableResourceId(){return reservableResourceId;}
	public String getDateTime(){return dateTime;}
	public String getStartTime(){return startTime;}
	public String getStartDate(){return startDate;}
	private String getInventory(){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getAvailableResourceCount(reservableResourceId, dateTime)));
//		rsInventory.print();
		return rsInventory.getValue("BK_CN");
	}
	
	public void setFreezeId(String freezeId){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/freezeId", freezeId);
		notSetFreezeId = false;
	}
	public void setFreezeId(){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");
//<<<<<<< HEAD
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
//=======
		Recordset rsInventory = null;
		startDate = getRequestServiceStartDate().contains("T") 
				   ? getRequestServiceStartDate().substring(0,getRequestServiceStartDate().indexOf("T"))
			       : getRequestServiceStartDate();
		if(!rrIdSetInAddDetails){
			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(),startDate)));
			setReservableResourceId(rsInventory.getValue("Resource_ID"));
		}else{
			rsInventory = new Recordset(db.getResultSet(AvailSE.getResourceAvailibleTimesByIdAndStartDate(getRequestReservableResourceId(),startDate)));			
		}
		if(rsInventory.getRowCount() == 0){
			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(),startDate)));
			setReservableResourceId(rsInventory.getValue("Resource_ID"));
		}
		startDate = rsInventory.getValue("START_DATE").contains(" ") 
				   ? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
			       : rsInventory.getValue("START_DATE");
		startTime = rsInventory.getValue("START_DATE").replace(".0", "");
		dateTime = startTime;
		freeze.setStartDate(startDate);	
		freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
		freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));
		TestReporter.logStep("Generating Freeze ID for Reservable Resource ["+rsInventory.getValue("Resource_ID")+"]");
		freeze.sendRequest();
//			TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
		int timesTried = 0;
		while(freeze.getSuccess().equals("failure") && timesTried < 5){				
			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
			
			startDate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
//>>>>>>> bcbd28f51f4f4d70956471a29f528c4e2d10d279
			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
			setReservableResourceId(rsInventory.getValue("Resource_ID"));
			freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
			freeze.setStartDate(startDate);	
			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
			freeze.sendRequest();	
			if(freeze.getSuccess().equals("failure"))timesTried++;
		}
		TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
		freezeId = freeze.getFreezeID();
		setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());		
		if(!invokeRimError) setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/freezeId", freezeId);
		notSetFreezeId = false;
	}
	public void setFreezeIdForError(String freezeId){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/freezeId", freezeId);
		invokeRimError = true;
	}
	
	public void setFreezeId(String throwAway, String startDate){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rs = null;
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");

		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), startDate)));
//		rsInventory.print();
		String startdate = rsInventory.getValue("START_DATE").contains(" ") 
				? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
				: rsInventory.getValue("START_DATE");
		String startTime = rsInventory.getValue("START_DATE").replace(".0", "");
		setReservableResourceId(rsInventory.getValue("Resource_ID"));
		freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));
		freeze.setStartDate(startdate);
		freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
		TestReporter.logStep("Generating Freeze ID for Reservable Resource ["+rsInventory.getValue("Resource_ID")+"]");
		freeze.sendRequest();
		TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
		int timesTried = 0;
		while(freeze.getSuccess().equals("failure") && timesTried < 5){
			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), startDate)));
//			rsInventory.print();
			startdate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
			setReservableResourceId(rsInventory.getValue("Resource_ID"));
			freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));
			freeze.setStartDate(startdate);
			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
			freeze.sendRequest();
			if(freeze.getSuccess().equals("failure")) timesTried++;
		}
		if(freeze.getSuccess().equals("failure")){
			TestReporter.logAPI(true, "Could not Freeze Inventory", freeze);
		}
		freezeId = freeze.getFreezeID();
		setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/freezeId", freezeId);
		notSetFreezeId = false;
	}

	public void setTaxExemptDetails(String certificateNumber, String taxExemptType){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/taxExemptDetail/taxExemptType", taxExemptType);
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest", "fx:AddNode;Node:taxExemptDetail");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptCertificateNumber");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptType");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/taxExemptDetail/taxExemptType", taxExemptType);
	}
	
	public void setProfileDetailIdAndType(String id, String type){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfProfileDetails= 1;
		try{
			numberOfProfileDetails= getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/profileDetails");
			getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/profileDetails["+numberOfProfileDetails+"]/id");
			numberOfProfileDetails+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity", "fx:AddNode;Node:profileDetails");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:id");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:type");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/profileDetails["+numberOfProfileDetails+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/profileDetails["+numberOfProfileDetails+"]/type", type);
	}
	
	public void setComments(String text, String type){
		int numberOfInternalComments= 1;
		try{
			numberOfInternalComments= getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/internalComments");
			getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/internalComments["+numberOfInternalComments+"]/commentText");
			numberOfInternalComments+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest", "fx:AddNode;Node:internalComments");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentText");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentType");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/internalComments["+numberOfInternalComments+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/internalComments["+numberOfInternalComments+"]/commentType", type);
	}
	
	public void setAllergies(String value){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfAllergies= 1;
		try{
			numberOfAllergies= getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/allergies");
			getRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/allergies["+numberOfAllergies+"]");
			numberOfAllergies+=1;;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity", "fx:AddNode;Node:allergies");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/allergies["+numberOfAllergies+"]", value);

	}
	
	
	/**
	 * Sets the primary guest suffix in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestSuffix(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/suffix", value);}	
	/**
	 * Sets the primary guest title in the SOAP request
	 * @param value - primary guest title
	 */
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/title", value);}
	/**
	 * Sets the contact name in the SOAP request
	 * @param value - contact name
	 */
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/contactName", value);}	
	/**
	 * Sets primary guest first name in the SOAP request
	 * @param value - primary guest first name
	 */
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/firstName", value);}
	/**
	 * Sets primary guest last name in the SOAP request
	 * @param value - primary guest last name
	 */
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/lastName", value);}
	/**
	 * Sets primary guest middle name in the SOAP request
	 * @param value - primary guest middle name
	 */
	public void setPrimaryGuestMiddleName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/middleName", value);}	
	/**
	 * Sets flag to determine a primary guest address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest address is the primary address
	 */
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/primary", value);}
	/**
	 * Sets primary guest address locator ID in the SOAP request
	 * @param value - guest address locator ID
	 */
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/locatorId", value);}	
	/**
	 * Sets primary guest address line 2 in the SOAP request
	 * @param value - primary guest address line 2
	 */
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/addressLine2", value);}		
	/**
	 * Sets primary guest address guest  locator ID in the SOAP request
	 * @param value - primary guest address guest  locator ID
	 */
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails/guestLocatorId", value);}	
	/**
	 * Sets primary guest party ID in the SOAP request
	 * @param value - primary guest party ID 
	 */
	public void setPrimaryGuestPartyid(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/partyId", value);}	
	/**
	 * Sets flag to determine a primary guest email address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest email address is the primary address
	 */
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails/primary", value);}
	/**
	 * Sets primary guest email address locator ID in the SOAP request
	 * @param value - primary guest email address locator ID
	 */
	public void setPrimaryGuestEmailAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails/locatorId", value);}	
	/**
	 * Sets primary guest email address guest locator ID in the SOAP request
	 * @param value - primary guest email address guest locator ID
	 */
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails/guestLocatorId", value);}	
	/**
	 * Sets primary guest email address in the SOAP request
	 * @param value - primary guest email address
	 */
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails/address", value);}	
	/**
	 * Adds travel agency in the SOAP request
	 * @param agencyId - travel agency
	 */
	public void addTravelAgency(String agencyId){addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");}

	public void addDetailsByProductName(String productName){
		addDetailsByFacilityIdProductName(null, getRequestFacilityId(), productName);
			
	}
	public void addDetailsByFacilityNameAndProductName(String facilityName, String productName){
		addDetailsByFacilityIdProductName(facilityName, null, productName);
	}
	
	private void addDetailsByFacilityIdProductName(String facilityName, String facilityId, String productName){
		Database dreamsDb = new OracleDatabase(getEnvironment(), Database.DREAMS);
		Database availDb = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		String sql= "";
		
		if(facilityName != null ) sql=Pricing.getProductInfoByFacilityNameAndProdName(facilityName, productName);
		else if(facilityId != null ) sql=Pricing.getProductInfoByFacilityIdAndProdName(facilityId, productName);
		if(sql != ""){	
			Recordset rsPricing = new Recordset(dreamsDb.getResultSet(sql));
			rsPricing.print();
			if(rsPricing.getRowCount() == 0) throw new AutomationException("Failed to retreive data for Facility name ["+facilityName+"] and Product Name ["+productName+"].\n SQL: "  +sql);
			setFacilityName(rsPricing.getValue("FAC_NM"));
			setFacilityId(rsPricing.getValue("FAC_ID"));
			setProductId(rsPricing.getValue("PROD_ID"));
			setProductType(rsPricing.getValue("PROD_TYP_NM"));
			Recordset rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByProductId(rsPricing.getValue("PROD_ID"))));
			if(rsInventory.getRowCount() == 0) throw new AutomationException("No external reference data was found for Product id ["+rsPricing.getValue("PROD_ID")+"]");
			setReservableResourceId(rsInventory.getValue("RSRVBL_RSRC_ID"));
			rrIdSetInAddDetails = true;

			PriceComponents price = null;
			
			for(Guest guest : party.getAllGuests()){
				price = new PriceComponents(getEnvironment(), "Main");
				price.setComponentId(getRequestProductId());
				price.setAge(guest.getAge());
				price.setAgeType(Integer.valueOf(guest.getAge()) > 18 ? "Adult" : "Child");
				price.sendRequest();
				setRequestDocument(new ComponentPriceBuilder().buildComponentPrices(this, ComponentPriceBuilder.ACTIVITY, "book", price));
			}
		}
	}
	/**
	 * Adds travel agency in the SOAP request
	 * @param agencyIataNumber - travel agency IATA number
	 * @param agencyOdsId - travel agency ODS ID
	 * @param guestAgencyId - guest travel agency ID
	 * @param agentId - travel agent ID
	 * @param guestAgentId - guest travel agent ID
	 * @param confirmationLocatorValue - travel agency confirmation locator value
	 * @param guestConfirmationLocationId - guest travel agency confirmation location ID
	 */
	public void addTravelAgency(String agencyIataNumber, String agencyOdsId, String guestAgencyId, String agentId, String guestAgentId, String confirmationLocatorValue, String guestConfirmationLocationId){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest", "fx:AddNode;Node:travelAgency");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:agencyIataNumber");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:agencyOdsId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:guestTravelAgencyId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:agentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:guestAgentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:confirmationLocatorValue");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency", "fx:AddNode;Node:guestConfirmationLocationId");

		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/agencyIataNumber", agencyIataNumber);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/agencyOdsId", agencyOdsId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/guestTravelAgencyId", guestAgencyId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/agentId", agentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/guestAgentId", guestAgentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/confirmationLocatorValue", confirmationLocatorValue);		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/travelAgency/guestConfirmationLocationId", guestConfirmationLocationId);
	}
	/**
	 * Sets the party for an instance of this class
	 * @param party - HouseHold instance containing all guests and related data
	 */
	public void setParty(HouseHold party){
		int currentGuest = 1;
		this.party = party;
		String partyRolePosition = "";
		addPartyRoleNodes(party.getAllGuests().size() - 1);
		for( Guest guest : party.getAllGuests()){
			if(guest.isPrimary()){
				if(guest.getTitle().isEmpty()) setPrimaryGuestTitle(BaseSoapCommands.REMOVE_NODE.toString());
				else setPrimaryGuestTitle(guest.getTitle());
				
				if(guest.getSuffix().isEmpty()) setPrimaryGuestSuffix(BaseSoapCommands.REMOVE_NODE.toString());
				else setPrimaryGuestSuffix(guest.getSuffix());
				
				setContactName(guest.getFullName());
				setPrimaryGuestFirstName(guest.getFirstName());
				setPrimaryGuestLastName(guest.getLastName());
				
				if(guest.getMiddleName().isEmpty()) setPrimaryGuestMiddleName(BaseSoapCommands.REMOVE_NODE.toString());
				else setPrimaryGuestMiddleName(guest.getMiddleName());

				setPrimaryGuestPartyid(guest.getPartyId());
				
				
				addPrimaryGuestAddresses(guest);
				addPrimaryGuestEmails(guest);
			}
			
			if(currentGuest == 1)	partyRolePosition="";
			else partyRolePosition = "[" + currentGuest + "]";
			
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/correlationID", "0");

			currentGuest++;
		}
	}
	/**
	 * Adds the primary guest address to the SOAP request
	 * @param guest - Guest-class instance for the primary guest
	 */
	private void addPrimaryGuestAddresses(Guest guest){
		addPrimaryGuestAddressDetailNodes( guest.getAllAddresses().size() - 1);
		int position = 1;
		
		for(Address address : guest.getAllAddresses()){
			if( position == 1){
				setPrimaryGuestAddressLocatorId("0");
				setPrimaryGuestAddressGuestLocatorId("0");
				setPrimaryGuestAddressIsPrimary(address.isPrimary() ? "true":"false");
				setPrimaryGuestAddress1(address.getAddress1());
				if(address.getAddress2().isEmpty()) setPrimaryGuestAddress2(BaseSoapCommands.REMOVE_NODE.toString());
				else setPrimaryGuestAddress2(address.getAddress2());
				setPrimaryGuestCity(address.getCity());
				setPrimaryGuestCountry(address.getCountry());
				setPrimaryGuestPostalCode(address.getZipCode());
				setPrimaryGuestState(address.getStateAbbv());
			}else{
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
			}
			position++;
		}
	}
	/**
	 * Adds nodes for the primary guest address details nodes
	 * @param numberToAdd - number of addresses to add
	 */
	private void addPrimaryGuestAddressDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
			position++;
		}
	}
	/**
	 * Adds the primary guest email address(es) to the SOAP request
	 * @param guest - Guest-class instance for the primary guest
	 */
	private void addPrimaryGuestEmails(Guest guest){		
		addPrimaryGuestEmailDetailNodes(guest.getAllEmails().size() - 1);		
		int position = 1;		
		for(Email email : guest.getAllEmails()){
			if(position == 1){
				setPrimaryGuestEmailAddressLocatorId("0");
				setPrimaryGuestEmailAddressGuestLocatorId("0");
				setPrimaryGuestEmailAddressIsPrimary(email.isPrimary() ? "true":"false");
				setPrimaryGuestEmailAddress(email.getEmail());
				
			}else{
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
			}
			position++;
		}
	}	
	/**
	 * Adds nodes for the primary guest email details nodes
	 * @param numberToAdd - number of emails to add
	 */
	private void addPrimaryGuestEmailDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}	
	/**
	 * Adds party roles for all guests that are not the primary guest
	 * @param numberToAdd - number of non-primary guests to add
	 */
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
}
