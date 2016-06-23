package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Book extends ShowDiningService {
	public Book(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("book")));
//		System.out.println(getRequest());
	
		
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
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityName", value);
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
	
	public void setServicePeriosId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/servicePeriodId", value);
	}
	
	public void setSignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/signInLocation", value);
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
	
	
	
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/facilityId");}
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/serviceStartDate");}
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/servicePeriodId");}
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/productId");}
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/inventoryDetails/reservableResourceId", value);}	
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/firstName", value);}
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/lastName", value);}
	public void setPrimaryGuestMiddleName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/middleName", value);}	
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/primary", value);}
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/locatorId", value);}	
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/guestLocatorId", value);}	
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/addressDetails/addressLine2", value);}		
	public void setPrimaryGuestPartyid(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/partyId", value);}	
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/primary", value);}
	public void setPrimaryGuestEmailAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/locatorId", value);}	
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/guestLocatorId", value);}	
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails/address", value);}
	public void setPrimaryGuestSuffix(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/suffix", value);}	
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/title", value);}
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/contactName", value);}	
	public void addTravelAgency(String agencyId){addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");}
	
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
			
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/dinnerShowPackage/partyRoles"+partyRolePosition + "/correlationID", "0");

			currentGuest++;
		}
	}
	
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
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookShowDiningRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
			}
			position++;
		}
	}
	
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
}
