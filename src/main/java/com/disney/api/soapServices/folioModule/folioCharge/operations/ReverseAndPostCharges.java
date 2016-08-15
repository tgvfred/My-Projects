package com.disney.api.soapServices.folioModule.folioCharge.operations;

import com.disney.api.soapServices.core.exceptions.XPathNullNodeValueException;
import com.disney.api.soapServices.folioModule.folioCharge.FolioChargeService;
import com.disney.utils.XMLTools;

public class ReverseAndPostCharges extends FolioChargeService{
	public ReverseAndPostCharges(String environment, String scenario){
		super(environment);
	
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reverseAndPostCharges")));
		
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
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/amount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/amount/currency", currency);
	}
	
	public void setTravelComponentGroupId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeGroupExternalReference/referenceValue", value);
	}

	public void setActualAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/actualAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/actualAmount/currency", currency);
	}

	public void setChargedAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/chargedAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/chargedAmount/currency", currency);
	}

	public void setDepositAmount(String amount, String currency){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositRequiredAmount/amount", amount);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositRequiredAmount/currency", currency);
	}

	public void setDepositDueDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/depositRequirement/depositDueDate", value);
	}
	
	public void setRevenueType(String id, String name){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/revenueType/revenueTypeID", id);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/chargeItem/revenueType/revenueTypeName", name);
	}

	public void setDescription(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/description", value);
	}

	public void setFulfilmentDateTime(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/fulfilmentDateTime", value);
	}

	public void setPartyMixCount(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/partyMix/count", value);
	}

	public void setPostingDate(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/postingDate", value);
	}

	public void setCommunicationChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/product/communicationChannel", value);
	}

	public void setSalesChannel(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/product/salesChannel", value);
	}

	public void setProduct(String id, String name){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/product/productID", id);
		try{
			setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/product/productTypeName", name);
		}catch(XPathNullNodeValueException e){}
	}

	public void setProductDescription(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/product/productDescription", value);
	}

	public void setRevenueClassification(String id, String name){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/revenueClassification/revenueClassificationID", id);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/revenueClassification/name", name);
	}

	public void setRevenueStatus(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/revenueRecognitionStatus", value);
	}

	public void setSourceAccountingCenterID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/sourceAccountingCenterID", value);
	}

	public void setTransactionAccountingCenterID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/transactionAccountingCenter", value);
	}

	public void setTransactionFacilityID(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/transactionFacilityID", value);
	}

	public void setLocationId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeDetail/location", value);
	}

	public void setTravelComponentId(String value){
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chargeExternalReferences/referenceValue", value);
		setRequestNodeValueByXPath("/Envelope/Body/reverseAndPostCharges/chgReqs/chargeExternalReferences/referenceValue", value);
	}
	
	public String getChargeId(){
		return getResponseNodeValueByXPath("/Envelope/Body/reverseAndPostChargesResponse/returnParameter/chargeID");
	}
}
