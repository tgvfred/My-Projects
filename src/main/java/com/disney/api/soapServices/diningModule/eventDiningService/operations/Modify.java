package com.disney.api.soapServices.diningModule.eventDiningService.operations;

import com.disney.AutomationException;
import com.disney.api.soapServices.availSEModule.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.ComponentPriceBuilder;
import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
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

public class Modify extends EventDiningService {
	private boolean notSetFreezeId = true;
	private boolean rrIdSetInAddDetails = false;
	public Modify(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setAddOnComponentUnitPriceDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/unitPrices/date", value);}	
	public void setAddOnComponentServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/serviceStartDate", value);}	
	public void setComponentUnitPriceDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices/unitPrices/date", value);}	
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/serviceStartDate", value);}	
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/serviceStartDate");}
	public String getResponseStatus(){try{
		return getResponseNodeValueByXPath("/Envelope/Body/modifyEventDiningResponse/status");
		}catch(XPathNotFoundException xpnf) {return "false";}
	}
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/travelPlanId", value);}	
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/reservationNumber", value);}	
	public void setComponentPriceComponentType(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices/componentType", value);}	
	public void setComponentPriceComponentId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices/componentId", value);}	
	public void setComponentPriceRevenueClassificationId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices/revenueClassification/id", value);}
	public void setComponentPriceRevenueClassificationName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices/revenueClassification/name", value);}	
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/facilityId", value);}/**
	 * Sets the facility ID in the SOAP Request by using a known Facility Name
	 * @param name - facility name to find Id for and insert into request
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITIES_FACILITIES
	 */
	public void setFacilityIdByFacilityName(String name){setRequestNodeValueByXPath("/Envelope/Body/book/modifyEventDiningRequest/eventDiningPackage/facilityId", FacilityInfo.getMealFacilityIDByName(name));}
	/**
	 * Sets the facility name in the SOAP Request by using known Facility ID
	 * @param id - facility id to find facility name for
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_FACILITIES
	 */
	public void setFacilityNameByFacilityId(String id){setRequestNodeValueByXPath("/Envelope/Body/book/modifyEventDiningRequest/eventDiningPackage/facilityName", FacilityInfo.getMealFacilityNameById(id));}
	/**
	 * Sets the product id in the SOAP Request by using known Product Name
	 * @param value - facility id to find facility name for
	 * @see http://fldcvpswa6204.wdw.disney.com/TDOD/public/gdo/row/QAAUTO_METADATA_ACTIVITY_PRODUCTS
	 */
	public void setProductIdByProductName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/modifyEventDiningRequest/eventDiningPackage/productId", ProductInfo.getMealProductIDByName(value));}
		
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/facilityId");}
	public void setFacilityName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/facilityName", value);
		}catch(XPathNotFoundException xpne){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;facilityName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/facilityName", value);			
		}
	}	
	public void setEnterpriseProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/enterpriseProductId", value);}	
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/productId", value);}
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/productId");}
	public void setProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/productType", value);}	
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/servicePeriodId", value);}	
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/servicePeriodId");}
	public void setSignInLocation(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/signInLocation", value);}	
	public void setAddOnComponentType(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/componentType", value);}	
	public void setAddOnComponentId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/componentId", value);}	
	public void setAddOnRevenueClassificationId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/revenueClassification/id", value);}	
	public void setAddOnRevenueClassificaitonName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/revenueClassification/name", value);}	
	public void setAddOnProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/productId", value);}	
	public void setAddOnProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/addOnComponents/productType", value);}	
	public void addInternalComments(String text, String type){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments/commentText", text);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments/commentType", type);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest", BaseSoapCommands.ADD_NODE.commandAppend("internalComments"));
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments", BaseSoapCommands.ADD_NODE.commandAppend("commentText"));
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments", BaseSoapCommands.ADD_NODE.commandAppend("commentType"));
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments/commentText", text);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments/commentType", type);
		}
	}
	public void setComponentPriceComponentType(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentType", value);}	
	public void setComponentPriceComponentId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentId", value);}	
	public void setComponentPriceRevenueClassificationId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/revenueClassification/id", value);}	
	public void setComponentPriceRevenueClassificationName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/revenueClassification/name", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/sourceAccountingCenter", value);}	
	public void setServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/serviceStartDate", value);}
	public void setPrimaryGuestPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/postalCode", value);}	
	public void setPrimaryGuestAddress1(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/addressLine1", value);}	
	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/country", value);}	
	public void setPrimaryGuestState(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/state", value);}
	public void setPrimaryGuestCity(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/city", value);}	
	public void setPrimaryGuestPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/partyId", value);}	
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/salesChannel", value);}	
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/communicationChannel", value);}
	
	public void setTaxExemptDetails(String certificateNumber, String taxExemptType){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/taxExemptDetail/taxExemptType", taxExemptType);
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest", "fx:AddNode;Node:taxExemptDetail");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptCertificateNumber");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptType");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/taxExemptDetail/taxExemptType", taxExemptType);
	}
	
	public void setProfileDetailIdAndType(String id, String type){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfProfileDetails= 1;
		try{
			numberOfProfileDetails= getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails");
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+numberOfProfileDetails+"]/id");
			numberOfProfileDetails+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:profileDetails");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:id");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:type");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+numberOfProfileDetails+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+numberOfProfileDetails+"]/type", type);
	}
	
	public void setComments(String text, String type){
		int numberOfInternalComments= 1;
		try{
			numberOfInternalComments= getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments");
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+numberOfInternalComments+"]/commentText");
			numberOfInternalComments+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest", "fx:AddNode;Node:internalComments");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentText");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentType");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+numberOfInternalComments+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+numberOfInternalComments+"]/commentType", type);
	}
	
	public void setAllergies(String value){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfAllergies= 1;
		try{
			numberOfAllergies= getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/allergies");
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/allergies["+numberOfAllergies+"]");
			numberOfAllergies+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:allergies");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/allergies["+numberOfAllergies+"]", value);
	}	

	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices");}
	private int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices" + index, BaseSoapCommands.REMOVE_NODE.toString());
		}
	}
	
	public void setReservableResourceId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/inventoryDetails/reservableResourceId", value);
		rrIdSetInAddDetails = true;
	}
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/title", value);}
	public void setPrimaryGuestSuffix(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/suffix", value);}
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/contactName", value);}
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/firstName", value);}
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/lastName", value);}
	public void setPrimaryGuestMiddleName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/middleName", value);}
	/**
	 * Retrieves and set a random reservable resource ID in the SOAP request based on Facility ID
	 */
	public void setReservableResourceId(){
		ReservableResourceByFacilityID resource = new ReservableResourceByFacilityID(getEnvironment(), "Main");
		resource.setFacilityId(getRequestFacilityId());
		resource.sendRequest();
		TestReporter.logAPI(!resource.getResponseStatusCode().equals("200"), "Failed to get Reservable Resource ID", resource);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/inventoryDetails/reservableResourceId", resource.getRandomReservableResourceId());		
	}
	public String getRequestReservableResourceId(){ return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/inventoryDetails/reservableResourceId");	}

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
			
			if(rsPricing.getRowCount() == 0) throw new AutomationException("Failed to retreive data for Facility name ["+facilityName+"] and Product Name ["+productName+"].\n SQL: "  +sql);
			setFacilityName(rsPricing.getValue("FAC_NM"));
			setFacilityId(rsPricing.getValue("FAC_ID"));
			setProductId(rsPricing.getValue("PROD_ID"));
			setProductType(rsPricing.getValue("PROD_TYP_NM"));
			Recordset rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByProductId(rsPricing.getValue("PROD_ID"))));
			if(rsInventory.getRowCount() == 0) throw new AutomationException("No external reference data was found for Product id ["+rsPricing.getValue("PROD_ID")+"]");
			setReservableResourceId(rsInventory.getValue("RSRVBL_RSRC_ID"));
			rrIdSetInAddDetails  = true;
			
			PriceComponents price = new PriceComponents(getEnvironment(), "Main");
			price.setComponentId(getRequestProductId());
			price.sendRequest();
			setRequestDocument(new ComponentPriceBuilder().buildComponentPrices(this, ComponentPriceBuilder.EVENT, "modify", price));
		}
	}
	
	public void addSpecialEventByProductName(String productName, String rrid){
		Database dreamsDb = new OracleDatabase(getEnvironment(), Database.DREAMS);
		Database availDb = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		String facilityId = "";
		String sql=Pricing.getProductSpecialEventByProdName(productName);
		
		
		Recordset rsPricing = new Recordset(dreamsDb.getResultSet(sql));
		
		if(rsPricing.getRowCount() == 0) throw new AutomationException("Failed to retreive data for Product Name ["+productName+"].\n SQL: "  +sql);
		facilityId = rsPricing.getValue("FAC_ID");
		Recordset rsInventory = null;
		String startdate = "";
		String startTime = "";
		rsInventory= new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(facilityId, getRequestServiceStartDate())));
		startdate = rsInventory.getValue("Start_Date").contains(" ") 
				   ? rsInventory.getValue("Start_Date").substring(0,rsInventory.getValue("Start_Date").indexOf(" "))
			       : rsInventory.getValue("Start_Date");
						   
		startTime = rsInventory.getValue("Start_Date").replace(".0", "");
		rrid = rsInventory.getValue("Resource_ID");
	
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:eventInventory");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory", "fx:AddNode;Node:duration");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory", "fx:AddNode;Node:freezeElapsedTime");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory", "fx:AddNode;Node:freezeTimeToLive");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory", "fx:AddNode;Node:inventoryDetails");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory/inventoryDetails", "fx:AddNode;Node:reservableResourceId");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory/inventoryDetails/reservableResourceId",rrid);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory", "fx:AddNode;Node:unitMeasureCount");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:eventOrShowStartDate");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory/duration","1");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory/freezeElapsedTime","0");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory/freezeTimeToLive","60");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventInventory/unitMeasureCount","1");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventOrShowStartDate",rsInventory.getValue("Start_Date").replace(" " , "T"));
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/eventOrShowFacilityId",facilityId);
	}
	

	@Override
	public void sendRequest(){
		if(notSetFreezeId ){
			setFreezeId();			
		}
		super.sendRequest();
		if(getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY") ||	
				getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
				getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
			if(notSetFreezeId) 	setFreezeId();
			super.sendRequest();	
		}
	}
	public void setFreezeId(){
		
	//	if(!isNewFreezeIdRequired){
			Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
			Recordset rs = null;
			String freezeId = "";
			Freeze freeze = new Freeze(getEnvironment(), "Main");
			Recordset rsInventory = null;
			String startdate = "";
			String startTime = "";
			startdate = getRequestServiceStartDate().contains("T") 
					   ? getRequestServiceStartDate().substring(0,getRequestServiceStartDate().indexOf("T"))
				       : getRequestServiceStartDate();
					   
			if(!rrIdSetInAddDetails){
				rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(),startdate)));
				setReservableResourceId(rsInventory.getValue("Resource_ID"));
			}else{
				rsInventory = new Recordset(db.getResultSet(AvailSE.getResourceAvailibleTimesByIdAndStartDate(getRequestReservableResourceId(),startdate)));			
			}
			if(rsInventory.getRowCount() == 0){
				rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(),startdate)));
				setReservableResourceId(rsInventory.getValue("Resource_ID"));
			}
			startdate = rsInventory.getValue("START_DATE").contains(" ") 
					   ? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
				       : rsInventory.getValue("START_DATE");
							   
			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
			freeze.setReservableResourceId(getRequestReservableResourceId());
			freeze.setStartDate(startdate);	
			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
			freeze.sendRequest();
	//			TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
			int timesTried = 0;
			while(freeze.getSuccess().equals("failure") && timesTried < 5){	
				rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
		
				startdate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
				startTime = rsInventory.getValue("START_DATE").replace(".0", "");
				setReservableResourceId(rsInventory.getValue("Resource_ID"));
				freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
				freeze.setStartDate(startdate);	
				freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
	
				freeze.sendRequest();	
				if(freeze.getSuccess().equals("failure")) timesTried++;
			}
	
			TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
	
			freezeId = freeze.getFreezeID();
			setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
			
		
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/freezeId", freezeId);
			notSetFreezeId = false;
	/*	}else{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/freezeId", BaseSoapCommands.REMOVE_NODE.toString());
			notSetFreezeId = false;
		}*/
	}

	public void setFreezeId(String freezeId){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/freezeId", freezeId);
		}catch(XPathNotFoundException e){}
		
		notSetFreezeId = false;
	}
	
	public void setParty(HouseHold party){
		int currentGuest = 1;
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

				setPrimaryGuestPartyId(guest.getPartyId());
				
				
				addPrimaryGuestAddresses(guest);
				addPrimaryGuestEmails(guest);
			}
			
			if(currentGuest == 1)	partyRolePosition="";
			else partyRolePosition = "[" + currentGuest + "]";
			
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/correlationID", "0");

			currentGuest++;
		}
	}
	
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/locatorId", value);}
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/guestLocatorId", value);}
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/primary", value);}
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails/addressLine2", value);}
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
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
			}
			position++;
		}
	}
	
	private void addPrimaryGuestAddressDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
			position++;
		}
	}
	
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/primary", value);}
	public void setPrimaryGuestEmailAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/locatorId", value);}	
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/guestLocatorId", value);}	
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails/address", value);}
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
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
			}
			position++;
		}
	}
	
	private void addPrimaryGuestEmailDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}
	
	
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
}