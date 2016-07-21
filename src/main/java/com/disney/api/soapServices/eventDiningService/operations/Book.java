package com.disney.api.soapServices.eventDiningService.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.eventDiningService.EventDiningService;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Book extends EventDiningService {
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
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelPlanId", value);
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest", "fx:AddNode;Node:travelPlanId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelPlanId", value);
		}
	}
	
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/salesChannel", value);}	
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/communicationChannel", value);}	
	public void setUnitPriceDateTime(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/unitPrices/date", value);}	
	public void setComponentUnitPriceDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices/unitPrices/date", value);}	
	public void setServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/serviceStartDate", value);}	
	public void setAddOnComponentUnitPriceDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/unitPrices/date", value);}	
	public void setAddOnServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/serviceStartDate", value);}	
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/sourceAccountingCenter", value);}
	public String getSourceAccountingCenter(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/sourceAccountingCenter");}	
	public String getSalesChannel(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/salesChannel");}	
	public String getCommunicationsChannel(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/communicationChannel");}	
	public String getPrimaryGuestFirstName(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/firstName");}	
	public String getPrimaryGuestLastName(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/lastName");}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/bookEventDiningResponse/bookResponse/travelPlanId");}	
	public String getTravelPlanSegmentId(){return getResponseNodeValueByXPath("/Envelope/Body/bookEventDiningResponse/bookResponse/confirmationNumber");}
	public void setComponentPriceComponentType(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentType", value);}	
	public void setComponentPriceComponentType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices/componentType", value);}
	public void setComponentPriceComponentId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentId", value);}
	public void setComponentPriceComponentId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices/componentId", value);}	
	public void setComponentPriceRevenueClassificationId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/revenueClassification/id", value);}	
	public void setComponentPriceRevenueClassificationId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices/revenueClassification/id", value);}	
	public void setComponentPriceRevenueClassificationName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/revenueClassification/name", value);}
	public void setComponentPriceRevenueClassificationName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices/revenueClassification/name", value);}	
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/facilityId", value);}
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/facilityId");}
	public void setFacilityName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/facilityName", value);}	
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/productId", value);}	
	public void setProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/productType", value);}	
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/servicePeriodId", value);}	
	public void setSignInLocation(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/signInLocation", value);}	
	public void setAddOnComponentType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/componentType", value);}	
	public void setAddOnComponentId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/componentId", value);}	
	public void setAddOnRevenueClassificationId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/revenueClassification/id", value);}	
	public void setAddOnRevenueClassificaitonName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/componentPrices/revenueClassification/name", value);}	
	public void setAddOnProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/productId", value);}	
	public void setAddOnProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/addOnComponents/productType", value);}
	public void setPrimaryGuestSuffix(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/suffix", value);}	
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/title", value);}
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/contactName", value);}	
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/firstName", value);}
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/lastName", value);}
	public void setPrimaryGuestMiddleName(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/middleName", value);}	
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/primary", value);}
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/locatorId", value);}	
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/guestLocatorId", value);}	
	public void setPrimaryGuestAddress1(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/addressLine1", value);}
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/addressLine2", value);}	
	public void setPrimaryGuestCity(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/city", value);}	
	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/country", value);}	
	public void setPrimaryGuestPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/postalCode", value);}	
	public void setPrimaryGuestState(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails/state", value);}	
	public void setPrimaryGuestPartyid(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/partyId", value);}	
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails/primary", value);}
	public void setPrimaryGuestEmailAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails/locatorId", value);}	
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails/guestLocatorId", value);}	
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails/address", value);}
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/inventoryDetails/reservableResourceId", value);}
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/serviceStartDate");}
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/servicePeriodId");}
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/productId");}
	
	public void setProfileDetailIdAndType(String id, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/profileDetails["+index+"]/id");
		}catch(Exception e){
			e.printStackTrace();
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:profileDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/profileDetails["+index+"]", "fx:AddNode;Node:id");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/profileDetails["+index+"]", "fx:AddNode;Node:type");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/profileDetails["+index+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/profileDetails["+index+"]/type", type);
	}
	
	public void setComments(String text, String type, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/internalComments["+index+"]/commentText");
		}catch(Exception e){
			e.printStackTrace();
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest", "fx:AddNode;Node:internalComments");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentText");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/internalComments["+index+"]", "fx:AddNode;Node:commentType");
		}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/internalComments["+index+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/internalComments["+index+"]/commentType", type);
	}
	
	public void setAllergies(String value, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/allergies["+index+"]", value);
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:allergies");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/allergies["+index+"]", value);
		}
	}
	
	public void addTravelAgency(String agencyId){
		addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");
	}
	
	public void addTravelAgency(String agencyIataNumber, String agencyOdsId, String guestAgencyId, String agentId, String guestAgentId, String confirmationLocatorValue, String guestConfirmationLocationId){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest", "fx:AddNode;Node:travelAgency");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:agencyIataNumber");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:agencyOdsId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:guestTravelAgencyId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:agentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:guestAgentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:confirmationLocatorValue");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency", "fx:AddNode;Node:guestConfirmationLocationId");

		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/agencyIataNumber", agencyIataNumber);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/agencyOdsId", agencyOdsId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/guestTravelAgencyId", guestAgencyId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/agentId", agentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/guestAgentId", guestAgentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/confirmationLocatorValue", confirmationLocatorValue);		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/travelAgency/guestConfirmationLocationId", guestConfirmationLocationId);
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
			
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles"+partyRolePosition + "/correlationID", "0");

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
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
			}
			position++;
		}
	}
	
	private void addPrimaryGuestAddressDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
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
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
			}
			position++;
		}
	}
	
	private void addPrimaryGuestEmailDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}
	
	
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices/componentId");}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/book/bookEventDiningRequest/eventDiningPackage/componentPrices" + index, "fx:removenode");
			index = "";
		}
	}
}