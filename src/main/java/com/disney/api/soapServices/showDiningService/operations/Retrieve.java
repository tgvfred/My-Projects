package com.disney.api.soapServices.showDiningService.operations;

import com.disney.api.soapServices.showDiningService.ShowDiningService;
import com.disney.utils.XMLTools;

public class Retrieve extends ShowDiningService {
	public Retrieve(String environment, String scenario) {
		super(environment);
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieve")));
		
		generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setReservationNumber(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveShowDiningRequest/reservationNumber", value);
	}
	
	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveShowDiningRequest/salesChannel", value);
	}
	
	public void setCommunicationsChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/retrieve/retrieveShowDiningRequest/communicationChannel", value);
	}
	
	public String getPartyId(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/primaryGuest/partyId");
	}
	
	public String getInternalCommentsText(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/internalComments/commentText");
	}
	
	public String getInternalCommentsType(){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/internalComments/commentType");
	}
	
	public String getInternalCommentText(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/internalComments["+index+"]/commentText");
	}
	
	public String getInternalCommentType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/internalComments["+index+"]/commentType");
	}
	
	public String getProfileDetailType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/profileDetails["+index+"]/type");
	}
	
	public String getProfileDetailId(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/profileDetails["+index+"]/id");
	}
	
	public String actualType(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/profileDetails["+index+"]/id");
	}
	
	public String getAllergies(String index){
		return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/allergies["+index+"]");
	}
	
	public String getPrimaryGuestId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/primaryGuest/guestId");}
	public String getReservationNumber(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/reservationNumber");}
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/travelPlanId");}
	public String getFacilityid(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/facilityId");}
	public String getProductId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/productId");}

	/**
	 * Gets reservation status from SOAP response
	 * @return reservation status 
	 */
	public String getStatus(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/status");}
	/**
	 * Gets number of guests from SOAP response
	 * @return number of guests 
	 */
	public int getNumberOfGuests(){return XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/partyRoles/guest").getLength();}
	/**
	 * Gets resource assignment identifier from SOAP response
	 * @return resource assignment identifier 
	 */
	public String getResourceAssignmentIdentifier(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/inventoryDetails/resourceAssignmentIdentifier");}
	/**
	 * Gets facility ID from SOAP response
	 * @return
	 */
	public String getResponseFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/facilityId");}	
	/**
	 * Gets primary guest age from SOAP response
	 * @return primary guest age 
	 */
	public String getPrimaryGuestAge(){return getResponseNodeValueByXPath("/Envelope/Body/retrieveShowDiningResponse/ShowDiningReservation/dinnerShowPackage/partyRoles[1]/age");}
}