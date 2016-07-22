package com.disney.api.soapServices.scheduledEventsServicePort.operations;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.scheduledEventsServicePort.ScheduledEventsServicePort;
import com.disney.utils.XMLTools;

public class RetrieveAll extends ScheduledEventsServicePort{
	// Allergies
	Map<String, String> allergies = new HashMap<String, String>();
	int numAllergies = -1;
	// Booking Status Values
	Map<String, String> bookingStatusValues = new HashMap<String, String>();
	int numBookingStatusValues = -1;
	// Cancel Reasons
	Map<String, String> cancelReasonCodes = new HashMap<String, String>();
	Map<String, String> cancelReasonDescriptions = new HashMap<String, String>();
	Map<String, String> cancelReasonIds= new HashMap<String, String>();
	int numCancelReasons = -1;
	// Change Reasons
	Map<String, String> changeReasonDescriptions = new HashMap<String, String>();
	Map<String, String> changeReasonIds = new HashMap<String, String>();
	int numChangeReasons = -1;
	// Communication Channels
	Map<String, String> communicationChannels = new HashMap<String, String>();
	int numCommunicationChannels = -1;
	// Guest Requests
	Map<String, String> guestRequestsCodes = new HashMap<String, String>();
	Map<String, String> guestRequestsDescriptions = new HashMap<String, String>();
	Map<String, String> guestRequestsIds = new HashMap<String, String>();
	int numGuestRequests = -1;
	// Mass Cancel Reasons
	Map<String, String> massCancelReasonsCode = new HashMap<String, String>();
	Map<String, String> massCancelReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> massCancelReasonsIds = new HashMap<String, String>();
	int numMassCancelsReasons = -1;
	// Reprint Reasons
	Map<String, String> reprintReasonsCodes = new HashMap<String, String>();
	Map<String, String> reprintReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> reprintReasonsIds = new HashMap<String, String>();
	int numReprintReasons = -1;
	// Roles
	Map<String, String> roles = new HashMap<String, String>();
	int numRoles = -1;
	// Sales Channels
	Map<String, String> salesChannels = new HashMap<String, String>();
	int numSalesChannels = -1;
	// Special Events
	Map<String, String> specialEventsCodes = new HashMap<String, String>();
	Map<String, String> specialEventsDescriptions = new HashMap<String, String>();
	Map<String, String> specialEventsIds = new HashMap<String, String>();
	int numSpecialEvents = -1;
	// Special Needs
	Map<String, String> specialNeedsCodes = new HashMap<String, String>();
	Map<String, String> specialNeedsDescriptions = new HashMap<String, String>();
	Map<String, String> specialNeedsIds = new HashMap<String, String>();
	int numSpecialNeeds = -1;
	// Tax Exempt Types
	Map<String, String> taxExemptTypes = new HashMap<String, String>();
	int numTaxExemptTypes = -1;
	// Comment Types
	Map<String, String> commentTypes = new HashMap<String, String>();
	int numCommentTypes = -1;
	// Inventory Overide Reasons
	Map<String, String> inventoryOverideReasonsCodes = new HashMap<String, String>();
	Map<String, String> inventoryOverideReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> inventoryOverideReasonsIds = new HashMap<String, String>();
	int numInventoryOverideReasons = -1;
	// Ala Carte Cancel Reasons
	Map<String, String> alaCarteCancelReasonsCodes = new HashMap<String, String>();
	Map<String, String> alaCarteCancelReasonsDescriptions = new HashMap<String, String>();
	Map<String, String> alaCarteCancelReasonsIds = new HashMap<String, String>();
	int numAlaCarteCancelReasons = -1;
	// Celebrations Codes
	Map<String, String> celebrationsCodes = new HashMap<String, String>();
	Map<String, String> celebrationsDescriptions = new HashMap<String, String>();
	Map<String, String> celebrationsIds = new HashMap<String, String>();
	int numCelebrations = -1;	
	
