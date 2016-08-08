package com.disney.api.soapServices.dvcModule.offerService.operations;
import com.disney.api.soapServices.dvcModule.offerService.OfferService;
import com.disney.utils.XMLTools;

public class RetrieveOffers extends OfferService {
	
	public RetrieveOffers(String environment, String scenario) {
		super(environment);
		
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("retrieveOffers")));
		System.out.println(getRequest());
		
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		
		removeComments() ;
		removeWhiteSpace();
	}
	
	/**
	 * @summary Sets the membership ref ID.  This best retrieved by using the 
	 * 			searchMembership service and passing in the membershipID (such as
	 * 			'232060108939' and getting the membershipRefID from the response.  It 
	 * 			is typically in the format of 'M:267946694987762079'
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setMembershipRefId(String membershipRefId){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/@membershipRefId", membershipRefId);
	}
	
	/**
	 * @summary Sets the member ref ID (really the membership ID) such as '232060108939'
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setMemberRefId(String memberRefId){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/@memberRefId", memberRefId);
	}
	
	
	/**
	 * @summary Sets the reservation type.  For members cash reservation would be 'M$R$',
	 * 			member points would be 'MP', etc	  			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setReservationType(String reservationType){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/@reservationType", reservationType);
	}
	
	/**
	 * @summary Sets the arrival date.  Needs to be in the format '2014-11-12T00:00:00-05:00'	  			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setArrivalDate(String arrivalDate){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/inventories/@arriveDate", arrivalDate);
	}
	
	/**
	 * @summary Sets the departure date.  Needs to be in the format '2014-11-12T00:00:00-05:00'	  			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setDeptDate(String deptDate){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/inventories/@departDate", deptDate);
	}
	
	/**
	 * @summary Sets the DVC facility ID (different then the dreams resort code).  
	 * 			For example, Beach club resort would be 'CLUB'   	  			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setFacilityID(String facilityID){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/inventories/facility/@facilityId", facilityID);
	}
	
	/**
	 * @summary Sets the vacation type.  For members cash reservation would be 'M$R$',
	 * 			member points would be 'MP', etc	  			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setVacationType(String vacationType){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/inventories/facility/@vacationType", vacationType);
	}
	
	/**
	 * @summary Sets the dreams location code (resort code)	  			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setDreamsLocationCode(String resortCode){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/inventories/facility/@dreamsLocationCode", resortCode);
	}
	
	/**
	 * @summary Sets the DVC room type code (might be different then dreams room code) 			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setRoomTypeCode(String roomTypeCode){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/inventories/roomType/@roomTypeCode", roomTypeCode);
	}
	
	/**
	 * @summary Sets the DVC age type - A for adult, C - for child		
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setAgeType(String ageType){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/partyMix/ageTypeCounts/@ageType", ageType);
	}
	
	/**
	 * @summary sets the DVC age count (number of age types) so 2 for 2 adults, etc			
	 * @param
	 * @author 	Jessica Marshall
	 * @version Created: 11/4/2014
	 * @return 	NA
	 */
	public void setAgeCount(String ageCount){
		setRequestNodeValueByXPath("//retrieveOffers/inventoryRequestInfo/partyMix/ageTypeCounts/@count", ageCount);
	}
	


}
