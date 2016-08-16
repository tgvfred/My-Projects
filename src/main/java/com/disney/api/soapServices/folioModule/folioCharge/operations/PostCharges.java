package com.disney.api.soapServices.folioModule.folioCharge.operations;

import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.api.soapServices.folioModule.folioCharge.FolioChargeService;
import com.disney.utils.XMLTools;

public class PostCharges extends FolioChargeService{
	public PostCharges(String environment, String scenario){
		super(environment);
	
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("postCharges")));
		
	    generateServiceContext();			
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
	    removeComments() ;
	    removeWhiteSpace();
	}

	public void setAllAmounts(String amount, String currency){
		setAmount(amount, currency);
		setActualAmount(amount, currency);
		setChargedAmount(amount, currency);
		setDepositAmount(amount, currency);
	}

	public void setAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/amount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/amount/currency", currency);
	}
	
	public void setTravelComponentGroupId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeGroupExternalReference/referenceValue", value);
	}

	public void setActualAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/actualAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/actualAmount/currency", currency);
	}

	public void setChargedAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/chargedAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/chargedAmount/currency", currency);
	}

	public void setDepositAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositRequiredAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositRequiredAmount/currency", currency);
	}

	public void setDepositDueDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositDueDate", value);
	}
	
	public void setRevenueType(String id, String name){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/revenueType/revenueTypeID", id);
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/chargeItem/revenueType/revenueTypeName", name);
	}

	public void setDescription(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/description", value);
	}

	public void setFulfilmentDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/fulfilmentDateTime", value);
	}

	public void setPartyMixCount(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/partyMix/count", value);
	}

	public void setPostingDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/postingDate", value);
	}

	public void setCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/product/communicationChannel", value);
	}

	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/product/salesChannel", value);
	}

	public void setProduct(String id, String name){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/product/productID", id);
		try{
			setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/product/productTypeName", name);
		}catch(XPathNullNodeValueException e){}
	}

	public void setProductDescription(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/product/productDescription", value);
	}

	public void setRevenueClassification(String id, String name){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/revenueClassification/revenueClassificationID", id);
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/revenueClassification/name", name);
	}

	public void setRevenueStatus(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/revenueRecognitionStatus", value);
	}

	public void setTransactionAccountingCenter(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/transactionAccountingCenter", value);
	}

	public void setTransactionFacilityID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/transactionFacilityID", value);
	}

	public void setLocationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeDetail/location", value);
	}

	public void setTravelComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/postCharges/chgReqs/chargeExternalReferences/referenceValue", value);
	}
	
	public String getChargeId(){
		return getResponseNodeValueByXPath("/Envelope/Body/postChargesResponse/returnParameter/chargeID");
	}
}
