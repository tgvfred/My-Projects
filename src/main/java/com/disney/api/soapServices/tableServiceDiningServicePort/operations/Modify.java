package com.disney.api.soapServices.tableServiceDiningServicePort.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.tableServiceDiningServicePort.TableServiceDiningServicePort;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Modify extends TableServiceDiningServicePort {
	public Modify(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/travelPlanId", value);}
	public void setPrimaryGuestPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/partyId", value);}
	public void setPrimaryGuestGuestId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/guestId", value);}
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/salesChannel", value);}
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/communicationChannel", value);}
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/sourceAccountingCenter", value);}
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/reservationNumber", value);}
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/modifyTableServiceResponse/status");}
	/**
	 * Sets the primary guest title in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestTitle(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/title", value);}
	/**
	 * Sets the primary guest suffix in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestSuffix(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/suffix", value);}
	/**
	 * Sets the contact name in the SOAP request
	 * @param value - contact name
	 */
	public void setContactName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/contactName", value);}
	/**
	 * Sets primary guest first name in the SOAP request
	 * @param value - primary guest first name
	 */
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/firstName", value);}
	/**
	 * Sets primary guest last name in the SOAP request
	 * @param value - primary guest last name
	 */
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/lastName", value);}
	/**
	 * Sets primary guest middle name in the SOAP request
	 * @param value - primary guest middle name
	 */
	public void setPrimaryGuestMiddleName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/middleName", value);}
	/**
	 * Sets the product ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setProductId(String value){
		try{
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/productId", value);
		}catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService","fx:AddNode;Node:productId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/productId", value);
		}
	}
	/**
	 * Sets the product type in the SOAP request
	 * @param value - service period ID
	 */
	public void setProductType(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/productType", value);}
	/**
	 * Sets the facility ID in the SOAP request
	 * @param value - facility ID
	 */
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/facilityId", value);}	
	/**
	 * Sets the reservable resource ID in the SOAP request
	 * @param value reservable resource ID
	 */
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/inventoryDetails/reservableResourceId", value);}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/servicePeriodId");}
	/**
	 * Gets the service start dateTime in the SOAP request
	 * @param value - service start dateTime
	 */
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/serviceStartDate");}
	/**
	 * Gets the status from modifying the reservation from the SOAP response
	 * @return status from modifying the reservation
	 */
	public String getResponseStatus(){
		try{
			return getResponseNodeValueByXPath("/Envelope/Body/modifyTableServiceResponse/status");
		}catch(XPathNotFoundException e ){
			TestReporter.logAPI(true, "Status node not found in response", this);
			return "";
		}
	}
	/**
	 * Gets the facility ID in the SOAP request
	 * @return facility ID
	 */
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/facilityId");}
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
				
				if(guest.getSuffix().isEmpty()) 
					try{setPrimaryGuestSuffix(BaseSoapCommands.REMOVE_NODE.toString());}
					catch(XPathNotFoundException e){}
				else setPrimaryGuestSuffix(guest.getSuffix());
				
				setContactName(guest.getFullName());
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
			
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles"+partyRolePosition + "/correlationID", "0");

			currentGuest++;
		}
	}
	/**
	 * Sets the primary guest postal code in the SOAP Request
	 * @param value - primary guest postal code
	 */
	public void setPrimaryGuestPostalCode(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/postalCode", value);}	
	/**
	 * Sets the primary guest address line 1 in the SOAP Request
	 * @param value - primary guest address line 1
	 */
	public void setPrimaryGuestAddress1(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/addressLine1", value);}	
	/**
	 * Sets the primary guest country in the SOAP Request
	 * @param value - primary guest country
	 */
	public void setPrimaryGuestCountry(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/country", value);}	
	/**
	 * Sets the primary guest state in the SOAP Request
	 * @param value - primary guest state
	 */
	public void setPrimaryGuestState(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/state", value);}
	/**
	 * Sets the primary guest city in the SOAP Request
	 * @param value - primary guest city
	 */
	public void setPrimaryGuestCity(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/city", value);}	
	/**
	 * Sets primary guest address locator ID in the SOAP request
	 * @param value - guest address locator ID
	 */
	public void setPrimaryGuestAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/locatorId", value);}
	/**
	 * Sets primary guest address guest  locator ID in the SOAP request
	 * @param value - primary guest address guest  locator ID
	 */
	public void setPrimaryGuestAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/guestLocatorId", value);}
	/**
	 * Sets flag to determine a primary guest address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest address is the primary address
	 */
	public void setPrimaryGuestAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/primary", value);}
	/**
	 * Sets primary guest address line 2 in the SOAP request
	 * @param value - primary guest address line 2
	 */
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails/addressLine2", value);}
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
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
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
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
			position++;
		}
	}	
	/**
	 * Sets flag to determine a primary guest email address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest email address is the primary address
	 */
	public void setPrimaryGuestEmailAddressIsPrimary(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails/primary", value);}
	/**
	 * Sets primary guest email address locator ID in the SOAP request
	 * @param value - primary guest email address locator ID
	 */
	public void setPrimaryGuestEmailAddressLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails/locatorId", value);}
	/**
	 * Sets primary guest email address guest locator ID in the SOAP request
	 * @param value - primary guest email address guest locator ID
	 */	
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails/guestLocatorId", value);}
	/**
	 * Sets primary guest email address in the SOAP request
	 * @param value - primary guest email address
	 */	
	public void setPrimaryGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails/address", value);}
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
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
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
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}	
	/**
	 * Adds party roles for all guests that are not the primary guest
	 * @param numberToAdd - number of non-primary guests to add
	 */
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setServicePeriodId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/servicePeriodId", value);}
	/**
	 * Sets the service start dateTime in the SOAP request
	 * @param value service start dateTime
	 */
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/serviceStartDate", value);}

	public void setAllergies(String value, String index){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		try{
			getRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/allergies["+index+"]");
		}catch(Exception e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService", "fx:AddNode;Node:allergies");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyTableServiceRequest/tableService/allergies["+index+"]", value);
		}
	}	

}
