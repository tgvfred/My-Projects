package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.AutomationException;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.ComponentPriceBuilder;
import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.database.sqlStorage.Pricing;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Modify extends ShowDiningService {
	private boolean notSetFreezeId = true;
	private boolean rrIdSetInAddDetails = false;
	private String reservableResourceId;
	private boolean newDateTime;
	private String existingRRID;
	private String existingStartDateTime;
	private String existingInventoryBefore;
	private String existingInventoryAfter;
	private String inventoryBefore;
	private String inventoryAfter;
	private String dateTime;
	private String startTime;
	private String startDate;
	private boolean invokeRimError;
	private boolean validateInventory;
	public Modify(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

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
			//setProductType(rsPricing.getValue("PROD_TYP_NM"));
			Recordset rsInventory = new Recordset(availDb.getResultSet(AvailSE.getReservableResourceByProductId(rsPricing.getValue("PROD_ID"))));
			if(rsInventory.getRowCount() == 0) throw new AutomationException("No external reference data was found for Product id ["+rsPricing.getValue("PROD_ID")+"]");
			setReservableResourceId(rsInventory.getValue("RSRVBL_RSRC_ID"));
			rrIdSetInAddDetails  = true;
			
			PriceComponents price = new PriceComponents(getEnvironment(), "Main");
			price.setComponentId(getRequestProductId());
			price.sendRequest();
			setRequestDocument(new ComponentPriceBuilder().buildComponentPrices(this, ComponentPriceBuilder.SHOW, "modify", price));
		}
	}
	

	public void setServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setComponentUnitPriceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/unitPrices/date", value);
	}
	
	public void setAddOnServiceStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/serviceStartDate", value);
	}	
	
	public void setTravelPlanId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/travelPlanId", value);
	}
	
	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/reservationNumber", value);
	}
	
	public void setFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/facilityId", value);
	}
	public void setFacilityName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/facilityName", value);
		}catch(XPathNotFoundException xpne){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage", "fx:AddNode;facilityName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/facilityName", value);			
		}
	}	
	public void setServicePeriodId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/servicePeriodId", value);
	}
	
	public void setSignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/signInLocation", value);
	}
	
	public void setAddOnComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentType", value);
	}
	
	public void setAddOnComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentId", value);
	}
	
	public void setAddOnRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/id", value);
	}
	
	public void setAddOnRevenueClassificaitonName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/name", value);
	}
	
	public void setAddOnProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/productId", value);
	}
	
	public void setAddOnProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/productType", value);
	}
	
	public void setAddOnFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/facilityId", value);
	}
	
	public void setAddOnFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/addOnComponents/facilityName", value);
	}
	
	public String getResponseStatus(){
		try{
			return getResponseNodeValueByXPath("/Envelope/Body/modifyShowDiningResponse/status");	
		}catch(XPathNotFoundException e) {return "fail";}
		
	}
	
	public void addInternalComments(String text, String type){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentText", text);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentType", type);
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments", "fx:AddNode;Node:commentType");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentText", text);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments/commentType", type);
		}
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/communicationChannel", value);
	}
	
	public void setProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/productId", value);
	}
	
	public void setProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/productType", value);
	}
	
	public void setServiceStartDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setFulfillmentDate(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/charges["+index+"]/fulfillmentDate", value);
	}
	public void setTaxExemptDetails(String certificateNumber, String taxExemptType){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/taxExemptDetail/taxExemptType", taxExemptType);
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest", "fx:AddNode;Node:taxExemptDetail");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptCertificateNumber");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptType");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/taxExemptDetail/taxExemptType", taxExemptType);
	}
	public void setProfileDetailIdAndType(String id, String type){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfProfileDetails= 1;
		try{
			numberOfProfileDetails= getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails");
			//getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+numberOfProfileDetails+"]/id");
			numberOfProfileDetails+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:profileDetails");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:id");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:type");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+numberOfProfileDetails+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/profileDetails["+numberOfProfileDetails+"]/type", type);
	}
	
	public void setComments(String text, String type){
		int numberOfInternalComments= 1;
		try{
			numberOfInternalComments= getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments");
		//	getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+numberOfInternalComments+"]/commentText");
			numberOfInternalComments+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest", "fx:AddNode;Node:internalComments");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentText");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentType");
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+numberOfInternalComments+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/internalComments["+numberOfInternalComments+"]/commentType", type);
	}
	public void setAllergies(String value){
		// Determine if the index exists. If not, create it and the necessary
				// child nodes. If so, then set the child node values
				int numberOfAllergies= 1;
				try{
					numberOfAllergies= getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/allergies");
				//	getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/allergies["+numberOfAllergies+"]");
					numberOfAllergies+=1;
				}catch(Exception e){}
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:allergies");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/allergies["+numberOfAllergies+"]", value);
	}
	
	public void setEnterpriseProductid(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/enterpriseProductId", value);}
	
	
	
	
	

	public int getNumberOfComponentIds(){
		int numNodes = 0;
		try{numNodes = getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/componentPrices");}
		catch(RuntimeException e){}
		return numNodes;
	}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
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
	
	
	/**
	 * Sets the reservable resource ID in the SOAP request
	 * @param value reservable resource ID
	 */
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/inventoryDetails/reservableResourceId", value);}
	public String getRequestReservableResourceId(){ return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/inventoryDetails/reservableResourceId");	}


//	@Override
//	public void sendRequest(){
//		if(notSetFreezeId) 	setFreezeId();
//		super.sendRequest();
//
//		if(getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY") ||	getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint")){
//			if(notSetFreezeId) 	setFreezeId();
//			super.sendRequest();	
//		}
//	}
	public void setFreezeId(){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rs = null;
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");
//<<<<<<< HEAD
		//rs = new Recordset(db.getResultSet(AvailSE.getFreezeId(getRequestReservableResourceId(), freeze.getRequestServiceStartDate() + " " + freeze.getRequestServiceStartTime())));
//		Recordset rsInventory;
//		if(reservableResourceId == null || newDateTime == true){
//			if(reservableResourceId == null)rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
//			else rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityDateAndRRID(getRequestFacilityId(), getRequestServiceStartDate(), reservableResourceId)));
//			rsInventory.print();
//			startDate = rsInventory.getValue("START_DATE").contains(" ") 
//							   ? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
//						       : rsInventory.getValue("START_DATE");
//							   
//			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
//			if(reservableResourceId == null) reservableResourceId = rsInventory.getValue("Resource_ID");
//		}
//		freeze.setReservableResourceId(reservableResourceId);	
//		freeze.setStartDate(startDate);	
//		freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
//		freeze.sendRequest();
//		int timesTried = 0;
//		while(freeze.getSuccess().equals("failure") && timesTried < 5){				
//			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
//			rsInventory.print();
//			startDate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
//=======
		Recordset rsInventory = null;
		String startdate = "";
		String startTime = "";
		startdate = getRequestServiceStartDate().contains("T") 
				   ? getRequestServiceStartDate().substring(0,getRequestServiceStartDate().indexOf("T"))
			       : getRequestServiceStartDate();
				   
		if(!rrIdSetInAddDetails){
//			rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(),startdate)));
//			setReservableResourceId(rsInventory.getValue("Resource_ID"));
			if(reservableResourceId == null || newDateTime == true){
				if(reservableResourceId == null)rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
				else rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityDateAndRRID(getRequestFacilityId(), getRequestServiceStartDate(), reservableResourceId)));
		//		rsInventory.print();
				startDate = rsInventory.getValue("START_DATE").contains(" ") 
								   ? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
							       : rsInventory.getValue("START_DATE");
								   
				startTime = rsInventory.getValue("START_DATE").replace(".0", "");
				this.startTime = startTime;
				if(reservableResourceId == null) reservableResourceId = rsInventory.getValue("Resource_ID");
			}
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
		freeze.setStartDate(startdate);	
		freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
		TestReporter.logStep("Generating Freeze ID for Reservable Resource ["+rsInventory.getValue("Resource_ID")+"]");
		freeze.sendRequest();
//			TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
		int timesTried = 0;
		while(freeze.getSuccess().equals("failure") && timesTried < 5){				
			if(!newDateTime)rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
			else rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityDateAndRRID(getRequestFacilityId(), getRequestServiceStartDate(), reservableResourceId)));
		//	rsInventory.print();
			startdate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
			startDate = startdate;
//>>>>>>> bcbd28f51f4f4d70956471a29f528c4e2d10d279
			startTime = rsInventory.getValue("START_DATE").replace(".0", "");
			this.startTime = startTime;
			if(!newDateTime)setReservableResourceId(rsInventory.getValue("Resource_ID"));
			freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
			freeze.setStartDate(startDate);	
			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
			freeze.sendRequest();	
			if(freeze.getSuccess().equals("failure")) timesTried++;
		}
//<<<<<<< HEAD
		TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
		freezeId = freeze.getFreezeID();
		setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
			
		
		if(!invokeRimError) setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/freezeId", freezeId);
//=======
//
//		TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
//
//		freezeId = freeze.getFreezeID();
//		setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
//		
//	
//		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/freezeId", freezeId);
//>>>>>>> bcbd28f51f4f4d70956471a29f528c4e2d10d279
		notSetFreezeId = false;
	}
	/**
	 * Sets the party for an instance of this class
	 * @param party - HouseHold instance containing all guests and related data
	 */
	public void setParty(HouseHold party){
		int currentGuest = 1;
		String partyRolePosition = "";
		addPartyRoleNodes(party.getAllGuests().size() - 1);
		for( Guest guest : party.getAllGuests()){
			if(guest.isPrimary()){
				try{
					if(guest.getTitle().isEmpty()) setPrimaryGuestTitle(BaseSoapCommands.REMOVE_NODE.toString());
					else setPrimaryGuestTitle(guest.getTitle());
				}
				catch(XPathNotFoundException e){}
				
				try{
					if(guest.getSuffix().isEmpty()) setPrimaryGuestSuffix(BaseSoapCommands.REMOVE_NODE.toString());
					else setPrimaryGuestSuffix(guest.getSuffix());
				}catch(XPathNotFoundException e){}
				
				try{setContactName(guest.getFullName());}catch(XPathNotFoundException e){}
				setPrimaryGuestFirstName(guest.getFirstName());
				setPrimaryGuestLastName(guest.getLastName());
				
				try{
					if(guest.getMiddleName().isEmpty()) setPrimaryGuestMiddleName(BaseSoapCommands.REMOVE_NODE.toString());
					else setPrimaryGuestMiddleName(guest.getMiddleName());
				}catch(XPathNotFoundException e){}

				setPrimaryGuestPartyId(guest.getPartyId());			
				
				addPrimaryGuestAddresses(guest);
				addPrimaryGuestEmails(guest);
			}
			
			if(currentGuest == 1)	partyRolePosition="";
			else partyRolePosition = "[" + currentGuest + "]";
			
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/correlationID", "0");

			currentGuest++;
		}
	}
	/**
	 * Sets primary guest address locator ID in the SOAP request
	 * @param value - guest address locator ID
	 */
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/locatorId", value);}
	/**
	 * Sets primary guest address guest  locator ID in the SOAP request
	 * @param value - primary guest address guest  locator ID
	 */
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/guestLocatorId", value);}
	/**
	 * Sets flag to determine a primary guest address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest address is the primary address
	 */
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/primary", value);}
	/**
	 * Sets primary guest address line 2 in the SOAP request
	 * @param value - primary guest address line 2
	 */
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/addressLine2", value);}
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
				try{
					if(address.getAddress2().isEmpty()) setPrimaryGuestAddress2(BaseSoapCommands.REMOVE_NODE.toString());
					else setPrimaryGuestAddress2(address.getAddress2());
				}catch(XPathNotFoundException e){}
				setPrimaryGuestCity(address.getCity());
				setPrimaryGuestCountry(address.getCountry());
				setPrimaryGuestPostalCode(address.getZipCode());
				setPrimaryGuestState(address.getStateAbbv());
			}else{
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
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
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
			position++;
		}
	}
	/**
	 * Sets flag to determine a primary guest email address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest email address is the primary address
	 */
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails/primary", value);}
	/**
	 * Sets primary guest email address locator ID in the SOAP request
	 * @param value - primary guest email address locator ID
	 */
	public void setPrimaryGuestEmailAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails/locatorId", value);}	
	/**
	 * Sets primary guest email address guest locator ID in the SOAP request
	 * @param value - primary guest email address guest locator ID
	 */
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails/guestLocatorId", value);}	
	/**
	 * Sets primary guest email address in the SOAP request
	 * @param value - primary guest email address
	 */
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails/address", value);}
	/**
	 * Adds the primary guest email address(es) to the SOAP request
	 * @param guest - Guest-class instance for the primary guest
	 */
	private void addPrimaryGuestEmails(Guest guest){
		
		addPrimaryGuestEmailDetailNodes(guest.getAllEmails().size() - 1);
		
		int position = 1;
		
		for(Email email : guest.getAllEmails()){
			if(position == 1){
				try{setPrimaryGuestEmailAddressLocatorId("0");}catch(XPathNotFoundException e){}
				try{setPrimaryGuestEmailAddressGuestLocatorId("0");}catch(XPathNotFoundException e){}
				try{setPrimaryGuestEmailAddressIsPrimary(email.isPrimary() ? "true":"false");}catch(XPathNotFoundException e){}
				try{setPrimaryGuestEmailAddress(email.getEmail());}catch(XPathNotFoundException e){}				
			}else{
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
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
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}	
	/**
	 * Adds party roles for all guests that are not the primary guest
	 * @param numberToAdd - number of non-primary guests to add
	 */
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
	/**
	 * Sets the primary guest title in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/title", value);}
	/**
	 * Sets the primary guest suffix in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestSuffix(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/suffix", value);}
	/**
	 * Sets the contact name in the SOAP request
	 * @param value - contact name
	 */
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/contactName", value);}
	/**
	 * Sets primary guest first name in the SOAP request
	 * @param value - primary guest first name
	 */
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/firstName", value);}
	/**
	 * Sets primary guest last name in the SOAP request
	 * @param value - primary guest last name
	 */
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/lastName", value);}
	/**
	 * Sets primary guest middle name in the SOAP request
	 * @param value - primary guest middle name
	 */
	public void setPrimaryGuestMiddleName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/middleName", value);}
	/**
	 * Sets the primary guest postal code in the SOAP Request
	 * @param value - primary guest postal code
	 */
	public void setPrimaryGuestPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/postalCode", value);}	
	/**
	 * Sets the primary guest address line 1 in the SOAP Request
	 * @param value - primary guest address line 1
	 */
	public void setPrimaryGuestAddress1(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/addressLine1", value);}	
	/**
	 * Sets the primary guest country in the SOAP Request
	 * @param value - primary guest country
	 */
	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/country", value);}	
	/**
	 * Sets the primary guest state in the SOAP Request
	 * @param value - primary guest state
	 */
	public void setPrimaryGuestState(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/state", value);}
	/**
	 * Sets the primary guest city in the SOAP Request
	 * @param value - primary guest city
	 */
	public void setPrimaryGuestCity(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/addressDetails/city", value);}	
	/**
	 * Gets the primary guest party ID in the SOAP Response
	 * @return - primary guest party ID
	 */
	public void setPrimaryGuestPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/primaryGuest/partyId", value);}
	/**
	 * Gets the service start dateTime in the SOAP request
	 * @param value - service start dateTime
	 */
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/serviceStartDate");}
	/**
	 * Gets the facility ID in the SOAP request
	 * @return facility ID
	 */
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/facilityId");}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/servicePeriodId");}
	/**
	 * Gets the service period ID in the SOAP request
	 * @return service period ID
	 */
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/productId");}
	/**
	 * Sets the authorization number in the SOAP request
	 * @param value - authorization number 
	 */
	public void setAuthorizationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/authorizationNumber", value);}
	/**
	 * Sets the freeze ID in the SOAP request
	 * @param value - freeze ID
	 */
	public void setFreezeId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/freezeId", value);
		notSetFreezeId = false;
	}
	public void setFreezeIdForError(String freezeId){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/freezeId", freezeId);
		invokeRimError = true;
	}
	/**
	 * Sets the party role age type in the SOAP request
	 * @param value - party role age type
	 */
	public void setPartyRoleAgeType(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/dinnerShowPackage/partyRoles/ageType", value);}
	/**
	 * Sets the source accounting center in the SOAP request
	 * @param value - source accounting center 
	 */
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyShowDiningRequest/sourceAccountingCenter", value);}
	
	
	
	
	
	
	

	
	public void setReservableResourceId(String value, boolean newDateTime){
		setReservableResourceId(value);
		reservableResourceId = value;
		this.newDateTime = newDateTime;
	}	
	
	/**
	 * If it is desired to validate inventory before and after a booking, set this to true
	 */
	public void setValidateInventory(boolean validate){ this.validateInventory = validate;}
	@Override
	public void sendRequest(){
		if(validateInventory)setExistingInventoryCountBefore(getInventory(existingRRID, existingStartDateTime));
		if(notSetFreezeId) 	setFreezeId();
		if(validateInventory)setInventoryCountBefore(getInventory());
		super.sendRequest();
		if(validateInventory)setInventoryCountAfter(getInventory());
		
		if(getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY") ||	
				getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
				getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
			if(notSetFreezeId) 	setFreezeId();
			if(validateInventory)setInventoryCountBefore(getInventory());
			super.sendRequest();	
			if(validateInventory)setInventoryCountAfter(getInventory());
		}
		if(validateInventory)setExistingInventoryCountAfter(getInventory(existingRRID, existingStartDateTime));
	}
	public void setExistingRRID(String rrid){existingRRID = rrid;}
	public void setExistingStartDateTime(String dateTime){existingStartDateTime = dateTime;}
	private void setExistingInventoryCountBefore(String before){existingInventoryBefore = before;}
	public String getExistingInventoryCountBefore(){return existingInventoryBefore;}
	private void setExistingInventoryCountAfter(String after){existingInventoryAfter = after;}
	public String getExistingInventoryCountAfter(){return existingInventoryAfter;}
	
	
	private void setInventoryCountBefore(String before){inventoryBefore = before;}
	public String getInventoryCountBefore(){return inventoryBefore;}
	private void setInventoryCountAfter(String after){inventoryAfter = after;}
	public String getInventoryCountAfter(){return inventoryAfter;}
	public String getReservableResourceId(){return reservableResourceId;}
	public String getDateTime(){return dateTime;}
	public void setStartTime(String time){startTime = time;}
	public void setStartDate(String date){startDate = date;}
	private String getInventory(){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getAvailableResourceCount(reservableResourceId, startTime.replace("T", " "))));
	//	rsInventory.print();
		return rsInventory.getValue("BK_CN");
	}
	private String getInventory(String existingRRID, String existingStartDateTime){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getAvailableResourceCount(existingRRID, existingStartDateTime.replace("T", " "))));
	//	rsInventory.print();
		return rsInventory.getValue("BK_CN");
	}
}