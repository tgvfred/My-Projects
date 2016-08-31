package com.disney.api.soapServices.pricingModule.pricingWebService.operations;

import java.util.List;
import java.util.ArrayList;

import com.disney.api.soapServices.core.exceptions.XPathNotFoundException;
import com.disney.api.soapServices.pricingModule.pricingWebService.PricingWebService;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents.Price.Unit;
import com.disney.api.soapServices.pricingModule.pricingWebService.operations.PriceComponents.Price.Unit.ChargeItem;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class PriceComponents extends PricingWebService {

	private List<Price> prices = new ArrayList<>();
	public PriceComponents(String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("priceComponents")));		

		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	
	public List<Price> prices(){
		return prices;
	}
	
	@Override
	public void sendRequest(){

		
		super.sendRequest();
		TestReporter.logAPI(!getResponseStatusCode().equals("200"), "Failed to get Pricing info: " +getFaultString(), this);
		int numPrices = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices").getLength();
		for(int intPrice = 1 ; intPrice <= numPrices ; intPrice++){
			Price price = new Price();
			price.setComponentProductId(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@componentProductID"));
			price.setProductTypeName(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@productTypeName"));
			price.setCommission(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@commission"));
			price.setProductDescription(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@productDescription"));
			price.setPrice(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@price"));
			price.setDiscount(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@discount"));
			price.setAdditionalCharges(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@additionalCharges"));
			price.setNet(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@net"));
			price.setTax(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/@tax"));
			price.setEndDate(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/travelPeriod/@endDate"));
			price.setStartDate(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/travelPeriod/@startDate"));
			price.setAge(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/partyMixes/@age"));
			price.setAgeType(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/partyMixes/@ageType"));
			price.setRevenueClassificationId(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/revenueClassification/@id"));
			price.setRevenueClassificationName(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/revenueClassification/@name"));
			price.setRevenueClassificationLevel(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/revenueClassification/@level"));
			int numUnits = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units").getLength();
			for(int intUnits = 1 ; intUnits <= numUnits ; intUnits++){
				Unit unit = price.getNewUnit();
				unit.setDate(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@date"));
				unit.setTaxIncluded(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@taxIncluded"));
				unit.setPrice(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@price"));
				unit.setDiscount(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@discount"));
				unit.setAdditionalCharges(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@additionalCharges"));
				unit.setNet(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@net"));
				unit.setTax(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/@tax"));
				
				int numChargeItems = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems").getLength();
				for(int intChargeItems = 1 ; intChargeItems <= numChargeItems ; intChargeItems++){
					ChargeItem charge = unit.getNewChargeItemt();
					
					charge.setType(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/@type"));
					charge.setProductPrice(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/@productPrice"));
					charge.setSellingPrice(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/@sellingPrice"));
					charge.setOverridePrice(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/@overridePrice"));
					charge.setRevenueLevel(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/revenueType/@level"));
					charge.setRevenueName(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/revenueType/@name"));
					try{
						charge.setAncestorLevel(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/revenueType/ancestor/@level"));
						charge.setAncestorName(getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/prices["+intPrice+"]/units["+intUnits+"]/chargeItems["+intChargeItems+"]/revenueType/ancestor/@name"));
					}catch(XPathNotFoundException e){}
					unit.addChargeItem(charge);
				}
				price.addNewUnit(unit);
			}
			prices.add(price);
		}
	}
	public void setUsageDate(String value){setRequestNodeValueByXPath("/Envelope/Body/priceComponents/priceRequests/priceRequests/@usageDate", value);}
	public void setComponentId(String value){setRequestNodeValueByXPath("/Envelope/Body/priceComponents/priceRequests/priceRequests/@componentID", value);}
	public void setAge(String value){setRequestNodeValueByXPath("/Envelope/Body/priceComponents/priceRequests/priceRequests/guestAges/@age", value);}
	public void setAgeType(String value){setRequestNodeValueByXPath("/Envelope/Body/priceComponents/priceRequests/priceRequests/guestAges/@ageType", value);}
	
	public String getPackageCommission(){return getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/@commission");}
	public String getPackagePrice(){return getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/@price");}
	public String getPackageDiscount(){return getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/@discount");}
	public String getPackageAdditionalCharges(){return getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/@additionalCharges");}
	public String getPackageNet(){return getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/@net");}
	public String getPackageTax(){return getResponseNodeValueByXPath("/Envelope/Body/priceComponentsResponse/CollectionOfPackagePriceVO/packagePrices/@tax");}
	

	//Values for ComponentPrices level node
	public class Price{
		private String componentProductId;
		private String productTypeName;
		private String commission;
		private String productDescription;
		private String price;
		private String discount;
		private String additionalCharges;
		private String net;
		private String tax;
		private String startDate;
		private String endDate;
		private String age;
		private String ageType;
		private String revenueClassificationId;
		private String revenueClassificationName;
		private String revenueClassificationLevel;
		private List<Unit> units  = new ArrayList<>();
		
		public List<Unit> getUnits(){	return units;}
		public void addNewUnit(Unit unit){ this.units.add(unit);}
		
		public String getComponentProductId() {	return componentProductId;}
		public void setComponentProductId(String componentProductId) {	this.componentProductId = componentProductId;}
		
		public String getProductTypeName() {return productTypeName;}
		public void setProductTypeName(String productTypeName) {this.productTypeName = productTypeName;}
		
		public String getCommission() {	return commission;}
		public void setCommission(String commission) {	this.commission = commission;}
		
		public String getProductDescription() {	return productDescription;}
		public void setProductDescription(String productDescription) {this.productDescription = productDescription;}
		
		public String getPrice() {return price;}
		public void setPrice(String price) {this.price = price;}
		
		public String getDiscount() {return discount;}
		public void setDiscount(String discount) {this.discount = discount;}
		
		public String getAdditionalCharges() {return additionalCharges;}
		public void setAdditionalCharges(String additionalCharges) {this.additionalCharges = additionalCharges;}
		
		public String getNet() {return net;}
		public void setNet(String net) {this.net = net;}
		
		public String getTax() {return tax;}
		public void setTax(String tax) {this.tax = tax;}
		
		public String getStartDate() {return startDate;}
		public void setStartDate(String startDate) {this.startDate = startDate;}
		
		public String getEndDate() {return endDate;}
		public void setEndDate(String endDate) {this.endDate = endDate;}
		
		public String getAge() {return age;}
		public void setAge(String age) {this.age = age;}
		
		public String getAgeType() {return ageType;}
		public void setAgeType(String ageType) {this.ageType = ageType;}

		public String getRevenueClassificationId() {return revenueClassificationId;}
		public void setRevenueClassificationId(String revenueClassificationId) {this.revenueClassificationId = revenueClassificationId;}
		
		public String getRevenueClassificationName() {return revenueClassificationName;}
		public void setRevenueClassificationName(String revenueClassificationName) {this.revenueClassificationName = revenueClassificationName;}
		
		public String getRevenueClassificationLevel() {	return revenueClassificationLevel;}
		public void setRevenueClassificationLevel(String revenueClassificationLevel) {this.revenueClassificationLevel = revenueClassificationLevel;}
		
		public Unit getNewUnit(){return new Unit();}
		
		public class Unit{
			private String date;
			private String taxIncluded;
			private String price;
			private String discount;
			private String additionalCharges;
			private String net;
			private String tax;
			private List<ChargeItem> chargeItem  = new ArrayList<>();
		
			public String getDate() {return date.replace("-04:00", "T00:00:00");}
			public void setDate(String date) {this.date = date;}
			
			public String getTaxIncluded() {return taxIncluded;}
			public void setTaxIncluded(String taxIncluded) {this.taxIncluded = taxIncluded;}
			
			public String getPrice() {return price;}
			public void setPrice(String price) {this.price = price;}
			
			public String getDiscount() {return discount;}
			public void setDiscount(String discount) {this.discount = discount;}
			
			public String getAdditionalCharges() {return additionalCharges;}
			public void setAdditionalCharges(String additionalCharges) {this.additionalCharges = additionalCharges;}
			
			public String getNet() {return net;}
			public void setNet(String net) {this.net = net;	}
			
			public String getTax() {return tax;}
			public void setTax(String tax) {this.tax = tax;}
			
			public List<ChargeItem> getChargeItems() {return chargeItem;	}
			public void addChargeItem(ChargeItem chargeItem) {this.chargeItem.add( chargeItem);}

			public ChargeItem getNewChargeItemt(){return new ChargeItem();}
			
			public class ChargeItem{
				private String type;
				private String productPrice;
				private String sellingPrice;
				private String overridePrice;
				private String revenueLevel;
				private String revenueName;
				private String ancestorLevel;
				private String ancestorName;
				
				public String getType() {return type;}
				public void setType(String type) {this.type = type;}
				public String getProductPrice() {return productPrice;}		
				public void setProductPrice(String productPrice) {this.productPrice = productPrice;	}
				public String getSellingPrice() {	return sellingPrice;}
				public void setSellingPrice(String sellingPrice) {this.sellingPrice = sellingPrice;	}
				public String getOverridePrice() {return overridePrice;	}
				public void setOverridePrice(String overridePrice) {this.overridePrice = overridePrice;	}
				public String getRevenueLevel() {return revenueLevel;}
				public void setRevenueLevel(String revenueLevel) {this.revenueLevel = revenueLevel;	}
				public String getRevenueName() {return revenueName;}
				public void setRevenueName(String revenueName) {this.revenueName = revenueName;}
				public String getAncestorLevel() {	return ancestorLevel;}
				public void setAncestorLevel(String ancestorLevel) {this.ancestorLevel = ancestorLevel;	}
				public String getAncestorName() {	return ancestorName;}
				public void setAncestorName(String ancestorName) {this.ancestorName = ancestorName;	}
			}
		}
	}
	
	
	
}
