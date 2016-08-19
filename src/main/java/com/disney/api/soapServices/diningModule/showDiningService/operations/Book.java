package com.disney.api.soapServices.diningModule.showDiningService.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.showDiningService.ShowDiningService;
import com.disney.api.soapServices.seWebServices.SEOfferService.operations.Freeze;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.database.Database;
import com.disney.utils.dataFactory.database.Recordset;
import com.disney.utils.dataFactory.database.databaseImpl.OracleDatabase;
import com.disney.utils.dataFactory.database.sqlStorage.AvailSE;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Book extends ShowDiningService {
	private boolean notSetFreezeId = true;
	public Book(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelPlanId", value);
	}

	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookShowDiningResponse/bookResponse/travelPlanId");
	}
	
	public String getSalesChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/salesChannel");
	}
	
	public String getCommunicationsChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/communicationChannel");
	}
	
	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookShowDiningResponse/bookResponse/confirmationNumber");
	}
	
	public void setComponentUnitPriceDepositDueDateTime(String value, String index){
		System.out.println(getRequest());
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/unitPrices/deposit/dueDate", value);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/unitPrices/date", value);
	}
	
	public void setServiceDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setAddOnComponentUnitPriceDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/unitPrices/date", value);
	}
	
	public void setAddOnServiceDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/serviceStartDate", value);
	}
	
	public void setComponentUnitPriceDateTime(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/unitPrices/date", value);
	}
	
	public void setComponentPriceComponentType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentType", value);
	}
	
	public void setComponentPriceComponentId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentId", value);
	}
	
	public void setComponentPriceRevenueClassificationId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/revenueClassification/id", value);
	}
	
	public void setComponentPriceRevenueClassificationName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/revenueClassification/name", value);
	}
	
	public void setFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityId", value);
	}
	
	public String getFacilityId(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityId");
	}
	
	public void setFacilityName(String value){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityName", value);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:addnode;node:facilityName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityName", value);
		}
	}
	
	public String getFacilityName(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityName");
	}
	
	public void setProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/productId", value);
	}
	
	public void setProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/productType", value);
	}
	
	public void setServicePeriodId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/servicePeriodId", value);
	}
	
	public void setSignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/signInLocation", value);
	}
	
	public void setAuthorizationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/authorizationNumber", value);
	}
	
	public void setAddOnComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentType", value);
	}
	
	public void setAddOnComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/componentId", value);
	}
	
	public void setAddOnRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/id", value);
	}
	
	public void setAddOnRevenueClassificaitonName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/componentPrices/revenueClassification/name", value);
	}
	
	public void setAddOnProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/productId", value);
	}
	
	public void setAddOnProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/productType", value);
	}
	
	public void setAddOnFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/facilityId", value);
	}
	
	public void setAddOnFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/addOnComponents/facilityName", value);
	}
	
	public void setPrimaryGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/addressLine1", value);
	}
	
	public void setPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/city", value);
	}
	
	public void setPrimaryGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/country", value);
	}
	
	public void setPrimaryGuestPostalCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/postalCode", value);
	}
	
	public void setPrimaryGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/state", value);
	}
	
	public void setSourceAccountingCenter(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/sourceAccountingCenter", value);
	}
	
	public void setServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/serviceStartDate", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/communicationChannel", value);
	}
	
	public void setProfileDetailIdAndType(String id, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/id");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:profileDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]", "fx:AddNode;Node:id");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]", "fx:AddNode;Node:type");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+index+"]/type", type);
	}
	
	public void setComments(String text, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]/commentText");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentType");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/internalComments["+index+"]/commentType", type);
	}
	
	public void setAllergies(String value, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/allergies["+index+"]");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:allergies");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/allergies["+index+"]", value);
	}
	
	
	

	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices");}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/componentPrices" + index, BaseSoapCommands.REMOVE_NODE.toString());
		}
	}

	/**
	 * Gets the facility ID from the SOAP request
	 * @return facility ID 
	 */
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityId");}
	/**
	 * Gets the service start date from the SOAP request
	 * @return service start date 
	 */
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/serviceStartDate");}
	/**
	 * Gets the service period ID from the SOAP request
	 * @return service period ID 
	 */
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/servicePeriodId");}
	/**
	 * Gets the product ID from the SOAP request
	 * @return product ID
	 */
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/productId");}
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
			
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/guestId", "0");}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/active", "true");}catch(XPathNotFoundException e){}
			try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/correlationID", "0");}catch(XPathNotFoundException e){}

			currentGuest++;
		}
	}	
	/**
	 * Adds travel agency in the SOAP request
	 * @param agencyId - travel agency
	 */
	public void addTravelAgency(String agencyId){addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");}
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
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest", "fx:AddNode;Node:travelAgency");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:agencyIataNumber");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:agencyOdsId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:guestTravelAgencyId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:agentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:guestAgentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:confirmationLocatorValue");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency", "fx:AddNode;Node:guestConfirmationLocationId");

		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/agencyIataNumber", agencyIataNumber);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/agencyOdsId", agencyOdsId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/guestTravelAgencyId", guestAgencyId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/agentId", agentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/guestAgentId", guestAgentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/confirmationLocatorValue", confirmationLocatorValue);		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/travelAgency/guestConfirmationLocationId", guestConfirmationLocationId);
	}
	/**
	 * Sets the reservable resource ID in the SOAP request
	 * @param value reservable resource ID
	 */
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/inventoryDetails/reservableResourceId", value);}

	@Override
	public void sendRequest(){
		if(notSetFreezeId) 	setFreezeId();
		super.sendRequest();

		if(getResponse().toUpperCase().contains("FACILITY SERVICE UNAVAILABLE OR RETURED INVALID FACILITY") ||	getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint")){
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
			TestReporter.logAPI(!freeze.getResponseStatusCode().equals("200"), "Failed to get Freeze ID", freeze);
			if(freeze.getSuccess().equals("failure")){				
				rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
				rsInventory.print();
				startdate = rsInventory.getValue("START_DATE").substring(0,rsInventory.getValue("START_DATE").indexOf(" "));
				startTime = rsInventory.getValue("START_DATE").replace(".0", "");
				setReservableResourceId(rsInventory.getValue("Resource_ID"));
				freeze.setReservableResourceId(rsInventory.getValue("Resource_ID"));	
				freeze.setStartDate(startdate);	
				freeze.setStartTime(startTime.substring(startTime.indexOf(" ") + 1,startTime.length()));
				freeze.sendRequest();	
				freezeId = freeze.getFreezeID();
				//setServiceStartDateTime(rs.getValue("FSELL_INVTRY_SRVC_DTS").replace(".0", "").replace(" ", "T"));
			}else {
				freezeId = freeze.getFreezeID();
				setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
			}
	/*	}else{
			freezeId = rs.getValue("FREEZE_ID");
			setServiceStartDateTime(rs.getValue("FSELL_INVTRY_SRVC_DTS").replace(".0", "").replace(" ", "T"));
		}*/
		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/freezeId", freezeId);
		notSetFreezeId = false;
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
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
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
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
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
				try{setPrimaryGuestEmailAddressLocatorId("0");}catch(XPathNotFoundException e){}
				try{setPrimaryGuestEmailAddressGuestLocatorId("0");}catch(XPathNotFoundException e){}
				try{setPrimaryGuestEmailAddressIsPrimary(email.isPrimary() ? "true":"false");}catch(XPathNotFoundException e){}
				try{setPrimaryGuestEmailAddress(email.getEmail());}catch(XPathNotFoundException e){}
				
			}else{
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
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
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}		
	/**
	 * Adds party roles for all guests that are not the primary guest
	 * @param numberToAdd - number of non-primary guests to add
	 */
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
	/**
	 * Sets primary guest first name in the SOAP request
	 * @param value - primary guest first name
	 */
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/firstName", value);}
	/**
	 * Sets primary guest last name in the SOAP request
	 * @param value - primary guest last name
	 */
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/lastName", value);}
	/**
	 * Sets primary guest middle name in the SOAP request
	 * @param value - primary guest middle name
	 */
	public void setPrimaryGuestMiddleName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/middleName", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest", "fx:addNode;node:middleName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/middleName", value);
		}
	}
	/**
	 * Sets flag to determine a primary guest address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest address is the primary address
	 */
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/primary", value);}
	/**
	 * Sets primary guest address locator ID in the SOAP request
	 * @param value - guest address locator ID
	 */
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/locatorId", value);}	
	/**
	 * Sets primary guest address guest  locator ID in the SOAP request
	 * @param value - primary guest address guest  locator ID
	 */	
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/guestLocatorId", value);}	
	/**
	 * Sets primary guest address line 2 in the SOAP request
	 * @param value - primary guest address line 2
	 */
	public void setPrimaryGuestAddress2(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/addressLine2", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails", "fx:addNode;node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/addressLine2", value);
		}
	}		
	/**
	 * Sets primary guest party ID in the SOAP request
	 * @param value - primary guest party ID 
	 */	
	public void setPrimaryGuestPartyid(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/partyId", value);}		
	/**
	 * Sets flag to determine a primary guest email address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest email address is the primary address
	 */
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/primary", value);}
	/**
	 * Sets primary guest email address locator ID in the SOAP request
	 * @param value - primary guest email address locator ID
	 */
	public void setPrimaryGuestEmailAddressLocatorId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/locatorId", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails", "fx:addNode;node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/locatorId", value);
		}
	}		
	/**
	 * Sets primary guest email address guest locator ID in the SOAP request
	 * @param value - primary guest email address guest locator ID
	 */
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/guestLocatorId", value);}	
	/**
	 * Sets primary guest email address in the SOAP request
	 * @param value - primary guest email address
	 */	
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/address", value);}
	/**
	 * Sets the primary guest suffix in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestSuffix(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/suffix", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest", "fx:addNode;node:suffix");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/suffix", value);
		}
	}
	/**
	 * Sets the primary guest title in the SOAP request
	 * @param value - primary guest title
	 */	
	public void setPrimaryGuestTitle(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/title", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest", "fx:addNode;node:title");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/title", value);
		}
	}
	/**
	 * Sets the contact name in the SOAP request
	 * @param value - contact name
	 */
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/contactName", value);}
	/**
	 * Sets the freeze ID in the SOAP request
	 * @param value - freexe ID
	 */
	public void setFreezeId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/freezeId", value);}
	/**
	 * Sets the party role age type
	 * @param value - party role age type
	 */
	public void setPartyRoleAgeType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles/ageType", value);}
	
	
	public void addSpecialRequest(String id, String type){
		int existingSpecialRequest = XMLTools.getNodeList(getRequestDocument(), "/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails").getLength();
		int nextNodeindex = existingSpecialRequest + 1;
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage", "fx:addNode;node:profileDetails");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+nextNodeindex+"]", "fx:addNode;node:id");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+nextNodeindex+"]", "fx:addNode;node:type");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+nextNodeindex+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/profileDetails["+nextNodeindex+"]/type", type);
	}
}
