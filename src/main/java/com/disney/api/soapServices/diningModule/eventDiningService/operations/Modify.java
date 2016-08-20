package com.disney.api.soapServices.diningModule.eventDiningService.operations;

import com.disney.api.soapServices.availSEModule.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.eventDiningService.EventDiningService;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.FacilityInfo;
import com.disney.utils.dataFactory.ProductInfo;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Modify extends EventDiningService {
	private boolean notSetFreezeId = true;
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
	public void setFacilityName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/facilityName", value);}	
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
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments/commentType", type);
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
	public void setProfileDetailIdAndType(String id, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+index+"]/id");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:profileDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+index+"]", "fx:AddNode;Node:id");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+index+"]", "fx:AddNode;Node:type");
		}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+index+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/profileDetails["+index+"]/type", type);
	}
	
	public void setComments(String text, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+index+"]/commentText");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentType");
		}
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+index+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/internalComments["+index+"]/commentType", type);
	}
	
	public void setAllergies(String value, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/allergies["+index+"]");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:allergies");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/allergies["+index+"]", value);
		}
	}	

	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/componentPrices");}
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
	
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/inventoryDetails/reservableResourceId", value);}
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
	

	@Override
	public void sendRequest(){
		if(notSetFreezeId) 	setFreezeId();
		super.sendRequest();
		if(getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY") ||	
				getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
				getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
			if(notSetFreezeId) 	setFreezeId();
			super.sendRequest();	
		}
	}
	public void setFreezeId(){
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rs = null;
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");
		//rs = new Recordset(db.getResultSet(AvailSE.getFreezeId(getRequestReservableResourceId(), freeze.getRequestServiceStartDate() + " " + freeze.getRequestServiceStartTime())));

	//	if(rs.getRowCount() == 0){
			Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
			rsInventory.print();
			String startdate = rsInventory.getValue("START_DATE").contains(" ") 
							   ? rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "))
						       : rsInventory.getValue("START_DATE");
							   
			String startTime = rsInventory.getValue("START_DATE").replace(".0", "");
			setReservableResourceId(rsInventory.getValue("Resource_ID"));
			freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
			freeze.setStartDate(startdate);	
			freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
			freeze.sendRequest();
//			TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
			int timesTried = 0;
			while(freeze.getSuccess().equals("failure") && timesTried < 5){				
				rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
				rsInventory.print();
				startdate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
				startTime = rsInventory.getValue("START_DATE").replace(".0", "");
				setReservableResourceId(rsInventory.getValue("Resource_ID"));
				freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
				freeze.setStartDate(startdate);	
				freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
				freeze.sendRequest();	
				if(freeze.getSuccess().equals("failure")) timesTried++;
			}

//			if(freeze.getSuccess().equals("failure")){
				TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
//			}
			freezeId = freeze.getFreezeID();
			setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
			
		
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/freezeId", freezeId);
		notSetFreezeId = false;
	}

	public void setFreezeId(String freezeId){
		setRequestNodeValueByXPath("/Envelope/Body/modify/modifyEventDiningRequest/eventDiningPackage/freezeId", freezeId);
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