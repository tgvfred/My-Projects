package com.disney.api.soapServices.directConnect.operations;

import com.disney.api.soapServices.directConnect.DirectConnect;
import com.disney.utils.XMLTools;

public class DestActivityResRQ extends DirectConnect{
	public DestActivityResRQ (String environment, String scenario) {
		super(environment);
		//Generate a request from a project xml file
		setRequestDocument(XMLTools.loadXML(buildRequestFromWSDL("DestActivityResRQ.java")));	
		
		setRequestNodeValueByXPath(getTestScenario(getService(), getOperation(), scenario));
		removeComments() ;
		removeWhiteSpace();
	}
	/**Payload Section **/
	public void setPayloadName(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/@Name", value);}
	public void setPayloadVersion(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/@Version", value);}
	public void setPayloadLocation(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/@Location",value);}
	public void setPayloadRequestId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/@RequestId", value);}
	public void setPayloadRequestorId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/@RequestorId", value);}
	public void setPayloadResponderId(String value){setRequestNodeValueByXPath("/Envelope/HeaderInterface/PayloadInfo/@ResponderId", value);}
	public void setPayloadDestinationId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/@DestinationId", value);}
	public void setPayloadRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/@RetryIndicator", value);}
	public void setPayloadSourceId(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/@SourceId", value);}
	public void setPayloadPassword(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/Authentication/@Password", value);}
	public void setPayloadUsername(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/CommDescriptor/Authentication/@Username", value);}
	public void setPayloadDescriptorName(String value){setRequestNodeValueByXPath("/Envelope/Header/Interface/PayloadInfo/PayloadDescriptor/@Name", value);}
	
	/**OTA Header information **/
	public void setVersion(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/@Version", value);}
	public void setTimeStamp(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/@TimeStamp", value);}
	public void setEchoToken(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/@EchoToken", value);}
	public void setTarget(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/@Target", value);}
	
	/**Unique Id Section **/
	public void setUniqueId(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/UniqueID/@ID", value);}
	public void setUniqueIdType(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/UniqueID/@Type", value);}
	
	/** Traveler Count **/
	public void setTravelerCode(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/TravelerCount/@Code", value);}
	public void setTravelerQuantity(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/TravelerCount/@Quantity", value);}
	
	/**
     * Gets the number of nodes for a given xpath in the SOAP Request
     * @param xpath - xpath for which to search
     * @return - number of node for a given xpath
     */
    public int getNumberOfRequestNodesByXPath(String xpath){return XMLTools.getNodeList(getRequestDocument(), xpath).getLength();}
	
	public void setTravelerDetails (String RPH, String BirthDate, String NamePrefix, String GivenName, String Surname, String CountryAccessCode, String PhoneNumber, String AreaCityCode, String PhoneTechType, String Extension, String Email, String Address1, String CityName, String PostalCode, String StateCode, String CountryCode){
		// Determine if the index exists. If not, create it and the necessary
        // child nodes. If so, then set the child node values
		int numberOfTravelerDetails= 1;
		try{
			numberOfTravelerDetails = getNumberOfRequestNodesByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler");
			getRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]");
		    numberOfTravelerDetails+=1;
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler", "fx:addNode;Node:Traveler");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:addNode:Node:@RPH");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/@BirthDate");		
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/PersonName/NamePrefix");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/PersonName/GivenName");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/PersonName/Surname");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Telephone/@CountryAccessCode");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Telephone/@PhoneNumber");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Telephone/@AreaCityCode");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Telephone/@PhoneTechType");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Telephone/@Extension");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Email");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Address/AddressLine");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Address/CityName");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Address/PostalCode");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Address/StateProv/@StateCode");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]","fx:AddNodes;Node:Profile/Customer/Address/CountryName/@Code");
		//Set Values to the nodes
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/@RPH",RPH);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/@BirthDate", BirthDate);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/PersonName/GivenName",GivenName);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/PersonName/Surname",Surname);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Telephone/@CountryAccessCode",CountryAccessCode);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Telephone/@PhoneNumber",PhoneNumber);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Telephone/@AreaCityCode",AreaCityCode);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Telephone/@PhoneTechType",PhoneTechType);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Telephone/@Extension",Extension);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Email",Email);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Address/AddressLine",Address1);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Address/CityName",CityName);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Address/PostalCode",PostalCode);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Address/StateProv/@StateCode",StateCode);
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler["+numberOfTravelerDetails+"]/Profile/Customer/Address/CountryName/@Code",CountryCode);
	}
	
	/** Contact **/
	public void setContactRhp(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Contact/@RPH", value);}
	
	// Item Array - tickets 
	public void setTicketDetails (String ItemCode, String OptionCode, String Quantity, String SelectedDate, String NegotiatedrRice, String AmountAfterTax, String CurrencyCode, String Type, String Id, String TravelerRPH){
		// Determine if the index exists. If not, create it and the necessary
		// child nodes. If so, then set the child node values
		int numberOfTicketDetails =1;
		try{
			numberOfTicketDetails = getNumberOfRequestNodesByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item");
			getRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/@ItemCode");
		}catch(Exception e){}
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNode;Node:@ItemCode");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNode;Node:@OptionCode");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNode;Node:@Quantity");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNode;Node:@SelectedDate");
		setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNodes;Node:NegotiatedPrice/@AmountAfterTax");
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNodes;Node:NegotiatedPrice/@CurrencyCode");
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNodes;Node:ItemReferences/ItemReference/@Type");
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNodes;Node:ItemReferences/ItemReference/@ID");
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]","fx:AddNodes;Node:SubAllocation/@TravelerRPH");
	
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/@ItemCode", ItemCode);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/@OptionCode", OptionCode);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/@Quantity", Quantity);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/@SelectedDate", SelectedDate);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/NegotiatedPrice/@AmountAfterTax", AmountAfterTax);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/NegotiatedPrice/@CurrencyCode", CurrencyCode);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/ItemReferences/ItemReference/@Type", Type);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/ItemReferences/ItemReference/@ID", Id);
	setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item["+numberOfTicketDetails+"]/SubAllocation/@TravelerRPH", TravelerRPH);
	}
	
	public String getRequestId(){
		return getResponseNodeValueByXPath("/Envelope/Header/Interface/Acknowledgement/@RequestId");
	}	
	

}
