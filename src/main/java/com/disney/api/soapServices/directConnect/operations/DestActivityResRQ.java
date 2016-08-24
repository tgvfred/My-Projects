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
	
	/** Traveler Section -- Array
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/@RPH", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/@BirthDate", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/PersonName/NamePrefix", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/PersonName/GivenName", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/PersonName/Surname", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Telephone/@CountryAccessCode", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Telephone/@PhoneNumber", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Telephone/@AreaCityCode", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Telephone/@PhoneTechType", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Telephone/@Extension", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Email", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Address/AddressLine", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Address/CityName", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Address/PostalCode", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Address/StateProv/@StateCode", value);}
	 * public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Travelers/Traveler[1]/Profile/Customer/Address/CountryName/@Code", value);}
	 * **/
	
	/** Contact **/
	public void setContactRhp(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/Contact/@RPH", value);}
	
	/** Item Array - tickets 
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/@ItemCode", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/@OptionCode", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/@Quantity", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/@SelectedDate", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/NegotiatedPrice/@AmountAfterTax", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/NegotiatedPrice/@CurrencyCode", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/ItemReferences/ItemReference/@Type", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/ItemReferences/ItemReference/@ID", value);}
	* public void setPaylodRetryIndicator(String value){setRequestNodeValueByXPath("/Envelope/Body/OTA_DestActivityResRQ/DestActivityReservation/DestActivityItems/Item[1]/SubAllocation/@TravelerRPH", value);}
	**/

}
