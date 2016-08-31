package com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.availSEModule.builtInventoryService.operations.ReservableResourceByFacilityID;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.diningModule.tableServiceDiningServicePort.TableServiceDiningServicePort;
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

public class Book extends TableServiceDiningServicePort {
	private boolean notSetFreezeId = true;
	private String numberResources = "1";
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
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelPlanId", value);
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest", "fx:AddNode;Node:travelPlanId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelPlanId", value);
		}
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/salesChannel", value);
	}

	public void setVipLevel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/vipLevel", value);
	}
	
	public void setCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/communicationChannel", value);
	}
	
	public void setUnitPriceDateTime(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/unitPrices/date", value);
	}

	public void setInventoryOverrideReasonId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryOverideReasonId", value);
	}
	
	public void setComponentUnitPriceDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices/unitPrices/date", value);
	}
	
	public void setServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/serviceStartDate", value);
	}
	
	public void setAddOnComponentUnitPriceDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/componentPrices/unitPrices/date", value);
	}
	
	public void setAddOnServiceStartDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/serviceStartDate", value);
	}
	
	public void setSourceAccountingCenter(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/sourceAccountingCenter", value);
	}
	
	public String getSalesChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/salesChannel");
	}
	
	public String getCommunicationsChannel(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/communicationChannel");
	}
	
	public String getPrimaryGuestFirstName(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/firstName");
	}
	
	public String getPrimaryGuestLastName(){
		return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/lastName");
	}

	public String getTravelPlanId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookTableServiceResponse/bookResponse/travelPlanId");
	}
	
	public String getTravelPlanSegmentId(){
		return getResponseNodeValueByXPath("/Envelope/Body/bookTableServiceResponse/bookResponse/confirmationNumber");
	}		

	public void setComponentPriceComponentType(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/componentType", value);
	}
	
	public void setComponentPriceComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices/componentType", value);
	}

	public void setComponentPriceComponentId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/componentId", value);
	}
	public void setComponentPriceComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices/componentId", value);
	}
	
	public void setComponentPriceRevenueClassificationId(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/revenueClassification/id", value);
	}
	
	public void setComponentPriceRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices/revenueClassification/id", value);
	}
	
	public void setComponentPriceRevenueClassificationName(String value, String index){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/revenueClassification/name", value);
	}
	public void setComponentPriceRevenueClassificationName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices/revenueClassification/name", value);
	}
	
	public void setFacilityId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/facilityId", value);
	}
	
	public void setFacilityName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/facilityName", value);
	}
	
	public void setProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/productId", value);
	}
	
	public void setProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/productType", value);
	}
	
	public void setServicePeriosId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/servicePeriodId", value);
	}
	
	public void setSignInLocation(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/signInLocation", value);
	}
	
	public void setAddOnComponentType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/componentPrices/componentType", value);
	}
	
	public void setAddOnComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/componentPrices/componentId", value);
	}
	
	public void setAddOnRevenueClassificationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/componentPrices/revenueClassification/id", value);
	}
	
	public void setAddOnRevenueClassificaitonName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/componentPrices/revenueClassification/name", value);
	}
	
	public void setAddOnProductId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/productId", value);
	}
	
	public void setAddOnProductType(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/addOnComponents/productType", value);
	}

	public void setPrimaryGuestSuffix(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/suffix", value);
	}
	
	public void setPrimaryGuestTitle(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/title", value);
	}

	public void setContactName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/contactName", value);
	}
	
	public void setPrimaryGuestFirstName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/firstName", value);
	}

	public void setPrimaryGuestLastName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/lastName", value);
	}

	public void setPrimaryGuestMiddleName(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/middleName", value);
	}
	
	public void setPrimaryGuestAddressIsPrimary(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/primary", value);
	}

	public void setPrimaryGuestAddressLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/locatorId", value);
	}
	
	public void setPrimaryGuestAddressGuestLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/guestLocatorId", value);
	}
	
	public void setPrimaryGuestAddress1(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/addressLine1", value);
	}

	public void setPrimaryGuestAddress2(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/addressLine2", value);
	}
	
	public void setPrimaryGuestCity(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/city", value);
	}
	
	public void setPrimaryGuestCountry(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/country", value);
	}
	
	public void setPrimaryGuestPostalCode(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/postalCode", value);
	}
	
	public void setPrimaryGuestState(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails/state", value);
	}
	
	public void setPrimaryGuestPartyid(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/partyId", value);
	}
	
	public void setPrimaryGuestEmailAddressIsPrimary(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails/primary", value);
	}

	public void setPrimaryGuestEmailAddressLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails/locatorId", value);
	}
	
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails/guestLocatorId", value);
	}
	
	public void setPrimaryGuestEmailAddress(String value){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails/address", value);
	}

	public void setTaxExemptDetails(String certificateNumber, String taxExemptType){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/taxExemptDetail/taxExemptType", taxExemptType);
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest", "fx:AddNode;Node:taxExemptDetail");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptCertificateNumber");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/taxExemptDetail", "fx:AddNode;Node:taxExemptType");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/taxExemptDetail/taxExemptCertificateNumber", certificateNumber);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/taxExemptDetail/taxExemptType", taxExemptType);
	}
	
	public void setProfileDetailIdAndType(String id, String type){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfProfileDetails= 1;
		try{
			numberOfProfileDetails= getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/profileDetails");
			getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/profileDetails["+numberOfProfileDetails+"]/id");
			numberOfProfileDetails+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService", "fx:AddNode;Node:profileDetails");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:id");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/profileDetails["+numberOfProfileDetails+"]", "fx:AddNode;Node:type");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/profileDetails["+numberOfProfileDetails+"]/id", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/profileDetails["+numberOfProfileDetails+"]/type", type);
	}
	
	public void setComments(String text, String type){
		int numberOfInternalComments= 1;
		try{
			numberOfInternalComments= getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookTableServiceRequest/internalComments");
			getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/internalComments["+numberOfInternalComments+"]/commentText");
			numberOfInternalComments+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest", "fx:AddNode;Node:internalComments");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentText");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/internalComments["+numberOfInternalComments+"]", "fx:AddNode;Node:commentType");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/internalComments["+numberOfInternalComments+"]/commentText", text);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/internalComments["+numberOfInternalComments+"]/commentType", type);
	}
	
	public void setAllergies(String value){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfAllergies= 1;
		try{
			numberOfAllergies= getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/allergies");
			getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/allergies["+numberOfAllergies+"]");
			numberOfAllergies+=1;;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService", "fx:AddNode;Node:allergies");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/allergies["+numberOfAllergies+"]", value);

	}
	
	public void setNumberOfResource(String number){
		this.numberResources = number;
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

				setPrimaryGuestPartyid("0");
				
				
				addPrimaryGuestAddresses(guest);
				addPrimaryGuestEmails(guest);
			}
			
			if(currentGuest == 1)	partyRolePosition="";
			else partyRolePosition = "[" + currentGuest + "]";
			
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/partyId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/correlationID", "0");

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
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
			}
			position++;
		}
	}
	
	private void addPrimaryGuestAddressDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
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
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
			}
			position++;
		}
	}
	
	private void addPrimaryGuestEmailDetailNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}
	
	
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
	public int getNumberOfComponentIds(){return getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices/componentId");}
	public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	public void setComponentIdAndType(int index, String id, String type){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/componentId", id);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices["+index+"]/componentType", type);
	}
	public void setComponentIdAndType(String index, String id, String type){setComponentIdAndType(Integer.parseInt(index), id, type);}
	public void removeComponentPrices(){
		int numberOfComponentPrices = getNumberOfRequestNodesByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices");
		String index = "";
		for(int i = numberOfComponentPrices; i >= 1; i--){
			if(i != 1) index = "["+i+"]";
			setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/componentPrices" + index, "fx:removenode");
			index = "";
		}
	}
	/**
	 * Gets the facility ID from the SOAP response
	 * @return facility ID from the SOAP response
	 */
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/facilityId");}
	/**
	 * Gets the service start dateTime from the SOAP response
	 * @return service start dateTime
	 */
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/serviceStartDate");}
	/**
	 * Gets the service period ID from the SOAP response
	 * @return service period ID
	 */
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/servicePeriodId");}
	/**
	 * Gets the product ID from the SOAP response
	 * @return product ID
	 */
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/productId");}
	/**
	 * Adds travel agency in the SOAP request
	 * @param agencyId - travel agency
	 */
	public void addTravelAgency(String agencyId){addTravelAgency(agencyId, "0", "0", "0", "0", "0", "0");}
	/**
	 * Sets the reservable resource ID in the SOAP request
	 * @param value reservable resource ID
	 */
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryDetails/reservableResourceId", value);}
	/**
	 * Retrieves and set a random reservable resource ID in the SOAP request based on Facility ID
	 */
	public void setReservableResourceId(){
		/*ReservableResourceByFacilityID resource = new ReservableResourceByFacilityID(getEnvironment(), "Main");
		resource.setFacilityId(getRequestFacilityId());
		resource.sendRequest();
		TestReporter.logAPI(!resource.getResponseStatusCode().equals("200"), "Failed to get Reservable Resource ID", resource);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryDetails/reservableResourceId", resource.getRandomReservableResourceId());*/		
	}
	public String getRequestReservableResourceId(){ return getRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryDetails/reservableResourceId");	}
	
	@Override
	public void sendRequest(){
		if(notSetFreezeId) 	setFreezeId();
		super.sendRequest();
		if(		getResponse().toLowerCase().contains("could not execute statement; sql [n/a]; constraint") ||
				getResponse().contains("RELEASE INVENTORY REQUEST IS INVALID")){
			setFreezeId();
			super.sendRequest();	
		}
	}
	public void setFreezeId(){
		TestReporter.logStep("Freezing inventory");
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rs = null;
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");

			Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), getRequestServiceStartDate())));
			
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

