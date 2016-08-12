package com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.operations;

import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.tpsoModule.travelPlanSalesOrderServiceV1.TravelPlanSalesOrderService;
import com.disney.test.utils.Randomness;
import com.disney.utils.XMLTools;

public class UpdateBundle extends TravelPlanSalesOrderService{
	public UpdateBundle(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("updateBundle")));
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		setRequestNodeValueByXPath("/Envelope/Header/ServiceContext/@creationTime", Randomness.generateCurrentXMLDatetime());
		removeComments() ;
		removeWhiteSpace();
	}
	
	
	//*******************************************
	//*******************************************
	//*******************************************
	//		Request Methods
	//*******************************************
	//*******************************************
	//*******************************************
	/*
	 * /Envelope/Body/updateBundle
	 */
	public void setTravelPlanId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/travelPlanId", value);}
	public void setSalesOrderId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/salesOrderId", value);}
	/*
	 * /Envelope/Body/updateBundle/packageBundleRequests
	 */
	public void setPackageBundleRequestsReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/@referenceId", value);}
	public void setPackageBundleRequestsCode(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/@code", value);}
	public void setPackageBundleRequestsBookDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/@bookDate", value);}
	public void setPackageBundleRequestsStartDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/@startDate", value);}
	public void setPackageBundleRequestsEndDate(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/@endDate", value);}
	/*
	 * /Envelope/Body/updateBundle/packageBundleRequests/salesOrderItemGuests
	 */
	public void setPackageBundleRequestsSalesOrderItemGuestsGuestReferenceId(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/salesOrderItemGuests/@guestReferenceId", value);}
	public void setPackageBundleRequestsSalesOrderItemGuestsAgeType(String value){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/packageBundleRequests/salesOrderItemGuests/@ageType", value);}
	/*
	 * /Envelope/Body/updateBundle/guests
	 */
	public void setGuestsId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+index+"]/@id", value);}
	public void setGuestsGuestReferenceId(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+index+"]/@guestReferenceId", value);}
	private void addGuestNode(String indexOfNewGuest){
		setRequestNodeValueByXPath("/Envelope/Body/updateBundle", BaseSoapCommands.ADD_NODE.commandAppend("ser1:guests"));
		setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+indexOfNewGuest+"]", BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("id"));
		setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+indexOfNewGuest+"]", BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("guestReferenceId"));
	}
	/*
	 * /Envelope/Body/updateBundle/guests/guestName
	 */
	public void setGuestsGuestNameFirstName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+index+"]/guestName/@firstName", value);}
	public void setGuestsGuestNameLastName(String value, String index){setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+index+"]/guestName/@lastName", value);}
	private void addGuestNameNode(String indexOfNewGuest){
		setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+indexOfNewGuest+"]", BaseSoapCommands.ADD_NODE.commandAppend("gues:guestName"));
		setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+indexOfNewGuest+"]/guestName", BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("firstName"));
		setRequestNodeValueByXPath("/Envelope/Body/updateBundle/guests["+indexOfNewGuest+"]/guestName", BaseSoapCommands.ADD_ATTIRBUTE.commandAppend("lastName"));
	}
	
	public void addNewGuest(String indexOfNewGuest){
		addGuestNode(indexOfNewGuest);
		addGuestNameNode(indexOfNewGuest);
	}

	//*******************************************
	//*******************************************
	//*******************************************
	//		Response Methods
	//*******************************************
	//*******************************************
	//*******************************************
	public String getUpdateBundleResponseSalesOrderId(){return getResponseNodeValueByXPath("/Envelope/Body/updateBundleResponse/updateBundleResponse/@salesOrderId");}
	public String getUpdateBundleResponseTravelPlanId(){return getResponseNodeValueByXPath("/Envelope/Body/updateBundleResponse/updateBundleResponse/@travelPlanId");}
}
