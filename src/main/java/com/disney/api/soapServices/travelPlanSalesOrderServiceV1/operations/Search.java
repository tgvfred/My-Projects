package com.disney.api.soapServices.travelPlanSalesOrderServiceV1.operations;

import org.w3c.dom.NodeList;

import com.disney.api.soapServices.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class Search extends TravelPlanSalesOrderService{
	private String[] salesOrderIds;
	
	public Search(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("search")));
			setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
			setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	//*************************************************************
	//*************************************************************
	//*************************************************************
	//				REQUEST METHODS
	//*************************************************************
	//*************************************************************
	//*************************************************************
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/search/searchRequest/@travelPlanId", value);}
	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/search/searchRequest/@salesOrderId", value);}
	public void setStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/search/searchRequest/@startDate", value);}
	public void setOrganizationId(String value){setRequestNodeValueByXPath("/Envelope/Body/search/searchRequest/@organizationId", value);}
	public void setTravelAgencyId(String value){setRequestNodeValueByXPath("/Envelope/Body/search/searchRequest/@travelAgencyId", value);}
	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/search/searchRequest/@enterpriseFacilityId", value);}
	public void setGuestFirstName(String value){setRequestNodeValueByXPath("/Envelope/Body/search/guestSearchRequest/@firstName", value);}
	public void setGuestLastName(String value){setRequestNodeValueByXPath("/Envelope/Body/search/guestSearchRequest/@lastName", value);}		
	public void setGuestZipCode(String value){setRequestNodeValueByXPath("/Envelope/Body/search/guestSearchRequest/@zipCode", value);}	
	public void setGuestExactMatch(String value){setRequestNodeValueByXPath("/Envelope/Body/search/guestSearchRequest/@exactNameMatch", value);	}	
	public void setGuestEmailAddress(String value){setRequestNodeValueByXPath("/Envelope/Body/search/guestSearchRequest/emailAddresses", value);}	
	public void setGuestPhoneNumber(String value){setRequestNodeValueByXPath("/Envelope/Body/search/guestSearchRequest/phoneNumbers", value);}

	//*************************************************************
	//*************************************************************
	//*************************************************************
	//				RESPONSE METHODS
	//*************************************************************
	//*************************************************************
	//*************************************************************

	public String setTermsAndConditionsVersionNumber(){return getResponseNodeValueByXPath("/Envelope/Body/updatePreArrivalInformation/request/preArrivalInfo/termsAndConditionsVersionNumber");}		
	public String getTravelPlanStatus(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/@status");}	
	public String getTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/@travelPlanId");}	
	public String getTravelPlanArrivalDate(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails/@arrivalDate");}	
	public String getTravelPlanDepartureDate(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails/@depatureDate");}	
	public String getTravelPlanSalesOrderId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails/@id");}	
	public String getTravelPlanSourceAccountCenterId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails/@sourceAccountingCenterId");}	
	public String getTravelPlanSalesOrderStatus(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails/@status");}	
	public String getTravelPlanTravelAgencyId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails/travelAgencySummary/@id");}	
	public String getTravelPlanGuestId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/guest/@id");}	
	public String getTravelPlanSalesOrderItemEndDate(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/@endDate");}	
	public String getTravelPlanSalesOrderItemProductId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/@productId");}	
	public String getTravelPlanSalesOrderItemStatus(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/@status");}	
	public String getTravelPlanSalesOrderItemDescription(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/facility/@description");}	
	public String getTravelPlanSalesOrderItemFacilityId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/facility/@enterpriseFacilityId");}	
	public String getTravelPlanSalesOrderItemName(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/facility/@officialName");}	
	public String getTravelPlanSalesOrderItemResourceDescription(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/salesOrderItemResources/@resourceDescription");}	
	public String getTravelPlanSalesOrderItemResourceId(){return getResponseNodeValueByXPath("/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/salesOrderItemResponse/salesOrderItemResources/@resourceId");}	
	
	public String[] getTravelPlanSalesOrderIds(){
		NodeList salesOrderSummaryDetails;
		salesOrderSummaryDetails = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/searchResponse/searchResponse/travelPlanResponses/searchSalesOrderSummaryDetails");
		salesOrderIds = new String[salesOrderSummaryDetails.getLength()];
		for(int i = 0; i < salesOrderSummaryDetails.getLength(); i++){
			salesOrderIds[i] = salesOrderSummaryDetails.item(i).getAttributes().getNamedItem("id").getNodeValue();
		}
		return salesOrderIds;
	}
}
