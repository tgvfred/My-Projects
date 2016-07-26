package com.disney.api.soapServices.builtInventoryService.operations;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.Test;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.disney.api.soapServices.builtInventoryService.BuiltInventoryService;
import com.disney.utils.TestReporter;
import com.disney.utils.XMLTools;

public class ReservableResourceByFacilityID extends BuiltInventoryService{
	ReservableResource firstReservableResource;
	String firstReservableResourceId;
	
	public ReservableResourceByFacilityID(String environment, String scenario) {
		super(environment);

		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("reservableResourceByFacilityID")));
	
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}

	public void setFacilityId(String value){setRequestNodeValueByXPath("/Envelope/Body/facilityId", value);}
	public Map<String, ReservableResource> getReservableResources(){
		Map<String, ReservableResource> resources = new HashMap<String, ReservableResource>();
		NodeList reservableResourceArrays = XMLTools.getNodeList(getResponseDocument(), "/Envelope/Body/reservableResourceByFacilityIDResponse/reservableResourceArray");
		
		for(int node = 0; node < reservableResourceArrays.getLength() - 1; node++){
			resources.put("resource" + String.valueOf(node), new ReservableResource(reservableResourceArrays.item(node)));
		}
		firstReservableResource  = resources.get("resource0");
		firstReservableResourceId = firstReservableResource.getReservableResourceId();
		return resources;
	}
	
	public ReservableResource getFirstReservableResource(){return firstReservableResource;}
	public String getFirstReservableResourceId(){
		if(firstReservableResourceId == null) getReservableResources();
		return firstReservableResourceId;
	}
	
	class ReservableResource{
		String effStartDateTime;
		String facilityId;
		String reservableResourceId;
		String reservableResourceType;
		String resourceType;
		String unitLength;
		String unitsCheckedAfterTarget;
		String unitsCheckedBeforeTarget;
		FeatureArray features;
		ProductArray products;
		ResourceCapacity capacity;
		QuantityArray quantity;
		
		public ReservableResource(Node resource){
			NamedNodeMap attributes = resource.getAttributes();
			for(int attribute = 0; attribute < attributes.getLength(); attribute++){
				switch (attributes.item(attribute).getNodeName()) {
				case "effStartDateTime":
					effStartDateTime = attributes.item(attribute).getNodeValue();
					break;
				case "facilityId":
					facilityId = attributes.item(attribute).getNodeValue();
					break;
				case "reservableResourceId":
					reservableResourceId = attributes.item(attribute).getNodeValue();
					break;
				case "reservableResourceType":
					reservableResourceType = attributes.item(attribute).getNodeValue();
					break;
				case "resourceType":
					resourceType = attributes.item(attribute).getNodeValue();
					break;
				case "unitLength":
					unitLength = attributes.item(attribute).getNodeValue();
					break;
				case "unitsCheckedAfterTarget":
					unitsCheckedAfterTarget = attributes.item(attribute).getNodeValue();
					break;
				case "unitsCheckedBeforeTarget":
					unitsCheckedBeforeTarget = attributes.item(attribute).getNodeValue();
					break;
				default:
					break;
				}
			}
			
			features = new FeatureArray(XMLTools.getNodeList(resource, "featureArray"));
			try{products = new ProductArray(XMLTools.getNodeList(resource, "productArray"));}catch(RuntimeException e){}
			capacity = new ResourceCapacity(XMLTools.getNode(resource, "resourceCapacity"));
			quantity = new QuantityArray(XMLTools.getNode(resource, "quantityArray"));
		}
		
		public String getEffStartDate(){return effStartDateTime;}
		public String getFacilityId(){return facilityId;}
		public String getReservableResourceId(){return reservableResourceId;}
		public String getReservableResourceType(){return reservableResourceType;}
		public String getResourceType(){return resourceType;}
		public String getUnitLenght(){return unitLength;}
		public String getUnitsCheckedAfterTarget(){return unitsCheckedAfterTarget;}
		public String getUnitsCheckedBeforeTarget(){return unitsCheckedBeforeTarget;}
		public Map<String, String> getFeatures(){return features.getFeatures();}
		public Map<String, String> getProducts(){return products.getProducts();}
		public String getResourceCapacityEffStartDate(){return capacity.getEffStartDate();}
		public String getResourceCapacityMaxCapacity(){return capacity.getMaxCapacity();}
		public String getResourceCapacityMinCapacity(){return capacity.getMinCapacity();}
		public String getQuantityArrayEffStartDate(){return quantity.getEffStartDate();}
		public String getQuantityArrayQuantity(){return quantity.getQuantity();}
		
		class FeatureArray{
			Map<String, String> features = new HashMap<String, String>();
			public FeatureArray(NodeList nodes){
				for(int node = 0; node < nodes.getLength(); node++){
					features.put("feature"+String.valueOf(node), nodes.item(node).getTextContent());
				}
			}
			
			public Map<String, String> getFeatures(){return features;}
		}
		
		class ProductArray{
			Map<String, String> products = new HashMap<String, String>();
			public ProductArray(NodeList nodes){
				for(int node = 0; node < nodes.getLength(); node++){
					products.put("product"+String.valueOf(node), nodes.item(node).getTextContent());
				}
			}
			
			public Map<String, String> getProducts(){return products;}
		}
		
		class ResourceCapacity{
			String effStartDate;
			String maxCapacity;
			String minCapacity;
			public ResourceCapacity(Node capacity){
				NamedNodeMap attributes = capacity.getAttributes();
				for(int attribute = 0; attribute < attributes.getLength(); attribute++){
					switch (attributes.item(attribute).getNodeName()) {
					case "effStartDate":
						effStartDate = attributes.item(attribute).getNodeValue();
						break;
					case "maxCapacity":
						maxCapacity = attributes.item(attribute).getNodeValue();
						break;
					case "minCapacity":
						minCapacity = attributes.item(attribute).getNodeValue();
						break;
					default:
						break;
					}
				}
			}
			
			public String getEffStartDate(){return effStartDate;}
			public String getMaxCapacity(){return maxCapacity;}
			public String getMinCapacity(){return minCapacity;}
		}
		
		class QuantityArray{
			String effStartDate;
			String quantity;
			public QuantityArray(Node quantityArray){
				NamedNodeMap attributes = quantityArray.getAttributes();
				for(int attribute = 0; attribute < attributes.getLength(); attribute++){
					switch (attributes.item(attribute).getNodeName()) {
					case "effStartDate":
						effStartDate = attributes.item(attribute).getNodeValue();
						break;
					case "quantity":
						quantity = attributes.item(attribute).getNodeValue();
						break;
					default:
						break;
					}
				}
			}
			
			public String getEffStartDate(){return effStartDate;}
			public String getQuantity(){return quantity;}
		}
	}	
	
	@Test
	public static void test(){
		ReservableResourceByFacilityID res = new ReservableResourceByFacilityID("Sleepy", "Main");
		res.setFacilityId("90002032");
		res.sendRequest();
		TestReporter.assertEquals(res.getResponseStatusCode(), "200", "");
		Map<String, ReservableResourceByFacilityID.ReservableResource> resources = res.getReservableResources();
		for(Entry<String, ReservableResourceByFacilityID.ReservableResource> entry : resources.entrySet()){
			System.out.println("Effective STart Date: " + resources.get(entry.getKey()).getEffStartDate());
			System.out.println("Facility ID: " + resources.get(entry.getKey()).getFacilityId());
			
			for(Entry<String, String> entry2 : resources.get(entry.getKey()).getFeatures().entrySet()){
				System.out.println(entry2.getKey() + ": " +entry2.getValue());
			}
			for(Entry<String, String> entry2 : resources.get(entry.getKey()).getProducts().entrySet()){
				System.out.println(entry2.getKey() + ": " +entry2.getValue());
			}
			
			System.out.println("Quantity Array Effective Start Date: " + resources.get(entry.getKey()).getQuantityArrayEffStartDate());
			System.out.println("Quantity Array Quantity: " + resources.get(entry.getKey()).getQuantityArrayQuantity());
			System.out.println("Reservable Resource Id: " + resources.get(entry.getKey()).getReservableResourceId());
			System.out.println("Reservable Resource Type: " + resources.get(entry.getKey()).getReservableResourceType());
			System.out.println("Resource Capacity Effective Start Date: " + resources.get(entry.getKey()).getResourceCapacityEffStartDate());
			System.out.println("Resource Capacity Max Capacity: " + resources.get(entry.getKey()).getResourceCapacityMaxCapacity());
			System.out.println("Resource Capacity Min Capacity: " + resources.get(entry.getKey()).getResourceCapacityMinCapacity());			
			System.out.println("Resource Type: " + resources.get(entry.getKey()).getResourceType());
			System.out.println("Unit Lenght: " + resources.get(entry.getKey()).getUnitLenght());
			System.out.println("Units Checked After Target: " + resources.get(entry.getKey()).getUnitsCheckedAfterTarget());
			System.out.println("Units Checked Before Target: " + resources.get(entry.getKey()).getUnitsCheckedBeforeTarget());
		}
	}
}