//			if(freeze.getSuccess().equals("failure")){
				TestReporter.logAPI(freeze.getSuccess().equals("failure"), "Could not Freeze Inventory", freeze);
//			}
			freezeId = freeze.getFreezeID();
			setServiceStartDateTime(freeze.getRequestServiceStartDate() + "T" + freeze.getRequestServiceStartTime());
		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/freezeId", freezeId);
		

		for(int x = 1 ; x <= Integer.valueOf(numberResources) ; x++){
			try{
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryDetails["+x+"]/reservableResourceId", freeze.getReservableResourceID());
			}catch(XPathNotFoundException e){
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService",BaseSoapCommands.ADD_NODE.commandAppend("inventoryDetails"));
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryDetails["+x+"]",BaseSoapCommands.ADD_NODE.commandAppend("reservableResourceId"));
				setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/inventoryDetails["+x+"]/reservableResourceId",freeze.getReservableResourceID());
			}
		}

		notSetFreezeId = false;
	}
	public void setFreezeId(String throwaway, String startDate){
		TestReporter.logStep("Freezing inventory");
		Database db = new OracleDatabase(getEnvironment(), Database.AVAIL_SE);
		Recordset rs = null;
		String freezeId = "";
		Freeze freeze = new Freeze(getEnvironment(), "Main");

			Recordset rsInventory = new Recordset(db.getResultSet(AvailSE.getReservableResourceByFacilityAndDateNew(getRequestFacilityId(), startDate)));
			
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
		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/freezeId", freezeId);
		notSetFreezeId = false;
	}
	public void setFreezeId(String freezeId){
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/freezeId", freezeId);
		notSetFreezeId = false;
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
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest", "fx:AddNode;Node:travelAgency");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:agencyIataNumber");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:agencyOdsId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:guestTravelAgencyId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:agentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:guestAgentId");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:confirmationLocatorValue");
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency", "fx:AddNode;Node:guestConfirmationLocationId");

		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/agencyIataNumber", agencyIataNumber);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/agencyOdsId", agencyOdsId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/guestTravelAgencyId", guestAgencyId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/agentId", agentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/guestAgentId", guestAgentId);
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/confirmationLocatorValue", confirmationLocatorValue);		
		setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/travelAgency/guestConfirmationLocationId", guestConfirmationLocationId);
	}
	
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/book/bookTableServiceRequest/tableService/servicePeriodId", value);}
}