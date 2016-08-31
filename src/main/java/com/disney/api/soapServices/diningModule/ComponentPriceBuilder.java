package com.disney.api.soapServices.diningModule;

import com.disney.AutomationException;
import com.disney.api.soapServices.core.BaseSoapCommands;
import com.disney.api.soapServices.core.BaseSoapService;
import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents.Price;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents.Price.Unit;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents.Price.Unit.ChargeItem;
import com.disney.utils.XMLTools;

import org.w3c.dom.Document;

public class ComponentPriceBuilder {
	public final static String ACTIVITY = "activity";
	public final static String EVENT = "event";
	public final static String SHOW = "show";
	public final static String TABLE = "table";
	
	private String diningRequest;
	private String diningPackage;
	private String baseXPath;
	public Document buildComponentPrices(final BaseSoapService bs, final String module, final String operation, final PriceComponents priceComponents){
		switch (module.toLowerCase()) {
			case "activity":
				diningRequest =  operation+"ActivityComponentRequest";
				diningPackage = "activity";
			break;
			case "event":
				diningRequest = operation+"EventDiningRequest";
				diningPackage= "eventDiningPackage";
			break;
			case "show":
				diningRequest = operation+"ShowDiningRequest";
				diningPackage = "dinnerShowPackage";
			break;
			case "table":
				diningRequest = "";
				diningPackage = "";
			break;

			default:
				throw new AutomationException("Module entered ["+module+"] is not [Activity], [Event], [Show], [Table]");
		}
		
		baseXPath = "/Envelope/Body/"+operation+"/"+diningRequest+"/"+diningPackage;
		int existingComponentPrices = 1;
		
		try{
			if (XMLTools.getNodeList(bs.getRequestDocument(), baseXPath + "/componentPrices").getLength() >= 1){
				existingComponentPrices += XMLTools.getNodeList(bs.getRequestDocument(), baseXPath + "/componentPrices").getLength() ;
			}
		}catch(XPathNotFoundException e){}
		
		String componentXPath; 
		String unitXPath;
		String chargePriceXPath="";
		int intComponentPrice = existingComponentPrices; 
		int intUnit = 1;
		int intBaseChargeItem= 0;
		int intTaxChargeItem= 0;
		int intDepositChargeItem= 0;
		for(Price priceComponent : priceComponents.prices()){
			intUnit=1;
			intBaseChargeItem=0;
			intTaxChargeItem=0;
			intDepositChargeItem= 0;
			componentXPath = baseXPath + "/componentPrices["+ String.valueOf(intComponentPrice)+"]";
			bs.setRequestNodeValueByXPath(baseXPath, BaseSoapCommands.ADD_NODE.commandAppend("componentPrices").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("additionalCharges").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("commission").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("discount").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("net").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("price").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("componentType").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("componentId").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("pricingPartyMix").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("revenueClassification").toString());
			bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("tax").toString());
			bs.setRequestNodeValueByXPath(componentXPath + "/additionalCharges", priceComponent.getAdditionalCharges());
			bs.setRequestNodeValueByXPath(componentXPath + "/commission", priceComponent.getCommission());
			bs.setRequestNodeValueByXPath(componentXPath + "/discount", priceComponent.getDiscount());
			bs.setRequestNodeValueByXPath(componentXPath + "/net", priceComponent.getNet());
			bs.setRequestNodeValueByXPath(componentXPath + "/price", priceComponent.getPrice());
			bs.setRequestNodeValueByXPath(componentXPath + "/componentType", priceComponent.getProductTypeName());
			bs.setRequestNodeValueByXPath(componentXPath + "/componentId", priceComponent.getComponentProductId());
			bs.setRequestNodeValueByXPath(componentXPath + "/tax", priceComponent.getTax());
			bs.setRequestNodeValueByXPath(componentXPath + "/pricingPartyMix", BaseSoapCommands.ADD_NODE.commandAppend("age").toString());
			bs.setRequestNodeValueByXPath(componentXPath + "/pricingPartyMix", BaseSoapCommands.ADD_NODE.commandAppend("ageType").toString());
			bs.setRequestNodeValueByXPath(componentXPath + "/pricingPartyMix/age", priceComponent.getAge());
			bs.setRequestNodeValueByXPath(componentXPath + "/pricingPartyMix/ageType", priceComponent.getAgeType());
			bs.setRequestNodeValueByXPath(componentXPath + "/revenueClassification", BaseSoapCommands.ADD_NODE.commandAppend("id").toString());
			bs.setRequestNodeValueByXPath(componentXPath + "/revenueClassification", BaseSoapCommands.ADD_NODE.commandAppend("level").toString());
			bs.setRequestNodeValueByXPath(componentXPath + "/revenueClassification", BaseSoapCommands.ADD_NODE.commandAppend("name").toString());
			bs.setRequestNodeValueByXPath(componentXPath + "/revenueClassification/id", priceComponent.getRevenueClassificationId());
			bs.setRequestNodeValueByXPath(componentXPath + "/revenueClassification/level", priceComponent.getRevenueClassificationLevel());
			bs.setRequestNodeValueByXPath(componentXPath + "/revenueClassification/name", priceComponent.getRevenueClassificationName());
			
			for(Unit unit : priceComponent.getUnits()){
				bs.setRequestNodeValueByXPath(componentXPath, BaseSoapCommands.ADD_NODE.commandAppend("unitPrices").toString());
				unitXPath = componentXPath + "/unitPrices["+ String.valueOf(intUnit)+"]";
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("additionalCharges").toString());
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("date").toString());
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("discount").toString());
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("net").toString());
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("price").toString());
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("tax").toString());
				bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("taxIncluded").toString());
				bs.setRequestNodeValueByXPath(unitXPath + "/additionalCharges", unit.getAdditionalCharges());
				bs.setRequestNodeValueByXPath(unitXPath + "/date", unit.getDate());
				bs.setRequestNodeValueByXPath(unitXPath + "/discount", unit.getDiscount());
				bs.setRequestNodeValueByXPath(unitXPath + "/net", unit.getNet());
				bs.setRequestNodeValueByXPath(unitXPath + "/price", unit.getPrice());
				bs.setRequestNodeValueByXPath(unitXPath + "/tax", unit.getTax());
				bs.setRequestNodeValueByXPath(unitXPath + "/taxIncluded", unit.getTaxIncluded());
				
				for(ChargeItem chargeItem : unit.getChargeItems()){
					if(chargeItem.getType().contains("BaseChargeItem")) {
						bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("baseCharge").toString());
						intBaseChargeItem++;
						chargePriceXPath = unitXPath + "/baseCharge["+ String.valueOf(intBaseChargeItem)+"]";	
						
					}else if(chargeItem.getType().contains("TaxChargeItem")) {
						bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("taxes").toString());
						intTaxChargeItem++;
						chargePriceXPath = unitXPath + "/taxes["+ String.valueOf(intTaxChargeItem)+"]";	
					}else if(chargeItem.getType().contains("DepositChargeItem")) {
						bs.setRequestNodeValueByXPath(unitXPath, BaseSoapCommands.ADD_NODE.commandAppend("deposit").toString());
						intDepositChargeItem++;
						chargePriceXPath = unitXPath + "/deposit["+ String.valueOf(intDepositChargeItem)+"]";	
						bs.setRequestNodeValueByXPath(chargePriceXPath, BaseSoapCommands.ADD_NODE.commandAppend("dueDate").toString());
						bs.setRequestNodeValueByXPath(chargePriceXPath + "/dueDate", unit.getDate());
					}else{
						throw new AutomationException("Unhandled Charge Item Type. ["+chargeItem.getType()+"]");
					}
					bs.setRequestNodeValueByXPath(chargePriceXPath, BaseSoapCommands.ADD_NODE.commandAppend("revenueType").toString());
					bs.setRequestNodeValueByXPath(chargePriceXPath, BaseSoapCommands.ADD_NODE.commandAppend("overridePrice").toString());
					bs.setRequestNodeValueByXPath(chargePriceXPath, BaseSoapCommands.ADD_NODE.commandAppend("productPrice").toString());
					bs.setRequestNodeValueByXPath(chargePriceXPath, BaseSoapCommands.ADD_NODE.commandAppend("sellingPrice").toString());
					bs.setRequestNodeValueByXPath(chargePriceXPath+ "/revenueType", BaseSoapCommands.ADD_NODE.commandAppend("level").toString());
					bs.setRequestNodeValueByXPath(chargePriceXPath+ "/revenueType", BaseSoapCommands.ADD_NODE.commandAppend("name").toString());
					bs.setRequestNodeValueByXPath(chargePriceXPath + "/overridePrice", chargeItem.getOverridePrice());
					bs.setRequestNodeValueByXPath(chargePriceXPath + "/productPrice", chargeItem.getProductPrice());
					bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType/level", chargeItem.getRevenueLevel());
					bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType/name", chargeItem.getRevenueName());
					bs.setRequestNodeValueByXPath(chargePriceXPath + "/sellingPrice", chargeItem.getSellingPrice());
					if(chargeItem.getAncestorLevel() != null) {

						bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType", BaseSoapCommands.ADD_NODE.commandAppend("ancestor").toString());
						bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType/ancestor", BaseSoapCommands.ADD_NODE.commandAppend("level").toString());
						bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType/ancestor", BaseSoapCommands.ADD_NODE.commandAppend("name").toString());
						bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType/ancestor/level", chargeItem.getAncestorLevel()); 
						bs.setRequestNodeValueByXPath(chargePriceXPath + "/revenueType/ancestor/name", chargeItem.getAncestorName());
					}
				
				}
				intUnit++;
			}
			intComponentPrice++;
		}
		
		return XMLTools.makeXMLDocument(bs.getRequest());
	}
}