	public RetrieveAll(String environment) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveAll")));
		//setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		generateServiceContext();
		removeComments() ;
		removeWhiteSpace();
	}	
	
	//**************************************
	//**************************************
	//				Allergies
	//**************************************
	//**************************************
	/**
	 * Retrieves all allergies
	 * @return - all allergies
	 */
	public Map<String, String> getAllergies(){
		if(numAllergies == -1) getNumberOfAllergies();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/allergies");
		for(int i = 0; i < numAllergies; i++){
			allergies.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return allergies;
	}
	/**
	 * Retrieves the number of allergies
	 * @return - number of allergies
	 */
	public int getNumberOfAllergies(){
		numAllergies = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/allergies").getLength();
		return numAllergies;
	}
	
	
	//**************************************
	//**************************************
	//		Booking Status Values
	//**************************************
	//**************************************
	/**
	 * Retrieves all booking status values
	 * @return - all booking status values
	 */
	public Map<String, String> getBookingStatusValues(){
		if(numBookingStatusValues == -1) getNumberOfBookingStatusValues();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/bookingStatusValues");
		for(int i = 0; i < numBookingStatusValues; i++){
			bookingStatusValues.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return bookingStatusValues;
	}
	/**
	 * Retrieves the number of booking status values
	 * @return - number of booking status values
	 */
	public int getNumberOfBookingStatusValues(){
		numBookingStatusValues = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/bookingStatusValues").getLength();
		return numBookingStatusValues;
	}

	
	//**************************************
	//**************************************
	//			Cancel Reasons
	//**************************************
	//**************************************
	/**
	 * Retrieves all cancel reason codes
	 * @return - all cancel reason codes
	 */
	public Map<String, String> getCancelReasonCodes(){
		if(numCancelReasons == -1) getNumberOfBookingStatusValues();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/cancelReasons/optionCode");
		for(int i = 0; i < numCancelReasons; i++){
			cancelReasonCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonCodes;
	}	
	/**
	 * Retrieves all cancel reason descriptions
	 * @return - all cancel reason descriptions
	 */
	public Map<String, String> getCancelReasonDescriptions(){
		if(numCancelReasons == -1) getNumberOfBookingStatusValues();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/cancelReasons/optionDescription");
		for(int i = 0; i < numCancelReasons; i++){
			cancelReasonDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonDescriptions;
	}
	/**
	 * Retrieves all cancel reason ids
	 * @return - all cancel reason ids
	 */
	public Map<String, String> getCancelReasonIds(){
		if(numCancelReasons == -1) getNumberOfBookingStatusValues();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/cancelReasons/optionId");
		for(int i = 0; i < numCancelReasons; i++){
			cancelReasonIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonIds;
	}
	/**
	 * Retrieves the number of cancel reasons
	 * @return - number of cancel reasons
	 */
	public int getNumberOfCancelReasons(){
		numCancelReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/cancelReasons").getLength();
		return numCancelReasons;
	}

	
	//**************************************
	//**************************************
	//			Change Reasons
	//**************************************
	//**************************************
	/**
	 * Retrieves all change reason descriptions
	 * @return - all change reason descriptions
	 */
	public Map<String, String> getChangeReasonCodes(){
		if(numChangeReasons == -1) getNumberOfChangeReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/changeReasons/optionCode");
		for(int i = 0; i < numChangeReasons; i++){
			cancelReasonDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return cancelReasonDescriptions;
	}
	/**
	 * Retrieves all change reason descriptions
	 * @return - all change reason descriptions
	 */
	public Map<String, String> getChangeReasonDescriptions(){
		if(numChangeReasons == -1) getNumberOfChangeReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/changeReasons/optionDescription");
		for(int i = 0; i < numChangeReasons; i++){
			changeReasonIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return changeReasonIds;
	}
	/**
	 * Retrieves the number of change reasons
	 * @return - number of change reasons
	 */
	public int getNumberOfChangeReasons(){
		numChangeReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/changeReasons").getLength();
		return numChangeReasons;
	}
	

	//**************************************
	//**************************************
	//		Communication Channels
	//**************************************
	//**************************************
	/**
	 * Retrieves all communication channels
	 * @return - all communication channels
	 */
	public Map<String, String> getCommunicationChannels(){
		if(numCommunicationChannels == -1) getNumberOfCommunicationChannels();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/communicationChannels");
		for(int i = 0; i < numCommunicationChannels; i++){
			communicationChannels.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return communicationChannels;
	}
	/**
	 * Retrieves the number of communication channels
	 * @return - number of communication channels
	 */
	public int getNumberOfCommunicationChannels(){
		numCommunicationChannels = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/communicationChannels").getLength();
		return numCommunicationChannels;
	}
	

	//**************************************
	//**************************************
	//			Guest Requests
	//**************************************
	//**************************************
	/**
	 * Retrieves all guest request codes
	 * @return - all guest request codes
	 */
	public Map<String, String> getGuestRequestCodes(){
		if(numGuestRequests == -1) getNumberOfGuestRequests();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/guestRequests/optionCode");
		for(int i = 0; i < numGuestRequests; i++){
			guestRequestsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return guestRequestsCodes;
	}
	/**
	 * Retrieves all guest request descriptions
	 * @return - all guest request descriptions
	 */
	public Map<String, String> getGuestRequestDescriptions(){
		if(numGuestRequests == -1) getNumberOfGuestRequests();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/guestRequests/optionDescription");
		for(int i = 0; i < numGuestRequests; i++){
			guestRequestsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return guestRequestsDescriptions;
	}
	/**
	 * Retrieves all guest request ids
	 * @return - all guest request ids
	 */
	public Map<String, String> getGuestRequestIds(){
		if(numGuestRequests == -1) getNumberOfGuestRequests();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/guestRequests/optionId");
		for(int i = 0; i < numGuestRequests; i++){
			guestRequestsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return guestRequestsIds;
	}
	/**
	 * Retrieves the number of guest requests
	 * @return - number of guest requests
	 */
	public int getNumberOfGuestRequests(){
		numGuestRequests = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/guestRequests").getLength();
		return numGuestRequests;
	}
	

	//**************************************
	//**************************************
	//			Mass Cancel
	//**************************************
	//**************************************
	/**
	 * Retrieves all mass cancel codes
	 * @return - all mass cancel codes
	 */
	public Map<String, String> getMassCancelCodes(){
		if(numMassCancelsReasons == -1) getNumberOfMassCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/massCancelReasons/optionCode");
		for(int i = 0; i < numMassCancelsReasons; i++){
			massCancelReasonsCode.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return massCancelReasonsCode;
	}
	/**
	 * Retrieves all mass cancel descriptions
	 * @return - all mass cancel descriptions
	 */
	public Map<String, String> getMassCancelDescriptions(){
		if(numMassCancelsReasons == -1) getNumberOfMassCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/massCancelReasons/optionDescription");
		for(int i = 0; i < numMassCancelsReasons; i++){
			massCancelReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return massCancelReasonsDescriptions;
	}
	/**
	 * Retrieves all mass cancel ids
	 * @return - all mass cancel ids
	 */
	public Map<String, String> getMassCancelIds(){
		if(numMassCancelsReasons == -1) getNumberOfMassCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/massCancelReasons/optionId");
		for(int i = 0; i < numMassCancelsReasons; i++){
			massCancelReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return massCancelReasonsIds;
	}
	/**
	 * Retrieves the number of mass cancel reasons
	 * @return - number of mass cancel reasons
	 */
	public int getNumberOfMassCancelReasons(){
		numMassCancelsReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/massCancelReasons").getLength();
		return numMassCancelsReasons;
	}
	

	//**************************************
	//**************************************
	//			Reprint Reasons
	//**************************************
	//**************************************
	/**
	 * Retrieves all reprint reasons codes
	 * @return - all reprint reasons codes
	 */
	public Map<String, String> getReprintReasonCodes(){
		if(numReprintReasons == -1) getNumberOfReprintReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/reprintReasons/optionCode");
		for(int i = 0; i < numReprintReasons; i++){
			reprintReasonsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return reprintReasonsCodes;
	}
	/**
	 * Retrieves all reprint reasons descriptions
	 * @return - all reprint reasons descriptions
	 */
	public Map<String, String> getReprintReasonDescriptions(){
		if(numReprintReasons == -1) getNumberOfReprintReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/reprintReasons/optionDescription");
		for(int i = 0; i < numReprintReasons; i++){
			reprintReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return reprintReasonsDescriptions;
	}
	/**
	 * Retrieves all reprint reasons ids
	 * @return - all reprint reasons ids
	 */
	public Map<String, String> getReprintReasonIds(){
		if(numReprintReasons == -1) getNumberOfReprintReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/reprintReasons/optionId");
		for(int i = 0; i < numReprintReasons; i++){
			reprintReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return reprintReasonsIds;
	}
	/**
	 * Retrieves the number of reprint reasons reasons
	 * @return - number of reprint reasons reasons
	 */
	public int getNumberOfReprintReasons(){
		numReprintReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/reprintReasons").getLength();
		return numReprintReasons;
	}
	

	//**************************************
	//**************************************
	//				Roles
	//**************************************
	//**************************************
	/**
	 * Retrieves all roles
	 * @return - all roles
	 */
	public Map<String, String> getRoles(){
		if(numRoles == -1) getNumberOfRoles();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/roles");
		for(int i = 0; i < numRoles; i++){
			roles.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return roles;
	}
	/**
	 * Retrieves the number of roles
	 * @return - number of roles
	 */
	public int getNumberOfRoles(){
		numRoles = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/roles").getLength();
		return numRoles;
	}
	

	//**************************************
	//**************************************
	//			Sales Channels
	//**************************************
	//**************************************
	/**
	 * Retrieves all sales channels
	 * @return - all sales channels
	 */
	public Map<String, String> getSalesChannels(){
		if(numSalesChannels == -1) getNumberOfSalesChannels();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/salesChannels");
		for(int i = 0; i < numSalesChannels; i++){
			salesChannels.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return salesChannels;
	}
	/**
	 * Retrieves the number of sales channels
	 * @return - number of sales channels
	 */
	public int getNumberOfSalesChannels(){
		numSalesChannels = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/salesChannels").getLength();
		return numSalesChannels;
	}
	

	//**************************************
	//**************************************
	//			Special Events
	//**************************************
	//**************************************
	/**
	 * Retrieves all special events codes
	 * @return - all special events codes
	 */
	public Map<String, String> getSpecialEventsCodes(){
		if(numSpecialEvents == -1) getNumberOfSpecialEvents();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialEvents/optionCode");
		for(int i = 0; i < numSpecialEvents; i++){
			specialEventsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialEventsDescriptions;
	}
	/**
	 * Retrieves all special events descriptions
	 * @return - all special events descriptions
	 */
	public Map<String, String> getSpecialEventsDescriptions(){
		if(numSpecialEvents == -1) getNumberOfSpecialEvents();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialEvents/optionDescription");
		for(int i = 0; i < numSpecialEvents; i++){
			specialEventsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialEventsDescriptions;
	}
	/**
	 * Retrieves all special events ids
	 * @return - all special events ids
	 */
	public Map<String, String> getSpecialEventsIds(){
		if(numSpecialEvents == -1) getNumberOfSpecialEvents();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialEvents/optionId");
		for(int i = 0; i < numSpecialEvents; i++){
			specialEventsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialEventsIds;
	}
	/**
	 * Retrieves the number of special events
	 * @return - number of special events
	 */
	public int getNumberOfSpecialEvents(){
		numSpecialEvents = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialEvents").getLength();
		return numSpecialEvents;
	}
	

	//**************************************
	//**************************************
	//			Special Needs
	//**************************************
	//**************************************
	/**
	 * Retrieves all special needs codes
	 * @return - all special needs codes
	 */
	public Map<String, String> getSpecialNeedsCodes(){
		if(numSpecialNeeds == -1) getNumberOfSpecialNeeds();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialNeeds/optionCode");
		for(int i = 0; i < numSpecialNeeds; i++){
			specialNeedsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialNeedsCodes;
	}
	/**
	 * Retrieves all special needs descriptions
	 * @return - all special needs descriptions
	 */
	public Map<String, String> getSpecialNeedsDescriptions(){
		if(numSpecialNeeds == -1) getNumberOfSpecialNeeds();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialNeeds/optionDescription");
		for(int i = 0; i < numSpecialNeeds; i++){
			specialNeedsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialNeedsDescriptions;
	}
	/**
	 * Retrieves all special needs ids
	 * @return - all special needs ids
	 */
	public Map<String, String> getSpecialNeedsIds(){
		if(numSpecialNeeds == -1) getNumberOfSpecialNeeds();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialNeeds/optionId");
		for(int i = 0; i < numSpecialNeeds; i++){
			specialNeedsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return specialNeedsIds;
	}
	/**
	 * Retrieves the number of special needs
	 * @return - number of special needs
	 */
	public int getNumberOfSpecialNeeds(){
		numSpecialNeeds = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/specialNeeds").getLength();
		return numSpecialNeeds;
	}
	

	//**************************************
	//**************************************
	//			Tax Exempt Types
	//**************************************
	//**************************************
	/**
	 * Retrieves all tax exempt types
	 * @return - all tax exempt types
	 */
	public Map<String, String> getTaxExemptTypes(){
		if(numTaxExemptTypes == -1) getNumberOfTaxExemptTypes();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/taxExemptTypes");
		for(int i = 0; i < numTaxExemptTypes; i++){
			taxExemptTypes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return taxExemptTypes;
	}
	/**
	 * Retrieves the number of tax exempt types
	 * @return - number of tax exempt types
	 */
	public int getNumberOfTaxExemptTypes(){
		numTaxExemptTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/taxExemptTypes").getLength();
		return numTaxExemptTypes;
	}
	

	//**************************************
	//**************************************
	//			Comment Types
	//**************************************
	//**************************************
	/**
	 * Retrieves all comment types
	 * @return - all comment types
	 */
	public Map<String, String> getCommentTypes(){
		if(numCommentTypes == -1) getNumberOfCommentTypes();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/commentTypes");
		for(int i = 0; i < numCommentTypes; i++){
			commentTypes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return commentTypes;
	}
	/**
	 * Retrieves the number of comment types
	 * @return - number of comment types
	 */
	public int getNumberOfCommentTypes(){
		numCommentTypes = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/commentTypes").getLength();
		return numCommentTypes;
	}
	

	//**************************************
	//**************************************
	//		Inventory Overide Reasons
	//**************************************
	//**************************************
	/**
	 * Retrieves all inventory overide reason codes
	 * @return - all inventory overide reason codes
	 */
	public Map<String, String> getInventoryOverideReasonsCodes(){
		if(numInventoryOverideReasons == -1) getNumberOfInventoryOverideReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/inventoryOverideReasons/optionCode");
		for(int i = 0; i < numInventoryOverideReasons; i++){
			inventoryOverideReasonsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return inventoryOverideReasonsCodes;
	}
	/**
	 * Retrieves all inventory overide reason descriptions
	 * @return - all inventory overide reason descriptions
	 */
	public Map<String, String> getInventoryOverideReasonsDescriptions(){
		if(numInventoryOverideReasons == -1) getNumberOfInventoryOverideReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/inventoryOverideReasons/optionDescription");
		for(int i = 0; i < numInventoryOverideReasons; i++){
			inventoryOverideReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return inventoryOverideReasonsDescriptions;
	}
	/**
	 * Retrieves all inventory overide reason ids
	 * @return - all inventory overide reason ids
	 */
	public Map<String, String> getInventoryOverideReasonsIds(){
		if(numInventoryOverideReasons == -1) getNumberOfInventoryOverideReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/inventoryOverideReasons/optionId");
		for(int i = 0; i < numInventoryOverideReasons; i++){
			inventoryOverideReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return inventoryOverideReasonsIds;
	}
	/**
	 * Retrieves the number of inventory overide reason codes
	 * @return - number of inventory overide reason codes
	 */
	public int getNumberOfInventoryOverideReasons(){
		numInventoryOverideReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/inventoryOverideReasons").getLength();
		return numInventoryOverideReasons;
	}
	

	//**************************************
	//**************************************
	//		Ala Carte Cancel Reasons
	//**************************************
	//**************************************
	/**
	 * Retrieves all Ala Carte Cancel Reasons codes
	 * @return - all Ala Carte Cancel Reasons codes
	 */
	public Map<String, String> getAlaCarteCancelReasonsCodes(){
		if(numAlaCarteCancelReasons == -1) getNumberOfAlaCarteCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons/optionCode");
		for(int i = 0; i < numAlaCarteCancelReasons; i++){
			alaCarteCancelReasonsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return alaCarteCancelReasonsCodes;
	}
	/**
	 * Retrieves all Ala Carte Cancel Reasons descriptions
	 * @return - all Ala Carte Cancel Reasons descriptions
	 */
	public Map<String, String> getAlaCarteCancelReasonsDescriptions(){
		if(numAlaCarteCancelReasons == -1) getNumberOfAlaCarteCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons/optionDescription");
		for(int i = 0; i < numAlaCarteCancelReasons; i++){
			alaCarteCancelReasonsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return alaCarteCancelReasonsDescriptions;
	}
	/**
	 * Retrieves all Ala Carte Cancel Reasons ids
	 * @return - all Ala Carte Cancel Reasons ids
	 */
	public Map<String, String> getAlaCarteCancelReasonsIds(){
		if(numAlaCarteCancelReasons == -1) getNumberOfAlaCarteCancelReasons();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons/optionId");
		for(int i = 0; i < numAlaCarteCancelReasons; i++){
			alaCarteCancelReasonsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return alaCarteCancelReasonsIds;
	}
	/**
	 * Retrieves the number of Ala Carte Cancel Reasons
	 * @return - number of Ala Carte Cancel Reasons
	 */
	public int getNumberOfAlaCarteCancelReasons(){
		numAlaCarteCancelReasons = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons").getLength();
		return numAlaCarteCancelReasons;
	}
	

	//**************************************
	//**************************************
	//			Celebrations
	//**************************************
	//**************************************
	/**
	 * Retrieves all Celebrations codes
	 * @return - all Celebrations codes
	 */
	public Map<String, String> getCelebrationsCodes(){
		if(numCelebrations == -1) getNumberOfCelebrations();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/celebrations/optionCode");
		for(int i = 0; i < numCelebrations; i++){
			celebrationsCodes.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return celebrationsCodes;
	}
	/**
	 * Retrieves all Celebrations descriptions
	 * @return - all Celebrations descriptions
	 */
	public Map<String, String> getCelebrationsDescriptions(){
		if(numCelebrations == -1) getNumberOfCelebrations();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons/optionDescription");
		for(int i = 0; i < numCelebrations; i++){
			celebrationsDescriptions.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return celebrationsDescriptions;
	}
	/**
	 * Retrieves all Celebrations ids
	 * @return - all Celebrations ids
	 */
	public Map<String, String> getCelebrationsIds(){
		if(numCelebrations == -1) getNumberOfCelebrations();
		NodeList nodes =  XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons/optionId");
		for(int i = 0; i < numCelebrations; i++){
			celebrationsIds.put(String.valueOf(i), nodes.item(i).getTextContent());
		}
		return celebrationsIds;
	}
	/**
	 * Retrieves the number of Celebrations
	 * @return - number of Celebrations
	 */
	public int getNumberOfCelebrations(){
		numCelebrations = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveAllResponse/allOptions/alaCarteCancelReasons").getLength();
		return numCelebrations;
	}
}