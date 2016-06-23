package com.disney.api.soapServices.activityServicePort.operations;

import com.disney.api.soapServices.activityServicePort.ActivityService;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.utils.XMLTools;
import com.disney.utils.dataFactory.guestFactory.Address;
import com.disney.utils.dataFactory.guestFactory.Email;
import com.disney.utils.dataFactory.guestFactory.Guest;
import com.disney.utils.dataFactory.guestFactory.HouseHold;

public class Modify extends ActivityService {
	public Modify(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("modify")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * Sets the travel plan ID in the SOAP Request
	 * @param travel plan ID
	 */
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/travelPlanId", value);}
	/**
	 * Sets the party ID in the SOAP Request
	 * @param value - party ID
	 */
	public void setPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/partyId", value);}
	/**
	 * Sets the sales channel in the in the SOAP Request
	 * @param value - sales channel
	 */
	public void setSalesChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/salesChannel", value);}
	/**
	 * Sets the communications channel in the SOAP Request
	 * @param value - communications channel
	 */
	public void setCommunicationChannel(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/communicationChannel", value);}
	/**
	 * Sets the source accounting center in the SOAP Request
	 * @param value = source accounting center
	 */
	public void setSourceAccountingCenter(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/sourceAccountingCenter", value);}
	/**
	 * Sets the reservation number in the SOAP request
	 * @param value - reservation number
	 */
	public void setReservationNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/reservationNumber", value);}
	/**
	 * Sets the facility ID in the SOAP request
	 * @param value - facility ID
	 */
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/facilityId", value);}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setProductId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/productId", value);}
	/**
	 * Gets the status from modifying the reservation from the SOAP response
	 * @return status from modifying the reservation
	 */
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/modifyActivityComponentResponse/status");}
	/**
	 * Gets the service start dateTime in the SOAP request
	 * @param value - service start dateTime
	 */
	public String getRequestServiceStartDate(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/serviceStartDate");}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public String getRequestServicePeriodId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/servicePeriodId");}
	/**
	 * Gets the facility ID in the SOAP request
	 * @return facility ID
	 */
	public String getRequestFacilityId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/facilityId");}
	/**
	 * Gets the service period ID in the SOAP request
	 * @return service period ID
	 */
	public String getRequestProductId(){return getRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/productId");}
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value service start dateTime
	 */
	public void setServiceStartDateTime(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/serviceStartDate", value);}
	/**
	 * Sets the primary guest postal code in the SOAP Request
	 * @param value - primary guest postal code
	 */
	public void setPrimaryGuestPostalCode(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/postalCode", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/postalCode", value);
		}
	}
	/**
	 * Sets the primary guest address line 1 in the SOAP Request
	 * @param value - primary guest address line 1
	 */
	public void setPrimaryGuestAddress1(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/addressLine1", value);}
		catch(XPathNotFoundException e){
			try{
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:addressLine1");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/addressLine1", value);				
			}catch(Exception e2){
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:addressDetails");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:addressLine1");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/addressLine1", value);
			}
		}
	}
	/**
	 * Sets the primary guest country in the SOAP Request
	 * @param value - primary guest country
	 */
	public void setPrimaryGuestCountry(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/country", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:country");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/country", value);
		}
	}
	/**
	 * Sets the primary guest state in the SOAP Request
	 * @param value - primary guest state
	 */
	public void setPrimaryGuestState(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/state", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:state");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/state", value);
		}
	}
	/**
	 * Sets the primary guest city in the SOAP Request
	 * @param value - primary guest city
	 */
	public void setPrimaryGuestCity(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/city", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:city");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/city", value);
		}
	}
	/**
	 * Gets the primary guest party ID in the SOAP Response
	 * @return - primary guest party ID
	 */
	public void setPrimaryGuestPartyId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/partyId", value);}	
	/**
	 * Sets the service period ID in the SOAP request
	 * @param value - service period ID
	 */
	public void setServicePeriosId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/servicePeriodId", value);}
	/**
	 * Sets the service start dateTime in the SOAP request
	 * @param value service start dateTime
	 */
	public void setServiceStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/serviceStartDate", value);}
	/**
	 * Sets the reservable resource ID in the SOAP request
	 * @param value reservable resource ID
	 */
	public void setReservableResourceId(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/inventoryDetails/reservableResourceId", value);}
	/**
	 * Sets the primary guest title in the SOAP request
	 * @param value - primary guest title
	 */
	public void setPrimaryGuestTitle(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/title", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:title");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/title", value);
		}
	}
	/**
	 * Sets the primary guest suffix in the SOAP request
	 * @param value - primary guest suffix
	 */
	public void setPrimaryGuestSuffix(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/suffix", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:suffix");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/suffix", value);
		}
	}
	/**
	 * Sets the contact name in the SOAP request
	 * @param value - contact name
	 */
	public void setContactName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/contactName", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest", "fx:addnode;node:contactName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/contactName", value);
		}
	}
	/**
	 * Sets primary guest first name in the SOAP request
	 * @param value - primary guest first name
	 */
	public void setPrimaryGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/firstName", value);}
	/**
	 * Sets primary guest last name in the SOAP request
	 * @param value - primary guest last name
	 */
	public void setPrimaryGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/lastName", value);}
	/**
	 * Sets primary guest middle name in the SOAP request
	 * @param value - primary guest middle name
	 */
	public void setPrimaryGuestMiddleName(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/middleName", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:middleName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/middleName", value);
		}
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
			
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/age", guest.getAge().toString());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/ageType", guest.isChild() ? "CHILD" : "ADULT");
			try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());}
			catch(XPathNotFoundException e){
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest", "fx:addnode;node:firstName");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/firstName", guest.getFirstName());
			}
			try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());}
			catch(XPathNotFoundException e){
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest", "fx:addnode;node:lastName");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/lastName", guest.getLastName());
			}
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/partyId", party.primaryGuest().getPartyId());
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/doNotMailIndicator", guest.primaryAddress().isOptIn() ? "true" :"false");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/doNotPhoneIndicator", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/dclGuestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/guestId", "0");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/guest/active", "true");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles"+partyRolePosition + "/correlationID", "0");

			currentGuest++;
		}
	}	
	/**
	 * Sets primary guest address locator ID in the SOAP request
	 * @param value - guest address locator ID
	 */
	public void setPrimaryGuestAddressLocatorId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/locatorId", value);}
		catch(XPathNotFoundException e){
			try{
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:locatorId");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/locatorId", value);
			}catch(Exception e2){
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:addressDetails");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:locatorId");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/locatorId", value);
			}
		}
	}
	/**
	 * Sets primary guest address guest  locator ID in the SOAP request
	 * @param value - primary guest address guest  locator ID
	 */
	public void setPrimaryGuestAddressGuestLocatorId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/guestLocatorId", value);}
		catch(XPathNotFoundException e){
			try{
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:guestLocatorId");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/guestLocatorId", value);
			}catch(Exception e2){
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:addressDetails");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode);node:guestLocatorId");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/guestLocatorId", value);
			}
		}
	}
	/**
	 * Sets flag to determine a primary guest address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest address is the primary address
	 */
	public void setPrimaryGuestAddressIsPrimary(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/primary", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails", "fx:addnode;node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/primary", value);
		}
	}
	/**
	 * Sets primary guest address line 2 in the SOAP request
	 * @param value - primary guest address line 2
	 */
	public void setPrimaryGuestAddress2(String value){setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails/addressLine2", value);}
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
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/primary", address.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/addressLine1", address.getAddress1());
			//	if(address.getAddress2().isEmpty()) setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", BaseSoapCommands.REMOVE_NODE.toString());
			//	else setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/addressLine2", address.getAddress2());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/city", address.getCity());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/country", address.getCountry());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/postalCode", address.getZipCode());
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails[" + position + "]/state", address.getStateAbbv());
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
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:AddNode;Node:addressDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine1");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:addressLine2");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:city");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:country");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:postalCode");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/addressDetails["+position+"]", "fx:AddNode;Node:state");
			position++;
		}
	}	
	/**
	 * Sets flag to determine a primary guest email address is the primary address in the SOAP request
	 * @param value - flag to determine a primary guest email address is the primary address
	 */
	public void setPrimaryGuestEmailAddressIsPrimary(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/primary", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails", "fx:addnode;node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/primary", value);
		}
	}
	/**
	 * Sets primary guest email address locator ID in the SOAP request
	 * @param value - primary guest email address locator ID
	 */
	public void setPrimaryGuestEmailAddressLocatorId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/locatorId", value);}
		catch(XPathNotFoundException e){
			try{
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails", "fx:addnode;node:locatorId");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/locatorId", value);				
			}catch(Exception e2){
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:addnode;node:emailDetails");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails", "fx:addnode;node:locatorId");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/locatorId", value);
			}
		}
	}
	/**
	 * Sets primary guest email address guest locator ID in the SOAP request
	 * @param value - primary guest email address guest locator ID
	 */
	public void setPrimaryGuestEmailAddressGuestLocatorId(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/guestLocatorId", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails", "fx:addnode;node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/guestLocatorId", value);
		}
	}
	/**
	 * Sets primary guest email address in the SOAP request
	 * @param value - primary guest email address
	 */
	public void setPrimaryGuestEmailAddress(String value){
		try{setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/address", value);}
		catch(XPathNotFoundException e){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails", "fx:addnode;node:address");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails/address", value);
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
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/primary", email.isPrimary() ? "true":"false");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/locatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/guestLocatorId", "0");
				setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails[" + position + "]/address", guest.getFirstName() + "." + guest.getLastName() + "@testautomation.disney.cm");
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
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest", "fx:AddNode;Node:emailDetails");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:locatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:guestLocatorId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:primary");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/primaryGuest/emailDetails["+position+"]", "fx:AddNode;Node:address");
		}
	}	
	/**
	 * Adds party roles for all guests that are not the primary guest
	 * @param numberToAdd - number of non-primary guests to add
	 */
	private void addPartyRoleNodes(int numberToAdd){
		int position = 2;
		for(int x=1 ; x <= numberToAdd ; x++){
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity", "fx:AddNode;Node:partyRoles");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:age");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:ageType");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:guest");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:firstName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:lastName");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:partyId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotMailIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:doNotPhoneIndicator");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:dclGuestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:guestId");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]/guest", "fx:AddNode;Node:active");
			setRequestNodeValueByXPath("/Envelope/Body/modify/modifyActivityComponentRequest/activity/partyRoles["+position+"]", "fx:AddNode;Node:correlationID");
			position++;
		}
	}
}